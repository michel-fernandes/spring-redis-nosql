package br.com.j38.springdataredis;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductRestController {

    public final ProductService productService;

    @PostMapping
    public Product save(@RequestBody Product product) {
        return this.productService.save(product);
    }
    @GetMapping
    public List<Product> getAll() {
        return this.productService.findAll();
    }
}