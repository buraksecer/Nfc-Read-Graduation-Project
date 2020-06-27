package com.burak.nfcokutogren;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Database {


	
	private static final String DB_NAME="Urunler";
	private static final int DB_VERSION=1;
  public static int degerBulunamadi=1;
	private SQLiteDatabase ourDatabase;
	private DBHelper ourHelper;
	private Context ourContext;
	
	public Database(Context context){
		
		ourContext=context;
		
	}
	
	public class DBHelper extends SQLiteOpenHelper{

		public DBHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
		
			db.execSQL("create table "+DB_NAME+"(urunId integer PRIMARY KEY AUTOINCREMENT,urunBarkod text not null,urunOnay text not null,urunAdi text not null,urunSKT text not null,urunUT text not null,urunIcindekiler text not null,urunBesinDegerleri text not null );");
//android.database.sqlite.SQLiteException: near "tableapp": syntax error (code 1): , while compiling: create tableapp(_idINTEGER PRIMARY KEY AUTOINCREMENT,u_name TEXT NOT NULL,u_pass TEXT NOT NULL);
//android.database.sqlite.SQLiteException: near "tableapp": syntax error (code 1): , while compiling: create tableapp(_id INTEGER PRIMARY KEY AUTOINCREMENT,u_name TEXT NOT NULL,u_pass TEXT NOT NULL);
		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXIST"+DB_NAME);
			onCreate(db);
		}		
	}
	public Database open(){
		ourHelper=new DBHelper(ourContext);
		ourDatabase=ourHelper.getWritableDatabase();
		return this;
	}
	public Database close(){
		
		ourHelper.close();
		return this;
	}
	public void addThat(String urunAdi,String urunSKT,String urunUT,String urunIcindekiler,String urunBesinDegerleri,String urunBarkod){
		ContentValues cv=new ContentValues();
		cv.put("urunBarkod", urunBarkod);
		cv.put("urunAdi", urunAdi);
		cv.put("urunSKT", urunSKT);
		cv.put("urunUT", urunUT);
		cv.put("urunOnay", "0");
		cv.put("urunIcindekiler", urunIcindekiler);
		cv.put("urunBesinDegerleri", urunBesinDegerleri);
		ourDatabase.insert(DB_NAME,null,cv);
	}
	public ArrayList<String> getThat(){
		
		ArrayList<String> arrayList =new ArrayList<String>();
		String[] columns=new String[]{"urunAdi","urunBarkod","urunOnay","urunSKT","urunUT","urunIcindekiler","urunBesinDegerleri"};
		Cursor c=ourDatabase.query(DB_NAME, columns,"urunOnay='1'", null, null, null, null, null);
		//Cursor c2=ourDatabase.query(DB_NAME, columns, null, null, null, null, null);
		int urunAdi=c.getColumnIndex("urunAdi");
	    int urunSKT=c.getColumnIndex("urunSKT");
	    int urunUT=c.getColumnIndex("urunUT");
	    int urunIcindekiler=c.getColumnIndex("urunIcindekiler");
	    int urunBesinDegerleri=c.getColumnIndex("urunBesinDegerleri");
	    
	   // int urunAdiList=c2.getColumnIndex("urunAdi");
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
	
			Listviews.arrayurunAdi.add(c.getString(urunAdi)+"\nÜrün SKT:"+c.getString(urunSKT)+"\nÜrün UT:"
					+c.getString(urunUT)+"\nÜrünün İçindekiler:"+c.getString(urunIcindekiler)+"\nÜrün Besin Değerleri:"
					+c.getString(urunBesinDegerleri));
			
				arrayList.add(c.getString(urunAdi));
			
		}
		return arrayList;
	}
	public ArrayList<String> getThat(String barkodNo){
		ArrayList<String> arrayList =new ArrayList<String>();
		String[] columns=new String[]{"urunAdi","urunBarkod","urunOnay","urunSKT","urunUT","urunIcindekiler","urunBesinDegerleri"};
		Cursor c=ourDatabase.query(DB_NAME, columns,"urunBarkod='"+barkodNo+"'", null, null, null, null, null);
		if(c.getCount()>0){
		int urunAdi=c.getColumnIndex("urunAdi");
	    int urunSKT=c.getColumnIndex("urunSKT");
	    int urunUT=c.getColumnIndex("urunUT");
	    int urunIcindekiler=c.getColumnIndex("urunIcindekiler");
	    int urunBesinDegerleri=c.getColumnIndex("urunBesinDegerleri");
	    int urunBarkod=c.getColumnIndex("urunBarkod");
	   
	    for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
	    	 arrayList.add(c.getString(urunAdi));
	 	    arrayList.add(c.getString(urunSKT));
	 	    arrayList.add(c.getString(urunUT));
	 	    arrayList.add(c.getString(urunIcindekiler));
	 	    arrayList.add(c.getString(urunBesinDegerleri));
	 	    arrayList.add(c.getString(urunBarkod));
			
		}
		}
		
		
		else{
			arrayList.add("boş");
		}
		
		return arrayList;
	}
	public String updateBarkodNo(String barkodNo){
		try {
			ContentValues cv = new ContentValues();
			cv.put("urunOnay","1");
			ourDatabase.update(DB_NAME, cv, "urunBarkod="+barkodNo, null);
			return "1";
		} catch (Exception e) {
			return e.getMessage();
		}
	
	}
	
	
	public ArrayList<String> search(String gelenBarkod){
		
		ArrayList<String> arrayList=new ArrayList<String>();
		String[] columns=new String[]{"urunAdi","urunBarkod","urunOnay","urunSKT","urunUT","urunIcindekiler","urunBesinDegerleri"};
		Cursor c=ourDatabase.query(DB_NAME, columns,"urunBarkod LIKE '%"+gelenBarkod+"%' and urunOnay='1'", null, null, null, null, null);
		if(c.getCount()>0){
		int urunAdi=c.getColumnIndex("urunAdi");
	    int urunSKT=c.getColumnIndex("urunSKT");
	    int urunUT=c.getColumnIndex("urunUT");
	    int urunIcindekiler=c.getColumnIndex("urunIcindekiler");
	    int urunBesinDegerleri=c.getColumnIndex("urunBesinDegerleri");

	   // int urunAdiList=c2.getColumnIndex("urunAdi");
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
	
			Listviews.arrayurunAdi.add(c.getString(urunAdi)+"\nÜrün SKT:"+c.getString(urunSKT)+"\nÜrün UT:"
					+c.getString(urunUT)+"\nÜrünün İçindekiler:"+c.getString(urunIcindekiler)+"\nÜrün Besin Değerleri:"
					+c.getString(urunBesinDegerleri));
			
				arrayList.add(c.getString(urunAdi));
			
			
		}
		Database.degerBulunamadi=1;
		return arrayList;
		}
		Database.degerBulunamadi=0;
		arrayList.add("ARANAN BARKOD BULUNAMADI!");
		return arrayList;
		
		
		
	}
}
