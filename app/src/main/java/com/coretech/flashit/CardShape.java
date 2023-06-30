package com.coretech.flashit;

public class CardShape {
    private ModelCardSets cardSet;

    public CardShape(ModelCardSets cardSet) {
        this.cardSet = cardSet;
    }

    public String getSubject() {
        return cardSet.name;
    }

    public long getId() {
        return cardSet.id;
    }
}
