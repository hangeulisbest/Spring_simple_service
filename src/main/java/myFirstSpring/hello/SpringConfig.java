package myFirstSpring.hello;


import myFirstSpring.hello.repository.JpaProductsRepository;
import myFirstSpring.hello.repository.ProductsRepository;
import myFirstSpring.hello.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final EntityManager em;
    private final DataSource dataSource;

    public SpringConfig(EntityManager em, DataSource dataSource) {
        this.em = em;
        this.dataSource = dataSource;
    }

    @Bean
    public ProductService productService(){
        return new ProductService(productsRepository());
    }


    /**
     * 이 부분으로 데이터베이스를 조립
     * */
    @Bean
    public ProductsRepository productsRepository(){
        return new JpaProductsRepository(em);
    }
}
