package br.com.lucas.virtualBankAPI.enums.exceptions;

public enum ErrorMessage {
    OBJETO_NAO_ENCONTRADO("Objeto não encontrado"),
    EAMIL_JA_CADASTRADO("E-mail já cadastrado no sistema"),
    NUMERO_ACC_JA_CADASTRADO("Número da conta já existe no sistem"),
    DIVERGENCIA_NOS_DADOS("Há divergencia nos dados"),
    USUARIO_NAO_ENCONTRADO("Usuário não encontrado"),
    NUM_ACC_NAO_ENCONTRADO("Número de Conta não encontrado");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
