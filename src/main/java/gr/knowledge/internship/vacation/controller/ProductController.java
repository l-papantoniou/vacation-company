package gr.knowledge.internship.vacation.controller;

import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.service.ProductService;
import gr.knowledge.internship.vacation.service.dto.BonusDTO;
import gr.knowledge.internship.vacation.service.dto.ProductDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api")
public class ProductController {

    public static final String ID_NOT_FOUND = "Product id not found";
    public static final String BAD_INPUT_FOR_UPDATE_PRODUCT = "Bad input for update product";
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        log.debug("REST request to save a Product : {}", productDTO);
        ProductDTO result = productService.save(productDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/product")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO) {
        log.debug("REST request to update a Product : {}", productDTO);
        if (productDTO == null || productDTO.getId() == null) {
            throw new NotFoundException(BAD_INPUT_FOR_UPDATE_PRODUCT);
        }
        Boolean isExistingProductId = productService.isExistingProductId(productDTO.getId());
        if (Boolean.FALSE.equals(isExistingProductId)) {
            throw new NotFoundException(ID_NOT_FOUND);
        }
        ProductDTO result = productService.save(productDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        log.debug("Rest request to get product by id :{}", id);
        ProductDTO result = productService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        log.debug("Rest request to get all Products");
        List<ProductDTO> result = productService.getAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.debug("REST request to delete an Employee : {}", id);
        productService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HttpHeaders.EMPTY)
                .build();
    }

}
