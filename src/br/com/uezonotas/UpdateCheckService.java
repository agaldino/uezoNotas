package br.com.uezonotas;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.util.Log;

public class UpdateCheckService extends Service {

	private NotificationManager mNM;

	Connection con = new Connection();
	DBManager db;
	ER_Filter filter = new ER_Filter();

	List<NameValuePair> userdata = new ArrayList<NameValuePair>(1);
	Cursor notas;
	Cursor notastemp;
	boolean hasUpdate = false;	

	@Override
	public void onCreate() {		
		android.os.Debug.waitForDebugger();
		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		db = new DBManager(getApplicationContext());
		Timer t = new Timer();	
		TimerTask tk = new updateCheck();
		t.scheduleAtFixedRate(tk, 600000, 600000);		
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	class updateCheck extends TimerTask{		
		public void run(){
			try {			
				userdata.add(new BasicNameValuePair("username", new UezoNotasActivity().getMat())); 
				userdata.add(new BasicNameValuePair("password", new UezoNotasActivity().getPwd()));
				String html = con.connect(userdata);
				ArrayList<ArrayList<String>> dadosHtml = filter.datafilter(html,
						getApplicationContext());
				db.Open();
				db.insertDataTemp(dadosHtml);
				db.Close();
	
				notas = db.selectNotas();
				notastemp = db.selectNotasTemp();
	
				for (int i = 1; i < notas.getCount(); i++) {
					notas.moveToPosition(i);
					notastemp.moveToPosition(i);
	
					if (notas.getString(1).equals(notastemp.getString(1))) {
						hasUpdate = true;
					}
	//				if (db.getAv1(notas).equals(db.getAv1(notastemp))) {
	//					hasUpdate = true;
	//				} 
	//				else if (!db.getAv2(notas).equals(db.getAv2(notastemp))) {
	//					hasUpdate = true;
	//				} else if (!db.getAv3(notas).equals(db.getAv3(notastemp))) {
	//					hasUpdate = true;
	//				}
				}
	
				if (hasUpdate == true) {
					Notification notification = new Notification(
							R.drawable.ic_menu_info_details, "UezoNotas",
							System.currentTimeMillis());
					//PendingIntent pendingintent = PendingIntent.getActivity(this,
						//	0, new Intent(this, Grades.class), 0);
					
					PendingIntent pendingintent = PendingIntent.getActivity(getApplication(), 0, new Intent(getApplicationContext(), Grades.class), 0);
	
					notification.setLatestEventInfo(getApplicationContext(), "Nota nova no sistema",
							"Nova nota no sistema", pendingintent);
	
					mNM.notify(0, notification);			
				}
	
			} catch (Exception e) {
				Log.e("UpdateCheckService", e.getMessage());
			}		
		}	
	}
}
