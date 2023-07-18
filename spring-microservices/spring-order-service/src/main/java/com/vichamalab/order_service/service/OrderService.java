package com.vichamalab.order_service.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.vichamalab.order_service.dto.InventoryResponse;
import com.vichamalab.order_service.dto.OrderItemRequest;
import com.vichamalab.order_service.dto.OrderRequest;
import com.vichamalab.order_service.entity.Order;
import com.vichamalab.order_service.entity.OrderItem;
import com.vichamalab.order_service.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {
	private final OrderRepository orderRepository;
	private final WebClient.Builder webClientBuilder;

	public void placeOrder(OrderRequest orderRequest) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());

		List<OrderItem> orderItems = orderRequest.getItems().stream().map(o -> map2OrderItem(o)).toList();
		order.setItems(orderItems);
		
		List<String> skuCodes = orderItems.stream().map(OrderItem::getSkuCode).toList();

		InventoryResponse[] inventoryResponses = webClientBuilder.build().get().uri("http://inventory-service/api/inventory",uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
				.retrieve()
				.bodyToMono(InventoryResponse[].class)
				.block();
		
		Boolean result = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);
		
		if (result) {
			orderRepository.save(order);
			log.info("Order was placed sucessfully");
		}else {
			throw new IllegalArgumentException("Product is not in stock");
		}		
	}

	private OrderItem map2OrderItem(OrderItemRequest orderItemRequest) {
		OrderItem orderItem = new OrderItem();
		orderItem.setId(orderItemRequest.getId());
		orderItem.setSkuCode(orderItemRequest.getSkuCode());
		orderItem.setQuantity(orderItemRequest.getQuantity());
		return orderItem;
	}
}
