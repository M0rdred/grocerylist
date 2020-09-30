package com.mordred.grocerylist.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class GroceryItemEntry {

	@Id
	private Long id;
	private Long listId;
	private String name;
	private Integer quantity;
	private boolean claimed;

	public Long getId()                       { return this.id; }

	public void setId(Long id)                { this.id = id; }

	public Long getListId()                   { return this.listId; }

	public void setListId(Long listId)        { this.listId = listId; }

	public String getName()                   { return this.name; }

	public void setName(String name)          { this.name = name; }

	public Integer getQuantity()              { return this.quantity; }

	public void setQuantity(Integer quantity) { this.quantity = quantity; }

	public boolean isClaimed()                { return this.claimed; }

	public void setClaimed(boolean claimed)   { this.claimed = claimed; }
}
