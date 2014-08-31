package uak.launcher;

import android.app.*;
import android.widget.*;
import android.view.*;
import android.content.*;

public class IconAdapter extends ArrayAdapter<Integer> {
	private Activity activity;
	private XImage[] entries;
	
	public IconAdapter(Activity a, int tvrid, XImage[] e) {
		super(a,tvrid,e);
		this.entries = e;
		activity = a;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO: Implement this method
		View v = convertView;
		viewHolder holder;
		if(v == null) {
			LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.list_view_apps, parent, false);
			holder = new viewHolder();
			holder.imageView = (ImageView) v.findViewById(R.id.AppIconView);
			holder.textView = (TextView) v.findViewById(R.id.AppNameView);
			v.setTag(holder);
		} else {
			holder = (viewHolder) v.getTag();
		}
		XImage ximg = entries[position]; 
		if(ximg != null) {
			holder.imageView.setImageDrawable(ximg.xImage);
			holder.textView.setText(ximg.name);
		}
		return v;
	}
	public static XImage[] push(XImage[] array, XImage push) {
		XImage[] longer = new XImage[array.length + 1];
		System.arraycopy(array, 0, longer, 0, array.length);
		longer[array.length] = push;
		return longer; 
	}
	private static class viewHolder {
		public ImageView imageView;
		public TextView textView;
	}
}
