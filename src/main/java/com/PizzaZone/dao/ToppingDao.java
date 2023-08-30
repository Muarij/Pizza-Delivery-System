package com.PizzaZone.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PizzaZone.entities.Toppings;


public interface ToppingDao extends JpaRepository<Toppings, Integer>{
	Optional<Toppings> findByToppingId(int id);
}
