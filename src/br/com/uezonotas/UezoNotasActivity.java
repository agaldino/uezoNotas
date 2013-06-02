package br.com.uezonotas;

/*
 * Autor: André Galdino da Silveira
 *
 * Activity principal.
 * */

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UezoNotasActivity extends Activity {

	TextView inet;
	Button connect;
	EditText matTxt, passwdTxt;
	String mat, pwd;
	Connection connection = new Connection();
	List<NameValuePair> data = new ArrayList<NameValuePair>(1);
	InternetConect ic = new InternetConect();
	ER_Filter filter = new ER_Filter();
	DBManager db;
	ProgressDialog progress;
	Handler handler;

	public static final int SOBRE = 0;
	public static final int DEV = 1;
	public static String PERIODO = "";

	Calendar c = Calendar.getInstance();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);		

		PERIODO = String.valueOf(c.get(Calendar.YEAR));
		PERIODO += "_";
		PERIODO += c.get(Calendar.MONTH) >= 7 ? "2" : "1";

		handler = new Handler();
		db = new DBManager(getApplicationContext());

		connect = (Button) findViewById(R.id.connect);
		matTxt = (EditText) findViewById(R.id.matTxt);
		passwdTxt = (EditText) findViewById(R.id.passwdTxt);

		// Teste de Conexão
		if (InternetConect.Conectado(this) == false) {
			connect.setClickable(false);
			connect.setEnabled(false);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.inet);
			builder.setCancelable(true);
			builder.setTitle("Sem conexão com a internet :(");
			builder.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			builder.show();
		}
		
		Intent service = new Intent(this, UpdateCheckService.class);
		startService(service);

		connect.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				progress = ProgressDialog.show(UezoNotasActivity.this,
						"Conectando", "Aguarde...");
				progress.setCancelable(false);

				new Thread() {
					@Override
					public void run() {

						try {

							mat = "1111311005";// matTxt.getText().toString();
							pwd = "RM123";// passwdTxt.getText().toString();

							data.add(new BasicNameValuePair("username", mat));
							data.add(new BasicNameValuePair("password", pwd));

							try {
								String html = connection.connect(data);
								ArrayList<ArrayList<String>> dadosHtml = filter
										.datafilter(html,
												getApplicationContext());
								db.Open();
								db.insertData(dadosHtml);
								db.Close();

							} catch (Exception e) {
								e.printStackTrace();
							}

						} catch (Exception e) {
							Log.e("tag", e.getMessage());
						}
						progress.dismiss();
						tratamento();
					}
				}.start();
			}

		});	

	}

	public void tratamento() {
		handler.post(new Runnable() {

			public void run() {
				if (Connection.cookie == null) {
					AlertDialog.Builder auth = new AlertDialog.Builder(
							UezoNotasActivity.this);
					auth.setMessage("Usuario ou Senha incorretos!");
					auth.setCancelable(true);
					auth.setTitle("Falha na Autenticação");
					auth.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface auth,
										int which) {
									auth.cancel();
								}
							});
					auth.show();
				} else {
					Intent Grades = new Intent(UezoNotasActivity.this,
							Grades.class);					
					startActivity(Grades);
				}
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, SOBRE, 0, "Sobre");
		menu.add(0, DEV, 0, "Desenvolvedor");

		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		switch (item.getItemId()) {
		case SOBRE:
			final Dialog about = new Dialog(this);
			about.setContentView(R.layout.about);

			Button ok = (Button) about.findViewById(R.about.ok);
			ok.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					about.dismiss();
				}
			});
			about.show();
			break;
		case DEV:
			final Dialog dev = new Dialog(this);
			dev.setContentView(R.layout.dev);
			Button devOk = (Button) dev.findViewById(R.dev.devOk);
			devOk.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					dev.dismiss();
				}
			});
			dev.show();
			break;

		}

		return false;

	}

}