package com.coretech.flashit;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "card_sets")
public class ModelCardSets {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "category_id", defaultValue = "0")
    public long categoryId;

    public ModelCardSets(long id, String name, long categoryId) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
    }

    @Ignore
    public ModelCardSets(String name) {
        this.name = name;
        this.categoryId = 0;
    }
}
