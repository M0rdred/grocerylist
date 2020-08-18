package com.mordred.grocerylist;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.mordred.grocerylist.GroceryListAdapter.GroceryListViewHolder;
import com.mordred.grocerylist.model.GroceryListEntry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class GroceryListAdapter extends RecyclerView.Adapter<GroceryListViewHolder> {

	private Context context;
	private List<GroceryListEntry> groceryList;

	public GroceryListAdapter(Context context) {
		this.context = context;
	}

	@NonNull
	@Override
	public GroceryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(this.context);
		View view = inflater.inflate(R.layout.layout_grocerylist, parent, false);

		return new GroceryListViewHolder(view);
	}

	@Override
	public void onBindViewHolder(
			@NonNull GroceryListViewHolder holder, int position
	) {
		if (CollectionUtils.isNotEmpty(this.groceryList)) {
			holder.nameText.setText(this.groceryList.get(position).getName());
		}
	}

	@Override
	public int getItemCount() {
		return this.groceryList != null ? this.groceryList.size() : 0;
	}

	public class GroceryListViewHolder extends ViewHolder {

		public TextView nameText;

		public GroceryListViewHolder(@NonNull View itemView) {
			super(itemView);

			this.nameText = itemView.findViewById(R.id.layoutGroceryList_textViewName);
		}

	}

	public void setData(List<GroceryListEntry> groceryList) {this.groceryList = groceryList;}
}
