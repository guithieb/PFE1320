package com.example.favoris;

import android.provider.BaseColumns;

public class FeedReaderContractFavoris {
	
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public FeedReaderContractFavoris() {}
    
    
    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "favoris";
        public static final String COLUMN_NAME_ID = "id";
    }

}
