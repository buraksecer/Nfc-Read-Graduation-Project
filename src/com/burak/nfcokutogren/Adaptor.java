package com.burak.nfcokutogren;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Adaptor extends BaseAdapter {

	 private LayoutInflater mInflater;
	 private List<Urun>     mUrun;
	
	 public Adaptor(Activity activity, List<Urun> urunler){
		 
		 mInflater = (LayoutInflater) activity.getSystemService(
	                Context.LAYOUT_INFLATER_SERVICE);
		 
		 mUrun = urunler;
	 }
	 
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mUrun.size();
	}

	@Override
	public Object getItem(int position) {
		  return mUrun.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 View satirView;

	        satirView = mInflater.inflate(R.layout.layout_satir, null);
	        TextView textView = 
	                (TextView) satirView.findViewById(R.id.urun); 
	        textView.setTypeface(Typeface.SERIF);

	        Urun urunler = mUrun.get(position);

	        textView.setText(urunler.getUrunIsmi());

	        return satirView;
	    }
	


}
