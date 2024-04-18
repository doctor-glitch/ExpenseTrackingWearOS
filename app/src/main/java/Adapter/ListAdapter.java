package Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.msd.finalproject_gp11.databinding.HistoryRowBinding;

import java.util.List;

import Models.ExpenseData;

// adapter for the recycler view
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private final List<ExpenseData> expenseDataList;
    HistoryRowBinding historyRowBinding;

    // constructor to set workout data
    public ListAdapter(List<ExpenseData> expenseDataList) {
        super();
        this.expenseDataList = expenseDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        historyRowBinding = HistoryRowBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(historyRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ((ViewHolder) holder).bindView(position + 1, expenseDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return expenseDataList.size();    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        HistoryRowBinding recyclerRowBinding;

        public ViewHolder(@NonNull HistoryRowBinding recyclerRowBinding) {
            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding = recyclerRowBinding;
        }

//        set text to the textview of the row
        public void bindView(int i, ExpenseData expenseData) {
            String history = "Amount: $" + expenseData.getAmount() +
                    ", Date Added:" + expenseData.getDate() +
                    ", Category:" + expenseData.getCategory()
                    ;
            recyclerRowBinding.txtWorkout.setText(history);
        }
    }

}
