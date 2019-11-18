package com.example.smartclassroomusingqr_code;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import info.androidhive.barcode.BarcodeReader;

public class ScanQuiz extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {
    BarcodeReader barcodeReaderquiz;
    public Result result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_quiz);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get the barcode reader instance
        barcodeReaderquiz = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_scannerquiz);
    }

    @Override
    public void onScanned(Barcode barcode) {

        // playing barcode reader beep sound
        barcodeReaderquiz.playBeep();

        // ticket details activity by passing barcode
        Intent intent = new Intent(ScanQuiz.this, AttemptQuiz.class);
        intent.putExtra("code", barcode.displayValue);

        startActivity(intent);
    }

    @Override
    public void onScannedMultiple(List<Barcode> list) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String s) {
        Toast.makeText(getApplicationContext(), "Error occurred while scanning " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraPermissionDenied() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

