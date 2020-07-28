package myFirstSpring.hello.repository;

import myFirstSpring.hello.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductsRepository {
    Optional<Product> findByName(String name);
    Optional<Product> findById(Long id);
    List<Product> findAll();
    List<Product> findByPrice(Long price);
    void delete(Product product);
    void save(Product product);
}
