package com.nagarro.productdetailservice.repository;

import com.nagarro.productdetailservice.entity.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailsRepository extends JpaRepository<ProductDetails, Long> {
}
