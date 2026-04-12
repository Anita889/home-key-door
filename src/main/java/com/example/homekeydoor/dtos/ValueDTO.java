package com.example.homekeydoor.dtos;

public class ValueDTO<T> extends AbstractDTO {

    private T value;

    public ValueDTO() {
    }

    public ValueDTO(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}

