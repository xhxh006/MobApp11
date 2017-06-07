package com.ma.mj.myapplication22;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    CustomCanvas customCanvas;
    CheckBox checkBox;
    static boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customCanvas = (CustomCanvas) findViewById(R.id.ccanvas);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
                    check = true;
                }
                else {
                    check = false;
                }
            }
        });

    }

    public void onClick(View v) {
        customCanvas.setOperationType((String) v.getTag());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuitem, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.bluring) {
            if (item.isChecked()) {
                item.setChecked(false);
                customCanvas.setOperationType("nobluring");
            } else {
                item.setChecked(true);
                customCanvas.setOperationType("bluring");
            }
        }
        if (item.getItemId() == R.id.coloring) {
            if (item.isChecked()) {
                item.setChecked(false);
                customCanvas.setOperationType("nocoloring");
            }
            else {
                item.setChecked(true);
                customCanvas.setOperationType("coloring");
            }
        }
        if (item.getItemId() == R.id.big) {
            if (item.isChecked()) {
                item.setChecked(false);
                customCanvas.setOperationType("nobig");
            }
            else {
                item.setChecked(true);
                customCanvas.setOperationType("big");
            }
        }
        if (item.getItemId() == R.id.red) {
            customCanvas.setOperationType("red");
        }
        if (item.getItemId() == R.id.blue) {
            customCanvas.setOperationType("blue");
        }
        return super.onOptionsItemSelected(item);
    }
}