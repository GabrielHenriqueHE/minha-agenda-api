package io.github.gabrielhenrique.minha_agenda_api.payloads;

public class ApiResponse<T> {

    public int status;
    public T data;
    public String message;

    public ApiResponse(int status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public ApiResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
