package br.com.j38.springdataredis;

import java.util.List;

public interface ProductService {
    public Product save(Product product);
    public List<Product> findAll();
}
