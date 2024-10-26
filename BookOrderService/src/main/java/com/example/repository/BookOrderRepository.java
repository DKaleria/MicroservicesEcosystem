package com.example.repository;

import com.example.repository.entity.BookOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookOrderRepository extends JpaRepository<BookOrderEntity, Long> {
}
