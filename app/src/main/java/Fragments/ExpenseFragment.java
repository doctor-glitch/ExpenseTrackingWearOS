package Fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.msd.finalproject_gp11.R;
import com.msd.finalproject_gp11.databinding.FragmentExpenseBinding;
import com.msd.finalproject_gp11.databinding.FragmentHomeBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import DatabaseOP.DBHelper;
import Models.ExpenseData;

public class ExpenseFragment extends Fragment {
    FragmentExpenseBinding expenseBinding;
    List<ExpenseData> expenseDataList = new ArrayList<ExpenseData>();
    DBHelper dbHelper;

    public ExpenseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        expenseBinding = FragmentExpenseBinding.inflate(inflater, container, false);
        View view = expenseBinding.getRoot();
        init();
        return view;
    }

    public void init() {
        dbHelper = new DBHelper(getActivity());

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
        float currentMonthTotal = getTotalExpenseOfCurrentMonth((ArrayList<ExpenseData>) expenseDataList);
        float currentDayTotal = getTotalExpenseOfToday((ArrayList<ExpenseData>) expenseDataList);

        expenseBinding.thisMonthSpendingValue.setText("$ " + currentMonthTotal);
        expenseBinding.todaySpendingValue.setText("$ " + currentDayTotal);

    }

    public float getTotalExpenseOfCurrentMonth(ArrayList<ExpenseData> expenseDataList) {
        float total = 0;
        float groceryTotal = 0;
        float foodTotal = 0;
        float movieTotal = 0;
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH is zero-based
        int currentYear = calendar.get(Calendar.YEAR);

        for (ExpenseData expenseData : expenseDataList) {
            // Parse the date string into a Date object
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date expenseDate = null;
            try {
                expenseDate = sdf.parse(expenseData.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
                continue;
            }

            // Check if the expenseDate is in the current month and year
            Calendar expenseCalendar = Calendar.getInstance();
            expenseCalendar.setTime(expenseDate);
            if (expenseCalendar.get(Calendar.MONTH) + 1 == currentMonth &&
                    expenseCalendar.get(Calendar.YEAR) == currentYear &&
                    (Objects.equals(expenseData.getCategory(), "Grocery") ||
                            Objects.equals(expenseData.getCategory(), "Food") ||
                            Objects.equals(expenseData.getCategory(), "Movie"))) {
                if (Objects.equals(expenseData.getCategory(), "Grocery")) {
                    groceryTotal += expenseData.getAmount();
                } else if (Objects.equals(expenseData.getCategory(), "Food")) {
                    foodTotal += expenseData.getAmount();
                } else {
                    movieTotal += expenseData.getAmount();
                }
                total += expenseData.getAmount();
            }
        }
        expenseBinding.groceryProgressBar.setMax((int) total);
        expenseBinding.foodProgressBar.setMax((int) total);
        expenseBinding.movieProgressBar.setMax((int) total);

        expenseBinding.groceryProgressBar.setProgress((int) groceryTotal);
        expenseBinding.foodProgressBar.setProgress((int) foodTotal);
        expenseBinding.movieProgressBar.setProgress((int) movieTotal);

        return total;
    }

    public float getTotalExpenseOfToday(ArrayList<ExpenseData> expenseDataList) {
        float sum = 0;
        Calendar today = Calendar.getInstance();

        for (ExpenseData expenseData : expenseDataList) {
            // Parse the date string into a Date object
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date expenseDate = null;
            try {
                expenseDate = sdf.parse(expenseData.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
                continue;
            }

            // Check if the expenseDate is today
            Calendar expenseCalendar = Calendar.getInstance();
            expenseCalendar.setTime(expenseDate);
            if (expenseCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                    expenseCalendar.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                    expenseCalendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH) &&
                    !Objects.equals(expenseData.getCategory(), "Salary")) {
                sum += expenseData.getAmount();
            }
        }
        return sum;
    }

}