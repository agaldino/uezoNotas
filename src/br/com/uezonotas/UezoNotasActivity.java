package br.com.uezonotas;

/*
 * Autor: André Galdino da Silveira
 *
 * Activity principal.
 * */

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
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
	ProgressDialog progress;
	Handler handler;

	public static final int SOBRE = 0;
	public static final int DEV = 1;
	public static String PERIODO= "";
	Calendar c = Calendar.getInstance();	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		PERIODO = String.valueOf(c.get(Calendar.YEAR));
		PERIODO += "_";
		PERIODO += c.get(Calendar.MONTH) >= 7 ? "2": "1"; 

		handler = new Handler();

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

		////////////////////////////////////////

		connect.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				progress = ProgressDialog.show(UezoNotasActivity.this,
						"Conectando", "Aguarde...");
				progress.setCancelable(true);

				new Thread() {
					@Override
					public void run() {

						try {

							mat = "1111311005";//matTxt.getText().toString();
							pwd = "RM123";//passwdTxt.getText().toString();

							data.add(new BasicNameValuePair("username", mat));
							data.add(new BasicNameValuePair("password", pwd));

							try {
								connection.connect(data, getApplicationContext());

							} catch (ClientProtocolException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							} catch (KeyManagementException e) {
								e.printStackTrace();
							} catch (NoSuchAlgorithmException e) {
								e.printStackTrace();
							} catch (KeyStoreException e) {
								e.printStackTrace();
							} catch (UnrecoverableKeyException e) {
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

			// about.setTitle("Sobre");
			/*TextView app = (TextView) about.findViewById(R.about.app);
			TextView appdesc = (TextView) about.findViewById(R.about.appdesc);*/

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
			// dev.setTitle("Desenvolvedor");
			/*TextView developer = (TextView) dev.findViewById(R.dev.dev);
			TextView devdesc = (TextView) dev.findViewById(R.dev.devdesc);
			TextView devemail = (TextView) dev.findViewById(R.dev.devemail);
			TextView colab = (TextView) dev.findViewById(R.dev.colab);*/

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