package com.example.springframework.repositories;

import com.example.springframework.domain.Product;
import com.example.springframework.configuration.RepositoryConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RepositoryConfiguration.class})
public class ProductRepositoryTest {
    private ProductRepository productRepository;
    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Test
    public void testSaveProduct(){
        //setup product
        Product product = new Product();
        product.setDescription("apple");
        product.setPrice(new BigDecimal("0.5"));
        product.setProductId("1234");
        //save product, verify has ID value after save
        assertNull(product.getId()); //null before save
        productRepository.save(product);
        assertNotNull(product.getId()); //not null after save
        //fetch from DB
        Product fetchedProduct = productRepository.findById(product.getId()).orElse(null);
        //should not be null
        assertNotNull(fetchedProduct);
        //should equal
        assertEquals(product.getId(), fetchedProduct.getId());
        assertEquals(product.getDescription(), fetchedProduct.getDescription());
        //update description and save
        fetchedProduct.setDescription("New Description");
        productRepository.save(fetchedProduct);
        //get from DB, should be updated
        Product fetchedUpdatedProduct = productRepository.findById(fetchedProduct.getId()).orElse(null);
        assertEquals(fetchedProduct.getDescription(), fetchedUpdatedProduct.getDescription());
        //verify count of products in DB
        long productCount = productRepository.count();
        assertEquals(productCount, 1);
        //get all products, list should only have one
        Iterable<Product> products = productRepository.findAll();
        int count = 0;
        for(Product p : products){
            count++;
        }
        assertEquals(count, 1);
    }
}
