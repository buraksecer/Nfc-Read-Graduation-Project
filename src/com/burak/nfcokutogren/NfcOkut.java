package com.burak.nfcokutogren;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NfcOkut extends Activity {
	 Dialog dialog;
	
		ArrayList<String> arrayList=new ArrayList<String>();
	    private NfcAdapter mAdapter;
	    private PendingIntent mPendingIntent;
	    private NdefMessage mNdefPushMessage;
	    private AlertDialog mDialog;
	    Button btnDialog,urunEkle;
		 TextView txtDialog;
	    Database db=new Database(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nfcokut);
		Button goKayitliUrunler=(Button) findViewById(R.id.nfcOkutKayitAc);
		 TextView txt=(TextView) findViewById(R.id.textView1);
		 ImageView img=(ImageView) findViewById(R.id.imageView1);
		Button urunEkle=(Button) findViewById(R.id.urunEkle);
		txt.setTypeface(Typeface.SERIF);
		goKayitliUrunler.setTypeface(Typeface.SERIF);
		urunEkle.setTypeface(Typeface.SERIF);
		
		try {
			 AnimationDrawable animationDrawable = (AnimationDrawable) img.getDrawable(); 
			animationDrawable.start();
		} catch (Exception e) {
			//Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
		}

		
		urunEkle.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			 
				Intent go=new Intent(getApplicationContext(), UrunEkle.class);
				startActivity(go);
				
			}
		});
		
		
		
		
		goKayitliUrunler.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent go=new Intent(getApplicationContext(), KayitliUrunler.class);
				startActivity(go);
				
			}
		});
		
		
		
		//Nfc Okutma Kısmı
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
		
	//Nfc Okutma Kısmı Bitiş(Onload için)
		
		
	}
	
	//Nfc Okutma Devam
	
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
	      kontrolEt(String.valueOf(getReversed(id)));
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
	
	
	
	// Nfc Okutma Son
	
	
	
		public void kontrolEt(String gelenBarkod){
			try {
				
				
				db.open();
				arrayList=db.getThat(gelenBarkod);
				db.close();
				
				dialog=new Dialog(NfcOkut.this);
           		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
           		dialog.setContentView(R.layout.dialogurunnfc);
           		
           		btnDialog=(Button) dialog.findViewById(R.id.dialogKapat);
           		txtDialog=(TextView) dialog.findViewById(R.id.dialogUrunler); 
           		urunEkle=(Button) dialog.findViewById(R.id.urunEkle);
           		btnDialog.setTypeface(Typeface.SERIF);
           		txtDialog.setTypeface(Typeface.SERIF);
           		
           		urunEkle.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						if(arrayList.get(0).toString()=="boş"){
							dialog.dismiss();
						}
						else{
							try {
							String sonuc;
								db.open();
								sonuc=db.updateBarkodNo(arrayList.get(5).toString());
								db.close();
								if(sonuc=="1"){
									Intent go=new Intent(getApplicationContext(), KayitliUrunler.class);
									startActivity(go);
								}
								else{
									Toast.makeText(getApplicationContext(), sonuc.toString(), Toast.LENGTH_LONG).show();
								}
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
						
					}
				});
           		
           		
           		
           		btnDialog.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						dialog.dismiss();
						
					}
				});
           		String gelenUrun="Ürün adı:"+arrayList.get(0).toString()+"\n"+"Ürün Son Kullanma Tarihi:"
				+arrayList.get(1).toString()+"\n"+"Ürün Üretim Tarihi:"+arrayList.get(2).toString()+"\n"
				+"Ürünün İçindekiler:"+arrayList.get(3).toString()+"\n"+"Ürün Besin Değeri:"+arrayList.get(4).toString()+"(kalori)\n"
				+"Ürün Barkod Numarası:"+arrayList.get(5).toString();
           		txtDialog.setText(gelenUrun);
           		dialog.show();
				
				
				
			} catch (Exception e) {
				// TODO: handle exception
			}
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
}
