package com.msd.finalproject_gp11;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.msd.finalproject_gp11.databinding.ActivityAddExpenseBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import DatabaseOP.DBHelper;
import Models.ExpenseData;

public class AddExpenseActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityAddExpenseBinding addExpenseBinding;
    Calendar calendar;
    Intent intent;
    DBHelper dbHelper;
    Boolean insertStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addExpenseBinding = ActivityAddExpenseBinding.inflate(getLayoutInflater());
        View view = addExpenseBinding.getRoot();
        setContentView(view);
        addExpenseBinding.dateEditText.setInputType(InputType.TYPE_NULL);

        init();
    }

    private void init() {
        dbHelper = new DBHelper(this);
        calendar = Calendar.getInstance(); // Initialize the calendar
        addExpenseBinding.dateEditText.setOnClickListener(this);
        addExpenseBinding.addBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == addExpenseBinding.dateEditText.getId()) {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateDateButtonText();
                }
            }, year, month, day);
            datePickerDialog.show();
        } else if (view.getId() == addExpenseBinding.addBtn.getId()) {
            String amount = addExpenseBinding.amountEditText.getText().toString();
            String dateStr = addExpenseBinding.dateEditText.getText().toString();
            String category = addExpenseBinding.categorySpinner.getSelectedItem().toString();

            if (TextUtils.isEmpty(amount)) {
                Toast.makeText(this, "Amount is required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(dateStr)) {
                Toast.makeText(this, "Date is required", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate date format
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date selectedDate;
            try {
                selectedDate = dateFormat.parse(dateStr);
            } catch (ParseException e) {
                Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check if selected date is less than today's date
            Date today = new Date();
            if (selectedDate.after(today)) {
                Toast.makeText(this, "Date cannot be in the future", Toast.LENGTH_SHORT).show();
                return;
            }

            if (category.equals("Select Category")) {
                Toast.makeText(this, "Category is required", Toast.LENGTH_SHORT).show();
                return;
            }

            ExpenseData expenseData = new ExpenseData();
            expenseData.setAmount(Float.parseFloat(amount));
            expenseData.setDate(dateStr);
            expenseData.setCategory(category);

            insertStatus = dbHelper.InsertExpenseData(expenseData);
            if (!insertStatus) {
                Toast.makeText(this, "Something unexpected happened!!", Toast.LENGTH_LONG).show();
            } else {
                intent = new Intent(this, MainActivity.class);
                Toast.makeText(this, "Expense added", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }


        }
    }

    private void updateDateButtonText() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        addExpenseBinding.dateEditText.setText(dateFormat.format(calendar.getTime()));
    }
}