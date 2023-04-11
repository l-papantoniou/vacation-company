package gr.knowledge.internship.vacation.service;


import gr.knowledge.internship.vacation.domain.Product;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.ProductRepository;
import gr.knowledge.internship.vacation.service.dto.ProductDTO;
import gr.knowledge.internship.vacation.service.mapper.ProductMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Log4j2
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private static final String NOT_FOUND_EXCEPTION_MESSAGE = "Not Found";

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    /**
     * Save a product
     *
     * @param productDTO the entity to save
     * @return the entty
     */
    @Transactional
    public ProductDTO save(ProductDTO productDTO) {
        log.debug("Request to save ProductDTO : {}", productDTO);
        Product product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }


    /**
     * Get a product by id
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional
    public ProductDTO getById(Long id) {
        ProductDTO result;
        log.debug("Request to get Product by id : {}", id);
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            result = productMapper.toDto(product.get());
        } else {
            throw new NotFoundException(NOT_FOUND_EXCEPTION_MESSAGE);
        }
        return result;

    }

    /**
     * Get all products
     *
     * @return a list of all products
     */
    @Transactional
    public List<ProductDTO> getAll() {
        List<ProductDTO> result = new ArrayList<>();
        log.debug("Request to get all Products");
        List<Product> productList = productRepository.findAll();
        if (!productList.isEmpty()) {
            for (Product product : productList) {
                result.add(productMapper.toDto(product));
            }
        }
        return result;
    }


    /**
     * Delete a Product by id
     *
     * @param id the id of the product
     */
    public void delete(Long id) {
        log.debug("Request to delete Product : {}", id);
        productRepository.deleteById(id);
    }


    @Transactional(readOnly = true)
    public Boolean isExistingProductId(Long id) {
        return productRepository.findById(id).isPresent();
    }

}