package com.luckwheat.notes.service;

import jakarta.inject.Singleton;

@Singleton
public class TokenExtractor {

    public String extract(String header) {
        var split = header.split(" ");

        if (split.length == 2) {
            return split[1];
        }
        throw new IllegalArgumentException("Couldn't parse header");
    }

}
