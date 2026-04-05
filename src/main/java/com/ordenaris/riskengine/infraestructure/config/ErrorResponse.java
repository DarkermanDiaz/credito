package com.ordenaris.riskengine.infraestructure.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private int status;
    private String mensaje;
    private String detalles;
    private String timestamp;
    private String path;

    public static ErrorResponse of(int status, String mensaje, String detalles, String path) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return ErrorResponse.builder()
                .status(status)
                .mensaje(mensaje)
                .detalles(detalles)
                .timestamp(LocalDateTime.now().format(formatter))
                .path(path)
                .build();
    }
}
