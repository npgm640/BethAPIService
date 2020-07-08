package com.beth.infy.controller;

import com.beth.infy.domain.CommonResponse;
import com.beth.infy.domain.TemplateMappingRequest;
import com.beth.infy.domain.PSAC20022ResponseTemplateMapping;
import com.beth.infy.model.TemplateMappingOrm;
import com.beth.infy.util.CommonConstants;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;

@RestController
public class GenerateXmlController extends AbstractController {

    @PostMapping(value = "/api/v1/generateXmlMapping", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> generateTemplateMapping(@RequestBody TemplateMappingRequest request) throws Exception {

        logger.info("***********Started generating xml template mapping ...**********");

        boolean valid = validateRequest(request);

        if (!valid) {
            //send error response back
            logger.error("Invalid Request. Pls. validate the request body.. ");
            CommonResponse response = new CommonResponse(CommonConstants.VALIDATION_ERROR, HttpStatus.BAD_REQUEST.value(), "Invalid Request. Pls. validate the request body.. ");
            return ResponseEntity.ok(gson.toJson(response));
        }
        String templateMappingLocation = getTemplateMappingLocationURL(request);

        if (StringUtils.isEmpty(templateMappingLocation)) {
            String errorMsg = "Template mapping not set for client - " + request.getClientId() + " and template - + " + request.getTemplateName();
            logger.error(errorMsg);
            CommonResponse response = new CommonResponse(CommonConstants.VALIDATION_ERROR, HttpStatus.BAD_REQUEST.value(), errorMsg);
            return ResponseEntity.ok(gson.toJson(response));
        }

        String result = generateTemplate(request);

        TemplateMappingOrm templateMappingOrm = null;
        if (result == null) {
            String errMsg = "Pls. correct the class mapping and try again.";
            logger.error(errMsg);
            CommonResponse response = new CommonResponse(CommonConstants.VALIDATION_ERROR, HttpStatus.BAD_REQUEST.value(), errMsg);
            return ResponseEntity.ok(gson.toJson(response));
        } else {
            templateMappingOrm = saveTemplateMapping(request);
        }

        if (StringUtils.isEmpty(templateMappingOrm)) {
            String errMsg = "Error in storing template mapping. Pls. try again!";
            logger.error(errMsg);
            CommonResponse response = new CommonResponse(CommonConstants.ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), errMsg);
            return ResponseEntity.ok(gson.toJson(response));
        }

        PSAC20022ResponseTemplateMapping response = new PSAC20022ResponseTemplateMapping();
        String successMsg = request.getTemplateName() + " Template generated successfully with template mapping id - " + templateMappingOrm.getTemplateMappingId();
        response.setMessageText(successMsg);
        logger.info(successMsg);
        return ResponseEntity.ok(gson.toJson(response));
    }


    private boolean validateRequest(TemplateMappingRequest request) {

        if (StringUtils.isEmpty(request.getMappingFields())) {
            return false;
        }

        if (StringUtils.isEmpty(request.getTemplateType())) {
            return false;
        }

        if (StringUtils.isEmpty(request.getSchemaFileName())) {
            return false;
        }

        if (StringUtils.isEmpty(request.getClientId())) {
            return false;
        }

        request.setMapping(convertMappingTemplateListToArray(request.getMappingFields()));
        return true;
    }


    private Map convertMappingTemplateListToArray(String mappingTemplates) {
        Map<String, String> templateMapping = new HashMap<>();
        String[] str = mappingTemplates.split(",");
        List<String> splitList = new ArrayList<String>();
        splitList = Arrays.asList(str);
        for (String each : splitList) {
            String[] str1 = each.split(":");
            templateMapping.put(str1[0], str1[1]);
        }
        return templateMapping;
    }


    private String generateTemplate(TemplateMappingRequest request) {
        Class clazz = null;
        try {
            clazz = generateClazz(request);
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (clazz == null) {
            return null;
        }
        return clazz.toString();
    }
}
