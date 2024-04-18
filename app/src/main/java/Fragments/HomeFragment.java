package Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.msd.finalproject_gp11.AddExpenseActivity;
import com.msd.finalproject_gp11.History;
import com.msd.finalproject_gp11.R;
import com.msd.finalproject_gp11.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment implements View.OnClickListener{
    Intent intent;
    FragmentHomeBinding fragmentHomeBinding;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = fragmentHomeBinding.getRoot();
        init();
        return view;
    }
    private void init() {
        fragmentHomeBinding.addExpenseButton.setOnClickListener(this);
        fragmentHomeBinding.viewHistoryButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == fragmentHomeBinding.addExpenseButton.getId()) {
            intent = new Intent(getActivity(), AddExpenseActivity.class);
            startActivity(intent);
        } else if (view.getId() == fragmentHomeBinding.viewHistoryButton.getId()){
            intent = new Intent(getActivity(), History.class);
            startActivity(intent);
        }
    }
}