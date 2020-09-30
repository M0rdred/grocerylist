package com.mordred.grocerylist.model.db;

import com.mordred.grocerylist.model.MyObjectBox;

import android.content.Context;
import io.objectbox.BoxStore;

public class ObjectBoxStore {

	public static final Long DEFAULT_ENTITY_ID = 0L;

	private static BoxStore boxStore;

	public static void initStore(Context context) {
		if (ObjectBoxStore.boxStore == null) {
			ObjectBoxStore.boxStore = MyObjectBox.builder().androidContext(
					context.getApplicationContext()).build();
		}
	}

	public static BoxStore getBoxStore() {
		if (ObjectBoxStore.boxStore == null) {
			throw new UnsupportedOperationException(
					"BoxStore not yet initialized. Call initStore() first");
		}

		return ObjectBoxStore.boxStore;
	}

}
