package com.AltGame.AltGame.Controller;

import com.AltGame.AltGame.Dto.RegisterDto;
import com.AltGame.AltGame.Dto.ResponseDto;
import com.AltGame.AltGame.Entity.RoleEntity;
import com.AltGame.AltGame.Entity.UserEntity;
import com.AltGame.AltGame.Service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class LoginController {
    @Autowired
    UserService userService;

    @PostMapping(value = "/api/signup")
    public ResponseEntity<?> createNewUser(@RequestBody RegisterDto registerDto) {
        ResponseDto response;
        UserEntity user = userService.getUserByUsername(registerDto.getUsername());
        if(userService.exitsByEmail(registerDto.getUsername())){
            return new ResponseEntity<>(new ResponseDto("400","Error Email Already Exist"), HttpStatus.BAD_REQUEST);
        }else if(Objects.isNull(user)){
            return new ResponseEntity<>(new ResponseDto("200","Success Register User",userService.store(registerDto)), HttpStatus.OK);
        }else{
            return  new ResponseEntity<>(new ResponseDto("400","Error Username Already Exist"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/refresh-token")
    public void refreshTokenController(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){

            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("altgame".getBytes());
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(refresh_token);
                String usernameDecode = decodedJWT.getSubject();
                UserEntity userLogin = userService.getUserByUsername(usernameDecode);
                Optional<RoleEntity> roleEntity = userService.role(userLogin.getRoleId());
                String accessToken = JWT.create()
                        .withSubject(userLogin.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() +10*60*1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", roleEntity.get().getName())
                        .sign(algorithm);

                Map<String,String> token = new HashMap<>();
                token.put("access_token", accessToken);
                Map<String,Object> data = new HashMap<>();
                data.put("tokens",token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), data);
            }catch (Exception e){
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());

                Map<String,String> errorMap = new HashMap<>();
                errorMap.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), errorMap);
            }
        }else{
            throw new RuntimeException("refresh token is missing");
        }
    }
}
