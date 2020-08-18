package com.mordred.grocerylist.model.db;

import com.mordred.grocerylist.model.MyObjectBox;

import android.content.Context;
import io.objectbox.BoxStore;

public class ObjectBoxStore {

	private static BoxStore boxStore;

	public static void initStore(Context context) {
		if (boxStore != null) {
			throw new UnsupportedOperationException("BoxStore already initialized");
		}

		boxStore = MyObjectBox.builder().androidContext(context.getApplicationContext()).build();
	}

	public static BoxStore getBoxStore() {
		if (boxStore == null) {
			throw new UnsupportedOperationException(
					"BoxStore not yet initialized. Call initStore() first");
		}

		return boxStore;
	}

}
