package com.udacity.stockhawk.sync;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;

/**
 * Created by victorpinto on 14/04/17. 
 */

public class StockRemoteCollectionViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(StockRemoteCollectionViewService.class.getSimpleName(), "onGetViewFactory");
        return new WidgetRemoteViewsFactory(this.getApplicationContext());
    }
    
    private static class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
        
        private final static String TAG = WidgetRemoteViewsFactory.class.getSimpleName();
        
        private final Context context;
        private Cursor cursor;
        
        public WidgetRemoteViewsFactory(Context context) {
            this.context = context;
        }
        
        @Override
        public void onCreate() {
        }
        
        @Override
        public void onDataSetChanged() {
            Uri uri = Contract.Quote.URI;
            String[] columns = Contract.Quote.QUOTE_COLUMNS.toArray(new String[]{});
            String order = Contract.Quote.COLUMN_SYMBOL;
            cursor = context.getContentResolver().query(uri, columns, null, null, order);
            Log.d(TAG, "onDataSetChanged " + cursor.getCount());
        }
        
        @Override
        public void onDestroy() {
        }
        
        @Override
        public int getCount() {
            return cursor != null ? cursor.getCount() : 0;
        }
        
        @Override
        public RemoteViews getViewAt(int position) {
            Log.d(TAG, "Criando " + position);
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.list_item_widget);
            if (cursor != null && cursor.moveToPosition(position)) {
                String quote = cursor.getString(Contract.Quote.POSITION_SYMBOL);
                String quote2 = cursor.getString(Contract.Quote.POSITION_PRICE);
                rv.setTextViewText(R.id.symbol, quote);
                rv.setTextViewText(R.id.price, quote2);
                
                return rv;
            }
            return null;
        }
        
        @Override
        public RemoteViews getLoadingView() {
            return null;
        }
        
        @Override
        public int getViewTypeCount() {
            return 1;
        }
        
        @Override
        public long getItemId(int position) {
            return position;
        }
        
        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
