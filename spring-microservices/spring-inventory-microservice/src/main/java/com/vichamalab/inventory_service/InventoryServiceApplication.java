package com.vichamalab.inventory_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.vichamalab.inventory_service.repository.InventoryRepository;

@SpringBootApplication
public class InventoryServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		return args -> {
			/*Inventory inventory = new Inventory();
			inventory.setSkuCode("IPHONE13");
			inventory.setQuantity(100);
			inventoryRepository.save(inventory);
			
			Inventory inventory2 = new Inventory();
			inventory.setSkuCode("ANDROID");
			inventory.setQuantity(0);
			inventoryRepository.save(inventory2);
			*/
		};		
	}
}
