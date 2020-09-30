package com.mordred.grocerylist.viewmodel;

import com.mordred.grocerylist.model.GroceryItemEntry;
import com.mordred.grocerylist.model.GroceryItemEntry_;
import com.mordred.grocerylist.model.db.ObjectBoxStore;

import androidx.lifecycle.ViewModel;
import io.objectbox.Box;
import io.objectbox.android.ObjectBoxLiveData;

public class EditGroceryListViewModel extends ViewModel {

	private Box<GroceryItemEntry> groceryItemBox;

	public EditGroceryListViewModel() {
		this.groceryItemBox = ObjectBoxStore.getBoxStore().boxFor(GroceryItemEntry.class);
	}

	public ObjectBoxLiveData<GroceryItemEntry> getGroceryItemList(Long groceryListId) {
		return new ObjectBoxLiveData<>(
				this.groceryItemBox.query().equal(GroceryItemEntry_.listId, groceryListId).build());
	}

	public Long saveGroceryItem(GroceryItemEntry groceryItem) {
		return this.groceryItemBox.put(groceryItem);
	}
}