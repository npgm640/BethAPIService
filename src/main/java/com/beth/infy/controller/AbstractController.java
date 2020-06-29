package com.beth.infy.controller;

import com.beth.infy.util.CommonConstants;
import com.google.gson.Gson;


import javassist.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class AbstractController {

    public static final Gson gson = new Gson();
    public static final Logger logger = LoggerFactory.getLogger(CommonConstants.LOGGER_FILE_NAME);
    String FILE_UPLOAD_LOCATION = CommonConstants.FILE_CONVERT_DESTINATION_FOLDER_LOCATION;

    public boolean fileExists(String templateName) {
        File tmpDir = new File(templateName);
       return(tmpDir.exists());
    }

    public  String loadFileContents(String fileName) {
        try {
            return new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public boolean verifyTemplateMappingExists(String templateName) {
        //TODO - need logic to check if tempalate mapping already done.
        return true;
    }

    protected String convertFileContentToJson(String fileContents, String fileType) {
        //TODO - need to convert to json string dynamically.
        return "" +
                "{\n" +
                "  \"key3\": {\n" +
                "    \"key103\": \"STG\",\n" +
                "    \"key113\": \"RR40\",\n" +
                "    \"key108\": \"2913105368733B06\",\n" +
                "    \"key111\": \"001\",\n" +
                "    \"key121\": \"0234cd54-dcf7-4361-b87f-6c0ddbaab3a2\"\n" +
                "  },\n" +
                "  \"key4\": {\n" +
                "    \"key20\": \"AT78096594500102\",\n" +
                "    \"key21\": \"FT39BT003401\",\n" +
                "    \"key32A\": \"190527GBP250000\",\n" +
                "    \"key52A\": \"BANKUS33XXX\",\n" +
                "    \"key56A\": \"//SC730018 PARBGB2LLON\",\n" +
                "    \"key57A\": \"INSECHZZXXX\",\n" +
                "    \"key58A\": \"/GB16RBOS16043110339295 BANKCHZZXXX\"\n" +
                "  },\n" +
                "  \"key1\": \"F01BOFAGB22DXXX4003469973\",\n" +
                "  \"key2\": \"I202RBOSGB55XXXXN\"\n" +
                "}";
    }


    protected String getMappingFileName(String fileType) {
        if (fileType.equalsIgnoreCase(CommonConstants.TEMPLATE_TYPE)) {
            return CommonConstants.PS009_TEMPLATE_MAPPING_LOCATION;
        }
        return null;
    }


    protected void writeToFile(String fullPathFilename, String content) {

        try {
            FileUtils.writeStringToFile(new File(fullPathFilename), content, StandardCharsets.UTF_8);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Class loadClazz(String rootLocation, String fullyQualifiedClassName) throws IOException, ClassNotFoundException {
        File root = new File(rootLocation);
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{root.toURI().toURL()});
        Class clazz = Class.forName(fullyQualifiedClassName , true, classLoader);
        return clazz;
    }

    public Class generateClazz(String fileName, String templateName,List<String> methods) throws IOException, ClassNotFoundException, NotFoundException, CannotCompileException {
        Class clazz;
        if (fileExists(fileName)) {
            File root = new File(FILE_UPLOAD_LOCATION);
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{root.toURI().toURL()});
            clazz = Class.forName(templateName, true, classLoader);
        } else {
            clazz = classGenerator(templateName, methods);
        }

        return clazz;
    }

    private Class classGenerator(String ps009ClassName, List<String> methodList) throws CannotCompileException, IOException, NotFoundException {

            ClassPool pool = ClassPool.getDefault();
            pool.importPackage("com.beth.infy.domain.ConvertToXmlRequest");
            CtClass superClazz = pool.get("com.beth.infy.templates.MappingTemplate");

            CtClass ctClass = pool.makeClass(ps009ClassName, superClazz);

            //TODO - the method uri should be in properties. its easy to change without deployment.

            String generateXmlMethodBody = loadFileContents(methodList.get(0));
            String modifyXmlMethodBody = loadFileContents(methodList.get(1));
            String populateXmlMethodBody = loadFileContents(methodList.get(2));


            ctClass.addMethod(CtMethod.make(generateXmlMethodBody, ctClass));
            ctClass.addMethod(CtMethod.make(modifyXmlMethodBody, ctClass));
            ctClass.addMethod(CtMethod.make(populateXmlMethodBody, ctClass));
            ctClass.writeFile(FILE_UPLOAD_LOCATION);
            return ctClass.toClass();
    }
}
