package com.panfutov.notes.dto;

import java.util.List;

public record ResultPage<T>(List<T> items, int currentPage, int pages) {
}
