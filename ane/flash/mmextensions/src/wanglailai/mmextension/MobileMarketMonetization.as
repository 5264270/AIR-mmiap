package wanglailai.mmextension
{
	
	import flash.events.StatusEvent;
	import flash.external.ExtensionContext;

	public class MobileMarketMonetization
	{
		private var _enabled:Boolean;
		private var _checkedEnabled:Boolean;
		private var _context:ExtensionContext; 
		public static const CONTEXT_KEY:String = "MobileMarket";
		public function MobileMarketMonetization(extensionID:String)
		{
			super();
			if(_context==null){
				try{
					_context = ExtensionContext.createExtensionContext(extensionID, CONTEXT_KEY);
				}catch(e:Error){
				}
			}
		}
		public function init(... args):void
		{
			try
			{
				_context.call("MMBillingInit", args[0], args[1]);
				
			} catch (e:Error) {
			}
		}
		public function startPayment(productID:String, quantity:uint=1):void
		{
			_context.call("MMBillingStartPayment", productID, quantity);
		}
	}
}