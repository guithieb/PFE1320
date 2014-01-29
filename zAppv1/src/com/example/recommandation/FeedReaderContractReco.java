package com.example.recommandation;

import android.provider.BaseColumns;

public class FeedReaderContractReco {
	
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
	public FeedReaderContractReco()
	{
		
	}
	
	 /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "recommandation";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_GENRE = "genre";
        
    }
	

}
