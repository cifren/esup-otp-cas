package org.esupportail.cas.authentication;

import org.jasig.cas.authentication.UsernamePasswordCredential;
import org.jasig.cas.authentication.HandlerResult;
import org.jasig.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.principal.Principal;
import org.jasig.cas.authentication.principal.SimplePrincipal;

import org.json.*;

import javax.security.auth.login.FailedLoginException;
import org.jasig.cas.authentication.PreventedException;
import java.security.GeneralSecurityException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import java.security.MessageDigest;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.util.Calendar;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class EsupOtpApiAuthenticationHandler extends AbstractUsernamePasswordAuthenticationHandler {

	public static String urlApi;

	public static String usersSecret;

	private static String apiPassword;

	@Override
	protected final HandlerResult authenticateUsernamePasswordInternal(final UsernamePasswordCredential credential)throws GeneralSecurityException, PreventedException{
		try{
			JSONObject response = verifyOtp(credential.getUsername(), credential.getPassword());
			if(response.getString("code").equals("Ok")){
				System.out.println(response.toString());
				return createHandlerResult(credential, createPrincipal(credential.getUsername()), null);
			}else{
				throw new FailedLoginException();
			}
		}catch(IOException e){
			throw new PreventedException("HTTP Request error", e);
		}
	}

	private JSONObject verifyOtp(String uid, String otp) throws IOException {
			String url = urlApi+"/verify_code/"+uid+"/"+otp+"/"+apiPassword;

			URL obj = new URL(url);
			int responseCode;
			HttpURLConnection con=null;
			con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			responseCode = con.getResponseCode();
			BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			return new JSONObject(response.toString());
	}

	/**
     * Creates a CAS principal with attributes
     *
     * @param username Username that was successfully authenticated which is used for principal
     *
     * @return Principal
     *
     * @throws LoginException On security policy errors related to principal creation.
     */
    protected Principal createPrincipal(final String username){
        return new SimplePrincipal(username);
    }

    public String getUserHash(String uid) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    	Calendar calendar = Calendar.getInstance();
    	MessageDigest md5Md = MessageDigest.getInstance("MD5");
		String md5 = (new HexBinaryAdapter()).marshal(md5Md.digest(usersSecret.getBytes()));
		md5 = md5.toLowerCase();
    	int day = calendar.get(Calendar.DAY_OF_WEEK)-1;
    	if(day<1)day=7;
    	int hour = calendar.get(Calendar.HOUR_OF_DAY);
    	String salt = md5+uid+day+hour;
    	MessageDigest sha256Md = MessageDigest.getInstance("SHA-256");
		String userHash = (new HexBinaryAdapter()).marshal(sha256Md.digest(salt.getBytes()));
		userHash = userHash.toLowerCase();
    	return userHash; 
    }

    public String getUrlApi() {
    	return urlApi; 
    }

    public void setUrlApi(String urlApi) {
    	this.urlApi = urlApi; 
 	}

 	public String getUsersSecret() {
    	return usersSecret; 
    }

    public void setUsersSecret(String usersSecret) {
     	this.usersSecret = usersSecret; 
 	}

 	public void setApiPassword(String apiPassword) {
    	this.apiPassword = apiPassword;
    }	
}