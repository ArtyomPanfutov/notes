package com.luckwheat.notes.dto;

/**
 * A generic result of creation.
 * @param <T> Created object.
 */
public record CreateResult<T> (
    boolean success,
    T body,
    Error error) {

    public static<T> CreateResult<T> success(T body) {
        return new CreateResult<>(true, body, null);
    }

    public static<T> CreateResult<T> error(Error error) {
        return new CreateResult<>(false, null, error);
    }
}
