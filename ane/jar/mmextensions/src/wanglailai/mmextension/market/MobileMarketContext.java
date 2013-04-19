package wanglailai.mmextension.market;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;

public class MobileMarketContext extends FREContext {
	
	public static final String KEY = "MobileMarket";
	private Map<String, FREFunction> functionMap=null;
	private String tag;
	
	public MobileMarketContext(){
		tag =  "com.playdom.labsextensions." + KEY; 
		Log.i(tag, "Creating context"); 
	}
	
	@Override
	public void dispose() {
		Log.i(tag, "Dispose context");
		BillingController.getInstance(this).dispose();
	}

	@Override
	public Map<String, FREFunction> getFunctions() {
		Log.i(tag, "getFunctions");
		
		functionMap = new HashMap<String, FREFunction>();
        //In-app purchasing stuff
  		functionMap.put(BillingStartPayment.KEY, new BillingStartPayment());
  		functionMap.put(BillingInit.KEY, new BillingInit());
		return functionMap;
	}
	
	public String getIdentifier() { 
		return tag; 
	} 


}
