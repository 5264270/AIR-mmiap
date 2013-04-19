package wanglailai.mmextension.market;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

public class BillingStartPayment implements FREFunction {	
	public static final String KEY = "MMBillingStartPayment";
	private String TAG;

	@Override
	public FREObject call(FREContext context, FREObject[] args) {
		MobileMarketContext ctx = (MobileMarketContext) context;
		TAG = ctx.getIdentifier() + "." + KEY;
		Log.i(TAG, "Invoked " + KEY);
		
		String productId;
		int quantity;
		try {
			productId = args[0].getAsString();
			quantity = args[1].getAsInt();
			BillingController.getInstance(ctx).startPayment(productId, quantity);
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
