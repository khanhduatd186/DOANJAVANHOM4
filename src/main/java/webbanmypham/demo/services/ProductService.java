package webbanmypham.demo.services;

import webbanmypham.demo.enity.Category;
import webbanmypham.demo.enity.Product;
import webbanmypham.demo.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private IProductRepository productRepository;
    public Page<Product> getPaginatedProducts(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return productRepository.findAll(pageable);
    }
    public Page<Product> getProductByName(int page, int pageSize, String name) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return productRepository.findByNameContainingIgnoreCase(name, pageable);
    }
    public Page<Product> getProductByCategory(int page, int pageSize, Category category) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return productRepository.findByCategory(category, pageable);
    }
    public List<Product> getAllBook(){
        return productRepository.findAll();
    }
    public Product getBookById(Long id){
        Optional<Product> optionalBook = productRepository.findById(id);
        return optionalBook.orElse(null);
    }
    public void addProduct(Product product){
        productRepository.save(product);
    }
    public void updateBook(Product newbook){
        productRepository.save(newbook);
    }
    public void deleteBook(Long id){
        productRepository.deleteById(id);
    }
}
