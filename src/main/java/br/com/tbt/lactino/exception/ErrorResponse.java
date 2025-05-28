package br.com.tbt.lactino.exception;

public class ErrorResponse {

    private int status;
    private String error;
    private String mensagem;

    public ErrorResponse(int status, String error, String menssagem) {
        this.status = status;
        this.error = error;
        this.mensagem = menssagem;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMenssagem() {
        return mensagem;
    }

    public void setMenssagem(String menssagem) {
        this.mensagem = menssagem;
    }
}
