package az.abbas.product.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GeneralTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeneralTaskApplication.class, args);
	}

}
