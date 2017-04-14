package com.udacity.stockhawk.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.stockhawk.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    
    private final Context context;
    private final DecimalFormat dollarFormat;
    private String[] cursor;
    
    HistoryAdapter(Context context) {
        this.context = context;
        
        dollarFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
    }
    
    void setCursor(String[] cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }
    
    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        
        View item = LayoutInflater.from(context).inflate(R.layout.list_item_history, parent, false);
        
        return new HistoryViewHolder(item);
    }
    
    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        
        String line = cursor[position];
        
        String[] splitted = line.split(", ");
        Long timestamp = Long.valueOf(splitted[0]);
        Float value = Float.valueOf(splitted[1]);
        
        holder.date.setText(DateUtils.formatDateTime(context, timestamp, DateUtils.FORMAT_SHOW_DATE));
        holder.price.setText(dollarFormat.format(value));
    }
    
    @Override
    public int getItemCount() {
        return cursor != null ? cursor.length : 0;
    }
    
    class HistoryViewHolder extends RecyclerView.ViewHolder {
        
        @BindView(R.id.date)
        TextView date;
        
        @BindView(R.id.price)
        TextView price;
        
        HistoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
