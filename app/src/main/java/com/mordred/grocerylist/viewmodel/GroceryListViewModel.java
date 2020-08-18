package com.mordred.grocerylist.viewmodel;

import org.apache.commons.lang3.StringUtils;

import com.mordred.grocerylist.model.GroceryListEntry;
import com.mordred.grocerylist.model.GroceryListEntry_;
import com.mordred.grocerylist.model.db.ObjectBoxStore;

import androidx.lifecycle.ViewModel;
import io.objectbox.Box;
import io.objectbox.android.ObjectBoxLiveData;

public class GroceryListViewModel extends ViewModel {

	private ObjectBoxLiveData<GroceryListEntry> groceryList;
	private Box<GroceryListEntry> groceryListBox;

	public GroceryListViewModel() {
		this.groceryListBox = ObjectBoxStore.getBoxStore().boxFor(GroceryListEntry.class);
		this.groceryList = new ObjectBoxLiveData<>(
				this.groceryListBox.query().notEqual(GroceryListEntry_.name,
													 StringUtils.EMPTY
				).build());
	}

	public ObjectBoxLiveData<GroceryListEntry> getGroceryList() {
		return this.groceryList;
	}
}