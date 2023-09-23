package br.com.lucas.virtualBankAPI.enums.exceptions;

public enum ErrorMessage {
    OBJETO_NAO_ENCONTRADO("Objeto não encontrado"),
    EAMIL_JA_CADASTRADO("E-mail já cadastrado no sistema"),
    NUMERO_ACC_JA_CADASTRADO("Número da conta já existe no sistem"),
    DIVERGENCIA_NOS_DADOS("Há divergencia nos dados"),
    USUARIO_NAO_ENCONTRADO("Usuário não encontrado"),
    NUM_ACC_NAO_ENCONTRADO("Número de Conta não encontrado"),
    TRANSACTION_NAO_ENCONTRADO("Transação não encontrada"),
    CONTAS_DEVEM_SER_FORNECIDAS("As contas de origem e destino devem ser fornecidas"),
    VALOR_MENOR_QUE_ZERO("O valor da transferência deve ser maior que zero"),
    SALDO_INSUFICIENTE("Saldo insuficiente na conta de origem"),
    TRANSACAO_NAO_ENCONTRADA("A transação que esta tentando fazer não existe"),
    TRANSACAO_NECESSARIA("Tipo de transação não pode ser nula"),
    NUMERO_ACC_INVALIDO("Número de conta inválido"),
    ACC_DESTINO_NECESSARIA("Necessário inserir id da conta de destino"),
    ACC_ORIGEM_NECESSARIA("Necessário inserir id da conta de origem")
    ;

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
