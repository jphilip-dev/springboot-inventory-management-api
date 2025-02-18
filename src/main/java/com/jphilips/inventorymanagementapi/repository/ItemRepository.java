package com.jphilips.inventorymanagementapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jphilips.inventorymanagementapi.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{

}
