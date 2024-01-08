package br.com.j38.springdataredis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductCacheRepository productCacheRepository;
    @Override
    public Product save(Product product) {
        return this.productCacheRepository.save(product);
    }
    @Override
    public List<Product> findAll() {
        return this.productCacheRepository.findAll();
    }
}
