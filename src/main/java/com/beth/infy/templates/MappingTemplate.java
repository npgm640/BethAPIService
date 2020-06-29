package com.beth.infy.templates;

import com.beth.infy.domain.ConvertToXmlRequest;
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
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
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
                //TODO - need to pass the fileName as parameter to the method.
                //String schemaFilename = "/home/ranga/sandbox/springboot/psac009/PSAC20022.xsd";
                // instance.

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
                XMLDocument xmlDocument = new XMLDocument(new StreamResult( schemaFilename.substring(0, schemaFilename.indexOf(".")) + ".xml"), false, 4, null);
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
    public void populateXmlData_02(String xmlFile) throws Exception {

        Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().parse(new InputSource(xmlFile));

        Node nodeElement = doc.getElementsByTagName("FICdtTrf").item(0);
        NodeList childList = nodeElement.getChildNodes();

      /*  for (int i = 0; i < childList.getLength(); i++) {
            Node node = childList.item(i);
            modifyNodeIfExists(doc, node);
        }*/
        modifyNodeIfExists(doc);
        // save the result
        Transformer xformer = TransformerFactory.newInstance().newTransformer();
        String modifiedxmlFile = "/home/ranga/sandbox/springboot/psac009/PSAC20022_Modified.xml";
        xformer.transform
                (new DOMSource(doc), new StreamResult(new File(modifiedxmlFile)));
        System.out.println("xml data modification completed...");

    }


    @Override
    public void populateXmlData_03(String xmlFile) throws Exception {

        Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().parse(new InputSource(xmlFile));

        Node nodeElement = doc.getElementsByTagName("FICdtTrf").item(0);
        NodeList childList = nodeElement.getChildNodes();

      /*  for (int i = 0; i < childList.getLength(); i++) {
            Node node = childList.item(i);
            modifyNodeIfExists(doc, node);
        }*/
        populateNodeIfExists(doc);
        // save the result
        Transformer xformer = TransformerFactory.newInstance().newTransformer();
        String populateXmlFile = "/home/ranga/sandbox/springboot/psac009/PSAC20022_Populate.xml";
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

    @Override
    public void populateXmlData_01(String xmlFile) throws Exception {

        try {
            //TODO - need to pass the string dynamically
            //String xmlFile = "/home/ranga/sandbox/springboot/psac009/PSAC20022.xml";
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();


            Document document = documentBuilder.parse(xmlFile);

            //get the root element
            Node root = document.getFirstChild();

            Node topElement = document.getElementsByTagName("FICdtTrf").item(0);
            NodeList topElementList = topElement.getChildNodes();
            System.out.println("Top List - " + topElementList.toString());
            //TODO - define strings in common constants file

            //TODO -  need to get values from object.

            Node nodeElement = document.getElementsByTagName("GrpHdr").item(0);
            NodeList childList = nodeElement.getChildNodes();

            for (int i = 0; i < childList.getLength(); i++) {
                Node node = childList.item(i);

                if ("MsgId".equalsIgnoreCase(node.getNodeName())) {
                    node.setTextContent("NONREF");
                }

                if ("CreDtTm".equalsIgnoreCase(node.getNodeName())) {
                    node.setTextContent(getCurrentDateTime());
                }

                if ("NbOfTxs".equalsIgnoreCase(node.getNodeName())) {
                    node.setTextContent("1");
                }

                if ("SttlmInf".equalsIgnoreCase(node.getNodeName())) {
                    Node nodeSubElement = document.getElementsByTagName("SttlmInf").item(0);
                    NodeList childSubList = nodeElement.getChildNodes();
                    for (int j = 0; j < childSubList.getLength(); j++) {
                        if ("SttlmMtd".equalsIgnoreCase(node.getNodeName())) {
                            node.setTextContent("CLRG");
                        }
                    }
                }
            }
            //write the content into xml file.
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult xmlResult = new StreamResult(new File(xmlFile));
            transformer.transform(domSource, xmlResult);
            System.out.println("XML data population completed...");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

  /*  private Map<String, String> getMappingElements() {
        Map<String, String> templateMapping = new HashMap<>();
        templateMapping.put("121", "CdtTrfTxInf/PmtId/UETR");
        templateMapping.put("20", "CdtTrfTxInf/PmtId/InstrId");
        templateMapping.put("21", "CdtTrfTxInf/PmtId/EndToEndId");
        templateMapping.put("52A", "IntrmyAgt1/FinInstnId/BICFI");
        templateMapping.put("56A", "Dbtr/FinInstnId/BICFI");
        templateMapping.put("57A", "CdtrAgt/FinInstnId/BICFI");
        templateMapping.put("58A", "CdtrAcct/Id/Othr/Id");
        templateMapping.put("58A.1", "Cdtr/FinInstnId/BICFI");

        return templateMapping;

    }*/

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
