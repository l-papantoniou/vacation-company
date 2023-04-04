package gr.knowledge.internship.vacation.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class ProductDTO implements Serializable {

    Long id;

    @NotNull
    @Size(max = 255)
    LocalDate name;

    @NotNull
    @Size(max = 255)
    LocalDate description;

    @NotNull
    @Size(max = 255)
    LocalDate barCode;
}
