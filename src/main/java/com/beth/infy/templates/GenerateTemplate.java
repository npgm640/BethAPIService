package com.beth.infy.templates;

import com.beth.infy.util.CommonConstants;
import javassist.*;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

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
        String templateName = "Rfc22_Psac2022";

        System.out.println("Generating XML Document started...");
        String rootLocation = CommonConstants.FILE_CONVERT_DESTINATION_FOLDER_LOCATION;

        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath(rootLocation);
        CtClass ctClass = pool.get("Mt202_Psac2022");
        
        Class clazz = ctClass.toClass();

        System.out.println("Class loaded - "+ clazz.getName());

        Object instance = clazz.newInstance();
        //invoke methods
        System.out.println("Invoking create xml method..");
        (clazz.getDeclaredMethod(CommonConstants.TEMPLATE_CREATE_XML_METHOD_NAME, String.class))
                    .invoke(instance, xmlSchemaFileName);
        System.out.println("Xml generated");
        //modify xml based on mapping fields
        (clazz.getDeclaredMethod(CommonConstants.TEMPLATE_MODIFY_XML_METHOD_NAME,  String.class))
                    .invoke(instance, xmlFileName);
        //populate xml based on mapping fields
        (clazz.getDeclaredMethod(CommonConstants.TEMPLATE_POPULATE_XML_METHOD_NAME,  String.class))
                .invoke(instance, xmlFileName);

    }
    
}

