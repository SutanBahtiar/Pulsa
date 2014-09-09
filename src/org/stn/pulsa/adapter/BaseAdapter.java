package org.stn.pulsa.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

public class BaseAdapter<T> extends ArrayAdapter<T> {

	private static LayoutInflater inflater = null;
	private ArrayList<T> arrayList;
	private List<T> list;

	public BaseAdapter(Context context, int textViewResourceId, List<T> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		this.list = objects;
		this.arrayList = new ArrayList<T>();
		this.arrayList.addAll(list);
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public T getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	public void filter(String string) {
	}

	public LayoutInflater getInflater() {
		return inflater;
	}

	public List<T> getList() {
		return list;
	}

	public ArrayList<T> getArrayList() {
		return arrayList;
	}

}
