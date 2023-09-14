package br.com.lucas.virtualBankAPI.services.exceptions;

public class DivergentDataException extends RuntimeException{

    public DivergentDataException(String message) {
        super(message);
    }
}
