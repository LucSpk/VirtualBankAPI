package br.com.lucas.virtualBankAPI.enums.exceptions;

public enum ErrorMessage {
    OBJETO_NAO_ENCONTRADO("Objeto não encontrado"),
    EAMIL_JA_CADASTRADO("E-mail já cadastrado no sistema");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
