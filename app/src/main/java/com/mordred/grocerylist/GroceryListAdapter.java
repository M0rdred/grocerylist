package com.mordred.grocerylist;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.mordred.grocerylist.GroceryListAdapter.GroceryListViewHolder;
import com.mordred.grocerylist.model.GroceryListEntry;
import com.mordred.grocerylist.model.db.ObjectBoxStore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class GroceryListAdapter extends RecyclerView.Adapter<GroceryListViewHolder> {

	private Context context;
	private List<GroceryListEntry> groceryList;
	private OnClickListener groceryListClickListener;

	public GroceryListAdapter(Context context) {
		this.context = context;
	}

	@NonNull
	@Override
	public GroceryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(this.context);
		View view = inflater.inflate(R.layout.layout_grocerylist, parent, false);

		if (this.groceryListClickListener != null) {
			view.setOnClickListener(this.groceryListClickListener);
		}

		return new GroceryListViewHolder(view);
	}

	@Override
	public void onBindViewHolder(
			@NonNull GroceryListViewHolder holder, int position
	) {
		if (CollectionUtils.isNotEmpty(this.groceryList)) {
			GroceryListEntry groceryList = this.groceryList.get(position);
			holder.nameText.setText(groceryList.getName());
			holder.itemView.setTag(groceryList.getId());
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

	public void setOnClickListener(
			OnClickListener onClickListener
	) { this.groceryListClickListener = onClickListener; }

	public long getGroceryListId(int position) {
		if (CollectionUtils.isEmpty(this.groceryList)) {
			return ObjectBoxStore.DEFAULT_ENTITY_ID;
		} else {
			return this.groceryList.get(position).getId();
		}
	}
}
