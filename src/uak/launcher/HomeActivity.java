package uak.launcher;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.graphics.drawable.*;
import android.content.*;
import android.util.*;

public class HomeActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		getActionBar().hide();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		// TODO: Implement this method
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.menu_wallpaper:
				Intent setWallpaperIntent = new Intent(this, SetWallpaperActivity.class);
				startActivity(setWallpaperIntent);
				break;
			case R.id.menu_add:
				Intent addIntent = new Intent(this, AddActivity.class);
				startActivity(addIntent);
				break;
		}
		// TODO: Implement this method
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDestroy() {
		// TODO: Implement this method
		super.onDestroy();
	}
	
}
