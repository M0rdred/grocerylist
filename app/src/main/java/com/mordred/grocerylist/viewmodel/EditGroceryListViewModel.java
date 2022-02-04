package com.mordred.grocerylist.viewmodel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mordred.grocerylist.model.GroceryItemEntry;
import com.mordred.grocerylist.model.GroceryItemEntry_;
import com.mordred.grocerylist.model.ItemName;
import com.mordred.grocerylist.model.db.ObjectBoxStore;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import io.objectbox.Box;
import io.objectbox.android.ObjectBoxLiveData;

public class EditGroceryListViewModel extends ViewModel {

	private Box<GroceryItemEntry> groceryItemBox;
	private Box<ItemName> groceryItemNameBox;

	private LiveData<List<String>> groceryItemNameList;

	public EditGroceryListViewModel() {
		this.groceryItemBox = ObjectBoxStore.getBoxStore().boxFor(GroceryItemEntry.class);
		this.groceryItemNameBox = ObjectBoxStore.getBoxStore().boxFor(ItemName.class);

		this.groceryItemNameList = this.getGroceryItemNameList();
	}

	public LiveData<List<GroceryItemEntry>> getGroceryItemList(Long groceryListId) {
		return new ObjectBoxLiveData<>(
				this.groceryItemBox.query().equal(GroceryItemEntry_.listId, groceryListId).build());
	}

	public Long saveGroceryItem(GroceryItemEntry groceryItem) {
		long groceryItemId = this.groceryItemBox.put(groceryItem);

		if (!this.groceryItemNameList.getValue().contains(groceryItem.getName())) {
			this.groceryItemNameBox.put(new ItemName(groceryItem.getName()));
		}

		return groceryItemId;
	}

	public LiveData<List<String>> getGroceryItemNameList() {
		return Transformations.map(
				new ObjectBoxLiveData<>(this.groceryItemNameBox.query().build()),
				this::mapItemNamesToString
		);
	}

	private List<String> mapItemNamesToString(List<ItemName> data) {
		Set<String> set = new HashSet<>();
		for (ItemName itemName : data) {
			String name = itemName.getName();
			set.add(name);
		}

		return new ArrayList<>(set);
	}
}