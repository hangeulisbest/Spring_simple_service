package myFirstSpring.hello.repository;

import myFirstSpring.hello.domain.Product;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaProductsRepository implements ProductsRepository{

    private final EntityManager em;

    public JpaProductsRepository(EntityManager em) {
        this.em = em;
    }


    @Override
    public Optional<Product> findByName(String name) {
        return em.createQuery("select p from Product p where p.name =:name",Product.class)
                .setParameter("name",name)
                .getResultList() // List<Product>
                .stream() //Stream<Product>
                .findAny(); //Optional<Product>
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(em.find(Product.class,id));
    }

    @Override
    public List<Product> findAll() {
        return em.createQuery("select p from Product p",Product.class)
                .getResultList();
    }

    @Override
    public List<Product> findByPrice(Long price) {
        return em.createQuery("select p from Product p where p.price=:price",Product.class)
                .setParameter("price",price)
                .getResultList();
    }

    @Override
    public void delete(Product product) {
        Product deleted = em.find(Product.class,product.getId());
        if(deleted != null) {
            em.remove(deleted);
        }
    }

    @Override
    public void save(Product product) {
        em.persist(product);
    }



    public void clear(){
        em.clear();
    }
}
