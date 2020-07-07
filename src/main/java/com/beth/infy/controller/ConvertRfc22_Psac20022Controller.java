package com.beth.infy.controller;

import com.beth.infy.domain.ConvertRFC22_PSAC20022Request;
import com.beth.infy.domain.PSAC20022ResponseTemplateMapping;
import com.beth.infy.util.CommonConstants;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ConvertRfc22_Psac20022Controller extends AbstractController {

    @PostMapping(value = "/api/v1/convertRFC22_PSAC20022Format", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> convertRFC22_PSAC20022XmlFile(@RequestBody ConvertRFC22_PSAC20022Request request) throws Exception {

        logger.info("Started uploading template mapping file...");

        //TODO validateFile(uploadedFile);

        String outputFileName = null;
        if (StringUtils.isEmpty(request.getTemplateMappingLocation())) {

            if (request.getTemplateType().equalsIgnoreCase(CommonConstants.TEMPLATE_TYPE)) {
                outputFileName = CommonConstants.PS009_TEMPLATE_MAPPING_LOCATION;
            }
            if (null == outputFileName) {
                //default location
                outputFileName = CommonConstants.PS009_TEMPLATE_MAPPING_LOCATION;
            }

            request.setTemplateMappingLocation(outputFileName);
        }

        try {
            transformToPsac20022Format(request);
        } catch (IOException e) {
            logger.error("Error: in processing file upload - " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        PSAC20022ResponseTemplateMapping response = new PSAC20022ResponseTemplateMapping();
        response.setMessageText(request.getFileName() + request.getTemplateName() + " formatted successfully");
        // return new ResponseEntity<>("Successfully uploaded - " + uploadedFile.getFileName(), new HttpHeaders(), HttpStatus.OK);
        return ResponseEntity.ok(gson.toJson(response));
    }

    private void transformToPsac20022Format(ConvertRFC22_PSAC20022Request request) throws IOException, CannotCompileException, NotFoundException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        byte[] decoded = java.util.Base64.getDecoder().decode(request.getContent());
        String outputFileName = request.getTemplateMappingLocation() + request.getFileName();
        //TODO - store mapping fields to Database

      /*  if (!file.getTemplateMappingLocation().endsWith("/")) {
            file.setTemplateMappingLocation(file.getTemplateMappingLocation() + "/");
        }

        FileOutputStream fileOutputStream = new FileOutputStream(outputFileName);
        fileOutputStream.write(decoded);
        fileOutputStream.flush();
        fileOutputStream.close(); */
        Class clazz;

        clazz = generateClazz(request);

        Object instance = clazz.newInstance();
        //invoke methods

        //modify xml based on mapping fields
        (clazz.getDeclaredMethod(CommonConstants.TEMPLATE_POPULATE_XML_METHOD_NAME,  String.class))
                .invoke(instance, "/home/ranga/sandbox/springboot/psac009/"+request.getTemplateType()+".xml");
    }
}
