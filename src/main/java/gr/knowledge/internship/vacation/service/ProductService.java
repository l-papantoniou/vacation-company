package gr.knowledge.internship.vacation.service;


import gr.knowledge.internship.vacation.domain.Product;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.ProductRepository;
import gr.knowledge.internship.vacation.service.dto.ProductDTO;
import gr.knowledge.internship.vacation.service.mapper.ProductMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Log4j2
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private static final String NotFoundExceptionMessage = "Not Found";

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Transactional
    public ProductDTO getById(Long id) {
        ProductDTO result;
        log.debug("Request to get Product by id : {}", id);
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            result = productMapper.toDto(product.get());
        } else {
            throw new NotFoundException(NotFoundExceptionMessage);
        }
        return result;

    }
}