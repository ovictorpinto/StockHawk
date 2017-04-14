package com.udacity.stockhawk.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    
    private static final int STOCK_LOADER = 10;
    
    @BindView(R.id.recycler_view)
    RecyclerView stockRecyclerView;
    
    HistoryAdapter adapter;
    private String symbol;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
    
        symbol = getIntent().getStringExtra("symbol");
        setTitle(getString(R.string.history_title, symbol));
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        
        adapter = new HistoryAdapter(this);
        stockRecyclerView.setAdapter(adapter);
        stockRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getSupportLoaderManager().initLoader(STOCK_LOADER, null, this);
    }
    
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, Contract.Quote.makeUriForStock(symbol), Contract.Quote.QUOTE_COLUMNS
                .toArray(new String[]{}), null, null, Contract.Quote.COLUMN_HISTORY);
    }
    
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.moveToFirst()) {
            String history = data.getString(Contract.Quote.POSITION_HISTORY);
            adapter.setCursor(history.split("\n"));
        }
    }
    
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        
    }
}
