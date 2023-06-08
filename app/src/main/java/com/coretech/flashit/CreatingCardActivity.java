package com.coretech.flashit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class CreatingCardActivity extends AppCompatActivity {

    ImageView cancel_Button;
    ImageView check_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_card);

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
    }
}