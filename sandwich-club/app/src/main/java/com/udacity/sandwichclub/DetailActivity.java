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

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

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
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        //Iterate through the two list types and append to their respective textview
        TextView mAlsoKnownAsTextView = (TextView) findViewById(R.id.also_known_tv);
        int lengthOfAKAList = sandwich.getAlsoKnownAs().size();
        for(int i = 0; i < lengthOfAKAList; i++) {
            mAlsoKnownAsTextView.append(sandwich.getAlsoKnownAs().get(i) + "\n");
        }
        TextView mIngredients = (TextView) findViewById(R.id.ingredients_tv);
        int lengthOfIngredientsList = sandwich.getIngredients().size();
        for(int i = 0; i < lengthOfIngredientsList; i++) {
            mIngredients.append(sandwich.getIngredients().get(i) + "\n");
        }

        //Get and set values
        TextView mPlaceOfOriginTextView = (TextView) findViewById(R.id.origin_tv);
        mPlaceOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        TextView mDescription = (TextView) findViewById(R.id.description_tv);
        mDescription.setText(sandwich.getDescription());
    }
}
