package gr.knowledge.internship.vacation.service.dto;

import gr.knowledge.internship.vacation.domain.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class EmployeeProductCount implements Serializable {

    @NotNull
    private Product product;

    @NotNull
    private Long employeeCount;

    public EmployeeProductCount(Product product, Long employeeCount) {
        this.product = product;
        this.employeeCount = employeeCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeProductCount that = (EmployeeProductCount) o;
        return Objects.equals(product, that.product) && Objects.equals(employeeCount, that.employeeCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, employeeCount);
    }
}
