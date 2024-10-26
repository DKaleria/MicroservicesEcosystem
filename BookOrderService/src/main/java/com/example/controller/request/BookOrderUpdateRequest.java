package com.example.controller.request;

import lombok.Builder;

import java.sql.Timestamp;
import java.time.LocalDate;

@Builder(toBuilder = true)
public record BookOrderUpdateRequest(
        Long userId,
        Long bookId,
        Integer quantity,
        String status,
        LocalDate orderDate
) {
}
