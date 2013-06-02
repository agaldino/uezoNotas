package br.com.uezonotas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	public static final String TABLE_NAME = "notas";
	public static final String TEMP_TABLE_NAME = "tempnotas";
	private static final String DATABASE_NAME = "notas.db";	

	public static final String MATERIA = "CodMateria";
	public static final String NM_MATERIA = "NM_Materia";
	public static final String PERIODO = "periodo";
	public static final String PROF_MATERIA = "prof_materia";
	public static final String AV1 = "av1";
	public static final String AV2 = "av2";
	public static final String AV3 = "av3";
	public static final String MEDIA = "media";
	
	
	public static final String CREATE_DB = "CREATE TABLE " + TABLE_NAME + " (" 
			+ MATERIA + " text NULL,"
			+ NM_MATERIA + " text NULL,"
			+ PERIODO + " text NULL,"
			+ PROF_MATERIA + " text NULL,"
			+ AV1 + " text NULL,"
			+ AV2 + " text NULL,"
			+ AV3 + " text NULL,"
			+ MEDIA + " text NULL" + "); ";
	
	public static final String CREATE_DB_TEMP = "CREATE TABLE " + TEMP_TABLE_NAME + " (" 
			+ MATERIA + " text NULL,"
			+ NM_MATERIA + " text NULL,"
			+ PERIODO + " text NULL,"
			+ PROF_MATERIA + " text NULL,"
			+ AV1 + " text NULL,"
			+ AV2 + " text NULL,"
			+ AV3 + " text NULL,"
			+ MEDIA + " text NULL" + ");";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_DB);
		db.execSQL(CREATE_DB_TEMP);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
