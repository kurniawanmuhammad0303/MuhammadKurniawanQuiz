package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName;
    private Spinner spinnerItems;
    private EditText editTextQuantity;
    private RadioGroup radioGroup;
    private Button buttonProcess;

    private Map<String, Integer> itemPrices;

    private static final double REGULAR_DISCOUNT_RATE = 0.02;
    private static final double SILVER_DISCOUNT_RATE = 0.05;
    private static final double GOLD_DISCOUNT_RATE = 0.10;

    private static final String MEMBERSHIP_GOLD = "Gold";
    private static final String MEMBERSHIP_SILVER = "Silver";
    private static final String MEMBERSHIP_REGULAR = "Biasa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        spinnerItems = findViewById(R.id.spinnerItems);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        radioGroup = findViewById(R.id.radioGroup);
        buttonProcess = findViewById(R.id.buttonProcess);

        // Initialize item prices
        itemPrices = new HashMap<>();
        itemPrices.put("SSG", 12999999);
        itemPrices.put("IPX", 5725300);
        itemPrices.put("PCO", 2730551);

        // Set up Spinner with item options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.items_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerItems.setAdapter(adapter);

        buttonProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    processTransaction();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void processTransaction() {
        String name = editTextName.getText().toString().trim();
        String selectedItem = spinnerItems.getSelectedItem().toString();
        String membershipType = getMembershipType();
        int quantity = Integer.parseInt(editTextQuantity.getText().toString()); // Get quantity input

        int price = itemPrices.get(selectedItem);

        // Calculate total price
        int totalPrice = price * quantity;

        // Calculate discount based on membership type
        double discountRate = getDiscountRate(membershipType);

        // Apply discount
        int discount = (int) (totalPrice * discountRate);
        int finalPrice = totalPrice - discount;

        // Start TransactionDetailActivity with transaction details
        Intent intent = new Intent(MainActivity.this, TransactionDetailActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("selectedItem", selectedItem);
        intent.putExtra("price", price);
        intent.putExtra("membershipType", membershipType);
        intent.putExtra("quantity", quantity);
        intent.putExtra("totalPrice", totalPrice);
        intent.putExtra("discount", discount);
        intent.putExtra("finalPrice", finalPrice);
        startActivity(intent);
    }

    private String getMembershipType() {
        RadioButton selectedRadioButton = findViewById(radioGroup.getCheckedRadioButtonId());
        return selectedRadioButton.getText().toString();
    }

    private double getDiscountRate(String membershipType) {
        switch (membershipType) {
            case MEMBERSHIP_GOLD:
                return GOLD_DISCOUNT_RATE;
            case MEMBERSHIP_SILVER:
                return SILVER_DISCOUNT_RATE;
            default:
                return REGULAR_DISCOUNT_RATE;
        }
    }
}
