package com.beth.infy.templates;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface IMappingTemplate {
    public void generateXmlDocument() throws Exception;
    public boolean verifyTemplateMappingExists(String templateName);
    public void populateXmlData_01() throws Exception;

}
