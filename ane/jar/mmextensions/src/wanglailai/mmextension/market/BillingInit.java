package wanglailai.mmextension.market;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

public class BillingInit implements FREFunction {
	public static final String KEY = "MMBillingInit";
	private String TAG;
	@Override
	public FREObject call(FREContext context, FREObject[] args) {
		MobileMarketContext ctx = (MobileMarketContext) context;
		TAG = ctx.getIdentifier() + "." + KEY;
		Log.i(TAG, "Invoked " + KEY);
		
		String appId;
		String appKey;
		try {
			appId = args[0].getAsString();
			appKey = args[1].getAsString();
			
			BillingController.getInstance(ctx).init(appId, appKey);
		} catch (IllegalStateException e) {
			Log.w(TAG, e);
		} catch (FRETypeMismatchException e) {
			Log.w(TAG, e);
		} catch (FREInvalidObjectException e) {
			Log.w(TAG, e);
		} catch (FREWrongThreadException e) {
			Log.w(TAG, e);
		}
		return null;
	}

}
