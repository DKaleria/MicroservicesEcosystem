package com.example.controller;

import com.example.controller.request.BookOrderCreateRequest;
import com.example.controller.request.BookOrderUpdateRequest;
import com.example.model.BookOrderModel;
import com.example.service.BookOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class BookOrderController {

    private final BookOrderService bookOrderService;

    @GetMapping
    public ResponseEntity<List<BookOrderModel>> getOrders() {
        List<BookOrderModel> orders = bookOrderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookOrderModel> createOrder(@RequestBody BookOrderCreateRequest createRequest) {
        BookOrderModel createdOrder = bookOrderService.createOrder(createRequest);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<BookOrderModel> updateOrder(@PathVariable Long orderId, @RequestBody BookOrderUpdateRequest updateRequest) {
        BookOrderModel updatedOrder = bookOrderService.updateOrder(orderId, updateRequest);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        bookOrderService.deleteOrder(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
