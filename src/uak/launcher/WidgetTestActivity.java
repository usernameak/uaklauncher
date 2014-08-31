package uak.launcher;

import android.app.*;
import android.appwidget.*;
import android.content.*;
import android.os.*;
import android.widget.*;
import java.util.*;

public class WidgetTestActivity extends Activity 
{	
	AppWidgetManager mAppWidgetManager;
	AppWidgetHost mAppWidgetHost;
	int REQUEST_PICK_APPWIDGET = 0x7f00;
	int REQUEST_CREATE_APPWIDGET = 0x7f01;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_test);
		mAppWidgetManager = AppWidgetManager.getInstance(this);
		mAppWidgetHost = new AppWidgetHost(this, 2376);
		selectWidget();
	}
	public void selectWidget() {
		int appWidgetId = this.mAppWidgetHost.allocateAppWidgetId();
		Intent pickIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_PICK);
		pickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		
		startActivityForResult(pickIntent, REQUEST_PICK_APPWIDGET);
	}
	public void addEmptyData(Intent pickIntent) {
		ArrayList customInfo = new ArrayList();
		pickIntent.putParcelableArrayListExtra(AppWidgetManager.EXTRA_CUSTOM_INFO, customInfo);
		ArrayList customExtras = new ArrayList();
		pickIntent.putParcelableArrayListExtra(AppWidgetManager.EXTRA_CUSTOM_EXTRAS, customExtras);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK) {
			if(requestCode == REQUEST_PICK_APPWIDGET) {
				configureWidget(data);
			} else if (requestCode == REQUEST_CREATE_APPWIDGET) {
				createWidget(data);
			}
		} else if(resultCode == RESULT_CANCELED && data != null) {
			int appWidgetId = data.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
			if(appWidgetId != -1) {
				mAppWidgetHost.deleteAppWidgetId(appWidgetId);
			}
		}
		// TODO: Implement this method
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void configureWidget(Intent data) {
		Bundle extras = data.getExtras();
		int appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
		AppWidgetProviderInfo appWidgetInfo = mAppWidgetManager.getAppWidgetInfo(appWidgetId);
		if(appWidgetInfo.configure != null) {
			Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
			intent.setComponent(appWidgetInfo.configure);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
			startActivityForResult(intent, REQUEST_CREATE_APPWIDGET);
		} else {
			createWidget(data);
		}
	}
	
	public void createWidget(Intent data) {
		Bundle extras = data.getExtras();
		int appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
		AppWidgetProviderInfo appWidgetInfo = mAppWidgetManager.getAppWidgetInfo(appWidgetId);
		AppWidgetHostView hostView = mAppWidgetHost.createView(this, appWidgetId, appWidgetInfo);
		hostView.setAppWidget(appWidgetId, appWidgetInfo);
		LinearLayout layout = (LinearLayout) findViewById(R.id.widgetTestLayout);
		layout.addView(hostView);
	}

	@Override
	protected void onStart() {
		// TODO: Implement this method
		super.onStart();
		mAppWidgetHost.startListening();
	}

	@Override
	protected void onStop() {
		// TODO: Implement this method
		super.onStop();
		mAppWidgetHost.stopListening();
	}
	
}
