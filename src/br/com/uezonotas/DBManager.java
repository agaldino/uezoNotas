package br.com.uezonotas;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

	private SQLiteDatabase database;
	private DBHelper Helper;

	public DBManager(Context context) {
		Helper = new DBHelper(context);
	}

	public void Open() throws SQLException {
		database = Helper.getWritableDatabase();
	}

	public void Close() {
		Helper.close();
	}

	@SuppressWarnings("static-access")
	public void insertData(ArrayList<ArrayList<String>> data) throws SQLException {

		database.execSQL("DELETE FROM " + Helper.TABLE_NAME);
		ContentValues values = new ContentValues();

		for (ArrayList<String> arr : data) {

			values.put(Helper.MATERIA, (String) arr.get(0));
			values.put(Helper.NM_MATERIA, (String) arr.get(1));
			values.put(Helper.PERIODO, (String) arr.get(2));
			values.put(Helper.PROF_MATERIA, (String) arr.get(3));
			values.put(Helper.AV1, (String) arr.get(4));
			values.put(Helper.AV2, (String) arr.get(5));
			values.put(Helper.AV3, (String) arr.get(6));
			values.put(Helper.MEDIA, (String) arr.get(7));

			database.insert(Helper.TABLE_NAME, null, values);
		}

	}
	
	@SuppressWarnings("static-access")
	public void insertDataTemp(ArrayList<ArrayList<String>> data) throws SQLException {

		database.execSQL("DELETE FROM " + Helper.TEMP_TABLE_NAME);
		ContentValues values = new ContentValues();

		for (ArrayList<String> arr : data) {

			values.put(Helper.MATERIA, (String) arr.get(0));
			values.put(Helper.NM_MATERIA, (String) arr.get(1));
			values.put(Helper.PERIODO, (String) arr.get(2));
			values.put(Helper.PROF_MATERIA, (String) arr.get(3));
			values.put(Helper.AV1, (String) arr.get(4));
			values.put(Helper.AV2, (String) arr.get(5));
			values.put(Helper.AV3, (String) arr.get(6));
			values.put(Helper.MEDIA, (String) arr.get(7));

			database.insert(Helper.TEMP_TABLE_NAME, null, values);
		}

	}
	
	public Cursor selectMateria(){
		return Helper.getReadableDatabase().rawQuery("SELECT "+DBHelper.MATERIA+","+DBHelper.NM_MATERIA+" as _id,"+DBHelper.PERIODO+","+DBHelper.PROF_MATERIA+
				","+DBHelper.AV1+","+DBHelper.AV2+","+DBHelper.AV3+","+DBHelper.MEDIA+" FROM " + DBHelper.TABLE_NAME, null);
	}
	
	public Cursor selectMateriaPeriodo(){
		return Helper.getReadableDatabase().rawQuery("SELECT "+DBHelper.MATERIA+","+DBHelper.NM_MATERIA+" as _id,"+DBHelper.PERIODO+","+DBHelper.PROF_MATERIA+
				","+DBHelper.AV1+","+DBHelper.AV2+","+DBHelper.AV3+","+DBHelper.MEDIA+" FROM " + DBHelper.TABLE_NAME + 
				" WHERE " + DBHelper.PERIODO+" = '" + UezoNotasActivity.PERIODO+"'", null);
	}
	
	public Cursor selectNotas(){
		return Helper.getReadableDatabase().rawQuery("SELECT "+DBHelper.AV1+","+DBHelper.AV2+","+DBHelper.AV3 + " FROM " + DBHelper.TABLE_NAME , null);
	}
	
	public Cursor selectNotasTemp(){
		return Helper.getReadableDatabase().rawQuery("SELECT "+DBHelper.AV1+","+DBHelper.AV2+","+DBHelper.AV3 + " FROM " + DBHelper.TEMP_TABLE_NAME, null);
	}
	
	public String getCodMateria(Cursor c){
		return c.getString(0);
	}
	
	public String getNomeMateria(Cursor c){
		return c.getString(1);
	}
	
	public String getPeriodo(Cursor c){
		return c.getString(2);
	}
	
	public String getProf(Cursor c){
		return c.getString(3);
	}
	
	public String getAv1(Cursor c){
		return c.getString(4);
	}
	
	public String getAv2(Cursor c){
		return c.getString(5);
	}
	
	public String getAv3(Cursor c){
		return c.getString(6);
	}
	
	public String getMedia(Cursor c){
		return c.getString(7);
	}
}
