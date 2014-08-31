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
import android.util.*;
import android.content.res.*;
import android.content.pm.PackageManager.*;
import android.graphics.*;

public class AddShortcutActivity extends Activity
{
	XImage[] lva;
	public static final int REQUEST_GET_SHORTCUT = 0x7f03;
	public static final String CREATE_SHORTCUT = "uak.launcher.action.CREATE_SHORTCUT";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_shortcut);
		setFinishOnTouchOutside(true);
		Intent appsIntent = new Intent(Intent.ACTION_CREATE_SHORTCUT);
		List<ResolveInfo> actList = getPackageManager().queryIntentActivities(appsIntent, 0);
		ListView lv = (ListView) findViewById(R.id.shortcutsList);
		lva = new XImage[]{};
		for(ResolveInfo i : actList) {
			Drawable activityIcon = i.activityInfo.loadIcon(this.getPackageManager());
			CharSequence activityName = i.activityInfo.loadLabel(this.getPackageManager());
			XImage aixi = new XImage();
			aixi.xImage = activityIcon;
			aixi.name = activityName;
			ComponentName cn = new ComponentName(i.activityInfo.applicationInfo.packageName,
												 i.activityInfo.name);
			Intent intent = new Intent(Intent.ACTION_CREATE_SHORTCUT);
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
					startActivityForResult(lva[pos].intent, REQUEST_GET_SHORTCUT);
				}


			});
    }

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQUEST_GET_SHORTCUT) {
			if(resultCode == RESULT_OK && data != null) {
				Bitmap shortcutIconBitmap = null;
				Parcelable iconResourceParcelable = data.getParcelableExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE);
				if(iconResourceParcelable != null && iconResourceParcelable instanceof Intent.ShortcutIconResource) {
					Intent.ShortcutIconResource iconResource = (Intent.ShortcutIconResource) iconResourceParcelable;
					try {
						Resources shcutRes = getPackageManager().getResourcesForApplication(iconResource.packageName);
						if(shcutRes != null) {
							int id = shcutRes.getIdentifier(iconResource.resourceName, null, null);
							shortcutIconBitmap = ((BitmapDrawable) shcutRes.getDrawable(id)).getBitmap();
						}
					} catch (PackageManager.NameNotFoundException e) {}
				} else {
					iconResourceParcelable = data.getParcelableExtra(Intent.EXTRA_SHORTCUT_ICON);
					shortcutIconBitmap = (Bitmap) iconResourceParcelable;
				}
				Intent intent = new Intent();
				intent.setAction(CREATE_SHORTCUT);
				intent.putExtra("EXTRA_SHORTCUT_BITMAP", shortcutIconBitmap);
				intent.putExtra("EXTRA_SHORTCUT_NAME", data.getStringExtra(Intent.EXTRA_SHORTCUT_NAME));
				intent.putExtra("EXTRA_SHORTCUT_INTENT", data.getParcelableExtra(intent.EXTRA_SHORTCUT_INTENT));
				sendBroadcast(intent);
				setResult(RESULT_OK);
				finish();
			}
		}
		// TODO: Implement this method
		super.onActivityResult(requestCode, resultCode, data);
	}
	
}
