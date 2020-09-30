package com.mordred.grocerylist.view;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mordred.grocerylist.GroceryListAdapter;
import com.mordred.grocerylist.R;
import com.mordred.grocerylist.model.db.ObjectBoxStore;
import com.mordred.grocerylist.viewmodel.GroceryListViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View.OnClickListener;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class MainActivity extends AppCompatActivity {

	private RecyclerView recyclerView;
	private GroceryListAdapter groceryListAdapter;
	private GroceryListViewModel groceryListViewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);

		ObjectBoxStore.initStore(this);

		this.initLayout();
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.initData();
	}

	private void initLayout() {
		this.recyclerView = this.findViewById(R.id.activityMain_recyclerView);
		this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
		this.groceryListAdapter = new GroceryListAdapter(this);
		this.groceryListAdapter.setOnClickListener(this.createGroceryListClickListener());
		this.recyclerView.setAdapter(this.groceryListAdapter);

		FloatingActionButton actionButton = this.findViewById(R.id.activityMain_actionButton);
		actionButton.setOnClickListener(view -> this.startGroceryListActivity(null));

		this.createSliding();
	}

	private void initData() {
		this.groceryListViewModel = new ViewModelProvider(this).get(GroceryListViewModel.class);
		this.groceryListViewModel.getGroceryList().observe(this, data -> {
			this.groceryListAdapter.setData(data);
			this.groceryListAdapter.notifyDataSetChanged();
		});
	}

	private OnClickListener createGroceryListClickListener() {
		return view -> {
			long groceryListId = this.groceryListAdapter.getGroceryListId(
					this.recyclerView.getChildLayoutPosition(view));

			this.startGroceryListActivity(groceryListId);
		};
	}

	private void startGroceryListActivity(Long groceryListId) {
		Intent intent;
		if (groceryListId == null) {
			intent = new Intent(this, EditGroceryListActivity.class);
		} else {
			intent = new Intent(this, ViewGroceryListActivity.class);
			intent.putExtra(this.getString(R.string.bundle_key_grocery_list_id), groceryListId);
		}

		this.startActivity(intent);
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
				MainActivity.this.removeListEntry((Long) viewHolder.itemView.getTag());
			}
		}).attachToRecyclerView(this.recyclerView);
	}

	private void removeListEntry(long id) {
		this.groceryListViewModel.removeGroceryList(id);
	}
}
