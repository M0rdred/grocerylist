package com.mordred.grocerylist;

import java.util.List;

import com.mordred.grocerylist.GroceryItemNameAdapter.GroceryItemNameViewHolder;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class GroceryItemNameAdapter extends RecyclerView.Adapter<GroceryItemNameViewHolder> {

	@NonNull
	@Override
	public GroceryItemNameViewHolder onCreateViewHolder(
			@NonNull ViewGroup parent, int viewType
	) {
		return null;
	}

	@Override
	public int getItemCount() {
		return 0;
	}

	@Override
	public void onBindViewHolder(@NonNull GroceryItemNameViewHolder holder, int position) {

	}

	public void setData(List<String> groceryItemNames) {
	}

	public class GroceryItemNameViewHolder extends ViewHolder {

		public GroceryItemNameViewHolder(@NonNull View itemView) {
			super(itemView);
		}
	}
}
