package br.com.uezonotas;

/*
 * Autor: André Galdino da Silveira
 * 
 * Classe responsavél pela conexão usando HttpClient
 * */

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.cookie.Cookie;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class Connection {

	private DefaultHttpClient client;
	private HttpPost httppost;
	HttpGet httpget;	
	public static Cookie cookie = null;
	public static String url = "https://www.uezo.rj.gov.br/aluno/log_in_aluno.php";
	public static String urlNotas = "https://www.uezo.rj.gov.br/aluno/boletim_aluno_b.php";
	List<Cookie> Cookies;
	
	
	ER_Filter filter = new ER_Filter();

	public String connect(List<NameValuePair> data)
			throws ClientProtocolException, IOException,
			KeyManagementException, NoSuchAlgorithmException,
			KeyStoreException, UnrecoverableKeyException {

		// Tratamento da conexão Https/SSL
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", new EasySSLSocketFactory(), 443));

		HttpParams params = new BasicHttpParams();
		params.setParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, 30);
		params.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE, new ConnPerRouteBean(30));
		params.setParameter(HttpProtocolParams.USE_EXPECT_CONTINUE, false);

		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		
		ClientConnectionManager cm = new SingleClientConnManager(params,schemeRegistry);		

		// HttpClient da conexão
		this.client = new DefaultHttpClient(cm, params);

		// Post na pagina
		httppost = new HttpPost(url);
		httppost.setEntity(new UrlEncodedFormEntity(data, HTTP.UTF_8));	
		client.execute(httppost);
		
		Cookies = client.getCookieStore().getCookies();

		for (int i = 0; i < Cookies.size(); i++) {
			cookie = Cookies.get(i);
		}

		// Get da pagina
		httpget = new HttpGet(urlNotas); 
		HttpResponse get = client.execute(httpget);
		HttpEntity entity = get.getEntity();
		String html = EntityUtils.toString(entity);
		
		client.getConnectionManager().shutdown();	
		return html;

	}

}
