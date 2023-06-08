package com.coretech.flashit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class CreatingCardSetActivity extends AppCompatActivity {

    ImageView cancel_Button;
    ImageView check_button;
    ImageView add_cards;
    TextView category_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_card_set);

        category_btn = findViewById(R.id.category_btn);
        add_cards = findViewById(R.id.add_cards);
        cancel_Button = findViewById(R.id.cancel_Button);
        check_button = findViewById(R.id.check_button);

        add_cards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreatingCardSetActivity.this, CreatingCardActivity.class);
                startActivity(intent);
            }
        });

        check_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CreatingCardSetActivity.this, "Check button was clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreatingCardSetActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        cancel_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CreatingCardSetActivity.this, "Close button was clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreatingCardSetActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        category_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCategoryDialog();
            }
        });

    }

        private void showCategoryDialog() {
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.modal_creatingcategory);

            ImageView cancelButton = dialog.findViewById(R.id.cancelButton);
            FloatingActionButton add_category = dialog.findViewById(R.id.fab_category);

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            add_category.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAddCategoryDialog();
                }
            });

            dialog.show();
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.getWindow().setGravity(Gravity.CENTER);
    }

    private void showAddCategoryDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.modal_addingcategory);

        Button confirmButton = dialog.findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve the category entered by the user
                TextInputEditText categoryEditText = dialog.findViewById(R.id.categoryInputEditText);
                String category = categoryEditText.getText().toString();

                // Perform your desired action with the category
                if (!category.isEmpty()) {
                    // Category is not empty, do something with it
                    Toast.makeText(CreatingCardSetActivity.this, "Category: " + category, Toast.LENGTH_SHORT).show();
                } else {
                    // Category is empty, display an error message
                    Toast.makeText(CreatingCardSetActivity.this, "Please enter a category", Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();
            }
        });

        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.CENTER);
    }

}