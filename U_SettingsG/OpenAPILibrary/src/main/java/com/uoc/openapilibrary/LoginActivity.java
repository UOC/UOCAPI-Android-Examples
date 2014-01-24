package com.uoc.openapilibrary;

import net.smartam.leeloo.client.OAuthClient;
import net.smartam.leeloo.client.URLConnectionClient;
import net.smartam.leeloo.client.request.OAuthClientRequest;
import net.smartam.leeloo.client.response.OAuthJSONAccessTokenResponse;
import net.smartam.leeloo.common.exception.OAuthProblemException;
import net.smartam.leeloo.common.exception.OAuthSystemException;
import net.smartam.leeloo.common.message.types.GrantType;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

//TODO: TRATAR CASO DE ERROR LOGIN o rechazo permisos
public abstract class LoginActivity extends Activity {
	private String oauthToken;
	private static SharedPreferences settings;
	private ProgressDialog pd;
	public boolean running;

	public abstract Intent NextActivityIntent();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pd = new ProgressDialog(this);
		pd.setCancelable(false);
		pd.setIndeterminate(true);
		pd.setTitle("Cargando");
		pd.setMessage("Estamos cargando sus datos");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

	protected static String getToken() {
		return settings.getString("oauthToken", "");
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// Se llama por ser SingleTask -> En manifest
		super.onNewIntent(intent);
		Uri uri = intent.getData();
		// Si venimos de callback, obtenemos el codigo y pedimosoauthToken
		if (uri != null && uri.toString().startsWith(Constants.REDIRECT_URI)) {
			String code = uri.getQueryParameter("code");
			Log.i("LOGIN", "codi: " + code);
			new getTokenTask().execute(code);
			if (running) pd.show();
			Log.i("LOGIN", "token: " + oauthToken);

		}
	}

	private String retriveAccesToken(String client, String secret,
			String redirectUri, String code) {
		OAuthClientRequest request = null;

		try {
			request = OAuthClientRequest
					.tokenLocation(Constants.ACCES_TOKEN_URI)
					.setGrantType(GrantType.AUTHORIZATION_CODE)
					.setClientId(client).setClientSecret(secret)
					.setRedirectURI(redirectUri).setCode(code)
					.buildBodyMessage();
		} catch (OAuthSystemException e) {
			Log.e("LOGIN_ERROR", "Se ha producido un error del sistema");
			e.printStackTrace();
			return null;
		}

		try {
			OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
			OAuthJSONAccessTokenResponse response;
			response = oAuthClient.accessToken(request);
			oauthToken = response.getAccessToken();
			Log.v("LOGIN", response.getBody().toString());
			return oauthToken;

		} catch (OAuthSystemException e1) {
			Log.e("LOGIN_ERROR", "Ha habido un error en accesToken System");
			e1.printStackTrace();
			return null;
		} catch (OAuthProblemException e1) {
			Log.e("LOGIN_ERROR", "Ha habido un error en accesToken Problem");
			e1.printStackTrace();
			return null;
		}
	}

	protected String getToken(String client, String secret, String redirectUri,
			String code) {

		Log.i("LOGIN", "Getting oauthToken");

		// Si no tenemos token guardado buscamos uno, si no, comprobamos si esta
		// "vivo"
		/*oauthToken = settings.getString("oauthToken", ""); 
		if (oauthToken.equals("")) { 
			return oauthToken = retriveAccesToken(client, secret, redirectUri, code); 
		}
		else if (!validToken(oauthToken)){ 
			return oauthToken = refreshToken(client, secret, redirectUri, code); 
			
		}*/

		oauthToken = retriveAccesToken(client, secret, redirectUri, code);
		return oauthToken;
	}

	private boolean validToken(String oauthToken2) {
		return false; // de momento token siempre caducado
		// TODO Ver como refrescar token
	}

	// TODO: public void IniciarLogin(String client, String redirect_uri);

	public void IniciarLogin() {
		// Con los datos de entrada, nos lleva a un navegador web para hacer
		// login en el servicio
		// TODO: Mirar javadoc para documentar
		OAuthClientRequest request = null;
		try {

			// preparamos la petici�n con nuestro code
			request = OAuthClientRequest
					.authorizationLocation(Constants.AUTHORIZE_URI)
					// No se pasa como parametro, porque deberia ser siempre la
					// misma
					.setClientId(Constants.CLIENT)
					.setRedirectURI(Constants.REDIRECT_URI).buildQueryMessage();
		} catch (OAuthSystemException e) {
			e.printStackTrace();
		}
		// Lanzamos las pagina de login
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(request
				.getLocationUri() + "&response_type=code"));

		startActivity(intent); // Iniciamos navegador

		// TODO: No lanzar activity e integrarlo en webview dentro de la app

	}

	private class getTokenTask extends AsyncTask<String, Void, String> {
		/*
		 * Get Token && start next activity
		 */
		@Override
		protected void onPreExecute() {
			running = true;
			super.onPreExecute();
		}

		protected void onPostExecute(String result) {
			oauthToken = result;
			running = false;
			pd.dismiss();
			if (oauthToken == null) {
				Toast.makeText(getApplicationContext(), "Error al hacer Login",
						Toast.LENGTH_LONG).show();
			} else {
				Intent i = NextActivityIntent();
				// Ya esta en sharedPreference
				// i.putExtra("token", oauthToken);
				settings = getSharedPreferences(Constants.CONFIG_FILE,
						MODE_PRIVATE);
				settings.edit().putString("oauthToken", oauthToken).commit();
				startActivity(i);
				finish(); // Cerramos pantalla de login.

			}
			Log.v("ASYNC", "Token obtenido en asyncTask: " + result); 
		}

		@Override
		protected String doInBackground(String... code) {
			return getToken(Constants.CLIENT, Constants.SECRET,
					Constants.REDIRECT_URI, code[0]);

		}
	}

}
