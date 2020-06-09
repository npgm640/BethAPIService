package com.beth.infy.domain;

import com.beth.infy.util.CommonConstants;
import org.springframework.http.HttpStatus;

public class UploadResponseTemplateMapping extends CommonResponse {

    public UploadResponseTemplateMapping() {
        super(CommonConstants.SUCCESS, HttpStatus.OK.value());
    }

}
