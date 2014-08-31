package uak.launcher;

import android.app.*;
import android.appwidget.*;
import android.content.*;
import android.os.*;
import android.widget.*;
import java.util.*;

public class WidgetManager {	
	private Activity context;
	AppWidgetManager mAppWidgetManager;
	AppWidgetHost mAppWidgetHost;
	int REQUEST_PICK_APPWIDGET = 0x7f00;
	int REQUEST_CREATE_APPWIDGET = 0x7f01;
	int RESULT_ACTION_CONFIGURE_WIDGET = 0x7e00;
	int RESULT_ACTION_CREATE_WIDGET = 0x7e01;
	public void WidgetManager(Context context) {
		this.context = (Activity) context;
		mAppWidgetManager = AppWidgetManager.getInstance(this.context);
		mAppWidgetHost = new AppWidgetHost(this.context, 2376);
	}
	public void selectWidget() {
		int appWidgetId = this.mAppWidgetHost.allocateAppWidgetId();
		Intent pickIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_PICK);
		pickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		addEmptyData(pickIntent);
		context.startActivityForResult(pickIntent, REQUEST_PICK_APPWIDGET);
	}
	public void addEmptyData(Intent pickIntent) {
		ArrayList customInfo = new ArrayList();
		pickIntent.putParcelableArrayListExtra(AppWidgetManager.EXTRA_CUSTOM_INFO, customInfo);
		ArrayList customExtras = new ArrayList();
		pickIntent.putParcelableArrayListExtra(AppWidgetManager.EXTRA_CUSTOM_EXTRAS, customExtras);
	}
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CREATE_APPWIDGET || requestCode == REQUEST_CREATE_APPWIDGET) 	{
			if (resultCode == Activity.RESULT_OK) {
				if (requestCode == REQUEST_PICK_APPWIDGET) {
					configureWidget(data);
				} else if (requestCode == REQUEST_CREATE_APPWIDGET) {
					createWidget(data);
				}
			} else if (resultCode == Activity.RESULT_CANCELED && data != null) {
				int appWidgetId = data.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
				if (appWidgetId != -1) {
					mAppWidgetHost.deleteAppWidgetId(appWidgetId);
				}
			}
		}
	}
	private AppWidgetHostView configureWidget(Intent data) {
		Bundle extras = data.getExtras();
		int appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
		AppWidgetProviderInfo appWidgetInfo = mAppWidgetManager.getAppWidgetInfo(appWidgetId);
		if (appWidgetInfo.configure != null) {
			Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
			intent.setComponent(appWidgetInfo.configure);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
			context.startActivityForResult(intent, REQUEST_CREATE_APPWIDGET);
			return null;
		} else {
			return createWidget(data);
		}
	}

	public AppWidgetHostView createWidget(Intent data) {
		Bundle extras = data.getExtras();
		int appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
		AppWidgetProviderInfo appWidgetInfo = mAppWidgetManager.getAppWidgetInfo(appWidgetId);
		AppWidgetHostView hostView = mAppWidgetHost.createView(context, appWidgetId, appWidgetInfo);
		hostView.setAppWidget(appWidgetId, appWidgetInfo);
		return hostView;
	}

	protected void onStart() {
		// TODO: Implement this method
		mAppWidgetHost.startListening();
	}

	@Override
	protected void onStop() {
		// TODO: Implement this method
		mAppWidgetHost.stopListening();
	}
}
