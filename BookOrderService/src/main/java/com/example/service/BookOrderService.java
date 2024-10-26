package com.example.service;

import com.example.controller.request.BookOrderCreateRequest;
import com.example.controller.request.BookOrderUpdateRequest;
import com.example.model.BookOrderModel;

import java.util.List;

public interface BookOrderService {
    List<BookOrderModel> getAllOrders();

    BookOrderModel createOrder(BookOrderCreateRequest createRequest);

    BookOrderModel updateOrder(Long orderId, BookOrderUpdateRequest updateRequest);

    void deleteOrder(Long orderId);
}
