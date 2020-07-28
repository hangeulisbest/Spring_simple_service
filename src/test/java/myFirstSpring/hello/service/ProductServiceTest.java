package myFirstSpring.hello.service;

import myFirstSpring.hello.domain.Product;
import myFirstSpring.hello.repository.ProductsRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired ProductService productService;
    @Autowired ProductsRepository productsRepository;

    @Test
    public void 상품등록테스트(){
        Product product = new Product();
        productService.join(product);

        Assertions.assertThat(product).isEqualTo(productsRepository.findById(product.getId()).get());
    }

    @Test
    public void 중복이름상품등록테스트(){
        Product product1 = new Product();
        product1.setName("spring");
        Product product2 = new Product();
        product2.setName("spring");

        productService.join(product1);

        IllegalStateException e = org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class,()->productService.join(product2));
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 이름입니다.");

    }


    @Test
    public void 가격정렬조회테스트(){
        List<Product> productList = new ArrayList<Product>();


        Product product1 = new Product(); product1.setPrice(1L);
        Product product2 = new Product(); product2.setPrice(100L);
        Product product3 = new Product(); product3.setPrice(10L);



        productService.join(product1);
        productService.join(product2);
        productService.join(product3);

        List<Product> result = productService.allProduct(true);

        Assertions.assertThat(result.get(0)).isEqualTo(product1);
        Assertions.assertThat(result.get(1)).isEqualTo(product3);
        Assertions.assertThat(result.get(2)).isEqualTo(product2);


    }


    @Test
    public void 이름과가격으로검색하기테스트(){
        Product product1 = new Product();
        product1.setPrice(1000L);
        product1.setName("spring");


        Product product2 = new Product();
        product2.setName("spring2");
        product2.setPrice(1000L);


        productService.join(product1);
        productService.join(product2);

        Assertions.assertThat(product1).isEqualTo(productService.searchName(product1.getName()).get(0));
        Assertions.assertThat(2).isEqualTo(productService.searchPrice(1000L).size());
    }

}