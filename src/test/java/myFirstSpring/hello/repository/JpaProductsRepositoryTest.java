package myFirstSpring.hello.repository;

import myFirstSpring.hello.domain.Product;
import myFirstSpring.hello.service.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

// 실제스프링부트로 실험하기 // 트렌젝셔널을 Test에서 하면 각 테스트는 독립적 롤백하기때문
@SpringBootTest
@Transactional
class JpaProductsRepositoryTest {

    //자동으로 연결
    @Autowired ProductsRepository productsRepository;

    @Test
    void 모든상품조회하기(){
        Product p1 = new Product();
        Product p2 = new Product();

        productsRepository.save(p1);
        productsRepository.save(p2);

        Assertions.assertThat(2).isEqualTo(
                productsRepository.findAll().size()
        );
    }

    @Test
    void 이름으로찾기테스트() {
        Product product = new Product();
        product.setName("spring");
        productsRepository.save(product);

        Assertions.assertThat(product).isEqualTo(
                productsRepository.findByName(product.getName()).get()
        );

    }

    @Test
    void 아이디로찾기테스트(){
        Product product = new Product();
        productsRepository.save(product);

        Assertions.assertThat(product).isEqualTo(
                productsRepository.findById(product.getId()).get()
        );
    }


    @Test
    void 가격으로찾기테스트() {
        Product product1 = new Product();
        product1.setPrice(1000L);

        Product product2 = new Product();
        product2.setPrice(1000L);

        Product product3 = new Product();
        product3.setPrice(2000L);

        productsRepository.save(product1);
        productsRepository.save(product2);
        productsRepository.save(product3);


        Assertions.assertThat(2).isEqualTo(
                productsRepository.findByPrice(product1.getPrice()).size()
        );
    }

    @Test
    void 상품지우기테스트(){
        Product product = new Product();
        productsRepository.save(product);

        productsRepository.delete(product);

        Assertions.assertThat(0).isEqualTo(productsRepository.findAll().size());
    }

}