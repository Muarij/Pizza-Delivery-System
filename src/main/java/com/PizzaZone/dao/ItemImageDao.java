package com.PizzaZone.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.PizzaZone.entities.Cart;
import com.PizzaZone.entities.Item;
import com.PizzaZone.entities.ItemImage;
import com.PizzaZone.entities.Users;

public interface ItemImageDao extends JpaRepository<ItemImage, Integer>{
	
	@Query(value = "select * from itemimage where itemId=?1", nativeQuery = true)
	ItemImage findByItemId(Integer itemId);
}
