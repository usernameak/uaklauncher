package uak.launcher;

import android.app.*;
import android.content.*;
import android.os.*;
import android.content.res.*;
import android.widget.*;
import android.view.View;
import android.widget.AdapterView.*;

public class AddActivity extends Activity {
	
	XImage[] lva;
	Resources res;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add);
		res = getResources();
		GridView lv = (GridView) findViewById(R.id.shortcutTypesList);
		lva = new XImage[]{};
		Intent intent = new Intent(this, AddShortcutActivity.class);
		lva = IconAdapter.push(lva, fastXImage(android.R.drawable.ic_menu_directions, res.getString(R.string.add_shortcut), intent));
		lva = IconAdapter.push(lva, fastXImage(android.R.drawable.ic_menu_call, res.getString(R.string.add_shortcut), intent));
		lv.setAdapter(new IconAdapter(this, R.layout.list_view_apps, lva));
		lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int pos, long p4) {
					// TODO: Implement this method
					startActivity(lva[pos].intent);
				}


			});
	}
	public XImage fastXImage(int xImage, CharSequence name, Intent intent) {
		XImage ximg = new XImage();
		ximg.xImage = res.getDrawable(xImage);
		ximg.name = name;
		ximg.intent = intent;
		return ximg;
	}
}
