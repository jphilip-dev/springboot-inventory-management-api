package com.jphilips.inventorymanagementapi.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jphilips.inventorymanagementapi.dto.ItemRequestDTO;
import com.jphilips.inventorymanagementapi.dto.ItemResponseDTO;
import com.jphilips.inventorymanagementapi.dto.mapper.ItemMapper;
import com.jphilips.inventorymanagementapi.entity.Item;
import com.jphilips.inventorymanagementapi.exception.custom.ItemNotFoundException;
import com.jphilips.inventorymanagementapi.repository.ItemRepository;

import jakarta.transaction.Transactional;

@Service
public class ItemService {
	private ItemRepository itemRepository;

	public ItemService(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	@Cacheable(value = "items", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
	public Page<ItemResponseDTO> getAllItems(Pageable pageable) {
		System.out.println("inside getAllitems");
		Page<Item> items = itemRepository.findAll(pageable);
		return items.map(item -> ItemMapper.toDto(item));
	}
	
	@Cacheable(value = "item", key = "#id")
	public ItemResponseDTO getItemById(Long id) {
		System.out.println("inside getItemById");
		return ItemMapper.toDto(getById(id));
	}
	
	@Transactional
	@CacheEvict(value = "items", allEntries = true)
	public void addItem(ItemRequestDTO itemRequestDTO) {
		itemRepository.save( ItemMapper.toEntity(itemRequestDTO));
	}
	
	@Transactional
	@CacheEvict(value = "items", allEntries = true)
	@CachePut(value = "item", key = "#id")
	public ItemResponseDTO updateItem(Long id, ItemRequestDTO itemRequestDTO) {
		Item item =  getById(id);
		
		item.setName(itemRequestDTO.getName());
		item.setDescription(itemRequestDTO.getDescription());
		item.setStocks(itemRequestDTO.getStocks());
		item.setPrice(itemRequestDTO.getPrice());
		
		return ItemMapper.toDto(item);
	}
	
	@Transactional
	@CacheEvict(value = {"item", "items"}, key = "#id", allEntries = true)
	public void deleteItem(Long id) {
		Item item = getById(id);
		itemRepository.delete(item);
	}
	
	// Helper methods
	
	private Item getById(Long id) {
		return itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException());
	}
}
