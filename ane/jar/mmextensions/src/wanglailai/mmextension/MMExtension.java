/**
	author: wanglailai@gmail.com
**/
package wanglailai.mmextension;

import wanglailai.mmextension.market.MobileMarketContext;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;

public class MMExtension implements FREExtension {

	private static final String EXT_NAME = "wanglailai.mmextension";
	private FREContext context;
	private String tag = EXT_NAME + ".MMExtension";

	@Override
	public FREContext createContext(String contextKey) {
		Log.i(tag, "Creating context:" + contextKey);

		context = new MobileMarketContext();
		return context;
	}

	@Override
	public void dispose() {
		Log.i(tag, "Disposing extension");
		context.dispose();
		context = null;
	}

	@SuppressLint("NewApi")
	@Override
	public void initialize() {
		Log.i(tag, "Initialize");

		// Disable strict mode so we can use the filesystem on the main thread and generally be bad citizens.
		if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			Log.i(tag, "Set StrictMode permitAll()");
		}
	}

}
