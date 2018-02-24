package com.adel.sandwichclub;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adel.sandwichclub.model.Sandwich;
import com.adel.sandwichclub.utils.JsonUtils;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    TextView tv_main, tv_also, tv_place, tv_ingredients, tv_desc;
    Sandwich sandwich = new Sandwich();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        tv_main = findViewById(R.id.main_name_tv);
        tv_also = findViewById(R.id.also_known_as_tv);
        tv_place = findViewById(R.id.place_of_origin_tv);
        tv_ingredients = findViewById(R.id.ingredients_tv);
        tv_desc = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = 0;
        if (intent != null) {
            position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        }
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
        tv_main.setText(sandwich.getMainName());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tv_also.setText(String.join(",", sandwich.getAlsoKnownAs()));
        }else {
            tv_also.setText(sandwich.getAlsoKnownAs().toString());
        }

        tv_place.setText(sandwich.getPlaceOfOrigin());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tv_ingredients.setText(String.join(", ", sandwich.getIngredients()));
        }else {
            tv_ingredients.setText(sandwich.getIngredients().toString());
        }

        tv_desc.setText(sandwich.getDescription());
    }
}
