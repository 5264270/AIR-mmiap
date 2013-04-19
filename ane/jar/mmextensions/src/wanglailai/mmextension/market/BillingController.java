/**
	author: wanglailai@gmail.com
**/
package wanglailai.mmextension.market;

import java.util.HashMap;

import mm.purchasesdk.OnPurchaseListener;
import mm.purchasesdk.Purchase;
import mm.purchasesdk.PurchaseCode;
import android.app.Activity;
import android.util.Log;

import com.adobe.fre.FREContext;

public class BillingController {
	private static final String TAG = "wanglailai.mmextension.market.BillingController";


    public FREContext mContext;

    private Activity activity;
    private Purchase purchase;

    private IAPListener iapListener;


    private static BillingController instance = null;

    public static BillingController getInstance(FREContext context) {
    	if (instance == null) {
    		instance = new BillingController(context);
    	}
    	Log.i(TAG, "instance()");
    	return instance;
    }

	protected BillingController(FREContext context){
		mContext = context;
		activity = mContext.getActivity();


		purchase = Purchase.getInstance();
		iapListener = new IAPListener();
	}

	public void init(String appId, String appKey){
		//设置计费应用id和key
		purchase.setAppInfo(appId, appKey);
		//设置超时时间
//		purchase.setTimeout(1000, 1000);

		purchase.init(activity, iapListener);
	}

	public void startPayment(String payCode, int quantity) {
		if(quantity < 1){
			quantity = 1;
		}
		purchase.order(activity, payCode, quantity, iapListener);
	}


	public void dispose(){
		BillingController.instance = null;
	}



	private class IAPListener implements OnPurchaseListener{
		private final static String TAG = "com.playdom.labsextensions.market.IAPListener";

		public IAPListener(){
			Log.i(TAG, "Invoked " + IAPListener.class.getName());
		}

		@Override
		public void onAfterApply() {
			Log.i(TAG, "onAfterApply execute!");
		}

		@Override
		public void onAfterDownload() {
			Log.i(TAG, "onAfterDownload execute!");
		}

		@Override
		public void onBeforeApply() {
			Log.i(TAG, "onBeforeApply execute!");
		}

		@Override
		public void onBeforeDownload() {
			Log.i(TAG, "onBeforeDownload execute!");
		}

		@SuppressWarnings("rawtypes")
		@Override
		public void onBillingFinish(int code, HashMap map) {
			Log.d(TAG, "billing finish, status code = " + code + "\nresult = " + PurchaseCode.getReason(code));
			// 此次订购的orderID
			String orderID = null;
			// 商品的paycode
			String paycode = null;
			// 商品的有效期(仅租赁类型商品有效)
			String leftday = null;
			// 商品的交易 ID，用户可以根据这个交易ID，查询商品是否已经交易
			String tradeID = null;
			// 交易类型: 0-测试;1-商用
			String orderType = null;
			if (code == PurchaseCode.ORDER_OK || (code == PurchaseCode.AUTH_OK)) {
				/**
				 * 商品购买成功或者已经购买。 此时会返回商品的paycode，orderID,以及剩余时间(租赁类型商品)
				 */
				if (map != null) {
					leftday = (String) map.get(OnPurchaseListener.LEFTDAY);
					orderID = (String) map.get(OnPurchaseListener.ORDERID);
					paycode = (String) map.get(OnPurchaseListener.PAYCODE);
					tradeID = (String) map.get(OnPurchaseListener.TRADEID);
					Log.i(TAG, "onBillingFinish() SUCCESS!\nLEFTDAY=" + leftday + ", ORDERID=" + orderID + ", PAYCODE" + paycode + ", TRADEID=" + tradeID);
				}
			} else {
				/**
				 * 表示订购失败。
				 */
				Log.i(TAG, "onBillingFinish() FAILED");
			}
		}

		@Override
		public void onInitFinish(int code) {
			Log.d(TAG, PurchaseCode.getDescription(code));
			if(PurchaseCode.INIT_OK == code){
				Log.i(TAG, "Init: " + PurchaseCode.getReason(code));
			}else{
				Log.e(TAG, "Init: " + PurchaseCode.getReason(code));
			}
		}

		@SuppressWarnings("rawtypes")
		@Override
		public void onQueryFinish(int code, HashMap map) {
			Log.d(TAG, "query finish, status code = " + code + "\nresult = " + PurchaseCode.getDescription(code));
		}
	}
}
