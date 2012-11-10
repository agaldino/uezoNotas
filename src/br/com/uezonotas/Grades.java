package br.com.uezonotas;

/*
 * Autor: André Galdino da Silveira
 * 
 * Classe com webview da grade de notas.
 * */

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class Grades extends Activity {

	private DBManager db;	
	CursorAdapter adapter;
	CursorAdapter adapter2;
	ListView lvPerido;
	ListView lvAll;
	TabHost tabs;

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grades);
		
		lvPerido = (ListView) findViewById(R.id.listMaterias);
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
				
		Cursor materiasPeriodo = db.selectMateriaPeriodo();
		Cursor materiasTodas = db.selectMateria();
		//c.moveToFirst();		
		//while(c.moveToNext()){
			//matGroup.put(Mat, db.getNomeMateria(c));
			//curChildMap.put(DadosMat, "Código:" + db.getCodMateria(c) + " Professor:" + db.getProf(c) + " AV1:" + db.getAv1(c) + " AV2:" + db.getAv2(c) + " AV3:" + db.getAv1(c) 
			//		+ " Média:" + db.getMedia(c));
		//} 
		
		adapter = new SimpleCursorAdapter(this, R.layout.materias, materiasPeriodo, new String[] {DBHelper.MATERIA,"_id"},new int[] {R.id.txtNmMateria,R.id.codMateria});
		adapter2 = new SimpleCursorAdapter(this, R.layout.materiasall, materiasTodas, new String[] {DBHelper.MATERIA,DBHelper.PERIODO,"_id"},new int[] {R.matall.txtNmMateria,R.matall.periodo,R.matall.codMateria});
		
		lvPerido.setAdapter(adapter);
		lvAll.setAdapter(adapter2);
	
	}
}
