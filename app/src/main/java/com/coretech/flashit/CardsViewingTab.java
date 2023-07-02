package com.coretech.flashit;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardsViewingTab extends AppCompatActivity {

    ImageView cancel_Button;
    Button add_cards;
    Button reviewButton;
    private long cardSetId;
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
                launchReviewActivity();
            }
        });

        add_cards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CardsViewingTab.this, CreatingCardActivity.class);
                intent.putExtra("card-set-id", getIntent().getExtras().getLong("card-set-id"));
                intent.putExtra("from-card-viewing-tab", true);

                startActivity(intent);
            }
        });

        cancel_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CardsViewingTab.this, MainActivity.class);
                startActivity(intent);
            }
        });

        AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
        cardSetId = getIntent().getExtras().getLong("card-set-id");

        AsyncTask.execute(() -> {
            List<ModelCards> cards = appDatabase.cardSets().getCards(cardSetId);

            runOnUiThread(() -> {
                if (cards.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Card set is empty", Toast.LENGTH_SHORT).show();// Display appropriate message or take necessary action
                } else {
                    GridAdapter gridAdapter = new GridAdapter(cards);
                    RecyclerView recyclerView = findViewById(R.id.recyclerView2);
                    recyclerView.setAdapter(gridAdapter);

                    int spanCount = 2;
                    int spacing = 16;
                    boolean includeEdge = true;
                    recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
                }
            });
        });
    }

    private void launchReviewActivity() {
        Intent intent = new Intent(CardsViewingTab.this, ReviewActivity.class);
        intent.putExtra("card-set-id", cardSetId);
        startActivity(intent);
    }
}