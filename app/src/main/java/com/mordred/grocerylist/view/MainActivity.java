package com.mordred.grocerylist.view;

import com.mordred.grocerylist.GroceryListAdapter;
import com.mordred.grocerylist.R;
import com.mordred.grocerylist.model.db.ObjectBoxStore;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

	private RecyclerView recyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);

		ObjectBoxStore.initStore(this);

		this.initLayout();

	}

	private void initLayout() {
		this.recyclerView = this.findViewById(R.id.activityMain_recyclerView);
		this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
		GroceryListAdapter groceryListAdapter = new GroceryListAdapter(this);
		this.recyclerView.setAdapter(groceryListAdapter);
	}
}
