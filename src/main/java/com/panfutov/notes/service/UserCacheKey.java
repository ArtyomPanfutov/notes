package com.panfutov.notes.service;

import java.util.Objects;

public record UserCacheKey(String subject, String accessToken) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCacheKey that = (UserCacheKey) o;
        return Objects.equals(subject, that.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject);
    }
}
