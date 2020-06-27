package com.burak.nfcokutogren;

import java.nio.charset.Charset;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UrunEkle extends Activity {
	 Dialog dialog;
	EditText urunAdi,urunBarkod,urunSKT,urunUT,urunIcindekiler,urunBesinDegerleri;
	Button btnKaydet,btnGeri;
	private NfcAdapter mAdapter;
    private PendingIntent mPendingIntent;
    private NdefMessage mNdefPushMessage;
    private AlertDialog mDialog;
    Button btnDialog;
	 TextView txtDialog;
	Button btnDialogYetki,btnGeriYetki;
	EditText dialogKullaniciAdi,dialogPassword;
	Database db=new Database(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_urun_ekle);
		
	
		//nfc okut(onLoad)
		
		 resolveIntent(getIntent());

	        mDialog = new AlertDialog.Builder(this).setNeutralButton("Ok", null).create();

	        mAdapter = NfcAdapter.getDefaultAdapter(this);
	        if (mAdapter == null) {
	            showMessage(R.string.error, R.string.no_nfc);
	            finish();
	            return;
	        }

	        mPendingIntent = PendingIntent.getActivity(this, 0,
	                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
	        mNdefPushMessage = new NdefMessage(new NdefRecord[] { newTextRecord(
	                "Message from NFC Reader :-)", Locale.ENGLISH, true) });
		
		//nfc okut bitti (onLoad)
		
		
		//dialog başlangıç
		  dialog=new Dialog(UrunEkle.this);
		  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		  dialog.setContentView(R.layout.dialogyetki);
		  dialogKullaniciAdi=(EditText) dialog.findViewById(R.id.dialogKullaniciAdi);
		  dialogPassword=(EditText) dialog.findViewById(R.id.dialogPassword);
		  btnDialogYetki=(Button) dialog.findViewById(R.id.dialogGiris);
		  btnGeriYetki=(Button) dialog.findViewById(R.id.geriYetki);
		  dialogKullaniciAdi.setTypeface(Typeface.SERIF);
		  btnDialogYetki.setTypeface(Typeface.SERIF);
		  btnDialogYetki.setTypeface(Typeface.SERIF);
		  dialog.show();
		  dialog.setCancelable(false);
		  
		  btnGeriYetki.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				Intent go=new Intent(getApplicationContext(), NfcOkut.class);
				startActivity(go);
			
			}
		});
		  
		  
		  btnDialogYetki.setOnClickListener(new View.OnClickListener() {
				
			
			  
				@Override
				public void onClick(View arg0) {
					
					  String gelenKullanici=dialogKullaniciAdi.getText().toString();
					  String gelenPass=dialogPassword.getText().toString();
					if(gelenKullanici.equals("Burak") && gelenPass.equals("123")){
						Toast.makeText(getApplicationContext(),"Geçerli", Toast.LENGTH_LONG).show();
						dialog.dismiss();
					}
					else{
						Toast.makeText(getApplicationContext(), "Geçersiz!",Toast.LENGTH_LONG).show();
						
					}
					
					
				}
			});
		  
	
		//dialog bitiş.
		
		urunBarkod=(EditText) findViewById(R.id.urunBarkod);
		urunAdi=(EditText) findViewById(R.id.urunAdi);
		urunSKT=(EditText) findViewById(R.id.urunSKT);
		urunUT=(EditText) findViewById(R.id.urunUT);
		urunIcindekiler=(EditText) findViewById(R.id.urunİcindekiler);
		urunBesinDegerleri=(EditText) findViewById(R.id.urunBesinDegeri);
		btnGeri=(Button) findViewById(R.id.geriButton);
		
		btnGeri.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent geri=new Intent(getApplicationContext(), NfcOkut.class);
				startActivity(geri);
				
			}
		});
		
		
		
		
		
		
		
		
		
		btnKaydet=(Button) findViewById(R.id.urunEkleButton);
		
		btnKaydet.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			try {
				db.open();
				db.addThat(urunAdi.getText().toString(), urunSKT.getText().toString(), urunUT.getText().toString(), urunIcindekiler.getText().toString(), urunBesinDegerleri.getText().toString(),urunBarkod.getText().toString());				
				db.close();
				Toast.makeText(getApplicationContext(), "Başarıyla Eklendi!", Toast.LENGTH_LONG).show();
				Intent go=new Intent(getApplicationContext(), KayitliUrunler.class);
				startActivity(go);
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "Ekleme Başarısız!", Toast.LENGTH_LONG).show();
			}
				
			}
		});
		
		
		
	}
	
	@SuppressWarnings("deprecation")
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog alertMessage = new AlertDialog.Builder(this).create();
            alertMessage.setTitle("Bilgi");
            alertMessage.setMessage("Lütfen Geri tuşuna Basmayınız!");
            alertMessage.setButton("Tamam", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Evet'e tıklanıldığında çalışması istenen kod buraya yazılacak.
                   
                }
            });
         
            alertMessage.show();
        }
        return super.onKeyDown(keyCode, event);
    }
	//nfc okut
	
	private void showMessage(int title, int message) {
        mDialog.setTitle(title);
        mDialog.setMessage(getText(message));
        mDialog.show();
    }

	private NdefRecord newTextRecord(String text, Locale locale, boolean encodeInUtf8) {
        byte[] langBytes = locale.getLanguage().getBytes(Charset.forName("US-ASCII"));

        Charset utfEncoding = encodeInUtf8 ? Charset.forName("UTF-8") : Charset.forName("UTF-16");
        byte[] textBytes = text.getBytes(utfEncoding);

        int utfBit = encodeInUtf8 ? 0 : (1 << 7);
        char status = (char) (utfBit + langBytes.length);

        byte[] data = new byte[1 + langBytes.length + textBytes.length];
        data[0] = (byte) status;
        System.arraycopy(langBytes, 0, data, 1, langBytes.length);
        System.arraycopy(textBytes, 0, data, 1 + langBytes.length, textBytes.length);

        return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], data);
    }

	
	 @Override
	    protected void onResume() {
	        super.onResume();
	        if (mAdapter != null) {
	            if (!mAdapter.isEnabled()) {
	                showWirelessSettingsDialog();
	            }
	            mAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
	            mAdapter.enableForegroundNdefPush(this, mNdefPushMessage);
	        }
	    }
	 
	 @Override
	    protected void onPause() {
	        super.onPause();
	        if (mAdapter != null) {
	            mAdapter.disableForegroundDispatch(this);
	            mAdapter.disableForegroundNdefPush(this);
	        }
	    }
	 
	 private void showWirelessSettingsDialog() {
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setMessage(R.string.nfc_disabled);
	        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialogInterface, int i) {
	                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
	                startActivity(intent);
	            }
	        });
	        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialogInterface, int i) {
	                finish();
	            }
	        });
	        builder.create().show();
	        return;
	    }

	 
	 private void resolveIntent(Intent intent) {
	        String action = intent.getAction();
	        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
	                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
	                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
	            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
	            NdefMessage[] msgs;
	            if (rawMsgs != null) {
	                msgs = new NdefMessage[rawMsgs.length];
	                for (int i = 0; i < rawMsgs.length; i++) {
	                    msgs[i] = (NdefMessage) rawMsgs[i];
	                }
	            } else {
	                // Unknown tag type
	                byte[] empty = new byte[0];
	                byte[] id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
	                Parcelable tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
	                byte[] payload = dumpTagData(tag).getBytes();
	                NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, id, payload);
	                NdefMessage msg = new NdefMessage(new NdefRecord[] { record });
	                msgs = new NdefMessage[] { msg };
	            }
	            // Setup the views
	            
	       //     buildTagViews(msgs);
	        }
	    }
	 
	 private String dumpTagData(Parcelable p) {
	        
	        Tag tag = (Tag) p;
	        byte[] id = tag.getId();
	        urunBarkod.setText("");
	     urunBarkod.setText(String.valueOf(getReversed(id)));
	       // Toast.makeText(getApplicationContext(),"ID1:"+getReversed(id), Toast.LENGTH_LONG).show();
	        return ""+getReversed(id);
	    }

	  private long getReversed(byte[] bytes) {
	        long result = 0;
	        long factor = 1;
	        for (int i = bytes.length - 1; i >= 0; --i) {
	            long value = bytes[i] & 0xffl;
	            result += value * factor;
	            factor *= 256l;
	        }
	        return result;
	    }
	  
	  @Override
	    public void onNewIntent(Intent intent) {
	        setIntent(intent);
	        resolveIntent(intent);
	    }
	
	
	// nfc okut son
	
	 
}
