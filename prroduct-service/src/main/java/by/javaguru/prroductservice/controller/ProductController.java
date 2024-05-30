package by.javaguru.prroductservice.controller;

import by.javaguru.prroductservice.dto.ProductDto;
import by.javaguru.prroductservice.exception.ErrorMessage;
import by.javaguru.prroductservice.service.ProductService;
import by.javaguru.prroductservice.service.event.ProductGetEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable("id") String id) {
        ProductGetEvent product = null;
        try {
            product = productService.getProductById(id);
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorMessage(new Date(), e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }
    @PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody ProductDto createProduct){
        String productId = null;
        try {
            productId = productService.createdProduct(createProduct);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorMessage(new Date(), e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productId);
    }
}
