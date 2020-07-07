package com.beth.infy.controller;

import com.beth.infy.auth.AuthorizationUtil;
import com.beth.infy.domain.AuthRequest;
import com.beth.infy.domain.AuthResponse;
import com.beth.infy.domain.CommonResponse;
import com.beth.infy.domain.UserDto;
import com.beth.infy.model.UserOrm;
import com.beth.infy.service.AuthorizationService;
import com.beth.infy.util.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AuthenticationController extends AbstractController {

    @Autowired
    private AuthorizationUtil jwtUtil;

    @Autowired
    private AuthorizationService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(value = "/api/v1/login", produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUserName(), authenticationRequest.getPassword());

        final UserDetails userDetail = userDetailsService.loadUserByUsername(authenticationRequest.getUserName());

        final String token = jwtUtil.generateToken(userDetail);
        AuthResponse response = new AuthResponse(token);
        return ResponseEntity.ok(gson.toJson(response));
    }

   @PostMapping(value = "/api/v1/register", produces= MediaType.APPLICATION_JSON_VALUE)
   @ResponseBody
    public ResponseEntity<?> registerUser(@RequestBody UserDto user) throws Exception {
        CommonResponse response ;
        UserOrm userOrm;
        try {
               userOrm = userDetailsService.save(user);
        } catch (Exception e) {
            logger.error("Error: in registering user");
             response = new CommonResponse(CommonConstants.ERROR, HttpStatus.EXPECTATION_FAILED.value(), "error in processing ");
            return ResponseEntity.ok(gson.toJson(response));
        }
         response = new CommonResponse(CommonConstants.SUCCESS, HttpStatus.CREATED.value(), "User successfully created");
        return ResponseEntity.ok(gson.toJson(response));
    }

    private void authenticate(String userName, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));

        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
