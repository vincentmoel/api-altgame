package com.AltGame.AltGame.Dto;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.Data;
import org.springframework.boot.jackson.JsonObjectSerializer;

import java.io.IOException;
import java.util.*;

@Data
public class ResponseDto {
    private String status;
    private String message;
    private Object data;

    public ResponseDto(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResponseDto(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseDto() {
    }

    public Map<String, Object> responseBuilder(String status, String message) {
        LinkedHashMap<String, Object> hm = new LinkedHashMap<>();
        hm.put("status",status);
        hm.put("message",message);

        return hm;
    }

    public Map<String, Object> responseBuilder(String status, String message, Object data) {
        LinkedHashMap<String, Object> hm = new LinkedHashMap<>();
        hm.put("status",status);
        hm.put("message",message);
        hm.put("data",data);
        return hm;
    }
}
