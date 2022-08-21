package com.luckwheat.notes.dto;

import javax.annotation.Nullable;

/**
 * A generic result of operation.
 * @param <T> Created or updated object (nullable)
 */
public record Result<T> (
    boolean success,
    @Nullable T body,
    Error error) {

    public static<T> Result<T> success(T body) {
        return new Result<>(true, body, null);
    }

    public static<T> Result<T> successVoid() {
        return new Result<>(true, null, null);
    }

    public static<T> Result<T> error(Error error) {
        return new Result<>(false, null, error);
    }

    public boolean fail() {
        return !success;
    }
}
