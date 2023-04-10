package gr.knowledge.internship.vacation.service.mapper;

import gr.knowledge.internship.vacation.domain.Product;
import gr.knowledge.internship.vacation.service.dto.ProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper extends ModelMapper {

    public ProductDTO toDto(Product product) {
        return this.map(product, ProductDTO.class);
    }

    public Product toEntity(ProductDTO productDTO) {
        return this.map(productDTO, Product.class);
    }
}
