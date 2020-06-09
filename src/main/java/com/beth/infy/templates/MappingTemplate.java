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
import java.io.File;
import java.io.IOException;
import org.joda.time.LocalDate;

public class MappingTemplate implements IMappingTemplate {


    @Override
    public void generateXmlDocument() {
            try {
                //TODO - need to pass the fileName as parameter to the method.
                String filename = "/home/ranga/sandbox/springboot/psac009/PSAC20022.xsd";
                // instance.

                final Document doc = loadXsdDocument(filename);
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

                org.apache.xerces.xs.XSModel xsModel = new XSParser().parse(filename);

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
                XMLDocument xmlDocument = new XMLDocument(new StreamResult( filename.substring(0, filename.indexOf(".")) + ".xml"), false, 4, null);
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
    public void populateXmlData_01() throws Exception {

        try {
            //TODO - need to pass the string dynamically
            String xmlFile = "/home/ranga/sandbox/springboot/psac009/PSAC20022.xml";
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);

            //get the root element
            Node root = document.getFirstChild();

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

    private String getCurrentDateTime() {

        LocalDateTime currentDateTime = LocalDateTime.now();
        return currentDateTime.toString();
    }


}
