package myFirstSpring.hello.service;

import myFirstSpring.hello.domain.Product;
import myFirstSpring.hello.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Transactional
public class ProductService {
    private final ProductsRepository productsRepository;

    public ProductService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    /**
     * 상품 등록하기
     * */

    public Long join(Product product){
        validateDuplicateName(product);
        productsRepository.save(product);
        return product.getId();
    }

    /**
     *  상품 등록하기 - 이름 중복 체크
     * */
    private void validateDuplicateName(Product product){
        productsRepository.findByName(product.getName())
                .ifPresent( m-> { throw new IllegalStateException("이미 존재하는 이름입니다.");});
    }
    /**
     * 모든 상품 조회하기
     * */

    public List<Product> allProduct(boolean sorted){
        if(sorted) {
            return productsSortedByPrice(productsRepository.findAll());
        }
        else {
            return productsRepository.findAll();
        }
    }

    /**
     * 가격이 저렴한 순으로 상품들 조회하기
     * */

    public List<Product> productsSortedByPrice(List<Product> products){
        Comparator<Product> comparator = (p1,p2)-> Long.compare(p1.getPrice(),p2.getPrice());
        List<Product> result = products.stream().sorted(comparator).collect(Collectors.toList());

        return result;
    }


    /**
     * 이름으로 검색하기
     * */
    public List<Product> searchName(String name){
        return productsRepository.findByName(name).stream().collect(Collectors.toList());
    }


    /**
     * 가격으로 검색하기
     * */
    public List<Product> searchPrice(Long price){
        return productsRepository.findByPrice(price);
    }

    /**
     * 이름으로 상품 제거하기
     * */

    public void deleteProduct(String name){
        productsRepository.findByName(name).ifPresent(
                x->productsRepository.delete(x)
        );
    }


}
