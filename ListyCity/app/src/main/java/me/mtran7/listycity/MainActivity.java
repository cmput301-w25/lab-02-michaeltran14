package me.mtran7.listycity;

import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private int selectedCityIndex = -1;
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    Button addBtn;
    Button removeBtn;
    Button submitBtn;

    EditText cityInput;
    ConstraintLayout inputWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeCityList();

        inputWrapper = findViewById(R.id.input_wrapper);
        inputWrapper.setVisibility(View.INVISIBLE); // Default set to invisible
        cityInput = findViewById(R.id.city_input);
        addBtn = findViewById(R.id.add_button);
        removeBtn = findViewById(R.id.remove_button);
        submitBtn = findViewById(R.id.submit_button);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSubmitButtonClick();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                handleInputWrapperToggle();
            }
        });

        removeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                handleRemoveButtonClick();
            }
        });
    }

    private void initializeCityList() {
        cityList = findViewById(R.id.city_list);
        String[] cities = {
                "Edmonton", "Vancouver", "Sydney"
        };

        dataList = new ArrayList<>(Arrays.asList(cities));
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int pos, long l) {
                if (selectedCityIndex == pos) {
                    cityList.getChildAt(selectedCityIndex).setBackgroundColor(Color.TRANSPARENT); // Deselect currently selected
                    selectedCityIndex = -1;
                } else {
                    if (selectedCityIndex != -1) {
                        cityList.getChildAt(selectedCityIndex).setBackgroundColor(Color.TRANSPARENT);
                    }
                    selectedCityIndex = pos;
                    cityList.getChildAt(selectedCityIndex).setBackgroundColor(Color.argb(100, 216, 191, 216));
                }
            }
        });
    }

    private void handleInputWrapperToggle() {
        boolean visibility = inputWrapper.getVisibility() == View.VISIBLE;
        inputWrapper.setVisibility(visibility ? View.INVISIBLE : View.VISIBLE);
    }

    private void handleRemoveButtonClick() {
        dataList.remove(dataList.get(selectedCityIndex));
        cityAdapter.notifyDataSetChanged();
    }

    private void handleSubmitButtonClick() {
        EditText input = findViewById(R.id.city_input);
        String city = input.getText().toString();
        if (city.isEmpty()) {
            Toast.makeText(this, "Cannot be empty!", Toast.LENGTH_SHORT).show();
        } else {
            dataList.add(input.getText().toString());
            cityAdapter.notifyDataSetChanged();
            input.setText("");
        }
    }
}