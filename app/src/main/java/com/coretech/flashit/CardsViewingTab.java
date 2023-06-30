package com.coretech.flashit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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

        // Define the terms and descriptions for each block
        String[] termsArray = {
                "Term 1", "Term 2",
                "Term 3", "Term 4",
                "Term 5", "Term 6",
                "Term 7", "Term 8",
                "Term 9", "Term 10,",
                "Term 11", "Term 12" };
        String[] descriptionArray = {
                "Description 1", "Description 2",
                "Description 3", "Description 4",
                "Description 5", "Description 6",
                "Description 7", "Description 8",
                "Description 9", "Description 10",
                "Description 11", "Description 12"
        };

        // Create the GridAdapter and set it to the RecyclerView
        GridAdapter gridAdapter = new GridAdapter(termsArray, descriptionArray);
        RecyclerView recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setAdapter(gridAdapter);

        // Add spacing between the items in the grid
        int spanCount = 2; // Number of columns in the grid
        int spacing = 16; // Spacing between items in pixels
        boolean includeEdge = true; // Whether to include spacing at the edges of the grid
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

    }
}