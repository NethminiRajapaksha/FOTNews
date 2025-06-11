package com.example.fotnews;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fotnews.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.util.HashMap;
import java.util.Map;

public class UserDetailsActivity extends AppCompatActivity {
    private boolean passwordVisible = false;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseRef;
    private FirebaseUser currentUser;

    // UI Components
    private TextView tvUsername, tvEmail;
    private EditText etUsername, etEmail, etPassword;
    private LinearLayout layoutInfo, layoutEdit;
    private ImageView ivTogglePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        initializeFirebase();
        initializeUIComponents();
        setupEventListeners();
        loadUserData();
    }

    private void initializeFirebase() {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            finish();
            return;
        }
        String databaseUrl = "https://fotnewsapp-703be-default-rtdb.asia-southeast1.firebasedatabase.app/";
        databaseRef = FirebaseDatabase.getInstance(databaseUrl)
                .getReference("users")
                .child(currentUser.getUid());
    }

    private void initializeUIComponents() {
        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        layoutInfo = findViewById(R.id.layoutInfo);
        layoutEdit = findViewById(R.id.layoutEdit);
        ivTogglePassword = findViewById(R.id.ivTogglePassword);
    }

    private void setupEventListeners() {
        findViewById(R.id.ivBack).setOnClickListener(v -> finish());
        findViewById(R.id.btnEditInfo).setOnClickListener(v -> switchToEditMode());
        findViewById(R.id.btnCancel).setOnClickListener(v -> switchToViewMode());
        ivTogglePassword.setOnClickListener(v -> togglePasswordVisibility());
        findViewById(R.id.btnSave).setOnClickListener(v -> updateUserProfile());
        findViewById(R.id.btnSignOut).setOnClickListener(v -> showSignOutDialog());
    }

    private void loadUserData() {
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    updateUIWithUserData(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserDetailsActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUIWithUserData(User user) {
        tvUsername.setText(user.getUsername());
        tvEmail.setText(user.getEmail());
        etUsername.setText(user.getUsername());
        etEmail.setText(user.getEmail());
    }

    private void switchToEditMode() {
        etUsername.setText(tvUsername.getText());
        etEmail.setText(tvEmail.getText());
        etPassword.setText("");

        etEmail.setEnabled(false);
        etEmail.setFocusable(false);
        etEmail.setClickable(false);

        etPassword.setEnabled(false);
        etPassword.setFocusable(false);
        etPassword.setClickable(false);

        layoutInfo.setVisibility(View.GONE);
        layoutEdit.setVisibility(View.VISIBLE);
    }

    private void switchToViewMode() {
        layoutEdit.setVisibility(View.GONE);
        layoutInfo.setVisibility(View.VISIBLE);
    }

    private void togglePasswordVisibility() {
        if (!etPassword.isEnabled()) return;

        passwordVisible = !passwordVisible;
        etPassword.setInputType(passwordVisible ?
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD :
                InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        etPassword.setSelection(etPassword.getText().length());
        ivTogglePassword.setImageResource(
                passwordVisible ? R.drawable.ic_visibility : R.drawable.ic_visibility_off);
    }

    private void updateUserProfile() {
        final String newUsername = etUsername.getText().toString().trim();

        if (newUsername.isEmpty()) {
            Toast.makeText(this, "Username required", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> updates = new HashMap<>();
        updates.put("username", newUsername);

        databaseRef.updateChildren(updates).addOnCompleteListener(dbTask -> {
            if (dbTask.isSuccessful()) {
                showSuccessAndRefresh("Profile updated successfully");
            } else {
                showError("Database update failed: " + dbTask.getException().getMessage());
            }
        });
    }

    private void showSuccessAndRefresh(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        loadUserData();
        switchToViewMode();
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void showSignOutDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.custom_signout_dialog, null);

        Button btnYes = dialogView.findViewById(R.id.btnYes);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setCancelable(false);

        AlertDialog dialog = builder.create();

        btnYes.setOnClickListener(v -> {

            mAuth.signOut();
            Toast.makeText(UserDetailsActivity.this, "Signed out successfully", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(UserDetailsActivity.this, WelcomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            dialog.dismiss();
            finish();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            window.setAttributes(wlp);
        }
    }
}
