package pl.myrecipebasket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * A starting class of <em>MyRecipeBasket</em> application. Contains main method that creates  
 * Spring context, populates it with Spring components after conducting component scan
 * in child packages of package pl.myrecipebasket. 
 *
 */

@SpringBootApplication
public class MyrecipebasketApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyrecipebasketApplication.class, args);
	}
}

