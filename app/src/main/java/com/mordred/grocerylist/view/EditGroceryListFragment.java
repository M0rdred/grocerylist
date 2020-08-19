package com.mordred.grocerylist.view;

import com.mordred.grocerylist.R;
import com.mordred.grocerylist.viewmodel.EditGroceryListViewModel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class EditGroceryListFragment extends Fragment {

	public static final String TAG = "EditGroceryListFragment";

	private EditGroceryListViewModel viewModel;

	public static EditGroceryListFragment newInstance() {
		return new EditGroceryListFragment();
	}

	@Override
	public View onCreateView(
			@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState
	) {
		return inflater.inflate(R.layout.fragment_edit_grocery_list, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.viewModel = new ViewModelProvider(this).get(EditGroceryListViewModel.class);
		// TODO: Use the ViewModel
	}

}