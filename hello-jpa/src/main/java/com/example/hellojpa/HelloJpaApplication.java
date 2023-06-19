package com.example.hellojpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@SpringBootApplication
public class HelloJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloJpaApplication.class, args);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("sungbae");
		EntityManager em = emf.createEntityManager();
		//code
		em.close();
		emf.close();

	}

}
