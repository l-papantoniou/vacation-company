package gr.knowledge.internship.vacation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "gr.knowledge.internship.vacation.repository")
@ServletComponentScan

public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
