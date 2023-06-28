package com.coretech.flashit;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DaoCardSets {
    @Query("SELECT * FROM card_sets")
    List<ModelCardSets> all();

    @Query("SELECT * FROM card_sets WHERE id = :id LIMIT 1")
    ModelCards find(long id);

    @Query("SELECT * FROM card_sets ORDER BY id DESC LIMIT 1")
    ModelCardSets latest();

    @Query("SELECT * FROM cards " +
            "WHERE card_set_id = :cardSetsId")
    List<ModelCards> getCards(long cardSetsId);

    @Query("SELECT * FROM categories " +
            "WHERE categories.id = :categoryId " +
            "LIMIT 1")
    ModelCategories category(long categoryId);

    @Query("SELECT * FROM card_sets " +
            "WHERE UPPER(name) LIKE UPPER('%' || :name || '%')")
    List<ModelCardSets> search(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void updateOrCreate(ModelCardSets card);

    @Update
    void update(ModelCardSets card);

    @Delete
    void delete(ModelCardSets card);
}
