package com.burak.nfcokutogren;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


public class KayitliUrunler extends Activity {
	 final List<Urun> urunler=new ArrayList<Urun>();
	 private LayoutInflater mInflater;
	 View satirView;
	 Dialog dialog;
	 Button btnDialog;
	 TextView txtDialog;
	  ListView listemiz;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kayitli_urunler);
		listemiz = (ListView) findViewById(R.id.listView1);
		TextView txt=(TextView) findViewById(R.id.textView1);
		Button btnBack=(Button) findViewById(R.id.backtomain);
		EditText editxt=(EditText) findViewById(R.id.editText1);
		ArrayList<String> arrayList=new ArrayList<String>();
		final Database db=new Database(this);
		btnBack.setTypeface(Typeface.SERIF);
		txt.setTypeface(Typeface.SERIF);
		editxt.setTypeface(Typeface.SERIF);
		btnBack.setTypeface(Typeface.SERIF);
		
		
		//textchange search
		editxt.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start,
				     int count, int after) {
				if(s.length() != 0)
				{//barkod numarasının text uzunluğuna erişince metoda girecek if komutu yaz.
				
					db.open();
					temizleListe();
					ArrayList<String> arrayList2 = db.search(s.toString());
					for (String string : arrayList2) {
						urunler.add(new Urun(string.toString()));
						
					}
					
					doldur();
					db.close();
					//Toast.makeText(getApplicationContext(), "gelen:"+gelens, Toast.LENGTH_LONG).show();
					
					
				}
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start,
				     int count, int after) {
				//Toast.makeText(getApplicationContext(), "!"+count, Toast.LENGTH_SHORT).show();
				
			}
			
			@Override
			public void afterTextChanged(Editable k) {
			//	Toast.makeText(getApplicationContext(), ""+k.length(), Toast.LENGTH_SHORT).show();
				
			}
		});
		//textchanger son search
		
		
		
		btnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			Intent go=new Intent(getApplicationContext(), NfcOkut.class);
			startActivity(go);
				
			}
		});
		
		try {
			db.open();
			temizleListe();
			arrayList=db.getThat();
			for (String string : arrayList) {
				urunler.add(new Urun(string.toString()));
				
			}
		
		
			doldur();
			db.close();
		
			
		} catch (Exception e) {
			//Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
		}
		
		
	}
	public void doldur(){
		
		Adaptor adaptors=new Adaptor(this,urunler);
		listemiz.setAdapter(adaptors);
		listemiz.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id) {
               try {
            	   dialog=new Dialog(KayitliUrunler.this);
           		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
           		dialog.setContentView(R.layout.dialogurun);
           		
           		btnDialog=(Button) dialog.findViewById(R.id.dialogKapat);
           		txtDialog=(TextView) dialog.findViewById(R.id.dialogUrunler); 
           		
           		btnDialog.setTypeface(Typeface.SERIF);
           		txtDialog.setTypeface(Typeface.SERIF);
           
           		
           		btnDialog.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						dialog.dismiss();
						
					}
				});
           	
         	
           			txtDialog.setText(Listviews.arrayurunAdi.get(position).toString());
           			if(Database.degerBulunamadi!=0)
               		dialog.show();
           			
           		
	             //  Toast.makeText(getBaseContext(),Listviews.arrayurunAdi.get(position).toString(), Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				//Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
			} 
               
                
            }
        });
	
		}
	
	public void temizleListe(){
		Listviews.arrayurunAdi.clear();
		urunler.clear();
	}
}
