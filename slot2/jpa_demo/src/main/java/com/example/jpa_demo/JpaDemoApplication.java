package com.example.jpa_demo;

import com.example.jpa_demo.pojo.Student;
import com.example.jpa_demo.service.StudentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpaDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaDemoApplication.class, args);
	}
    @Bean
    public CommandLineRunner commandLineRunner(StudentService service) {
        return args -> {
            service.create("Nguyen van a","abt@gmail.com","18");
            service.create("Nguyen van b","csacd@gmail.com","20");
            service.create("Phan van c","ccsat@gmail.com","22");
            service.printAll();
            service.update(1, "Phan Văn T", "luyt_new@gmail.com", "21");

            System.out.println("danh sach sau update");
            service.printAll();
        };
    }
}
