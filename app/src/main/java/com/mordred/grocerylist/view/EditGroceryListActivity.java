package com.mordred.grocerylist.view;

import com.mordred.grocerylist.R;
import com.mordred.grocerylist.model.db.ObjectBoxStore;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class EditGroceryListActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_edit_grocery_list);

		FragmentManager manager = this.getSupportFragmentManager();
		Fragment fragment = manager.findFragmentByTag(EditGroceryListFragment.TAG);

		if (fragment == null) {
			fragment = EditGroceryListFragment.newInstance();
		}

		Bundle bundle = new Bundle();

		String bundleKey = this.getString(R.string.bundle_key_grocery_list_id);

		bundle.putLong(
				bundleKey,
				this.getIntent().getLongExtra(bundleKey, ObjectBoxStore.DEFAULT_ENTITY_ID)
		);

		fragment.setArguments(bundle);

		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(R.id.activityEditGroceryList_root, fragment);
		transaction.commit();
	}
}