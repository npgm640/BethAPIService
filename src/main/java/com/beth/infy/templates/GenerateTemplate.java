package com.beth.infy.templates;

import com.beth.infy.service.TemplateService;
import com.beth.infy.util.CommonConstants;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class GenerateTemplate  {
    
    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NotFoundException, CannotCompileException {
        /* ranga bethapudi
        order of arguments
        1. userId     (from infy_schema.USER_ID)
        2. clientId   (from infy_schema.CLIENT)
        3. templateId (from infy_schema.TEMPLATE)

         */

      /*  long userId, clientId, templateId;
        if (args.length == 3) {
            try {
                userId = Integer.parseInt(args[0]);
                clientId = Integer.parseInt(args[1]);
                templateId = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                System.err.println("Arguments must be an integer.");
                System.exit(1);
            }
        } */
        //TODO need to retreive from Database.
        String xmlSchemaFileName = "/home/ranga/sandbox/springboot/psac009/1/input/PSAC20022.xsd";
        String xmlFileName = "/home/ranga/sandbox/springboot/psac009/1/output/PSAC20022.xml";
        String mt202FilePath = "/home/ranga/sandbox/springboot/psac009/1/input/MT202.txt";
        String templateName = "Mt202_Psac2022";
        String templateType = "psac2022";
        String clientId = "1";

        System.out.println("Generating XML Document started for consortium - " + clientId + " with templateId - " + templateName);
        String classRootLocation = CommonConstants.FILE_CONVERT_DESTINATION_FOLDER_LOCATION;

        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath(classRootLocation);
        CtClass ctClass = pool.get(templateName);

        Class clazz = ctClass.toClass();

        System.out.println("Auto Gen Template loaded - "+ clazz.getName()+".class");

        Object instance = clazz.newInstance();
        //TODO - need to pass ths strings dynamically to avoid hard coding path names in create/modify/populate xml"
        //invoke methods
        System.out.println("Generating xml method using schema - " + xmlSchemaFileName);
        (clazz.getDeclaredMethod(CommonConstants.TEMPLATE_CREATE_XML_METHOD_NAME, String.class))
                    .invoke(instance, xmlSchemaFileName);

        //modify xml based on mapping fields
        System.out.println("Modifying  xml process started with required mapping fields..");
        (clazz.getDeclaredMethod(CommonConstants.TEMPLATE_MODIFY_XML_METHOD_NAME,  String.class))
                    .invoke(instance, xmlFileName);

        //populate xml based on mapping fields
        System.out.println("Xml data population started using: " + mt202FilePath);
        (clazz.getDeclaredMethod(CommonConstants.TEMPLATE_POPULATE_XML_METHOD_NAME,  String.class))
                .invoke(instance, xmlFileName);

    }
    
}

