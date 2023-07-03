package com.coretech.flashit;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "cards")
public class ModelCards {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "card_set_id")
    public long cardSetId;

    @ColumnInfo(name = "question")
    public String question;

    @ColumnInfo(name = "answer")
    public String answer;

    public ModelCards(long id, long cardSetId, String question, String answer) {
        this.id = id;
        this.cardSetId = cardSetId;
        this.question = question;
        this.answer = answer;
    }

    @Ignore
    public ModelCards(long cardSetId, String question, String answer) {
        this.cardSetId = cardSetId;
        this.question = question;
        this.answer = answer;
    }
}
