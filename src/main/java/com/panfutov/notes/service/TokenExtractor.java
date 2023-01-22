package com.panfutov.notes.service;

import jakarta.inject.Singleton;

@Singleton
public class TokenExtractor {

    public String extractBearerToken(String header) {
        if (header != null) {
            var split = header.split(" ");

            if (split.length == 2) {
                return split[1];
            }
        }
        throw new IllegalArgumentException("Couldn't parse header");
    }

}
