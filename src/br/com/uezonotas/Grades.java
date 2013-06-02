package br.com.uezonotas;

/*
 * Autor: André Galdino da Silveira
 * 
 * Classe com webview da grade de notas.
 * */

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class Grades extends Activity {

	private DBManager db;	
	CursorAdapter adapter;
	CursorAdapter adapter2;
	ListView lvPeriodo;
	ListView lvAll;
	TabHost tabs;
	Cursor materiasPeriodo;
	Cursor materiasTodas;
	
	ArrayList<String> data = new ArrayList<String>();	

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grades);
		
		lvPeriodo = (ListView) findViewById(R.id.listMaterias);
		lvAll = (ListView) findViewById(R.id.listAll);
		tabs = (TabHost) findViewById(R.id.tabhost);
		tabs.setup();
		
		TabSpec spec1 = tabs.newTabSpec("tabPeriodo");
		spec1.setContent(R.id.tabPeriodo);
		spec1.setIndicator("Periodo\nAtual");
		
		TabSpec spec2 = tabs.newTabSpec("tabTodos");
		spec2.setContent(R.id.tabAll);
		spec2.setIndicator("Todos");
		
		tabs.addTab(spec1);
		tabs.addTab(spec2);
					
		db = new DBManager(this);	
				
		materiasPeriodo = db.selectMateriaPeriodo();
		materiasTodas = db.selectMateria();
	
		
		adapter = new SimpleCursorAdapter(this, R.layout.materias, materiasPeriodo, new String[] {DBHelper.MATERIA,"_id"},new int[] {R.id.txtNmMateria,R.id.codMateria});
		adapter2 = new SimpleCursorAdapter(this, R.layout.materiasall, materiasTodas, new String[] {DBHelper.MATERIA,DBHelper.PERIODO,"_id"},new int[] {R.matall.codMateria,R.matall.periodo,R.matall.txtNmMateria});
		
		lvPeriodo.setAdapter(adapter);
		lvAll.setAdapter(adapter2);		
		
		lvPeriodo.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {		
				materiasPeriodo.moveToPosition(arg2);	
				data.clear();
							
				data.add(db.getNomeMateria(materiasPeriodo));
				data.add(db.getCodMateria(materiasPeriodo));
				data.add(db.getPeriodo(materiasPeriodo));
				data.add(db.getProf(materiasPeriodo));
				data.add(db.getAv1(materiasPeriodo));
				data.add(db.getAv2(materiasPeriodo));
				data.add(db.getAv3(materiasPeriodo));
				data.add(db.getMedia(materiasPeriodo));	
				
				Intent intent = new Intent(Grades.this, MatDetalhar.class);
				Bundle b = new Bundle();
				b.putStringArrayList("data", data);
				intent.putExtra("data", b);				
				
				startActivity(intent);					
			}
			
		});
		
		lvAll.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				materiasTodas.moveToPosition(arg2);
				data.clear();
				
				data.add(db.getNomeMateria(materiasTodas));
				data.add(db.getCodMateria(materiasTodas));
				data.add(db.getPeriodo(materiasTodas));
				data.add(db.getProf(materiasTodas));
				data.add(db.getAv1(materiasTodas));
				data.add(db.getAv2(materiasTodas));
				data.add(db.getAv3(materiasTodas));
				data.add(db.getMedia(materiasTodas));	
				
				Intent intent = new Intent(Grades.this, MatDetalhar.class);
				Bundle b = new Bundle();
				
				b.putStringArrayList("data", data);
				intent.putExtra("data", b);
				
				startActivity(intent);
			}
			
		});
	}
}
