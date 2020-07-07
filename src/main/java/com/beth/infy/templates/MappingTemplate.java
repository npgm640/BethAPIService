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
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class MappingTemplate implements IMappingTemplate {


    @Override
    public void generateXmlDocument(String schemaFilename) {
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
                XMLDocument xmlDocument = new XMLDocument(new StreamResult( "/home/ranga/sandbox/springboot/psac009/1/output/PSAC20022.xml"), false, 4, null);
                xmlDocument.getNamespaceSupport().setSuggestPrefix("");
                instance.generate(xsModel, rootElement, xmlDocument);
                System.out.println("Xml generation completed");
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
        String modifiedxmlFile = "/home/ranga/sandbox/springboot/psac009/1/output/PSAC20022.xml";
        xformer.transform
                (new DOMSource(doc), new StreamResult(new File(modifiedxmlFile)));
        System.out.println("xml data modification completed...");

    }


    @Override
    public void populateXmlData_01(String xmlFile) throws Exception {

        Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().parse(new InputSource(xmlFile));

        Node nodeElement = doc.getElementsByTagName("FICdtTrf").item(0);
        NodeList childList = nodeElement.getChildNodes();

        populateNodeIfExists(doc);
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
        Map<String, String> me = convertMappingTemplateListToArray(loadFileContents(CommonConstants.PS009_TEMPLATE_MAPPING_LOCATION+"TemplateMappingFields.txt"));
        //Map<String, String> mev = convertMappingTemplateListToArray(loadFileContents(CommonConstants.PS009_TEMPLATE_MAPPING_LOCATION+"TemplateMappingValues.txt"));
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

    private  void populateNodeIfExists(Document doc) throws Exception {

        // locate the node(s)
        XPath xpath = XPathFactory.newInstance().newXPath();
        Map<String, String> me = convertMappingTemplateListToArray(loadFileContents(CommonConstants.PS009_TEMPLATE_MAPPING_LOCATION+"TemplateMappingFields.txt"));
       // Map<String, String> mev = convertMappingTemplateListToArray(loadFileContents(CommonConstants.PS009_TEMPLATE_MAPPING_LOCATION+"TemplateMappingValues.txt"));
        Map<String, String> mev = getMappingElementValues();
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

    public static Map<String, String> getMappingElementValues() {
        Map<String, String> templateMapping = new HashMap<>();
        templateMapping.put("121", "0234cd54-dcf7-4361-b87f-6c0ddbaab3a2");
        templateMapping.put("20", "AT78096594500102");
        templateMapping.put("21", "FT39BT003401");
        templateMapping.put("52A", "BANKUS33XXX");
        templateMapping.put("56A", "//SC730018");
        templateMapping.put("56A.1", "PARBGB2LLON");
        templateMapping.put("57A", "INSECHZZXXX");
        templateMapping.put("58A", "/GB16RBOS16043110339295");
        templateMapping.put("58A.1", "BANKCHZZXXX");

        return templateMapping;
    }

}
