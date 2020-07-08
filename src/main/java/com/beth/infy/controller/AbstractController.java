package com.beth.infy.controller;

import com.beth.infy.domain.AbstractRequest;
import com.beth.infy.domain.ConvertRFC22_PSAC20022Request;
import com.beth.infy.domain.TemplateMappingRequest;
import com.beth.infy.domain.TemplateMappingDto;
import com.beth.infy.model.*;
import com.beth.infy.service.*;
import com.beth.infy.util.CommonConstants;
import com.google.gson.Gson;
import com.strobel.decompiler.Decompiler;
import com.strobel.decompiler.DecompilerSettings;
import com.strobel.decompiler.PlainTextOutput;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javassist.*;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class AbstractController {

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private TemplateMappingService templateMappingService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TemplateClassService templateClassService;

    @Autowired
    private AuthorizationService userService;

    final Logger LOGGER = LoggerFactory.getLogger(getClass());



    public static final Gson gson = new Gson();
    public static final Logger logger = LoggerFactory.getLogger(CommonConstants.LOGGER_FILE_NAME);
    //TODO - store in DB
    String FILE_UPLOAD_LOCATION = CommonConstants.FILE_CONVERT_DESTINATION_FOLDER_LOCATION;

    public boolean fileExists(String templateName) {
        File tmpDir = new File(templateName);
       return(tmpDir.exists());
    }

    public String getTemplateMappingLocationURL(AbstractRequest request) {

        TemplateMappingRequest req = (TemplateMappingRequest) request;
        ClientOrm clientOrm = clientService.getClient(req.getClientId());

        if (StringUtils.isEmpty(clientOrm)) {
            return null;
        }
        TemplateOrm templateOrm =  templateService.getTemplateUsing(clientOrm, req.getTemplateName(), req.getTemplateType());

        if (StringUtils.isEmpty(templateOrm)) {
            return null;
        }

        return templateOrm.getTemplateMappingLocation();
    }

    public  String loadResourceFileContents(String classPath) throws IOException {
       /* try {
            return new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } */

        Resource resource = resourceLoader.getResource(classPath);
        InputStream inputStream = resource.getInputStream();
        String data = null;
        try
        {
            byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
             data = new String(bdata, StandardCharsets.UTF_8);
            LOGGER.info(data);
        }
        catch (IOException e)
        {
            LOGGER.error("IOException", e);
        }
        return data;
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

    public Class generateClazz(AbstractRequest request) throws IOException, ClassNotFoundException, NotFoundException, CannotCompileException {
        Class clazz;

        String templateName = null;
        TemplateClassOrm templateClassOrm = null;

        if (request instanceof TemplateMappingRequest) {
            TemplateMappingRequest req =   (TemplateMappingRequest) request;
             templateName = getTemplateMappingUsing(req.getClientId(), req.getTemplateName(), req.getTemplateType());

             templateClassOrm = getTemplateClass(req);

             //if templateName is empty or overide template is true then generate template , else load existing template.
            if ((StringUtils.isEmpty(templateName)) || (Boolean.valueOf(req.getTemplateOveride()))) {
                clazz = classGenerator(templateClassOrm);
            } else {
                File root = new File(templateClassOrm.getClazzOutputLocation());
                URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{root.toURI().toURL()});
                clazz = Class.forName(templateName, true, classLoader);
            }

        } else {
            ConvertRFC22_PSAC20022Request req = (ConvertRFC22_PSAC20022Request) request;
             templateName = getTemplateMappingUsing(req.getClientId(), req.getTemplateName(), req.getTemplateType());
            templateClassOrm = getTemplateClass(req);
            if (StringUtils.isEmpty(templateName)) {
                clazz = classGenerator(templateClassOrm);
            } else {
                File root = new File(templateClassOrm.getClazzOutputLocation());
                URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{root.toURI().toURL()});
                clazz = Class.forName(templateName, true, classLoader);
            }
        }

        return clazz;
    }

    private TemplateClassOrm getTemplateClass(AbstractRequest abstractRequest) {
        TemplateClassOrm templateClassOrm = null;

        if (abstractRequest instanceof TemplateMappingRequest) {
            TemplateMappingRequest request = (TemplateMappingRequest) abstractRequest;
            ClientOrm clientOrm = clientService.getClient(request.getClientId());
            if (StringUtils.isEmpty(clientOrm)) {
                return null;
            }
            TemplateOrm templateOrm = templateService.getTemplateUsing(clientOrm, request.getTemplateName(), request.getTemplateType());

            if (StringUtils.isEmpty(templateOrm)) {
                return null;
            }
            templateClassOrm = templateClassService.getTemplateClassUsing(templateOrm, clientOrm);

        } else if (abstractRequest instanceof ConvertRFC22_PSAC20022Request) {
            ConvertRFC22_PSAC20022Request request = (ConvertRFC22_PSAC20022Request) abstractRequest;
            ClientOrm clientOrm = clientService.getClient(request.getClientId());
            if (StringUtils.isEmpty(clientOrm)) {
                return null;
            }
            TemplateOrm templateOrm = templateService.getTemplateUsing(clientOrm, request.getTemplateName(), request.getTemplateType());
            if (StringUtils.isEmpty(templateOrm)) {
                return null;
            }
            templateClassOrm = templateClassService.getTemplateClassUsing(templateOrm, clientOrm);
        }
        return templateClassOrm;
    }


    private String getTemplateMappingUsing(long clientId, String templateName, String templateType) {
        ClientOrm client = clientService.getClient(clientId);
        TemplateOrm templateOrm = templateService.getTemplateUsing(client, templateName, templateType);
        TemplateMappingOrm templateMappingOrm =  templateMappingService.getTemplateMappingUsing(templateOrm);

        if (templateMappingOrm == null) {
            return null;
        }

        return templateMappingOrm.getTemplate().getTemplateName();
    }


    private Class classGenerator(TemplateClassOrm templateClassOrm) throws CannotCompileException, IOException, NotFoundException {

            ClassPool pool = ClassPool.getDefault();


            if (StringUtils.isEmpty(templateClassOrm)) {
               return null;
            }

            pool.importPackage(templateClassOrm.getPackageName());
            CtClass superClazz = pool.get(templateClassOrm.getSuperClazz());

            CtClass ctClass = pool.makeClass(templateClassOrm.getClazzName(), superClazz);
            List<String> methodList = formatMethodList(templateClassOrm.getMethodUris());

            for (String methodString : methodList) {
                ctClass.addMethod(CtMethod.make(loadResourceFileContents(methodString), ctClass));
            }

            ctClass.writeFile(templateClassOrm.getClazzOutputLocation());

            //TODO need to convert .java class
        //String sourcePath = templateClassOrm.getClazzOutputLocation() + templateClassOrm.getClazzName();
        // String destinationPath = "/home/ranga/sandbox/springboot/BethAPIService/src/main/java/com/beth/infy/templates/"+templateClassOrm.getClazzName();

        //copyAndDecompileClassFile( sourcePath, destinationPath);
            return ctClass.toClass();

    }

    private void copyAndDecompileClassFile(String sourcePath, String destinationPath) {
        //String classe = "Mt202_Psac2022";
        /*List<String> classes = Arrays.asList("Mt202_Psac2022");
        List<File> sourceDir = Arrays.asList(new File (sourcePath));
        File outputDir = new File(destinationPath);
        KrakatauLibrary krakatauLibrary = new KrakatauLibrary();
        krakatauLibrary.decompile(sourceDir, classes, outputDir); */

        try {
            final FileOutputStream stream = new FileOutputStream(destinationPath);

            try {
                final OutputStreamWriter writer = new OutputStreamWriter(stream);

                try {
                    Decompiler.decompile(
                            "com.beth.infy.templates.Mt202_Psac2022.java",
                            new PlainTextOutput(writer),
                            DecompilerSettings.javaDefaults()
                    );
                }
                finally {
                    writer.close();
                }
            }
            finally {
                stream.close();
            }
        }
        catch (final IOException e) {
            // handle error
        }
    }


    private List<String> formatMethodList(String methodUris) {
        String str[] = methodUris.split(";");
        List<String> methodList = Arrays.asList(str);
        return methodList;
    }

    public TemplateMappingOrm saveTemplateMapping(TemplateMappingRequest request) {
        TemplateMappingDto mappingDto = new TemplateMappingDto();
        ClientOrm client = clientService.getClient(request.getClientId());
        TemplateOrm templateOrm = templateService.getTemplateUsing(client, request.getTemplateName(), request.getTemplateType());
        UserOrm userOrm = userService.getUser(request.getUserId());
        mappingDto.setTemplateId(templateOrm.getTemplateId());
        mappingDto.setMappingData(request.getMappingFields());
        mappingDto.setActive("1");
        mappingDto.setCrtdBy(userOrm.getUserName());
        mappingDto.setLastMdfdBy(userOrm.getUserName());
        mappingDto.setCrtdTs(new DateTime().toString());
        mappingDto.setLastMdfdTs(new DateTime().toString());
        TemplateMappingOrm mappingOrm = templateMappingService.save(mappingDto);
        return mappingOrm;
    }
}
