package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;


    private TextView mOriginTV;
    private TextView mOriginLabelTV;
    private TextView mDescriptionTV;
    private TextView mIngredientsTV;
    private TextView mAlsoKnownAsTV;
    private TextView mAlsoKnownAsLabelTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mOriginTV = findViewById(R.id.origin_tv);
        mOriginLabelTV = findViewById(R.id.originLabelTv);
        mDescriptionTV = findViewById(R.id.description_tv);
        mIngredientsTV = findViewById(R.id.ingredients_tv);
        mAlsoKnownAsTV = findViewById(R.id.also_known_tv);
        mAlsoKnownAsLabelTV = findViewById(R.id.alsoKnownasLabel);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
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

        if (sandwich.getPlaceOfOrigin() != null && !(sandwich.getPlaceOfOrigin().isEmpty())) {
            makeVisible(mOriginLabelTV);
            makeVisible(mOriginTV);
            mOriginTV.setText(sandwich.getPlaceOfOrigin());
        }

        mDescriptionTV.setText(sandwich.getDescription());

        if (sandwich.getAlsoKnownAs() != null && !(sandwich.getAlsoKnownAs().isEmpty())) {
            makeVisible(mAlsoKnownAsLabelTV);
            makeVisible(mAlsoKnownAsTV);

            for (String knownAsString : sandwich.getAlsoKnownAs()) {
                mAlsoKnownAsTV.append((knownAsString) + "\n");
            }
        }

        StringBuilder strBuilder = new StringBuilder();

        for (int i = 0; i < sandwich.getIngredients().size(); i++){
            strBuilder.append(sandwich.getIngredients().get(i));
            strBuilder.append(", ");
        }
        strBuilder.setLength(strBuilder.length() - 2);
        mIngredientsTV.setText(strBuilder.toString());
    }

    private void makeVisible(TextView textView) {
        textView.setVisibility(View.VISIBLE);
    }
}
