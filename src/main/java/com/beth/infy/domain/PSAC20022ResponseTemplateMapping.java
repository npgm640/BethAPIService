package com.beth.infy.domain;

import com.beth.infy.util.CommonConstants;
import org.springframework.http.HttpStatus;

public class PSAC20022ResponseTemplateMapping extends CommonResponse {

    public PSAC20022ResponseTemplateMapping() {
        super(CommonConstants.SUCCESS, HttpStatus.OK.value());
    }

}
