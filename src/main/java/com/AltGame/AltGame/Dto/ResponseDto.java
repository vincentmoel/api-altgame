package com.AltGame.AltGame.Dto;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.Data;
import org.springframework.boot.jackson.JsonObjectSerializer;

import java.io.IOException;

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
}
