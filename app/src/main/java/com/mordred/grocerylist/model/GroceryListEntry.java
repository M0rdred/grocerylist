package com.mordred.grocerylist.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class GroceryListEntry {

	@Id
	private Long id;
	private String name;

	public Long getId() { return this.id; }

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
