package com.coretech.flashit;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        version = 1,
        entities = {
                ModelCardSets.class,
                ModelCards.class,
                ModelCategories.class
        }
)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "flash_it_db";
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

    public abstract DaoCategories categories();
    public abstract DaoCardSets cardSets();
    public abstract DaoCards cards();
}
