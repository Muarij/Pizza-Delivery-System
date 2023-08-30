package com.PizzaZone.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.PizzaZone.entities.Item;

public interface ItemDao extends JpaRepository<Item, Integer>{
	List<Item> findByType(String type);
	@Query(value="SELECT * from item WHERE type like '%veg'",nativeQuery = true)
	List<Item> findAllPizza();
	Optional<Item> findByItemid(int id);
	@Query(value = "DELETE FROM item WHERE itemid = ?1", nativeQuery = true)
	void deleteByItemId(Integer itemId);
}
