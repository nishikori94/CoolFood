package com.example.coolfood.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.coolfood.model.Favourites;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {

    private static final String DB_NAME = "CoolFoodDB.db";
    private static final int DB_VER = 1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    //Favourites
    public void addToFavourites(Favourites favourites) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = String.format("INSERT INTO Favourites(name,description, imgUrl, address, restaurantId, user) VALUES ('%s', '%s', '%s', '%s', '%s', '%s');", favourites.getName(), favourites.getDescription(), favourites.getImgUrl(), favourites.getAddress(), favourites.getRestaurantId(), favourites.getUser());
        sqLiteDatabase.execSQL(query);
    }

    public void removeFromFavourites(String restaurantId) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = String.format("DELETE FROM Favourites WHERE RestaurantId='%s';", restaurantId);
        sqLiteDatabase.execSQL(query);
    }

    public boolean isFavourite(String restaurantId) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = String.format("SELECT * FROM Favourites WHERE RestaurantId='%s';", restaurantId);
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public List<Favourites> getAllFavourites(String user) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        String[] sqlSelect = {"name", "description", "imgUrl", "address", "restaurantId", "user"};
        String sqlTable = "Favourites";

        builder.setTables(sqlTable);
        Cursor cursor = builder.query(db, sqlSelect, "user=?", new String[]{user}, null, null, null);

        final List<Favourites> result = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                result.add(new Favourites(
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("description")),
                        cursor.getString(cursor.getColumnIndex("imgUrl")),
                        cursor.getString(cursor.getColumnIndex("address")),
                        cursor.getString(cursor.getColumnIndex("restaurantId")),
                        cursor.getString(cursor.getColumnIndex("user"))
                ));
            } while (cursor.moveToNext());

        }
        return result;
    }


}
