package br.com.j38.springdataredis;

import java.util.List;

public interface ProductCacheRepository {
    public Product save(Product product);
    public Object convertValue(Object object, Class clazz);
    public List<Product> findAll();
}
