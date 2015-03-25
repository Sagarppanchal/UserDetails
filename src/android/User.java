/** @author Imran Nazeer
 * 
 * A Cordova Plugin file which is used to get user primary email and number from Android device.
 * 
 */


package com.imran.getuserinfo;


import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.telephony.TelephonyManager;

public class User extends CordovaPlugin {
public static final String TAG = "User";

public static String number;                           // user number
public static String email;                            // user email


/**
* Constructor.
*/
public User() {
}

/**
* Sets the context of the Command. This can then be used to do things like
* get file paths associated with the Activity.
*
* @param cordova The context of the main Activity.
* @param webView The CordovaWebView Cordova is running in.
*/
public void initialize(CordovaInterface cordova, CordovaWebView webView) {
 super.initialize(cordova, webView);
 User.email = getUserMail();
 User.number = getUserNumber();
}

/**
* Executes the request and returns PluginResult.
*
* @param action            The action to execute.
* @param args              JSONArry of arguments for the plugin.
* @param callbackContext   The callback id used when calling back into JavaScript.
* @return                  True if the action was valid, false if not.
*/
public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
 if (action.equals("getuserinfo")) {
     JSONObject r = new JSONObject();
     r.put("email", User.email);
     r.put("number", User.number);
     callbackContext.success(r);
 }
 else {
     return false;
 }
 return true;
}

/************************************************************
            Plugin Core Methods
 ************************************************************/
/**
* Get the user number
* 
* Note : If the mobile number is not available it returns null or empty string
* If you want return both the number use array instead or combine both number into Strings and use as per your req.
* 
* @return number
*/
private String getUserNumber() {
    TelephonyManager mManager =(TelephonyManager)this.cordova.getActivity().getSystemService(Context.TELEPHONY_SERVICE);
    String mPhoneNumber =   mManager.getLine1Number();
    if(mPhoneNumber.length() > 1){
        return mPhoneNumber;
    }else {
        return "0";
    }

}

/**
* Get the user primary email address
* 
* Note : If the mobile number is not available it returns null or empty string
* 
* @return email
*/
private String getUserMail() {
    AccountManager accountManager = AccountManager.get(this.cordova.getActivity()); 
    Account account = getAccount(accountManager);
    
    if (account == null) {
        return null;
    } else {
        return account.name;
    }
}

private static Account getAccount(AccountManager accountManager) {
    Account[] accounts = accountManager.getAccountsByType("com.google");
    Account account;
    if (accounts.length > 0) {
        account = accounts[0];     
    } else {
        account = null;
    }
    return account;
}

}

