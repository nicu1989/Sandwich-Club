package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mDescription;
    private TextView mOrigin;
    private TextView mIngredients;
    private TextView mAkas;
    private Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        mDescription = (TextView) findViewById(R.id.description_tv);
        mOrigin = (TextView) findViewById(R.id.origin_tv);
        mIngredients = (TextView) findViewById(R.id.ingredients_tv);
        mAkas = (TextView) findViewById(R.id.also_known_tv);


        mDescription.setText(sandwich.getDescription());
        mOrigin.setText(sandwich.getPlaceOfOrigin());

        String toPopulateIngred = "";
        for (int i = 0; i < sandwich.getIngredients().size(); i++)
        {
            toPopulateIngred += sandwich.getIngredients().get(i);
            toPopulateIngred += "\n";
        }
        mIngredients.setText(toPopulateIngred);

        String toPopulateAkas = "";
        for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++)
        {
            toPopulateAkas += sandwich.getAlsoKnownAs().get(i);
            toPopulateAkas += "\n";
        }
        mAkas.setText(toPopulateAkas);
    }
}
