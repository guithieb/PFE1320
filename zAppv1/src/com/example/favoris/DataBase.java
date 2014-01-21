package com.example.favoris;

public class DataBase {
	
	public static String id;
	public static boolean favorite;
	public DataBase(){
		
	}
	public static String getId() {
		return id;
	}
	public static void setId(String id) {
		DataBase.id = id;
	}
	public static boolean isFavorite() {
		return favorite;
	}
	public static void setFavorite(boolean favorite) {
		DataBase.favorite = favorite;
	}
	
}
