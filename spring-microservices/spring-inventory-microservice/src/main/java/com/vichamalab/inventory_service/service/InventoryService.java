package com.vichamalab.inventory_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vichamalab.inventory_service.dto.InventoryResponse;
import com.vichamalab.inventory_service.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {
	private final InventoryRepository inventoryRepository;
	
	public List<InventoryResponse> isInStock(List<String> skuCodes) {
		return inventoryRepository.findBySkuCodeIn(skuCodes).stream()
		.map(inventory -> InventoryResponse.builder()
				.skuCode(inventory.getSkuCode())
				.isInStock(inventory.getQuantity()>0)
				.build()
		).toList();
	}

}
