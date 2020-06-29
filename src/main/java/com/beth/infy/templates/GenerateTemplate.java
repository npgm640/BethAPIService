package com.beth.infy.templates;

import com.beth.infy.controller.AbstractController;
import com.beth.infy.controller.GenerateXmlController;
import com.beth.infy.util.CommonConstants;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import com.beth.infy.templates.*;

public class GenerateTemplate  {
    
    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NotFoundException, CannotCompileException {
        System.out.println("Inside main of GenerateTemplate");
        String xmlSchemaFileName = "/home/ranga/sandbox/springboot/psac009/PSAC20022.xsd";
        String xmlFileName = "/home/ranga/sandbox/springboot/psac009/PSAC20022.xml";
        String templateName = "Rfc22_Psac2022";

        System.out.println("Generating XML Document started...");
        String rootLocation = CommonConstants.FILE_CONVERT_DESTINATION_FOLDER_LOCATION;

        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath("/home/ranga/sandbox/springboot/infy/target/infy-0.0.1-SNAPSHOT.jar");
        CtClass ctClass = pool.get("Rfc22_Psac2022");
        
        Class clazz = ctClass.toClass();
        
     //   String fullyQualifiedFileName =  templateName;
     //   Class clazz = loadClazz(rootLocation, fullyQualifiedFileName);
        
        System.out.println("Class loaded - "+ clazz.getName());

        Object instance = clazz.newInstance();
        //invoke methods
        System.out.println("Invoking create xml method..");
        (clazz.getDeclaredMethod(CommonConstants.TEMPLATE_CREATE_XML_METHOD_NAME, String.class))
                    .invoke(instance, xmlSchemaFileName);
        System.out.println("Xml generated");
        //modify xml based on mapping fields
          /*  (clazz.getDeclaredMethod(CommonConstants.TEMPLATE_MODIFY_XML_METHOD_NAME,  String.class))
                    .invoke(instance, "/home/ranga/sandbox/springboot/psac009/"+request.getFileType()+".xml"); */

    }

    /*public static Class loadClazz(String rootLocation, String fullyQualifiedClassName) throws IOException, ClassNotFoundException {
        File root = new File(rootLocation);
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{root.toURI().toURL()});
        System.out.println("URL Class Loader " + classLoader.toString());
        Class clazz = Class.forName(com.beth.infy.templates.Rfc22_Psac2022 , true, classLoader);
        
        return clazz;*/
    
}

