package com.example.upisaver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class transactionViewAdapter extends RecyclerView.Adapter<transactionViewAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView viewAmount;
        public TextView viewDate;
        public TextView viewType;
        public TextView viewUsage;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            viewAmount = itemView.findViewById(R.id.view_amount);
            viewDate = itemView.findViewById(R.id.view_date);
            viewType=itemView.findViewById(R.id.view_type);
            viewUsage=itemView.findViewById(R.id.view_usage);
        }
    }

    private List<transactionViewData> transList;

    public transactionViewAdapter(List<transactionViewData> transactionList){
        transList=transactionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View transactionView = inflater.inflate(R.layout.transaction_view_custom, parent, false);
        ViewHolder viewHolder = new ViewHolder(transactionView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
