package gr.knowledge.internship.vacation.service.mapper;

import gr.knowledge.internship.vacation.domain.Company;
import gr.knowledge.internship.vacation.service.dto.CompanyDTO;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Component;

@Component
public class CompanyMapper extends ModelMapper{

    public CompanyDTO toDto(Company company){
        return this.map(company, CompanyDTO.class);
    }

    public Company toEntity(CompanyDTO companyDTO){
        return this.map(companyDTO, Company.class);
    }
}
