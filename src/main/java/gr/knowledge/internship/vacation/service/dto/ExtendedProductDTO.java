package gr.knowledge.internship.vacation.service.dto;


import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExtendedProductDTO implements Serializable {

    @NotNull
    private ProductDTO product;

    @NotNull
    private Long employeesCount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExtendedProductDTO that = (ExtendedProductDTO) o;
        return Objects.equals(product, that.product) && Objects.equals(employeesCount, that.employeesCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, employeesCount);
    }
}