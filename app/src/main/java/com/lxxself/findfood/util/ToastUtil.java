/**
 * 
 */
package com.lxxself.findfood.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.amap.api.services.core.AMapException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class ToastUtil {

	public static void show(Context context, String info) {
		Toast.makeText(context, info, Toast.LENGTH_LONG).show();
	}

	public static void show(Context context, int info) {
		Toast.makeText(context, info, Toast.LENGTH_LONG).show();
	}
	public static void showShort(Context context, String info) {
		Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
	}

	public static void showShort(Context context, int info) {
		Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
	}
	public static void showerror(Context context, int rCode){
	    try {
	        switch (rCode) {
	        case 13:
	            throw new AMapException(AMapException.ERROR_USERID);
            case 15:
                throw new AMapException(AMapException.ERROR_UPLOADAUTO_STARTED);
	        case 16:
	            throw new AMapException(AMapException.ERROR_BINDER_KEY);
	        case 21:
                throw new AMapException(AMapException.ERROR_IO);
	        case 22:
                throw new AMapException(AMapException.ERROR_SOCKET);
	        case 23:
                throw new AMapException(AMapException.ERROR_SOCKE_TIME_OUT);
	        case 24:
                throw new AMapException(AMapException.ERROR_INVALID_PARAMETER);
	        case 25:
                throw new AMapException(AMapException.ERROR_NULL_PARAMETER);
	        case 26:
                throw new AMapException(AMapException.ERROR_URL);
	        case 27:
                throw new AMapException(AMapException.ERROR_UNKNOW_HOST);
	        case 28:
                throw new AMapException(AMapException.ERROR_UNKNOW_SERVICE);
	        case 29:
                throw new AMapException(AMapException.ERROR_PROTOCOL);
	        case 30:
                throw new AMapException(AMapException.ERROR_CONNECTION);
	        case 31:
                throw new AMapException(AMapException.ERROR_UNKNOWN);
	        case 32:
                throw new AMapException(AMapException.ERROR_FAILURE_AUTH);
	        case 33:
	            throw new AMapException(AMapException.ERROR_SERVICE);
	        case 34:
	            throw new AMapException(AMapException.ERROR_SERVER);
	        case 35:
                throw new AMapException(AMapException.ERROR_QUOTA);
	        case 36:
                throw new AMapException(AMapException.ERROR_REQUEST);
	        case 37:
                throw new AMapException(AMapException.ERROR_SHARE_SEARCH_FAILURE);
	        case 1901:
                throw new AMapException(AMapException.AMAP_LICENSE_IS_EXPIRED);
	        case 39:
                throw new AMapException(AMapException.ERROR_USERKEY_PLAT_NOMATCH);
	        case 1001:
                throw new AMapException(AMapException.AMAP_SIGNATURE_ERROR);
	        case 43:
                throw new AMapException(AMapException.ERROR_ROUTE_FAILURE);
	        case 44:
                throw new AMapException(AMapException.ERROR_OVER_DIRECTION_RANGE);
	        case 45:
                throw new AMapException(AMapException.ERROR_OUT_OF_SERVICE);
	        case 46:
                throw new AMapException(AMapException.ERROR_ID_NOT_FOUND);
	        case 60:
                throw new AMapException(AMapException.ERROR_SCODE);
	        case 2000:
                throw new AMapException(AMapException.AMAP_TABLEID_NOT_EXIST );
	        case 2001:
                throw new AMapException(AMapException.AMAP_ID_NOT_EXIST);
	        case 11:
	            Toast.makeText(context, "两次单次上传的间隔低于 5 秒", Toast.LENGTH_LONG).show();
	            break;
	        case 12:
	            Toast.makeText(context, "Uploadinfo 对象为空", Toast.LENGTH_LONG).show();
                break;
	        case 14:
	            Toast.makeText(context, "Point 为空，或与前次上传的相同", Toast.LENGTH_LONG).show();
                break;    
	        }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
	}
	public static String sHA1(Context context) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), PackageManager.GET_SIGNATURES);

			byte[] cert = info.signatures[0].toByteArray();

			MessageDigest md = MessageDigest.getInstance("SHA1");
			byte[] publicKey = md.digest(cert);
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < publicKey.length; i++) {
				String appendString = Integer.toHexString(0xFF & publicKey[i])
						.toUpperCase(Locale.US);
				if (appendString.length() == 1)
					hexString.append("0");
				hexString.append(appendString);
				hexString.append(":");
			}
			return hexString.toString();
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
