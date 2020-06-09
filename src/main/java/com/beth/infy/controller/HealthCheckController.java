package com.beth.infy.controller;

import com.beth.infy.domain.CommonResponse;
import com.beth.infy.util.CommonConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HealthCheckController extends AbstractController {

    @GetMapping(path = "/api/v1/health", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> healthCheck() {
        CommonResponse response = new CommonResponse(CommonConstants.SUCCESS, HttpStatus.OK.value());
        response.setMessageText("I am healthy and running on http://localhost:8080");
        return ResponseEntity.ok(gson.toJson(response));
    }
}
