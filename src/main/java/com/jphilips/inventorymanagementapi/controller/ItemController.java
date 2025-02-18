package com.jphilips.inventorymanagementapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jphilips.inventorymanagementapi.dto.ItemRequestDTO;
import com.jphilips.inventorymanagementapi.dto.ItemResponseDTO;
import com.jphilips.inventorymanagementapi.service.ItemService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/items")
@Valid
public class ItemController {

	private ItemService itemService;

	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}

	@GetMapping()
	public Page<ItemResponseDTO> getAllItems(Pageable pageable) {
		return itemService.getAllItems(pageable);
	}

	@GetMapping("/{id}")
	public ItemResponseDTO getItemById(@PathVariable Long id) {
		return itemService.getItemById(id);
	}

	@PostMapping()
	public ResponseEntity<Void> addItem(@Valid @RequestBody ItemRequestDTO itemRequestDTO) {

		itemService.addItem(itemRequestDTO);

		return new ResponseEntity<>(HttpStatus.OK);

	}

	@PutMapping("/{id}")
	public ItemResponseDTO updateItem(@PathVariable Long id, @Valid @RequestBody ItemRequestDTO itemRequestDTO) {
		return itemService.updateItem(id, itemRequestDTO);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteItemById(@PathVariable Long id) {

		itemService.deleteItem(id);

		return new ResponseEntity<>(HttpStatus.OK);

	}

}
