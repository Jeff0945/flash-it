package com.coretech.flashit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class CreatingCardActivity extends AppCompatActivity {

    ImageView cancel_Button;
    ImageView check_button;
    private String termInputData; //will store term data
    private String descriptionInputData; //will store description data


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_card);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        cancel_Button = findViewById(R.id.cancel_Button);
        check_button = findViewById(R.id.check_button);

        check_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CreatingCardActivity.this, "Check button was clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreatingCardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        cancel_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CreatingCardActivity.this, "Close button was clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreatingCardActivity.this, CreatingCardSetActivity.class);
                startActivity(intent);
            }
        });

        check_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Store the entered data
                termInputData = ((TextInputEditText) findViewById(R.id.termInputEditText)).getText().toString();
                descriptionInputData = ((TextInputEditText) findViewById(R.id.descriptionInputEditText)).getText().toString();

                Toast.makeText(CreatingCardActivity.this, "Check button was clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreatingCardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button add_another_cards = findViewById(R.id.add_another_cards);
        add_another_cards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear the existing input data
                termInputData = null;
                descriptionInputData = null;

                // Clear the input fields
                ((TextInputEditText) findViewById(R.id.termInputEditText)).setText("");
                ((TextInputEditText) findViewById(R.id.descriptionInputEditText)).setText("");

                Toast.makeText(CreatingCardActivity.this, "Add Another Card clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }
}