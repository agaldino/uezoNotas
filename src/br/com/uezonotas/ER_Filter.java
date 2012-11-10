package br.com.uezonotas;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;

public class ER_Filter extends Activity {
	
	private DBManager db;

	public void datafilter(String html, Context context) {
		

		db = new DBManager(context);

		@SuppressWarnings("unused")
		String student = new String();
		String tabela = new String();		
		ArrayList<String> values = new ArrayList<String>();
		ArrayList<ArrayList> data = new ArrayList<ArrayList>();

		Pattern tablePattern = Pattern.compile("<table.*?>(.*?)</table>",
				Pattern.DOTALL);
		Matcher tableMatch = tablePattern.matcher(html);

		if (tableMatch.find()) {
			tabela = tableMatch.group();
		}

		Pattern spanPattern = Pattern.compile("<span.*?>(.*?)</span>",
				Pattern.DOTALL);
		Matcher spanMatch = spanPattern.matcher(tabela);

		spanMatch.find();
		student = spanMatch.group(1).trim();

		while (spanMatch.find()) {			
			values.add(spanMatch.group(1).trim());
			if (values.size() == 8) {
				data.add((ArrayList<String>) values.clone());
				values.clear();
			}
		}
		
		db.Open();
		db.insertData(data);
		db.Close();
	}

}
