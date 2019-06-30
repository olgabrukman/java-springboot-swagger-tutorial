package com.example.springframework.bootstrap;

import com.example.springframework.domain.Product;
import com.example.springframework.repositories.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SpringJpaBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private ProductRepository productRepository;


    private Logger log = LogManager.getLogger(SpringJpaBootstrap.class);

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadProducts();
    }

    private void loadProducts() {
        Product apple = new Product();
        apple.setDescription("apple");
        apple.setPrice(new BigDecimal("0.5"));
        apple.setImageUrl("http://apple.com");
        apple.setProductId("235268845711068308");
        productRepository.save(apple);

        log.info("Saved apple - id: " + apple.getId());

        Product orange = new Product();
        orange.setDescription("orangeg");
        orange.setImageUrl("https://orange.com");
        orange.setProductId("168639393495335947");
        orange.setPrice(new BigDecimal("0.15"));
        productRepository.save(orange);

        log.info("Saved orange - id:" + orange.getId());
    }


}



