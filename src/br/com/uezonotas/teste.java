package br.com.uezonotas;

/*
 * Autor: André Galdino da Silveira
 * 
 * Classe de teste para exibir o webview.
 * */

import org.apache.http.cookie.Cookie;
import android.webkit.SslErrorHandler;
import android.net.http.SslError;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class teste extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.teste);

		WebView webTeste;
		webTeste = (WebView) findViewById(R.id.webTeste);

		Cookie sessionCookie = Connection.cookie;
		CookieSyncManager.createInstance(this);
		CookieManager cookieManager = CookieManager.getInstance();

		if (sessionCookie != null) {			
			String cookieString = sessionCookie.getName() + "="
					+ sessionCookie.getValue() + "; domain="
					+ sessionCookie.getDomain();
			cookieManager.setCookie(Connection.url, cookieString);
			CookieSyncManager.getInstance().sync();
		}
		webTeste.setWebViewClient(new WebViewClient() {

			public void onReceivedSslError(WebView view,
					SslErrorHandler handler, SslError error) {
				handler.proceed();
			}
		});

		webTeste.loadUrl("https://www.uezo.rj.gov.br/aluno/boletim_aluno_b.php");
		//cookieManager.removeSessionCookie();
	}
}
