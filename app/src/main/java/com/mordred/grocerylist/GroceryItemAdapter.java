package com.mordred.grocerylist;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.mordred.grocerylist.GroceryItemAdapter.GroceryItemViewHolder;
import com.mordred.grocerylist.model.GroceryItemEntry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class GroceryItemAdapter extends RecyclerView.Adapter<GroceryItemViewHolder> {

	private Context context;
	private GroceryItemClaimChangeListener stateChangeListener;
	private List<GroceryItemEntry> groceryItemList;

	public GroceryItemAdapter(Context context, GroceryItemClaimChangeListener listener) {
		this.context = context;
		this.stateChangeListener = listener;
	}

	@NonNull
	@Override
	public GroceryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(this.context);
		View view = inflater.inflate(R.layout.layout_groceryitem, parent, false);

		return new GroceryItemViewHolder(view);
	}

	@Override
	public void onBindViewHolder(
			@NonNull GroceryItemViewHolder holder, int position
	) {
		if (CollectionUtils.isNotEmpty(this.groceryItemList)) {
			GroceryItemEntry groceryItem = this.groceryItemList.get(position);

			holder.nameText.setText(groceryItem.getName());
			holder.quantityText.setText(String.valueOf(groceryItem.getQuantity()));
			holder.itemView.setTag(groceryItem.getId());

			holder.buttonClaim.setOnClickListener(
					v -> this.stateChangeListener.claimItem(groceryItem));
			holder.buttonUnclaim.setOnClickListener(
					v -> this.stateChangeListener.unclaimItem(groceryItem));

			if (groceryItem.isClaimed()) {
				holder.buttonClaim.setVisibility(View.GONE);
				holder.buttonUnclaim.setVisibility(View.VISIBLE);
				holder.nameText.setAlpha(0.5F);
				holder.quantityText.setAlpha(0.5F);
			} else {
				holder.buttonClaim.setVisibility(View.VISIBLE);
				holder.buttonUnclaim.setVisibility(View.GONE);
				holder.nameText.setAlpha(1F);
				holder.quantityText.setAlpha(1F);
			}

			if (position % 2 == 1) {
				holder.baseLayout.setBackgroundColor(
						ContextCompat.getColor(this.context, R.color.item_odd_background));
			}
		}
	}

	@Override
	public int getItemCount() { return CollectionUtils.size(this.groceryItemList); }

	public class GroceryItemViewHolder extends ViewHolder {

		public LinearLayout baseLayout;
		public TextView nameText;
		public TextView quantityText;
		public ImageView buttonClaim;
		public ImageView buttonUnclaim;

		public GroceryItemViewHolder(@NonNull View itemView) {
			super(itemView);

			this.baseLayout = itemView.findViewById(R.id.layoutGroceryItem_layoutBase);
			this.nameText = itemView.findViewById(R.id.layoutGroceryItem_textViewName);
			this.quantityText = itemView.findViewById(R.id.layoutGroceryItem_textViewQuantity);
			this.buttonClaim = itemView.findViewById(R.id.layoutGroceryItem_buttonClaim);
			this.buttonUnclaim = itemView.findViewById(R.id.layoutGroceryItem_buttonUnclaim);
		}

	}

	public void setData(
			List<GroceryItemEntry> groceryItemList
	) {this.groceryItemList = groceryItemList;}

	public interface GroceryItemClaimChangeListener {

		void claimItem(GroceryItemEntry item);

		void unclaimItem(GroceryItemEntry item);
	}

}
