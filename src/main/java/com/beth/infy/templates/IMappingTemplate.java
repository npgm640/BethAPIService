package com.beth.infy.templates;

import com.beth.infy.domain.ConvertToXmlRequest;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface IMappingTemplate {
    public void generateXmlDocument(String xmlFile) throws Exception;
    public boolean verifyTemplateMappingExists(String templateName);
    public void modifyXml_01(String xmlFile) throws Exception;
    public void populateXmlData_01(String xmlFile) throws Exception;
}
