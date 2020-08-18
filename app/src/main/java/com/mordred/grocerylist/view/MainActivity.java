package com.mordred.grocerylist.view;

import com.mordred.grocerylist.GroceryListAdapter;
import com.mordred.grocerylist.R;
import com.mordred.grocerylist.model.db.ObjectBoxStore;
import com.mordred.grocerylist.viewmodel.GroceryListViewModel;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

	private RecyclerView recyclerView;
	private GroceryListAdapter groceryListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);

		ObjectBoxStore.initStore(this);

		this.initLayout();
		this.initData();
	}

	private void initLayout() {
		this.recyclerView = this.findViewById(R.id.activityMain_recyclerView);
		this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
		this.groceryListAdapter = new GroceryListAdapter(this);
		this.recyclerView.setAdapter(this.groceryListAdapter);
	}

	private void initData() {
		GroceryListViewModel groceryListViewModel = new ViewModelProvider(this).get(
				GroceryListViewModel.class);
		groceryListViewModel.getGroceryList().observe(this, data -> {
			this.groceryListAdapter.setData(data);
		});
	}
}
