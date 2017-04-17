package com.udacity.stockhawk.sync;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.ui.StockWidgetProvider;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class StockWidgetIntentService extends IntentService {
    private static final String TAG = StockWidgetIntentService.class.getSimpleName();
    
    public StockWidgetIntentService() {
        super("StockWidget");
    }
    
    @Override
    protected void onHandleIntent(Intent intentp) {
        Log.d(TAG, "onHandleIntent");
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, StockWidgetProvider.class));
        Log.d(TAG, "appWidgetIds " + appWidgetIds.length);
        for (int appWidgetId : appWidgetIds) {
            Log.d(TAG, "Atualizando " + appWidgetId);
            // Specify the service to provide data for the collection widget.  Note that we need to
            // embed the appWidgetId via the data otherwise it will be ignored.
            
            final Intent intent = new Intent(this.getApplicationContext(), StockRemoteCollectionViewService.class)
                    .putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            final RemoteViews rv = new RemoteViews(this.getPackageName(), R.layout.quote_widget);
            rv.setRemoteAdapter(R.id.listview, intent);
            
            appWidgetManager.updateAppWidget(appWidgetId, rv);
            Log.d(TAG, "Atualizando " + appWidgetId + " ok");
        }
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
