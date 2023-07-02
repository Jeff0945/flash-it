package com.coretech.flashit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

public class CardsViewingTab extends AppCompatActivity {

    ImageView cancel_Button;
    Button add_cards;
    Button reviewButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_viewing_tab);

        cancel_Button = findViewById(R.id.cancel_Button);
        add_cards = findViewById(R.id.add_cards);
        reviewButton = findViewById(R.id.reviewButton);

        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CardsViewingTab.this, "Review button was clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CardsViewingTab.this, ReviewActivity.class);
                startActivity(intent);
            }
        });

        add_cards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CardsViewingTab.this, CreatingCardActivity.class);
                intent.putExtra("card-set-id", getIntent().getExtras().getLong("card-set-id"));

                startActivity(intent);
            }
        });

        cancel_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CardsViewingTab.this, "Close button was clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CardsViewingTab.this, MainActivity.class);
                startActivity(intent);
            }
        });

        AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
        long cardSetId = getIntent().getExtras().getLong("card-set-id");

        AsyncTask.execute(() -> {
            List<ModelCards> cards = appDatabase.cardSets().getCards(cardSetId);

            if (cards.isEmpty()) {
                // Handle the case where there are no cards
                runOnUiThread(() -> {
                    Toast.makeText(getApplicationContext(), "Card set is empty", Toast.LENGTH_SHORT).show();// Display appropriate message or take necessary action
                });
                return;
            }

            String[] termsArray = new String[cards.size()];
            String[] descriptionArray = new String[cards.size()];

            for (int i = 0; i < cards.size(); i++) {
                ModelCards card = cards.get(i);
                termsArray[i] = card.question;
                descriptionArray[i] = card.answer;
            }

            runOnUiThread(() -> {
                GridAdapter gridAdapter = new GridAdapter(termsArray, descriptionArray);
                RecyclerView recyclerView = findViewById(R.id.recyclerView2);
                recyclerView.setAdapter(gridAdapter);

                int spanCount = 2;
                int spacing = 16;
                boolean includeEdge = true;
                recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
            });
        });
    }
}