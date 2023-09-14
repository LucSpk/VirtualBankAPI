package br.com.lucas.virtualBankAPI.controllers.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor @Getter @Setter
public class StandardError {

    private String error;
    private Integer status;
    private String path;
    private LocalDateTime timeStamp;
}
