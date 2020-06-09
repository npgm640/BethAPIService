package com.beth.infy.controller;

import com.beth.infy.domain.UploadRequestTemplateFile;
import com.beth.infy.domain.UploadResponseTemplateMapping;
import com.beth.infy.util.CommonConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.io.FileOutputStream;
import java.io.IOException;

@RestController
public class MappingFileUploadController extends AbstractController {

    @PostMapping(value = "/api/v1/uploadTemplateMapping", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> uploadTemplateMappingXsdFile(@RequestBody UploadRequestTemplateFile request) throws Exception {

        logger.info("Started uploading template mapping file...");

        //TODO validateFile(uploadedFile);

        String outputFileName = null;
        if (StringUtils.isEmpty(request.getTemplateMappingLocation())) {

            if (request.getFileType().equalsIgnoreCase(CommonConstants.TEMPLATE_TYPE)) {
                outputFileName = CommonConstants.PS009_TEMPLATE_MAPPING_LOCATION;
            }
            if (null == outputFileName) {
                //default location
                outputFileName = CommonConstants.PS009_TEMPLATE_MAPPING_LOCATION;
            }

            request.setTemplateMappingLocation(outputFileName);
        }

        try {
            processFileToUpload(request);
        } catch (IOException e) {
            logger.error("Error: in processing file upload - " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UploadResponseTemplateMapping response = new UploadResponseTemplateMapping();
        response.setMessageText(request.getFileName() + CommonConstants.FILE_UPLOAD_SUCCESS);
        // return new ResponseEntity<>("Successfully uploaded - " + uploadedFile.getFileName(), new HttpHeaders(), HttpStatus.OK);
        return ResponseEntity.ok(gson.toJson(response));
    }

    private void processFileToUpload(UploadRequestTemplateFile file) throws IOException {

        byte[] decoded = java.util.Base64.getDecoder().decode(file.getContent());
        String outputFileName = file.getTemplateMappingLocation() + file.getFileName();
        System.out.println("Mapping file contents saved to location - " + outputFileName);
        System.out.println("Contents - " + new String(decoded));

        if (!file.getTemplateMappingLocation().endsWith("/")) {
            file.setTemplateMappingLocation(file.getTemplateMappingLocation() + "/");
        }

        FileOutputStream fileOutputStream = new FileOutputStream(outputFileName);
        fileOutputStream.write(decoded);
        fileOutputStream.flush();
        fileOutputStream.close();
    }
}
