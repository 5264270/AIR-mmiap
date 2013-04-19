package wanglailai.mmextension
{
	import flash.external.ExtensionContext;
	import flash.filesystem.File;
	import flash.system.ApplicationDomain;
	import flash.system.Capabilities;

	public class MMExtensionContext
	{
		private static const EXTENSION_ID : String = "wanglailai.mmextension";
		private static var _instance:MMExtensionContext;
		private var _context:ExtensionContext;
		
		public function MMExtensionContext()
		{
			_context = ExtensionContext.createExtensionContext(EXTENSION_ID, null);
			
			
		}
		
		public static function get instance():MMExtensionContext
		{
			if (!_instance)
				_instance = new MMExtensionContext();
			
			return _instance;
		}
		
		private var _monetization:MobileMarketMonetization;
		public function get monetization():MobileMarketMonetization
		{
			if (!_monetization) {
				_monetization = new MobileMarketMonetization(EXTENSION_ID);
			}
			return _monetization;
		}

		internal static function get currentContext():ExtensionContext
		{
			return MMExtensionContext.instance.context;
		}
		
		internal function get context():ExtensionContext
		{
			return _context;
		}
	}
}