package com.mordred.grocerylist.view;

import java.util.Collections;

import org.apache.commons.lang3.StringUtils;

import com.mordred.grocerylist.GroceryItemAdapter;
import com.mordred.grocerylist.GroceryItemAdapter.GroceryItemClaimChangeListener;
import com.mordred.grocerylist.R;
import com.mordred.grocerylist.model.GroceryItemEntry;
import com.mordred.grocerylist.model.GroceryListEntry;
import com.mordred.grocerylist.model.GroceryListEntry_;
import com.mordred.grocerylist.model.db.ObjectBoxStore;
import com.mordred.grocerylist.viewmodel.ViewGroceryListViewModel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class ViewGroceryListFragment extends Fragment implements GroceryItemClaimChangeListener {

	public static final String TAG = "ViewGroceryListFragment";

	protected GroceryItemAdapter groceryItemAdapter;

	protected long groceryListId;

	private TextView textViewListName;
	private RecyclerView recyclerView;
	private ViewGroceryListViewModel viewModel;

	public static ViewGroceryListFragment newInstance() {
		return new ViewGroceryListFragment();
	}

	@Override
	public View onCreateView(
			@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState
	) {
		this.groceryListId = this.getArguments().getLong(
				this.getString(R.string.bundle_key_grocery_list_id));

		View fragmentView = inflater.inflate(R.layout.fragment_edit_grocery_list, container, false);

		this.initLayout(fragmentView);

		return fragmentView;
	}

	protected void initLayout(View fragmentView) {
		this.recyclerView = fragmentView.findViewById(R.id.fragEditGroceryList_recyclerView);
		this.recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

		this.groceryItemAdapter = new GroceryItemAdapter(this.getActivity(), this);
		this.recyclerView.setAdapter(this.groceryItemAdapter);

		this.textViewListName = fragmentView.findViewById(
				R.id.fragEditGroceryList_textViewListName);

		this.createSliding();
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.viewModel = new ViewModelProvider(this).get(ViewGroceryListViewModel.class);

		GroceryListEntry first = ObjectBoxStore.getBoxStore().boxFor(
				GroceryListEntry.class).query().equal(
				GroceryListEntry_.id, this.groceryListId).build().findFirst();

		this.textViewListName.setText(first != null ? first.getName() : "");

		//@formatter:off
		this.viewModel
				.getGroceryItemList(this.groceryListId)
				.observe(this.getViewLifecycleOwner(),
						 groceryItemEntries -> {
								Collections.sort(groceryItemEntries, this::compareGroceryItems);

								this.groceryItemAdapter.setData(groceryItemEntries);
								this.groceryItemAdapter.notifyDataSetChanged();
						}
		);
		//@formatter:on
	}

	private void createSliding() {
		new ItemTouchHelper(new SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
			@Override
			public boolean onMove(
					@NonNull RecyclerView recyclerView, @NonNull ViewHolder viewHolder,
					@NonNull ViewHolder target
			) {
				return false;
			}

			@Override
			public void onSwiped(
					@NonNull ViewHolder viewHolder, int direction
			) {
				ViewGroceryListFragment.this.removeListEntry((Long) viewHolder.itemView.getTag());
			}
		}).attachToRecyclerView(this.recyclerView);
	}

	private void removeListEntry(long id) {
		this.viewModel.removeGroceryItem(id);
	}

	@Override
	public void claimItem(GroceryItemEntry item) {
		item.setClaimed(true);
		this.viewModel.saveGroceryItem(item);
	}

	@Override
	public void unclaimItem(GroceryItemEntry item) {
		item.setClaimed(false);
		this.viewModel.saveGroceryItem(item);
	}

	private int compareGroceryItems(GroceryItemEntry item1, GroceryItemEntry item2) {
		if (item1.isClaimed() == item2.isClaimed()) {
			return StringUtils.compareIgnoreCase(item1.getName(), item2.getName());
		} else if (item1.isClaimed()) {
			return 1;
		} else {
			return -1;
		}
	}
}