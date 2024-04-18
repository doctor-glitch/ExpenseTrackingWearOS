package com.msd.finalproject_gp11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.msd.finalproject_gp11.databinding.ActivityHistoryBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Adapter.ListAdapter;
import DatabaseOP.DBHelper;
import Models.ExpenseData;

public class History extends AppCompatActivity {
    ActivityHistoryBinding historyBinding;
    ListAdapter mAdapter;
    List<ExpenseData> expenseDataList = new ArrayList<ExpenseData>();
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        historyBinding = ActivityHistoryBinding.inflate(getLayoutInflater());
        View view = historyBinding.getRoot();
        setContentView(view);
        init();
    }

    private void init() {
        dbHelper = new DBHelper(this);

        Cursor cursor = dbHelper.getAllExpenses();
        expenseDataList.clear();
        if (cursor == null || cursor.getCount() == 0) {
            // show no records info
        } else {
            cursor.moveToFirst();
            do {
                ExpenseData expenseData = new ExpenseData();
                expenseData.setId(cursor.getInt(0));
                expenseData.setAmount(Float.parseFloat(cursor.getString(1)));
                expenseData.setDate(cursor.getString(2));
                expenseData.setCategory(cursor.getString(3));

                expenseDataList.add(expenseData);
            } while (cursor.moveToNext());
            cursor.close();
            dbHelper.close();
        }
        bindAdapter(expenseDataList);
    }


    //
    private void bindAdapter(List<ExpenseData> expenseData) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        historyBinding.wrcWorkouts.setLayoutManager(layoutManager);
        mAdapter = new ListAdapter(expenseData);

        // set adapter to recycler view
        historyBinding.wrcWorkouts.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}