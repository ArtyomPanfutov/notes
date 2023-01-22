package com.panfutov.notes.service;

import java.util.Objects;

public record UserCacheKey(String subject, String accessToken) {

    /**
     * The equals method does not use accessToken field as it is not constant value.
     * @param o   the reference object with which to compare.
     * @return true - if subject field matches, otherwise - false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCacheKey that = (UserCacheKey) o;
        return Objects.equals(subject, that.subject);
    }

    /**
     * The hashCode method does not use accessToken field as it is not constant value.
     * @return computed hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(subject);
    }
}
