package ru.geekbrains.vklimovich.springmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
1. Добавьте на главную страницу форму с фильтрами. С возможностью указывать минимальную и
максимальную цену искомых товаров
2. * Добавить к фильтрам по цене фильтр по названию, т.е. искать товары в названии которых
есть указанная последовательность симловом (sql оператор Like)
 */



@SpringBootApplication
public class SpringMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMvcApplication.class, args);
	}

}
