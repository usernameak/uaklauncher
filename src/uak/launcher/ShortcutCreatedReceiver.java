package uak.launcher;

import android.content.*;
import android.widget.*;

public class ShortcutCreatedReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "Tes", Toast.LENGTH_LONG).show();
	}
	
}
