package com.coretech.flashit;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DaoCategories {
    @Query("SELECT * FROM categories")
    List<ModelCategories> all();

    @Query("SELECT * FROM categories WHERE id = :id LIMIT 1")
    ModelCategories find(long id);

    @Query("SELECT * FROM card_sets WHERE category_id = :id")
    List<ModelCardSets> cardSets(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void updateOrCreate(ModelCategories card);

    @Update
    void update(ModelCategories card);

    @Delete
    void delete(ModelCategories card);
}
