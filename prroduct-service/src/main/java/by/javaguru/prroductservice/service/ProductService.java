package by.javaguru.prroductservice.service;

import by.javaguru.prroductservice.dto.ProductDto;
import by.javaguru.prroductservice.service.event.ProductGetEvent;

import java.util.concurrent.ExecutionException;

public interface ProductService {
    String createdProduct(ProductDto productDto) throws ExecutionException, InterruptedException;
    ProductGetEvent getProductById(String id) throws ExecutionException, InterruptedException;
}
