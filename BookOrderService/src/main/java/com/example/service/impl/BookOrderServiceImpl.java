package com.example.service.impl;

import com.example.controller.request.BookOrderCreateRequest;
import com.example.controller.request.BookOrderUpdateRequest;
import com.example.enums.BookOrderStatus;
import com.example.mapper.BookOrderMapper;
import com.example.model.BookOrderModel;
import com.example.repository.BookOrderRepository;
import com.example.repository.entity.BookOrderEntity;
import com.example.service.BookOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class BookOrderServiceImpl implements BookOrderService {

    private final BookOrderRepository bookOrderRepository;
    private final BookOrderMapper bookOrderMapper;

    @Override
    public List<BookOrderModel> getAllOrders() {
        return bookOrderMapper.toModels(bookOrderRepository.findAll());
    }

    @Override
    public BookOrderModel createOrder(BookOrderCreateRequest createRequest) {
        BookOrderEntity bookOrderEntity = BookOrderEntity.builder()
                .userId(createRequest.userId())
                .bookId(createRequest.bookId())
                .status(BookOrderStatus.valueOf(createRequest.status()))
                .quantity(createRequest.quantity())
                .orderDate(createRequest.orderDate())
                .build();
        bookOrderEntity = bookOrderRepository.save(bookOrderEntity);
        return bookOrderMapper.toModel(bookOrderEntity);
    }

    @Override
    public BookOrderModel updateOrder(Long orderId, BookOrderUpdateRequest updateRequest) {
        BookOrderEntity bookOrderEntity = bookOrderRepository.findById(orderId).orElseThrow(
                () -> new RuntimeException(format("Заказ с id %d отсутствует в базе", orderId)));

        bookOrderEntity.setUserId(updateRequest.userId());
        bookOrderEntity.setBookId(updateRequest.bookId());
        bookOrderEntity.setStatus(BookOrderStatus.valueOf(updateRequest.status()));
        bookOrderEntity.setQuantity(updateRequest.quantity());
        bookOrderEntity.setOrderDate(updateRequest.orderDate());

        bookOrderEntity = bookOrderRepository.save(bookOrderEntity);
        return bookOrderMapper.toModel(bookOrderEntity);
    }

    @Override
    public void deleteOrder(Long orderId) {
        bookOrderRepository.deleteById(orderId);
    }
}
