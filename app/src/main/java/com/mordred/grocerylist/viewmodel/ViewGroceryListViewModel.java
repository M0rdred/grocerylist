package com.mordred.grocerylist.viewmodel;

import com.mordred.grocerylist.model.GroceryItemEntry;
import com.mordred.grocerylist.model.GroceryItemEntry_;
import com.mordred.grocerylist.model.db.ObjectBoxStore;

import androidx.lifecycle.ViewModel;
import io.objectbox.Box;
import io.objectbox.android.ObjectBoxLiveData;

public class ViewGroceryListViewModel extends ViewModel {

	private Box<GroceryItemEntry> groceryItemBox;

	public ViewGroceryListViewModel() {
		this.groceryItemBox = ObjectBoxStore.getBoxStore().boxFor(GroceryItemEntry.class);
	}

	public ObjectBoxLiveData<GroceryItemEntry> getGroceryItemList(Long groceryListId) {
		return new ObjectBoxLiveData<>(
				this.groceryItemBox.query().equal(GroceryItemEntry_.listId, groceryListId).build());
	}


	public long saveGroceryItem(GroceryItemEntry itemEntry) {
		return this.groceryItemBox.put(itemEntry);
	}
}