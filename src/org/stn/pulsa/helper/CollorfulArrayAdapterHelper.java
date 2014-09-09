package org.stn.pulsa.helper;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

public class CollorfulArrayAdapterHelper extends SimpleAdapter {

	public CollorfulArrayAdapterHelper(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = super.getView(position, convertView, parent);

		Log.i("VIEW ID", "ID == " + view.findViewById(1));

		switch (position % 3) {
		case 0: // Red
			view.setBackgroundColor(0xffff0000);
			break;
		case 1: // Green
			view.setBackgroundColor(0xff00ff00);
			break;
		case 2: // Blue
			view.setBackgroundColor(0xff0000ff);
		}
		return view;
	}

}
