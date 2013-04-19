package
{
	import flash.display.Sprite;
	import flash.display.StageAlign;
	import flash.display.StageScaleMode;
	import flash.events.MouseEvent;
	import flash.text.TextField;
	
	import wanglailai.mmextension.MMExtensionContext;
	
	public class iapExample extends Sprite
	{
		//应用编码
		private var appID:String = "000000000000";
		//app key
		private var appKey:String = "000000000000";
		//计费点代码 
		private var paycode:String = "000000000000";
		
		public function iapExample()
		{
			super();
			//初始化支付的信息，需要传入appID和appKey
			MMExtensionContext.instance.monetization.init(appID, appKey);
			// support autoOrients
			stage.align = StageAlign.TOP_LEFT;
			stage.scaleMode = StageScaleMode.NO_SCALE;
			
			var text:TextField = new TextField();
			text.text = "点击下面的颜色块触发中国移动的支付功能！";
			text.width = text.textWidth + 100;
			text.height = 50;
			text.selectable = false;
			text.x = text.y = 100;
			addChild(text);
			
			var sprite:Sprite = new Sprite();
			sprite.graphics.beginFill(0x00FF00);
			sprite.graphics.drawRoundRect(0, 0, 150, 100, 5, 5);
			sprite.graphics.endFill();
			sprite.addEventListener(MouseEvent.MOUSE_DOWN, _onMouseDown);
			
			sprite.x = 130;sprite.y = 150;
			
			addChild(sprite);
		}
		
		private function _onMouseDown(event:MouseEvent):void{
			//调用支付接口
			MMExtensionContext.instance.monetization.startPayment(paycode);
		}
	}
}