package br.com.tbt.lactino.model.enums;

public enum StatusLeiteEnum {
    DISPONIVEL("Disponível"),
    UTILIZADO("Utilizado"),
    VENCIDO("Vencido"),
    DESCARTADO("Descartado");

    private String status;

    StatusLeiteEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
