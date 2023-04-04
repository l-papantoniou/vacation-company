package gr.knowledge.internship.vacation.service.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class CompanyDTO implements Serializable {
    Long id;

    @NotNull
    @Size(max = 255)
    String name;

    @NotNull
    @Size(max = 255)
    String address;

    @NotNull
    @Size(max = 10, min = 10)
    String phone;
}
