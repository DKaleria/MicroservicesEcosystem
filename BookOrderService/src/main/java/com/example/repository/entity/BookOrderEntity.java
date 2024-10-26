package com.example.repository.entity;

import com.example.enums.BookOrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Data
@Builder(toBuilder = true)
@Table(name = "book_order")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BookOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long bookId;
    private Long userId;
    private Integer quantity;
    @Enumerated(EnumType.STRING)
    private BookOrderStatus status = BookOrderStatus.AVAILABLE;
    private LocalDate orderDate;
    @CreatedDate
    private Timestamp created;
    @LastModifiedDate
    private Timestamp changed;
}
