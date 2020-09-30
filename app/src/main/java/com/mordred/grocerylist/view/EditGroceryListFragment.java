package com.mordred.grocerylist.view;

import org.apache.commons.lang3.StringUtils;

import com.mordred.grocerylist.R;
import com.mordred.grocerylist.model.GroceryItemEntry;
import com.mordred.grocerylist.model.GroceryListEntry;
import com.mordred.grocerylist.model.GroceryListEntry_;
import com.mordred.grocerylist.model.db.ObjectBoxStore;
import com.mordred.grocerylist.viewmodel.EditGroceryListViewModel;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import io.objectbox.Box;

public class EditGroceryListFragment extends ViewGroceryListFragment {

	public static final String TAG = "EditGroceryListFragment";

	private EditGroceryListViewModel viewModel;

	private EditText editTextListName;
	private EditText editTextProductName;
	private TextView textAmount;

	public static EditGroceryListFragment newInstance() {
		return new EditGroceryListFragment();
	}

	@Override
	protected void initLayout(View fragmentView) {
		super.initLayout(fragmentView);

		TextView textViewListName = fragmentView.findViewById(
				R.id.fragEditGroceryList_textViewListName);
		this.editTextListName = fragmentView.findViewById(
				R.id.fragEditGroceryList_editTextListName);
		this.editTextProductName = fragmentView.findViewById(
				R.id.fragEditGroceryList_editTextProductName);
		this.textAmount = fragmentView.findViewById(R.id.fragEditGroceryList_textAmount);
		Button buttonIncrease = fragmentView.findViewById(R.id.fragEditGroceryList_buttonIncrease);
		Button buttonDecrease = fragmentView.findViewById(R.id.fragEditGroceryList_buttonDecrease);
		Button buttonAdd = fragmentView.findViewById(R.id.fragEditGroceryList_buttonAdd);

		buttonIncrease.setOnClickListener(v -> this.increaseAmount());
		buttonDecrease.setOnClickListener(v -> this.decreaseAmount());
		buttonAdd.setOnClickListener(v -> this.addNewItem());

		textViewListName.setVisibility(View.GONE);
		this.editTextListName.setVisibility(View.VISIBLE);
		this.editTextProductName.setVisibility(View.VISIBLE);
		this.textAmount.setVisibility(View.VISIBLE);
		buttonIncrease.setVisibility(View.VISIBLE);
		buttonDecrease.setVisibility(View.VISIBLE);
		buttonAdd.setVisibility(View.VISIBLE);
	}

	private void increaseAmount() {
		this.textAmount.setText(String.valueOf(this.getAmountValue() + 1));
	}

	private void decreaseAmount() {
		int amount = this.getAmountValue();
		this.textAmount.setText(String.valueOf(amount > 0 ? amount - 1 : 0));
	}

	private void addNewItem() {
		if (this.groceryListId == 0) {
			this.saveNewGroceryList();
		}

		String productName = this.editTextProductName.getText().toString();
		int amount = this.getAmountValue();

		if (amount > 0 && StringUtils.isNotBlank(productName)) {
			GroceryItemEntry item = new GroceryItemEntry();

			item.setListId(this.groceryListId);
			item.setName(productName);
			item.setQuantity(amount);

			this.viewModel.saveGroceryItem(item);

			this.textAmount.setText("0");
			this.editTextProductName.setText("");
		}
	}

	private void saveNewGroceryList() {
		Box<GroceryListEntry> box = ObjectBoxStore.getBoxStore().boxFor(GroceryListEntry.class);

		GroceryListEntry entry = new GroceryListEntry();

		entry.setId(ObjectBoxStore.DEFAULT_ENTITY_ID);
		entry.setName(this.editTextListName.getText().toString());

		this.groceryListId = box.put(entry);
		this.observeData();
	}

	private int getAmountValue() {
		if (this.textAmount != null) {
			CharSequence text = this.textAmount.getText();

			try {
				return Integer.parseInt(String.valueOf(text));
			} catch (NumberFormatException e) {
				return 0;
			}
		} else {
			return 0;
		}
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.viewModel = new ViewModelProvider(this).get(EditGroceryListViewModel.class);

		this.observeData();
	}

	private void observeData() {
		GroceryListEntry first = ObjectBoxStore.getBoxStore().boxFor(
				GroceryListEntry.class).query().equal(
				GroceryListEntry_.id, this.groceryListId).build().findFirst();

		this.editTextListName.setText(first != null ? first.getName() : "");

		//@formatter:off
		this.viewModel
				.getGroceryItemList(this.groceryListId)
				.observe(this.getViewLifecycleOwner(),
						 groceryItemEntries -> {
								this.groceryItemAdapter.setData(groceryItemEntries);
								this.groceryItemAdapter.notifyDataSetChanged();
						}
		);
		//@formatter:off
	}

}