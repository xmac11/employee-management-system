package com.team.ghana.errorHandling;

public class GenericResponse<T> {

    private T data;
    private CustomError error;

    public GenericResponse(T data) {
        this.data = data;
    }

    public GenericResponse(CustomError error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public CustomError getError() {
        return error;
    }

    public void setError(CustomError error) {
        this.error = error;
    }
}
