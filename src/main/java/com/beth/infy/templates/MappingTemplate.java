package com.beth.infy.templates;

import com.beth.infy.util.CommonConstants;
import jlibs.xml.sax.XMLDocument;
import jlibs.xml.xsd.XSInstance;
import jlibs.xml.xsd.XSParser;
import org.joda.time.LocalDateTime;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class MappingTemplate implements IMappingTemplate {


    @Override
    public void generateXmlDocument(String schemaFilename) {

            //TODO - the xmlFileName string should be part of method parameter
            String xmlFileName = "/home/ranga/sandbox/springboot/psac009/1/output/PSAC20022.xml";
            try {
                final Document doc = loadXsdDocument(schemaFilename);

                if (doc == null) {
                    //TODO - "throw error"
                    System.out.println("XSD Document is null  ");
                }

                //Find the docs root element and use it to find the targetNamespace
                final Element rootElem = doc.getDocumentElement();
                String targetNamespace = null;
                if (rootElem != null && rootElem.getNodeName().equals("xs:schema"))
                {
                    targetNamespace = rootElem.getAttribute("targetNamespace");
                }

                if (null == targetNamespace) {
                    targetNamespace = "http://jlibs.org";
                }

                //Parse the file into an XSModel object

                org.apache.xerces.xs.XSModel xsModel = new XSParser().parse(schemaFilename);

                //Define defaults for the XML generation
                XSInstance instance = new XSInstance();
                instance.minimumElementsGenerated = 0;
                instance.maximumElementsGenerated = 0;
                instance.generateDefaultAttributes = false;
                instance.generateOptionalAttributes = false;
                instance.maximumRecursionDepth = 0;
                // instance.generateAllChoices = true;
                //instance.showContentModel = true;
                instance.generateOptionalElements = true;


                //Build the sample xml doc
                //Replace first param to XMLDoc with a file input stream to write to file
                QName rootElement = new QName(targetNamespace, "Document");
                XMLDocument xmlDocument = new XMLDocument(new StreamResult( xmlFileName), false, 4, null);
                xmlDocument.getNamespaceSupport().setSuggestPrefix("");
                instance.generate(xsModel, rootElement, xmlDocument);
                System.out.println("Xml generated successfully in location - " + xmlFileName);
            } catch (TransformerConfigurationException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        public  Document loadXsdDocument(String inputName) {
            final String filename = inputName;

            final DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            factory.setValidating(false);
            factory.setIgnoringElementContentWhitespace(true);
            factory.setIgnoringComments(true);
            Document doc = null;

            try {
                final DocumentBuilder builder = factory.newDocumentBuilder();
                final File inputFile = new File(filename);

                if (!(inputFile.isFile() && inputFile.getName().contains("xsd"))) {
                    return null;
                }
                doc = builder.parse(inputFile);
            } catch (final Exception e) {
                e.printStackTrace();
                // throw new ContentLoadException(msg);
            }

            return doc;
        }


    @Override
    public boolean verifyTemplateMappingExists(String templateName) {
        return true;
    }

    @Override
    public void modifyXml_01(String xmlFile) throws Exception {

        Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().parse(new InputSource(xmlFile));

        Node nodeElement = doc.getElementsByTagName("FICdtTrf").item(0);
        NodeList childList = nodeElement.getChildNodes();

        modifyNodeIfExists(doc);
        // save the result
        Transformer xformer = TransformerFactory.newInstance().newTransformer();
       // String modifiedxmlFile = "/home/ranga/sandbox/springboot/psac009/1/output/PSAC20022.xml";
        xformer.transform
                (new DOMSource(doc), new StreamResult(new File(xmlFile)));
        System.out.println("xml data modification completed...");

    }


    @Override
    public void populateXmlData_01(String xmlFile) throws Exception {

        //TODO the MT202.txt file should be part of parameter.
        String mt202FilePath = "/home/ranga/sandbox/springboot/psac009/1/input/MT202.txt";
        String templateName = "Mt202_Psac2022";
        String templateType = "psac2022";
        String clientId = "1";

        if (!fileExists(mt202FilePath) ) {
                System.err.println("File - " + mt202FilePath + " doesn't exist");
                System.exit(1);
        }

      /*  if (!fileExists(xmlFile) ) {
            System.err.println("File - " + xmlFile + " doesn't exist");
            System.exit(1);
        }

        if (StringUtils.isEmpty(templateName)) {
            System.err.println("Template Name is required and cannot be empty");
            System.exit(1);
        }

        if (StringUtils.isEmpty(templateType)) {
            System.err.println("Template Type is required and cannot be empty");
            System.exit(1);
        }

        if (StringUtils.isEmpty(client)) {
            System.err.println("Client name is required and cannot be empty");
            System.exit(1);
        } */
        Document doc = null;
        Node nodeElement = null;
        NodeList childList = null;

        if (templateType.equals(CommonConstants.TEMPLATE_TYPE_PSAC2022)) {
            doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().parse(new InputSource(xmlFile));
             nodeElement = doc.getElementsByTagName("FICdtTrf").item(0);
             childList = nodeElement.getChildNodes();
        }

        if (doc == null) {
            System.err.println("Template Name - " + templateName + " with type - " + templateType + " not supported.");
            System.exit(1);
        }

        populateNodeIfExists(doc, mt202FilePath);
        // save the result
        Transformer xformer = TransformerFactory.newInstance().newTransformer();
        String populateXmlFile = "/home/ranga/sandbox/springboot/psac009/1/output/PSAC20022.xml";
        xformer.transform
                (new DOMSource(doc), new StreamResult(new File(populateXmlFile)));
        System.out.println("xml data population completed...");

    }

    private  void modifyNodeIfExists(Document doc) throws Exception {

        // locate the node(s)
        XPath xpath = XPathFactory.newInstance().newXPath();


       // Map<String, String> me = convertMappingTemplateListToArray(loadFileContents(CommonConstants.PS009_TEMPLATE_MAPPING_LOCATION+"TemplateMappingFields.txt"));


       /* ClientOrm clientOrm = clientService.getClient(1);
        System.out.println("Client Orm loaded");
        TemplateOrm templateOrm = templateService.getTemplate(2);
        System.out.println("Template Orm loaded"); */
        Map<String, String> me = convertMappingTemplateListToArray(loadFileContents(CommonConstants.PS009_TEMPLATE_MAPPING_LOCATION+"TemplateMappingFields.txt"));
      //  Map<String, String> me = convertMappingTemplateListToArray(loadFileContents(templateMappingService.getTemplateMappingUsing(templateOrm).getMappingData()));
       // Map<String, String> mev = convertMappingTemplateListToArray(loadFileContents(CommonConstants.PS009_TEMPLATE_MAPPING_LOCATION+"TemplateMappingValues.txt"));
      //  System.out.println("Mapping Contents - " + me.toString());
        Iterator it = me.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry mappingElement = (Map.Entry) it.next();
            String elementKey = (String) mappingElement.getKey();
            String elementValue = (String) mappingElement.getValue();
            NodeList nodes = (NodeList)xpath.evaluate
                    (elementValue, doc, XPathConstants.NODESET);
            if (nodes.getLength() == 0) {
               // nodes.item(0).setTextContent("");
                Node nodeElement = nodes.item(0);
                deleteNodeIfNotInMapping(doc, nodeElement);
            }
            else {
               // nodes.item(0).setTextContent(mev.get(elementKey));
                nodes.item(0).setTextContent("");
            }
        }
    }

    private  void populateNodeIfExists(Document doc, String mt202FilePath) throws Exception {

        // locate the node(s)
        XPath xpath = XPathFactory.newInstance().newXPath();
     //   ClientOrm clientOrm = clientService.getClient(1);
      //  TemplateOrm templateOrm = templateService.getTemplate(2);
        Map<String, String> me = convertMappingTemplateListToArray(loadFileContents(CommonConstants.PS009_TEMPLATE_MAPPING_LOCATION+"TemplateMappingFields.txt"));
     //   Map<String, String> me = convertMappingTemplateListToArray(loadFileContents(templateMappingService.getTemplateMappingUsing(templateOrm).getMappingData()));

        // Map<String, String> mev = convertMappingTemplateListToArray(loadFileContents(CommonConstants.PS009_TEMPLATE_MAPPING_LOCATION+"TemplateMappingValues.txt"));
        Map<String, String> mev = getMappingElementValues(mt202FilePath);
        Iterator it = me.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry mappingElement = (Map.Entry) it.next();
            String elementKey = (String) mappingElement.getKey();
            String elementValue = (String) mappingElement.getValue();
            NodeList nodes = (NodeList)xpath.evaluate
                    (elementValue, doc, XPathConstants.NODESET);
            if (nodes.getLength() == 0) {
                 nodes.item(0).setTextContent("");
            }
            else {
                 nodes.item(0).setTextContent(mev.get(elementKey));
            }
        }
    }


    private String getCurrentDateTime() {

        LocalDateTime currentDateTime = LocalDateTime.now();
        return currentDateTime.toString();
    }

    public void deleteNodeIfNotInMapping(Document doc, Node nodeElement) throws Exception {
        NodeList nodeList = nodeElement.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            nodeElement.removeChild(node);
        }
    }


    public  String loadFileContents(String fileName) {
        try {
            return new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private Map convertMappingTemplateListToArray(String mappingTemplates)  {
        Map<String, String> templateMapping = new HashMap<>();
        String str[] = mappingTemplates.split(",");
        List<String> splitList = new ArrayList<String>();
        splitList = Arrays.asList(str);
        for(String each: splitList) {
            String str1[] = each.split(":");
            templateMapping.put(str1[0], str1[1]);
        }
        return templateMapping;
    }

    public  Map<String, String> getMappingElementValues(String mt202File) {
       /*Map<String, String> templateMapping = new HashMap<>();
       templateMapping.put("121", "0234cd54-dcf7-4361-b87f-6c0ddbaab3a2");
        templateMapping.put("20", "AT78096594500102");
        templateMapping.put("21", "FT39BT003401");
        templateMapping.put("52A", "BANKUS33XXX");
        templateMapping.put("56A", "//SC730018");
        templateMapping.put("56A.1", "PARBGB2LLON");
        templateMapping.put("57A", "INSECHZZXXX");
        templateMapping.put("58A", "/GB16RBOS16043110339295");
        templateMapping.put("58A.1", "BANKCHZZXXX"); */

        BufferedReader reader;
        Map<String, String> tagMap = new HashMap<>();
        try {
            reader = new BufferedReader(new FileReader(mt202File));
            String line = reader.readLine();

            while (line != null) {
              //  System.out.println(line);
                List<String> tagItems = getTagItems();
                for(String tag: tagItems) {
                    if (line.startsWith(tag)) {
                        String splitArray[]= line.split(":");
                       // System.out.println(splitArray.toString());
                        if (splitArray.length == 3) {
                            if ((":"+splitArray[1]).equals(tag) ) {
                                tagMap.put(splitArray[1],splitArray[2]);
                                break;
                            }
                        }
                    }
                }

                // read next line
                line = reader.readLine();
            }
            reader.close();
          //  System.out.println(tagMap.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tagMap;
    }

  /*  private List<String> getTagItems() {
        List<Mt202Orm> mt202OrmList = mt202Service.getAllActiveMt202Items(1);
        List<String> results = new ArrayList<>();
        for (Mt202Orm each : mt202OrmList) {
            results.add(each.getTag());
        }
        return results;
    } */

    private  List<String> getTagItems() {
        List <String> aList = new ArrayList<>();
        aList.add(":20");
        aList.add(":21");
        aList.add(":32A");
        aList.add(":52A");
        aList.add(":56A");
        aList.add(":57A");
        aList.add(":58A");
        return aList;
    }

    public boolean fileExists(String templateName) {
        File tmpDir = new File(templateName);
        return(tmpDir.exists());
    }

}