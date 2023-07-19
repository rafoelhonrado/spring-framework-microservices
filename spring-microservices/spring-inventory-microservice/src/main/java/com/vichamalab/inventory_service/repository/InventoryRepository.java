package com.vichamalab.inventory_service.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.vichamalab.inventory_service.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
	List<Inventory> findBySkuCodeIn(List<String> skuCodes);
}
