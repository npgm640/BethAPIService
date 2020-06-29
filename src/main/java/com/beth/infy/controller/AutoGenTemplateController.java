package com.beth.infy.controller;

import com.beth.infy.domain.CommonResponse;
import com.beth.infy.domain.TemplateRequest;
import com.beth.infy.util.CommonConstants;
import com.google.gson.Gson;
import javassist.*;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

@RestController
public class AutoGenTemplateController extends AbstractController{

    @PostMapping(value = "/api/v1/convertMt202ToPacs009Format", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> generateOutputFile(@RequestBody TemplateRequest templateRequest) throws Exception {


        if (!validateRequest(templateRequest)) {
            CommonResponse response = new CommonResponse(CommonConstants.VALIDATION_ERROR, HttpStatus.EXPECTATION_FAILED.value(), CommonConstants.MISSING_FIELDS);
            return ResponseEntity.ok(gson.toJson(response));
        }

        if (!verifyTemplateMappingExists(templateRequest.getTemplateName())) {
            //check in DB if the template mapping is completed.
            CommonResponse response = new CommonResponse(CommonConstants.VALIDATION_ERROR, HttpStatus.EXPECTATION_FAILED.value(), CommonConstants.TEMPLATE_MAPPING_NOT_EXISTS);
            return ResponseEntity.ok(gson.toJson(response));
        }
        try {
            processConvertTo009Format(templateRequest);
        } catch (IOException e) {
            logger.error("Error: in processing file upload - " + e.getMessage());
            CommonResponse response = new CommonResponse(CommonConstants.ERROR, HttpStatus.BAD_REQUEST.value(), "error in processing ");
            return ResponseEntity.ok(gson.toJson(response));
        }

        CommonResponse response = new CommonResponse(CommonConstants.SUCCESS, HttpStatus.CREATED.value(), CommonConstants.FILE_CRATED);
        return ResponseEntity.ok(gson.toJson(response));
    }

    private void processConvertTo009Format(TemplateRequest templateRequest) throws IOException {

        Class clazz;
        try {
            byte[] decoded = java.util.Base64.getDecoder().decode(templateRequest.getContent());

            String fileContents =  new String(decoded);
            String fileType = templateRequest.getConvertTo();
            //TODO - convert jsonString to object by using POJO classes. Generate POJO dynamically.
            String jsonString = convertFileContentToJson(fileContents, fileType);

            if (fileExists(FILE_UPLOAD_LOCATION+templateRequest.getTemplateName()+".class")) {
                File root = new File(FILE_UPLOAD_LOCATION);
                URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { root.toURI().toURL() });
                clazz = Class.forName(CommonConstants.FULLY_QUALIFIED_TEMPLATE_FOLDER+templateRequest.getTemplateName(), true, classLoader);
            } else {
                 clazz = classGenerator(templateRequest.getTemplateName(), CommonConstants.TEMPLATE_CREATE_XML_METHOD_NAME);
            }
            /**** ranga 05/23 - from above either class loads if exists, or creates dynamcially.
             * once class exists, invoke the ncessary meethos dynamcally.
             * create a new instance of newly or existing class
             * invoke the methods.
             */

            Object instance = clazz.newInstance();
            //invoke methods
            //generateXml
            (clazz.getDeclaredMethod(CommonConstants.TEMPLATE_CREATE_XML_METHOD_NAME)).invoke(instance);
            //populate xml
            (clazz.getDeclaredMethod(CommonConstants.TEMPLATE_POPULATE_XML_METHOD_NAME)).invoke(instance);

        }
        catch (InstantiationException instantiationException) {
            instantiationException.printStackTrace();
        } catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }



    private Class classGenerator(String ps009ClassName, String methodName) throws CannotCompileException, IOException, NotFoundException {


        ClassPool pool = ClassPool.getDefault();
        CtClass superClazz = pool.get("com.beth.infy.templates.MappingTemplate");
        CtClass ctClass = pool.makeClass(ps009ClassName, superClazz);

        //TODO - the method uri should be in properties. its easy to change without deployment.
        String createXmlMethodFilename = CommonConstants.RESOURCE_FOLDER+ "methods/100/createXml01.txt";
        String populateXmlMethodFilename = CommonConstants.RESOURCE_FOLDER+ "methods/100/populateXmlData01.txt";

        String generateXmlMethodBody = loadFileContents(createXmlMethodFilename);
        String populateXmlMethodBody = loadFileContents(populateXmlMethodFilename);


        ctClass.addMethod(CtMethod.make(generateXmlMethodBody, ctClass));
        ctClass.addMethod(CtMethod.make(populateXmlMethodBody, ctClass));
        ctClass.writeFile(FILE_UPLOAD_LOCATION);
        return ctClass.toClass();
    }

    private HashMap<String, String> generateMethodsForPAC009Format(TemplateRequest request) throws ClassNotFoundException, FileNotFoundException, InstantiationException, IllegalAccessException, JAXBException {

        HashMap resultMap = new HashMap();

        StringBuffer sb = new StringBuffer();
        sb.append("public void createXmlFile(request) throws ClassNotFoundException, FileNotFoundException, InstantiationException, IllegalAccessException, JAXBException { ");
        sb.append("mappingTemplate.generateXml(request.getFileName(), CommonConstants.FILE_CONVERT_DESTINATION_FOLDER_LOCATION, new Pacs())");
        resultMap.put("createXmlFile", sb.toString());
        sb = new StringBuffer();
        sb.append("public String healthMethod() {java.lang.System.out.println(\"Hello!\");" );
        resultMap.put("healthMethod", sb.toString());
        return resultMap;
    }


    private boolean validateRequest(TemplateRequest templateRequest) {

        if (templateRequest.getContent().isEmpty()) {
            return false;
        }

        if (templateRequest.getFileName().isEmpty()) {
            return false;
        }

        if (templateRequest.getConvertTo().isEmpty()) {
            return false;
        }

        if (templateRequest.getUserIdentifier().isEmpty()) {
            return false;
        }
        return true;
    }


}
