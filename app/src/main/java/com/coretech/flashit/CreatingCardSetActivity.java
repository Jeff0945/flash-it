package com.coretech.flashit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coretech.flashit.ui.home.HomeFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import com.coretech.flashit.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CreatingCardSetActivity extends AppCompatActivity {

    ImageView cancel_Button;
    ImageView check_button;
    Button add_cards;
    TextView category_btn;
    ListView list_view;
    ArrayList<String> list;
    Button confirmButton;

    ArrayAdapter<String> arrayAdapter;

    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "CategoryPreferences";
    private static final String KEY_CATEGORIES = "categories";
    private List<CardShape> cardShapes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_card_set);

        category_btn = findViewById(R.id.category_btn);
        add_cards = findViewById(R.id.add_cards);
        cancel_Button = findViewById(R.id.cancel_Button);
        check_button = findViewById(R.id.check_button);
        // Initialize shared preferences
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        add_cards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreatingCardSetActivity.this, CreatingCardActivity.class);
                startActivity(intent);

                String subject = "Sample Subject"; // Replace with the actual subject
                CardShape cardShape = new CardShape(subject);
                cardShapes.add(cardShape);

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

    private boolean isDialogShowing = false; // Add this variable

    private void showCategoryDialog() {

        if (isDialogShowing) {
            return; // Dialog is already showing, exit the method
        }

        isDialogShowing = true; // Set the flag to indicate dialog is showing

        final Dialog dialog = new Dialog(this);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                isDialogShowing = false; // Reset the flag when the dialog is dismissed
            }
        });

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.modal_creatingcategory);

        ListView listView = dialog.findViewById(R.id.list_view);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);
        FloatingActionButton add_category = dialog.findViewById(R.id.fab_category);
        TextView updateTextView = findViewById(R.id.updateTextView);

        list = getCategories();

        // Move "None" category to the top if it's present in the list
        if (list.contains("None")) {
            list.remove("None");
        }
        list.add(0, "None");

        arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1, list);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String selectedItem = list.get(position);
                updateTextView.setText(selectedItem);
                dialog.dismiss();
            }
        });

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

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {

                String selectedCategory = list.get(position);
                if (selectedCategory.equals("None")) {
                    return true; // Skip the deletion process for "None" category
                }

                // Prompt the user with a confirmation dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(CreatingCardSetActivity.this);
                builder.setTitle("Delete Category")
                        .setMessage("Are you sure you want to delete this category?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String selectedCategory = list.get(position);
                                removeCategory(selectedCategory);
                                Toast.makeText(CreatingCardSetActivity.this, "Category deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();

                return true;
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.CENTER);
    }

    private boolean isAddCategoryDialogShowing = false; // Add this variable

    private void showAddCategoryDialog() {
        if (isAddCategoryDialogShowing) {
            return; // Dialog is already showing, exit the method
        }

        isAddCategoryDialogShowing = true; // Set the flag to indicate dialog is showing

        final Dialog dialog = new Dialog(this);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                isAddCategoryDialogShowing = false; // Reset the flag when the dialog is dismissed
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.modal_addingcategory);

        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);
        confirmButton = dialog.findViewById(R.id.confirmButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve the category entered by the user
                TextInputEditText categoryEditText = dialog.findViewById(R.id.categoryInputEditText);
                String subject = categoryEditText.getText().toString();

                // Check if the category is empty
                if (!subject.isEmpty()) {
                    // Save the category to shared preferences
                    list.add(subject);
                    arrayAdapter.notifyDataSetChanged();
                    saveCategories(list);
                    Toast.makeText(CreatingCardSetActivity.this, "Category: " + subject, Toast.LENGTH_SHORT).show();

                    // Dismiss the dialog
                    dialog.dismiss();
                } else {
                    // Category is empty, display an error message
                    Toast.makeText(CreatingCardSetActivity.this, "Please enter a category", Toast.LENGTH_SHORT).show();
                }
            }
        });


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.CENTER);
    }

    private void saveCategories(List<String> categories) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> categorySet = new HashSet<>(categories);
        editor.putStringSet(KEY_CATEGORIES, categorySet);
        editor.apply();
    }

    private ArrayList<String> getCategories() {
        Set<String> categorySet = sharedPreferences.getStringSet(KEY_CATEGORIES, null);
        if (categorySet != null) {
            return new ArrayList<>(categorySet);
        } else {
            return new ArrayList<>();
        }
    }

    private void removeCategory(String category) {
        // Remove the category from the list
        list.remove(category);
        arrayAdapter.notifyDataSetChanged();

        // Save the updated categories to shared preferences
        saveCategories(list);
    }

}
