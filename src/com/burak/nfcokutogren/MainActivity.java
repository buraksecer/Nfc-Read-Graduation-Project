package com.burak.nfcokutogren;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button introductionButton=(Button) findViewById(R.id.introductionButton);
		Button enterButton=(Button) findViewById(R.id.enterButton);
		final TextView pp=(TextView) findViewById(R.id.appName);
		final TextView ppp=(TextView) findViewById(R.id.explanation);
		pp.setTypeface(Typeface.SERIF);
		ppp.setTypeface(Typeface.SERIF);
		enterButton.setTypeface(Typeface.SERIF);
		introductionButton.setTypeface(Typeface.SERIF);
		
		introductionButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
			
				Intent go=new Intent(getBaseContext(), Egitim.class);
			    startActivity(go);
				
			}
		});
		
		
		enterButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
			
				
				Intent go=new Intent(getBaseContext(),NfcOkut.class);
				startActivity(go);
				
			}
		});
		
	}
}
