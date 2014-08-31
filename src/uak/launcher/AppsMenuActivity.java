package uak.launcher;

import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.graphics.drawable.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.*;
import java.util.*;

public class AppsMenuActivity extends Activity
{
	XImage[] lva;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apps_menu);
		Intent appsIntent = new Intent(Intent.ACTION_MAIN);
		appsIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> actList = getPackageManager().queryIntentActivities(appsIntent, 0);
		GridView lv = (GridView) findViewById(R.id.appsList);
		lva = new XImage[]{};
		for(ResolveInfo i : actList) {
			Drawable activityIcon = i.activityInfo.loadIcon(this.getPackageManager());
			CharSequence activityName = i.activityInfo.loadLabel(this.getPackageManager());
			XImage aixi = new XImage();
			aixi.xImage = activityIcon;
			aixi.name = activityName;
			ComponentName cn = new ComponentName(i.activityInfo.applicationInfo.packageName,
					i.activityInfo.name);
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
					Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
			intent.setComponent(cn);
			aixi.intent = intent;
			lva = IconAdapter.push(lva, aixi);
		}
		IconAdapter adapter = new IconAdapter(this,R.layout.list_view_apps, lva);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int pos, long p4) {
					// TODO: Implement this method
					startActivity(lva[pos].intent);
				}

			
		});
    }
}
