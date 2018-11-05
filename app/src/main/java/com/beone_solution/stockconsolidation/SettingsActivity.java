package com.beone_solution.stockconsolidation;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.beone_solution.stockconsolidation.data.remote.ApiUtils;

public class SettingsActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private TextInputEditText etURL;
    private AppCompatButton btSave;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }
        final TextView tvToolbar = toolbar.findViewById(R.id.toolbar_title);
        Typeface font = Typeface.createFromAsset(SettingsActivity.this.getAssets(), "Roboto-Bold.ttf");
        tvToolbar.setTypeface(font);
        tvToolbar.setTextSize(22);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btSave = findViewById(R.id.btSave);
        etURL = findViewById(R.id.etURL);

        SharedPreferences prefs = getSharedPreferences("SETTINGS", MODE_PRIVATE);
        String BASEURL = prefs.getString("BASEURL", "");
        if (!BASEURL.equals("")) {
            ApiUtils.BASE_URL = BASEURL;
            etURL.setText(BASEURL);
        }

        btSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String BASEURL = etURL.getText().toString();
                ApiUtils.BASE_URL = BASEURL;
                SharedPreferences.Editor editor = getSharedPreferences("SETTINGS", MODE_PRIVATE).edit();
                editor.putString("BASEURL", BASEURL);
                editor.apply();
                Toast.makeText(SettingsActivity.this, "API URL Setting has been saved", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
