package br.com.uezonotas;

/*
 * Autor: André Galdino da Silveira
 *
 * Coleta dados aplicando filtro no html da pagina usando expressões regulares.
 * */

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;

public class ER_Filter extends Activity {
	
	@SuppressWarnings("unchecked")
	public ArrayList<ArrayList<String>> datafilter(String html, Context context) {	

		@SuppressWarnings("unused")
		String student = new String();
		String tabela = new String();		
		ArrayList<String> values = new ArrayList<String>();
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();

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
		
		return data;	
		
	}

}
