package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinnerBrand = findViewById(R.id.spinnerBrand);
        Spinner spinnerModel = findViewById(R.id.spinnerModel);
        Spinner spinnerDrivingType = findViewById(R.id.spinnerDrivingType);
        Button buttonCalculate = findViewById(R.id.buttonCalculate);
        TextView resultTextView = findViewById(R.id.textViewResult);

        // Създаване на адаптер за марките
        ArrayAdapter<CharSequence> brandAdapter = ArrayAdapter.createFromResource(
                this, R.array.car_brands, android.R.layout.simple_spinner_item);
        brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBrand.setAdapter(brandAdapter);

        // Създаване на адаптер за моделите
        ArrayAdapter<CharSequence> modelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        modelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerModel.setAdapter(modelAdapter);

        // Слушател за промяна на марката
        spinnerBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedBrand = spinnerBrand.getSelectedItem().toString();
                int modelArrayResId = 0;

                // Избор на съответния масив с модели
                if (selectedBrand.equals("BMW")) {
                    modelArrayResId = R.array.bmw_models;
                } else if (selectedBrand.equals("Audi")) {
                    modelArrayResId = R.array.audi_models;
                } else if (selectedBrand.equals("Mercedes")) {
                    modelArrayResId = R.array.mercedes_models;
                } else if (selectedBrand.equals("Volkswagen")) {
                    modelArrayResId = R.array.volkswagen_models;
                }

                // Актуализиране на адаптера за модели
                ArrayAdapter<CharSequence> modelAdapter = ArrayAdapter.createFromResource(
                        MainActivity.this, modelArrayResId, android.R.layout.simple_spinner_item);
                modelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerModel.setAdapter(modelAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Няма избрана марка
            }
        });

        // Слушател за бутон за изчисление
        buttonCalculate.setOnClickListener(v -> {
            try {
                // Вземаме въведените стойности
                float urbanConsumption = Float.parseFloat(((EditText) findViewById(R.id.editTextUrbanConsumption)).getText().toString());
                float highwayConsumption = Float.parseFloat(((EditText) findViewById(R.id.editTextHighwayConsumption)).getText().toString());
                float combinedConsumption = Float.parseFloat(((EditText) findViewById(R.id.editTextCombinedConsumption)).getText().toString());
                float distance = Float.parseFloat(((EditText) findViewById(R.id.editTextDistance)).getText().toString());
                float fuelPrice = Float.parseFloat(((EditText) findViewById(R.id.editTextFuelPrice)).getText().toString());

                // Избиране на тип шофиране
                String drivingType = spinnerDrivingType.getSelectedItem().toString();

                // Определяне на разход в зависимост от типа шофиране
                float consumption = 0;
                if (drivingType.equals("Градско")) {
                    consumption = urbanConsumption;
                } else if (drivingType.equals("Извънградско")) {
                    consumption = highwayConsumption;
                } else if (drivingType.equals("Комбинирано")) {
                    consumption = combinedConsumption;
                }


                float totalCost = consumption * fuelPrice;

                // Показване на резултата
                resultTextView.setText("Общата цена на горивото е: " + totalCost + " лв.");
            } catch (NumberFormatException e) {
                // Грешка при въвеждането
                Toast.makeText(MainActivity.this, "Моля, въведете валидни стойности!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}