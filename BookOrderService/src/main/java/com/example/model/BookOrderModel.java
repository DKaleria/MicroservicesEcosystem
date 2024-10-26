package com.example.model;

import lombok.Builder;

import java.sql.Timestamp;
import java.time.LocalDate;

@Builder(toBuilder = true)
public record BookOrderModel(
        Long userId,
        Long bookId,
        Integer quantity,
        String status,
        LocalDate orderDate
) {
}
