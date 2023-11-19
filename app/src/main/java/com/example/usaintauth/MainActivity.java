/*
2023-11-15: Class UsaintLogin
2023-11-20: UsaintLogin Class -> LoginWebPageActivity
 */

package com.example.usaintauth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.login_btn);
        btn.setOnClickListener(v ->
                startActivityForResult(new Intent(MainActivity.this, LoginWebPageActivity.class), 200));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 200 && resultCode == RESULT_OK) {
            //TODO After code merging, second parameter will be changed to MainActivity(==rent_main)
            startActivity(new Intent(MainActivity.this, MainActivity.class));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}