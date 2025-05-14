package br.com.tbt.lactino.exception;

public class ErrorResponse {

    private int status;
    private String error;
    private String menssagem;

    public ErrorResponse(int status, String error, String menssagem) {
        this.status = status;
        this.error = error;
        this.menssagem = menssagem;
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
        return menssagem;
    }

    public void setMenssagem(String menssagem) {
        this.menssagem = menssagem;
    }
}
