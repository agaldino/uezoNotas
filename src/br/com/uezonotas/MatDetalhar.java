package br.com.uezonotas;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.TextView;

public class MatDetalhar extends Activity {
	
	TextView materia;
	TextView cod;
	TextView periodo;
	TextView prof;
	TextView av1;
	TextView av2;
	TextView av3;
	TextView media;
	
	ArrayList<String> dados;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.materiadetalhar);        
    }
    
    @Override
    public void onStart(){
    	Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        dados = (ArrayList<String>)bundle.getStringArrayList("data");
        
        materia = (TextView) findViewById(R.matDet.txtMateria);
        cod = (TextView) findViewById(R.matDet.txtCod);
        periodo = (TextView) findViewById(R.matDet.txtPeriodo);
        prof = (TextView) findViewById(R.matDet.txtProfessor);        
        av1 = (TextView) findViewById(R.matDet.av1);
        av2 = (TextView) findViewById(R.matDet.av2);
        av3 = (TextView) findViewById(R.matDet.av3);
        media = (TextView) findViewById(R.matDet.media);
        
        materia.setText(dados.get(0));
        cod.setText(dados.get(1));
        periodo.setText(dados.get(2));
        prof.setText(dados.get(3));
        av1.setText(dados.get(4));
        av2.setText(dados.get(5));
        av3.setText(dados.get(6));
        media.setText(dados.get(7));
        
        
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.d, menu);
        return true;
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent keyevent) {  
    	   if(keyCode == KeyEvent.KEYCODE_BACK) {  
    	     MatDetalhar.this.finish();
    	   }  
    	   return super.onKeyDown(keyCode, keyevent);
    	}
}
