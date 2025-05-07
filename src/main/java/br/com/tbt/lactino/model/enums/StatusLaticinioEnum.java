package br.com.tbt.lactino.model.enums;

public enum StatusLaticinioEnum {
    EM_ESTOQUE("Em Estoque"),
    VENDIDO("Vendido"),
    VENCIDO("Vencido"),
    DESCARTADO("Descartado");

    private String descricao;

    StatusLaticinioEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
