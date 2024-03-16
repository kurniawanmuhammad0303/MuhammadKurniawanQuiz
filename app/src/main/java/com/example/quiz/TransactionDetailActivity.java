package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;

public class TransactionDetailActivity extends AppCompatActivity {

    private TextView textViewDetails; // Deklarasikan textViewDetails sebagai variabel kelas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);

        TextView textViewWelcome = findViewById(R.id.textViewWelcome);
        textViewDetails = findViewById(R.id.textViewDetails); // Tetapkan textViewDetails di sini
        TextView textViewThankYou = findViewById(R.id.textViewThankYou); // Tambahkan deklarasi untuk textViewThankYou
        Button buttonShare = findViewById(R.id.buttonShare);

        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("name");
            String selectedItem = intent.getStringExtra("selectedItem");
            int price = intent.getIntExtra("price", 0);
            String membershipType = intent.getStringExtra("membershipType");
            int totalPrice = intent.getIntExtra("totalPrice", 0);
            int discount = intent.getIntExtra("discount", 0);
            int finalPrice = intent.getIntExtra("finalPrice", 0);

            DecimalFormat formatter = new DecimalFormat("#,###");
            String formattedPrice = formatter.format(price);
            String formattedTotalPrice = formatter.format(totalPrice);
            String formattedDiscount = formatter.format(discount);
            String formattedFinalPrice = formatter.format(finalPrice);

            String message = "Nama Pelanggan: " + name +
                    "\nTipe Membership: " + membershipType +
                    "\nKode Barang: " + selectedItem +
                    "\nHarga Barang: Rp " + formattedPrice +
                    "\nTotal Harga: Rp " + formattedTotalPrice +
                    "\nDiskon: Rp " + formattedDiscount +
                    "\nJumlah yang akan dibayar: Rp " + formattedFinalPrice;

            textViewWelcome.setText("Selamat Datang, " + name + "!");
            textViewDetails.setText(message);
            textViewThankYou.setText("Terima kasih!");
        }

        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareTransactionDetails();
            }
        });
    }

    private void shareTransactionDetails() {
        if (textViewDetails != null) { // Periksa jika textViewDetails tidak null
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Detail Transaksi:\n" +
                    textViewDetails.getText().toString());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
    }
}
