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

public class SetWallpaperActivity extends Activity
{
	XImage[] lva;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_wallpaper);
		Intent appsIntent = new Intent(Intent.ACTION_SET_WALLPAPER);
		List<ResolveInfo> actList = getPackageManager().queryIntentActivities(appsIntent, 0);
		GridView lv = (GridView) findViewById(R.id.wallpaperTypesList);
		lva = new XImage[]{};
		for(ResolveInfo i : actList) {
			Drawable activityIcon = i.activityInfo.loadIcon(this.getPackageManager());
			CharSequence activityName = i.activityInfo.loadLabel(this.getPackageManager());
			XImage aixi = new XImage();
			aixi.xImage = activityIcon;
			aixi.name = activityName;
			ComponentName cn = new ComponentName(i.activityInfo.applicationInfo.packageName,
												 i.activityInfo.name);
			Intent intent = new Intent(Intent.ACTION_SET_WALLPAPER);
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
