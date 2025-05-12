package com.nagarro.productdetailservice.service.impl;

import com.nagarro.productdetailservice.dto.ProductResponse;
import com.nagarro.productdetailservice.dto.ProductUpdateRequest;
import com.nagarro.productdetailservice.entity.ProductDetails;
import com.nagarro.productdetailservice.repository.ProductDetailsRepository;
import com.nagarro.productdetailservice.dto.NewProduct;
import com.nagarro.productdetailservice.service.ProductDetailsService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.nagarro.productdetailservice.utils.ProductUtils.createProductEntity;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductDetailsServiceImpl implements ProductDetailsService {

    @Autowired
    private ProductDetailsRepository productDetailsRepository;


    private static final String TOPIC = "ecommerceTopic";

//    @Autowired
//    private KafkaTemplate<String,String> kafkaTemplate;
//
//    public ProductDetailsServiceImpl(KafkaTemplate<String, String> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    @Override
//    public void sendNotification(String data) {
//        kafkaTemplate.send(TOPIC,"1",data);
//    }

    @Override
    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "fallbackResponse")
    public List<ProductResponse> fetchProducts() {
        return productDetailsRepository.findAll().stream()
                .map(product -> new ProductResponse(
                        product.getProductId(), product.getPrice(), product.getSize(),
                        product.getProductName(),product.getColor(),product.getGender(),product.getProductType()
                )).toList();
    }

    @Override
    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "fallbackResponse")
    public ProductResponse saveNewProduct(NewProduct productDetails) {

        ProductDetails product = createProductEntity(productDetails.price(),
                productDetails.size(),productDetails.productName(),
                productDetails.color(),productDetails.gender(),productDetails.productType());
        ProductDetails response = productDetailsRepository.save(product);
//        sendNotification("Added new product successfully! "+ product);
        log.info("Product created successfully!");
        log.info("Product: "+response);
        return new ProductResponse(response.getProductId(), response.getPrice(),
                response.getSize(),response.getProductName(), response.getColor(),
                response.getGender() ,response.getProductType());
    }

    @Override
    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "fallbackResponse")
    public Optional<ProductResponse> fetchProductById(Long id) {
        Optional<ProductDetails> response = productDetailsRepository.findById(id);
        if(response.isEmpty()){
//            sendNotification("Product id not found in database: "+id);
            return Optional.empty();
        }
        return response.map(res -> new ProductResponse(res.getProductId(),res.getPrice(),
                res.getSize(),res.getProductName(),res.getColor(),
                res.getGender(),res.getProductType()));
    }

    @Override
    public boolean deleteProduct(Long id) {

        if(productDetailsRepository.findById(id).isEmpty()){
//            sendNotification("Unable to delete product as product id is not present: "+id);
            return false;
        }
        productDetailsRepository.deleteById(id);
//        sendNotification("Product deleted with id: "+id);
        return true;
    }

    @Override
    public ProductResponse updateProductDetails(ProductUpdateRequest productUpdateRequest) {

        ProductDetails productDetails= productDetailsRepository.findById(
                productUpdateRequest.productId()).orElseThrow(
                ()-> new RuntimeException("Product Id is not present!"));

        productDetails.setProductType(productUpdateRequest.productName());
        productDetails.setProductType(productUpdateRequest.productType());
        productDetails.setColor(productUpdateRequest.color());
        productDetails.setGender(productUpdateRequest.gender());
        productDetails.setSize(productUpdateRequest.size());
        productDetails.setPrice(productUpdateRequest.price());

        productDetailsRepository.save(productDetails);
//        sendNotification("Product update successfully!!");
        ProductResponse response= new ProductResponse(productDetails.getProductId(),
                productDetails.getPrice(), productDetails.getSize(),productDetails.getProductName(),
                productDetails.getColor(),productDetails.getGender(),productDetails.getProductType());
        return response;
    }

    @Override
    public double getProductPrice(Long id) {

        Optional<ProductDetails> productDetails =
        productDetailsRepository.findById(id);
        return productDetails.map(ProductDetails::getPrice).orElse(0.0);
    }

    public String fallbackResponse(Throwable throwable){
        return "Fallback response: Service unavailable. Please try again in sometime";
    }
}
