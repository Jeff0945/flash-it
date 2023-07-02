package com.coretech.flashit;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class CreatingCardActivity extends AppCompatActivity {
    Button add_another_cards;
    ImageView cancel_Button;
    ImageView check_button;
    TextInputEditText termInputField;
    TextInputEditText descriptionInputField;
    private Long cardSetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_card);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        Bundle extras = getIntent().getExtras();
        boolean fromCardViewingTab = extras.containsKey("from-card-viewing-tab");

        cardSetId = extras.getLong("card-set-id");

        add_another_cards = findViewById(R.id.add_another_cards);
        cancel_Button = findViewById(R.id.cancel_Button);
        check_button = findViewById(R.id.check_button);
        termInputField = findViewById(R.id.termInputEditText);
        descriptionInputField = findViewById(R.id.descriptionInputEditText);

        cancel_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;

                if (fromCardViewingTab) {
                    intent = new Intent(CreatingCardActivity.this, CardsViewingTab.class);
                    intent.putExtra("card-set-id", cardSetId);
                } else {
                    intent = new Intent(CreatingCardActivity.this, CreatingCardSetActivity.class);
                }

                startActivity(intent);
            }
        });

        check_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (saveToDatabase(termInputField.getText().toString(), descriptionInputField.getText().toString())) {
                    Intent intent;

                    if (fromCardViewingTab) {
                        intent = new Intent(CreatingCardActivity.this, CardsViewingTab.class);
                        intent.putExtra("card-set-id", cardSetId);
                    } else {
                        intent = new Intent(CreatingCardActivity.this, MainActivity.class);
                    }

                    startActivity(intent);
                }
            }
        });

        add_another_cards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (saveToDatabase(termInputField.getText().toString(), descriptionInputField.getText().toString())) {
                    // Clear the input fields
                    termInputField.setText("");
                    descriptionInputField.setText("");
                }
            }
        });
    }

    private boolean saveToDatabase(String question, String answer) {
        if (question.trim().length() == 0 || answer.trim().length() == 0) {
            Toast.makeText(CreatingCardActivity.this, "Term and Description field is required.", Toast.LENGTH_LONG).show();

            return false;
        }

        disableButtons();

        AppDatabase database = AppDatabase.getInstance(getApplicationContext());

        AsyncTask.execute(() -> {
            database.cards().updateOrCreate(new ModelCards(cardSetId, question, answer));
        });

        enableButtons();

        return true;
    }

    private void disableButtons() {
        check_button.setClickable(false);
        cancel_Button.setClickable(false);
        add_another_cards.setClickable(false);
        add_another_cards.setText("Saving");
    }

    private void enableButtons() {
        check_button.setClickable(true);
        cancel_Button.setClickable(true);
        add_another_cards.setClickable(true);
        add_another_cards.setText("Add another card");
    }
}