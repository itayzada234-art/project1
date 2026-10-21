package com.example.project1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LAST_NAME = "last_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Name input section views
        EditText etFirstName = findViewById(R.id.etFirstName);
        EditText etLastName = findViewById(R.id.etLastName);
        Button btnSubmitName = findViewById(R.id.btnSubmitName);
        TextView tvFullNameResult = findViewById(R.id.tvFullNameResult);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Ensure input fields start completely empty
        if (etFirstName != null) {
            etFirstName.setText("");
        }
        if (etLastName != null) {
            etLastName.setText("");
        }

        // Load saved greeting on startup (without pre-filling input fields)
        String savedFirstName = sharedPreferences.getString(KEY_FIRST_NAME, "");
        String savedLastName = sharedPreferences.getString(KEY_LAST_NAME, "");

        if (!savedFirstName.isEmpty() && !savedLastName.isEmpty()) {
            if (tvFullNameResult != null) {
                tvFullNameResult.setText(getString(R.string.full_name_format, savedFirstName, savedLastName));
                tvFullNameResult.setVisibility(View.VISIBLE);
            }
        }

        // Handle Submit Name click
        if (btnSubmitName != null) {
            btnSubmitName.setOnClickListener(v -> {
                String firstName = etFirstName != null ? etFirstName.getText().toString().trim() : "";
                String lastName = etLastName != null ? etLastName.getText().toString().trim() : "";

                if (!firstName.isEmpty() && !lastName.isEmpty()) {
                    // Save to SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_FIRST_NAME, firstName);
                    editor.putString(KEY_LAST_NAME, lastName);
                    editor.apply();

                    if (tvFullNameResult != null) {
                        tvFullNameResult.setText(getString(R.string.full_name_format, firstName, lastName));
                        tvFullNameResult.setVisibility(View.VISIBLE);
                    }

                    // Clear input fields after submitting
                    etFirstName.setText("");
                    etLastName.setText("");
                } else {
                    if (tvFullNameResult != null) {
                        tvFullNameResult.setText(getString(R.string.fill_both_names_error));
                        tvFullNameResult.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }
}