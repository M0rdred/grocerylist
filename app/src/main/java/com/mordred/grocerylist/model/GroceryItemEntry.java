package com.mordred.grocerylist.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class GroceryItemEntry {

	@Id
	private Long id;
	private String name;
	private Integer quantity;
	private boolean claimed;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public boolean isClaimed() {
		return claimed;
	}

	public void setClaimed(boolean claimed) {
		this.claimed = claimed;
	}
}
