package com.coretech.flashit;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DaoCards {
    @Query("SELECT * FROM cards")
    List<ModelCards> all();

    @Query("SELECT * FROM cards WHERE id = :id LIMIT 1")
    ModelCards find(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void updateOrCreate(ModelCards card);

    @Update
    void update(ModelCards card);

    @Delete
    void delete(ModelCards card);
}
