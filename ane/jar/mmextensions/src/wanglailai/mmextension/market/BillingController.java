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
		//���üƷ�Ӧ��id��key
		purchase.setAppInfo(appId, appKey);
		//���ó�ʱʱ��
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
			// �˴ζ�����orderID
			String orderID = null;
			// ��Ʒ��paycode
			String paycode = null;
			// ��Ʒ����Ч��(������������Ʒ��Ч)
			String leftday = null;
			// ��Ʒ�Ľ��� ID���û����Ը����������ID����ѯ��Ʒ�Ƿ��Ѿ�����
			String tradeID = null;
			// ��������: 0-����;1-����
			String orderType = null;
			if (code == PurchaseCode.ORDER_OK || (code == PurchaseCode.AUTH_OK)) {
				/**
				 * ��Ʒ����ɹ������Ѿ����� ��ʱ�᷵����Ʒ��paycode��orderID,�Լ�ʣ��ʱ��(����������Ʒ)
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
				 * ��ʾ����ʧ�ܡ�
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
