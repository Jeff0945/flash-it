package com.coretech.flashit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CardLayoutShape extends AppCompatActivity {

    Button reviewButton;
    Button practiceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_card_shape_layout);

        reviewButton = findViewById(R.id.reviewButton);
        practiceButton = findViewById(R.id.practiceButton);

        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CardLayoutShape.this, PracticeActivity.class);
                startActivity(intent);
            }
        });
    }
}
