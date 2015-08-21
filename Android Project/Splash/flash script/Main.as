
package  
{
	import com.freeactionscript.Car;
	import flash.geom.ColorTransform;
	import flash.display.MovieClip;
	import flash.display.Sprite;
	import flash.text.TextField;
	import flash.events.Event;
	import flash.events.KeyboardEvent;
	import flash.ui.Keyboard;
	import flash.events.MouseEvent;
	import flash.utils.Timer;
    import flash.events.TimerEvent;
	
	public class Main extends Sprite
	{
		var playerCar:Car;
		
		
		private var _Welcome:MovieClip;
		private var _start:start;
				
		var maxHP:Number = 10;
		var currentHP:Number = 0;
		var percentHP:Number = currentHP / maxHP;
		var countN1 :Number = 0;
		
		var myTimer:Timer = new Timer(1000,2);
		var myTimer2:Timer = new Timer(1000,2);
		var myTimer3:Timer = new Timer(1000,2);
		
		var myTimer4:Timer = new Timer(1000,2);
		var myTimer5:Timer = new Timer(1000,2);
		var myTimer6:Timer = new Timer(1000,2);
		
		var myTimer7:Timer = new Timer(1000,2);
		var myTimer8:Timer = new Timer(1000,2);
		var myTimer9:Timer = new Timer(1000,2);
		var myTimer10:Timer = new Timer(1000,2);
		var myTimer11:Timer = new Timer(1000,2);
		
		var noser : Boolean = false;
		
		var tank1:Boolean = false;
		var tank2:Boolean = false;
		var tank3:Boolean = false;
		
		var nosT1:Boolean = false;
		var nosT2:Boolean = false;
		var nosT3:Boolean = false;
		var done:Boolean = false;
		
		function ditcher()
		{
			nosT1 = false;
			nosT2 = false;
			nosT3 = false;
			tank1 = false;
			tank2 = false;
			
		}

		function tankChecker()
		{
			if(!tank1)
			{
				tank1 = true;
				//trace("t1 check");
				sequence();
				return;
			}
			if(tank1 && !tank2)
			{	
				tank2 = true;
				//trace("t1,2 check");
				sequence();
				return;
			}
			if(tank1 && tank2)
			{
				tank1 = false;
				tank2 = false;
				//trace("false");
				sequence();
				return;
			}
		}

		function nosChecker()
		{
			if(!nosT1)
			{
				nosT1 = true;
				//trace(" n1 check ");
				sequence();
				return;
			}
			if(nosT1 && !nosT2)
			{
				nosT2 = true;
				//trace("n1,2 check");
				sequence();
				return;
			}
			if(nosT1 && nosT2)
			{
				nosT3 = true;
				//trace("n1,2,3 check");
				sequence();
				return;
			}
		}

		function sequence()
		{

				if (nosT1)
				{
					//nos2 = true;
					trace("lvl1 s3");
					if(nosT1 && nosT2)
					{
						//nos3 = true;
						trace("lvl2 s3");
						if(nosT1 && nosT2 && nosT3 )
						{
							trace("seq3");
							currentHP += 5;
							updateHealthBar();
							ditcher();
							return;
						}
					}
				}
				if(nosT1)
				{
					trace("lvl1 s1");
					if(nosT1 && nosT2)
					{
						trace("lvl2 s1");
						if(nosT1 && nosT2 && tank1)
						{
							trace("lvl4 s1");
							if(nosT1 && nosT2 && tank1 && tank2)
							{
								trace("seq1");
								currentHP += 2.5;
								updateHealthBar();	
								ditcher();
								return;
							}
						}
					}
				}
				if(tank1)
				{
					trace("lvl1 s2");
					if(tank1 && nosT1)
					{
						trace("lvl2 s2");
						if(tank1 && nosT1 && tank2)
						{
							trace("seq2");
							currentHP += 8;
							updateHealthBar();
							ditcher();
							return;
						}
					}
				}
		}
		


		function updateHealthBar():void
		{
			 
			percentHP = currentHP / maxHP;
			if(currentHP >= 10)
				gasBar.barColor.scaleX = 5/5;
			else
				gasBar.barColor.scaleX = percentHP;
		}
		
		public function Main() 
		{
			percentHP = currentHP / maxHP;
			gasBar.barColor.scaleX = percentHP/2 ;
			gasBar.x = 0;
			gasBar.y = 0;
			
			createWelcome();
			
			init();
			
				

			
			   
			updateHealthBar();
			// instantiate car class
		
			
			
		}
		
		private function createWelcome():void
		{
			_Welcome = new Welcome();
			_Welcome.x = stage.stageWidth / 2;
			_Welcome.y = 360;
			stage.addChild(_Welcome);
		
			_start = new start();
			_start.x = 1250;
			_start.y = 640;
			stage.addChild(_start);
		}
		
		
		 function myOnPress(event:KeyboardEvent):void
		{
			switch( event.keyCode )
			{
				case Keyboard.SPACE:
					up = true;
					break;
					
			}
			
			event.updateAfterEvent();
		}
		
		var clicker:Boolean = false;
		
		function myOnRelease(event:KeyboardEvent):void
		{
			switch( event.keyCode )
			{
				case Keyboard.SPACE:
					up = false;
					clicker = false;
					break;
					
			}
			
		}
		
		var up:Boolean = false;

		function init():void
		{
			stage.addEventListener(Event.ENTER_FRAME, runGame);			
			stage.addEventListener(KeyboardEvent.KEY_DOWN, myOnPress);
			stage.addEventListener(KeyboardEvent.KEY_UP, myOnRelease);
			_start.addEventListener( MouseEvent.CLICK, onClickStart );
		}
		
		function init2():void
		{
			stage.addEventListener( Event.ENTER_FRAME, handleCollision);
			stage.addEventListener( Event.ENTER_FRAME, pump1handleCollision); // semi laters
			stage.addEventListener( Event.ENTER_FRAME, pump2handleCollision);
			stage.addEventListener( Event.ENTER_FRAME, pump3handleCollision);
			stage.addEventListener( Event.ENTER_FRAME, pump4handleCollision);
			stage.addEventListener( Event.ENTER_FRAME, pump5handleCollision);
			
			myTimer.addEventListener(TimerEvent.TIMER, timerListener);
			myTimer2.addEventListener(TimerEvent.TIMER, timerListener2);
			myTimer3.addEventListener(TimerEvent.TIMER, timerListener3);
			
			myTimer4.addEventListener(TimerEvent.TIMER, timerListener4);
			myTimer5.addEventListener(TimerEvent.TIMER, timerListener5);
			myTimer6.addEventListener(TimerEvent.TIMER, timerListener6);
			
			myTimer7.addEventListener(TimerEvent.TIMER, timerListener7);
			myTimer8.addEventListener(TimerEvent.TIMER, timerListener8);
			myTimer9.addEventListener(TimerEvent.TIMER, timerListener9);
			myTimer10.addEventListener(TimerEvent.TIMER, timerListener10);
			myTimer11.addEventListener(TimerEvent.TIMER, timerListener11);

			
			stage.addEventListener( Event.ENTER_FRAME, nos1handleCollision); // semi laters
			stage.addEventListener( Event.ENTER_FRAME, nos2handleCollision);
			stage.addEventListener( Event.ENTER_FRAME, nos3handleCollision);
			
			stage.addEventListener( Event.ENTER_FRAME, ditch1handleCollision); // semi laters
			stage.addEventListener( Event.ENTER_FRAME, ditch2handleCollision);
			stage.addEventListener( Event.ENTER_FRAME, ditch3handleCollision);
			//stage.addEventListener( Event.ENTER_FRAME, sequence);
		}
				
		function onClickStart(event:MouseEvent):void
		{
			stage.removeChild(_Welcome);
			//stage.removeChild(_start);
			_start.visible = false;
			
			createPlayer();
			
		}
		
		private function createPlayer():void
		{
			playerCar = new Car();
			
			// center car on stage
			playerCar.x = stage.stageWidth / 6;
			playerCar.y = stage.stageHeight / 3.5;
			playerCar.rotation = 90;
			
			// add car to display list
			addChild(playerCar);
			init2();
		}
		

		
		function handleCollision( e:Event ):void//etoller
		{
			if(eToll.hitTestObject(playerCar) )
			{
				removeChild(eToll);
				stage.removeEventListener(Event.ENTER_FRAME, handleCollision);
				if(currentHP >= 10)
				{
					var winner:Winner = new Winner();
					winner.x = stage.stageWidth/2;
					winner.y = stage.stageHeight/2;
					addChild(winner);
				}
				else
				{
					var gameOverText:GameOverText = new GameOverText();
					gameOverText.x = stage.stageWidth/2;
					gameOverText.y = stage.stageHeight/2;
					addChild(gameOverText);
				}
				
			}
			else
			{
				//trace("MISS");
			}
		}
		
		function pump1handleCollision( e:Event ):void//pump1
		{
			if(pump1.hitTestObject(playerCar) )
			{
				tankChecker();
				Double = true;
				myTimer7.start();
				
				removeChild(pump1);
				//currentHP += 0.5;
				//playerCar.speed +=0.5;
				updateHealthBar();
				stage.removeEventListener(Event.ENTER_FRAME, pump1handleCollision);
			}
			else
			{
				//trace("MISS");
			}
		}
		
		function pump2handleCollision( e:Event ):void//pump2
		{
			if(pump2.hitTestObject(playerCar) )
			{
				Double = true;
				myTimer8.start();
				
				tankChecker();
				removeChild(pump2);
				//currentHP += 0.5;
				//playerCar.speed +=0.5;
				//updateHealthBar();
				stage.removeEventListener(Event.ENTER_FRAME, pump2handleCollision);
			}
			else
			{
				//trace("MISS");
			}
		}
		
		function pump3handleCollision( e:Event ):void//pump3
		{
			if(pump3.hitTestObject(playerCar) )
			{
				Double = true;
				myTimer9.start();
				
				tankChecker();
				removeChild(pump3);
				//pump3.visible =false;
				//currentHP += 0.5;
				//playerCar.speed +=0.5;
				//updateHealthBar();
				stage.removeEventListener(Event.ENTER_FRAME, pump3handleCollision);
			}
			else
			{
				//trace("MISS");
			}
		}
		
		function pump4handleCollision( e:Event ):void//pump4
		{
			if(pump4.hitTestObject(playerCar) )
			{
				Double = true;
				myTimer10.start();
				
				tankChecker();
				removeChild(pump4);
				//currentHP += 0.5;
				//playerCar.speed +=0.5;
				//updateHealthBar();
				stage.removeEventListener(Event.ENTER_FRAME, pump4handleCollision);
			}
			else
			{
				//trace("MISS");
			}
		}

		function pump5handleCollision( e:Event ):void//pump5
		{
			if(pump5.hitTestObject(playerCar) )
			{
				Double = true;
				myTimer11.start();
				
				tankChecker();
				removeChild(pump5);
				//currentHP += 0.5;
				//playerCar.speed +=0.5;
				//updateHealthBar();
				stage.removeEventListener(Event.ENTER_FRAME, pump5handleCollision);
			}
			else
			{
				//trace("MISS");
			}
		}
		
				
		function timerListener (e:TimerEvent):void
		{
			countN1++;
			//trace("timer "+countN1);
			if(countN1 == 2)
			{
				//trace("back");
				//playerCar.speed -=0.3;
				noser = true;
				countN1=0;
			}
			else
			{
				//trace("inc");
				playerCar.speed +=0.3;
			}
		}
		
		function timerListener2 (e:TimerEvent):void
		{
			countN1++;
			//trace("timer "+countN1);
			if(countN1 == 2)
			{
				//trace("back");
				//playerCar.speed -=0.3;
				noser = true;
				countN1=0;
			}
			else
			{
				//trace("inc");
				playerCar.speed +=0.3;
			}
		}
		
		function timerListener3 (e:TimerEvent):void
		{
			countN1++;
			//trace("timer "+countN1);
			if(countN1 == 2)
			{
				//trace("back");
				//playerCar.speed -=0.3;
				noser = true;
				countN1=0;
			}
			else
			{
				//trace("inc");
				playerCar.speed +=0.3;
			}
		}
		
		
		
		function timerListener4 (e:TimerEvent):void
		{
			counter++;
			if(counter == 2)
			{
				var obj_color4:ColorTransform = new ColorTransform();
				obj_color4.color = 0x00CC00;
				gasBar.barColor.transform.colorTransform = obj_color4;
				counter=0;
				
				ditchBool = true;
			}
			else
			{
				var obj_color1:ColorTransform = new ColorTransform();
				obj_color1.color = 0xFF0000;
				gasBar.barColor.transform.colorTransform = obj_color1;
			}
		}
		var counter:Number=0;
		function timerListener5 (e:TimerEvent):void
		{
			counter++;
			if(counter ==2)
			{
				var obj_color5:ColorTransform = new ColorTransform();
				obj_color5.color = 0x00CC00;
				gasBar.barColor.transform.colorTransform = obj_color5;
				counter=0;
				ditchBool2 = true;
			}
			else
			{
				//var obj_color5:ColorTransform = new ColorTransform();
				obj_color5.color = 0xFF0000;
				gasBar.barColor.transform.colorTransform = obj_color5;
			}
		}
		
		function timerListener6 (e:TimerEvent):void
		{
			counter++;
			if(counter == 2)
			{
				var obj_color6:ColorTransform = new ColorTransform();
				obj_color6.color = 0x00CC00;
				gasBar.barColor.transform.colorTransform = obj_color6;
				counter=0;
				ditchBool3 = true;
			}
			else
			{
				//var obj_color6:ColorTransform = new ColorTransform();
				obj_color6.color = 0xFF0000;
				gasBar.barColor.transform.colorTransform = obj_color6;
			}
		}
		
		var speedCheck:Boolean = true;
		
		function nos1handleCollision( e:Event ):void//nos1
		{
			
			if(nos1.hitTestObject(playerCar) && speedCheck)
			{
				nosChecker();
				speedCheck = false;
				myTimer.start();
				//holder = playerCar.speed;
				playerCar.speed +=3;
				
				nos1.visible =false;
				var obj_color:ColorTransform = new ColorTransform();
				obj_color.color = 0x0066FF;
				gasBar.barColor.transform.colorTransform = obj_color;
			}
			if(nos1.hitTestObject(playerCar)==false && noser)
			{
				
				noser = false;
				playerCar.speed -=3;
				removeChild(nos1);
				stage.removeEventListener(Event.ENTER_FRAME, nos1handleCollision);
				myTimer.stop();
				speedCheck = true;
				var obj_color1:ColorTransform = new ColorTransform();
				obj_color1.color = 0x00CC00;
				gasBar.barColor.transform.colorTransform = obj_color1;
				//trace(playerCar.speed)
			}
		}
		function nos2handleCollision( e:Event ):void//nos2
		{
			if(nos2.hitTestObject(playerCar) && speedCheck)
			{
				nosChecker();
				//trace("hello");
				speedCheck = false;
				myTimer2.start();
				//holder = playerCar.speed;
				playerCar.speed +=3;
				nos2.visible =false;
				
				var obj_color:ColorTransform = new ColorTransform();
				obj_color.color = 0x0066FF;
				gasBar.barColor.transform.colorTransform = obj_color;
				
			}
			if(nos2.hitTestObject(playerCar)==false && noser)
			{
				
				noser = false;
				playerCar.speed -=3;
				removeChild(nos2);
				stage.removeEventListener(Event.ENTER_FRAME, nos2handleCollision);
				myTimer2.stop();
				//myTimer2.reset();
				speedCheck = true;
				var obj_color1:ColorTransform = new ColorTransform();
				obj_color1.color = 0x00CC00;
				gasBar.barColor.transform.colorTransform = obj_color1;
				//trace(playerCar.speed)
				//trace("hello2");
			}
		}

		
		
		function nos3handleCollision( e:Event ):void//nos3
		{
			if(nos3.hitTestObject(playerCar) && speedCheck)
			{
				nosChecker();
				speedCheck = false;
				myTimer3.start();
				//holder = playerCar.speed;
				playerCar.speed +=3;
				nos3.visible =false;
				
				var obj_color:ColorTransform = new ColorTransform();
				obj_color.color = 0x0066FF;
				gasBar.barColor.transform.colorTransform = obj_color;
				
			}
			if(nos3.hitTestObject(playerCar)==false && noser)
			{
				noser = false;
				playerCar.speed -=3;
				removeChild(nos3);
				stage.removeEventListener(Event.ENTER_FRAME, nos3handleCollision);
				//myTimer3.stop();
				//myTimer2.reset();
				var obj_color1:ColorTransform = new ColorTransform();
				obj_color1.color = 0x00CC00;
				gasBar.barColor.transform.colorTransform = obj_color1;
				speedCheck = true;
				//trace(playerCar.speed)
			}
		}
		
		var ditchCheck:Boolean = true;
		var ditchBool:Boolean = false;
				var ditchBool2:Boolean = false;
						var ditchBool3:Boolean = false;
		
		function ditch1handleCollision( e:Event ):void//ditch1
		{
			
			if(ditch1.hitTestObject(playerCar)&& ditchCheck )
			{
				ditchCheck=false;
				myTimer4.start();
				ditcher();
				playerCar.speed -=1;
				ditch1.visible = false;
			}
			if(ditch1.hitTestObject(playerCar)==false && ditchBool )
			{
				ditchBool = false;
				playerCar.speed +=1;
				removeChild(ditch1);
				stage.removeEventListener(Event.ENTER_FRAME, ditch1handleCollision);
				//myTimer3.stop();
				//myTimer2.reset();
				var obj_color1:ColorTransform = new ColorTransform();
				obj_color1.color = 0x00CC00;
				gasBar.barColor.transform.colorTransform = obj_color1;
				ditchCheck = true;
				//trace(playerCar.speed)
			}
		}
		
		function ditch2handleCollision( e:Event ):void//ditch2
		{
			if(ditch2.hitTestObject(playerCar)&& ditchCheck )
			{
				ditchCheck=false;
				myTimer5.start();
				ditcher();
				playerCar.speed -=1;
				ditch2.visible = false;
			}
			if(ditch2.hitTestObject(playerCar)==false && ditchBool2 )
			{
				ditchBool = false;
				playerCar.speed +=1;
				removeChild(ditch2);
				stage.removeEventListener(Event.ENTER_FRAME, ditch2handleCollision);
				//myTimer3.stop();
				//myTimer2.reset();
				var obj_color1:ColorTransform = new ColorTransform();
				obj_color1.color = 0x00CC00;
				gasBar.barColor.transform.colorTransform = obj_color1;
				ditchCheck = true;
				//trace(playerCar.speed)
			}
		}
		
		function ditch3handleCollision( e:Event ):void//ditch3
		{
			if(ditch3.hitTestObject(playerCar)&& ditchCheck )
			{
				ditchCheck=false;
				myTimer6.start();
				ditcher();
				playerCar.speed -=1;
				ditch3.visible = false;
			}
			if(ditch3.hitTestObject(playerCar)==false && ditchBool3 )
			{
				ditchBool = false;
				playerCar.speed +=1;
				removeChild(ditch3);
				stage.removeEventListener(Event.ENTER_FRAME, ditch3handleCollision);

				var obj_color1:ColorTransform = new ColorTransform();
				obj_color1.color = 0x00CC00;
				gasBar.barColor.transform.colorTransform = obj_color1;
				ditchCheck = true;
				//trace(playerCar.speed)
			}
		}
		
		
		function timerListener7 (e:TimerEvent):void
		{
			counter++;
			if(counter == 2)
			{
				var obj_color7:ColorTransform = new ColorTransform();
				obj_color7.color = 0x00CC00;
				gasBar.barColor.transform.colorTransform = obj_color7;
				counter=0;
				Double = false;
				trace("hello123");
			}
			else
			{
				//var obj_color6:ColorTransform = new ColorTransform();
				obj_color7.color = 0xFFFF00;
				trace("hello");
				gasBar.barColor.transform.colorTransform = obj_color7;
			}
		}
		
		
		function timerListener8 (e:TimerEvent):void
		{
			counter++;
			if(counter == 2)
			{
				var obj_color7:ColorTransform = new ColorTransform();
				obj_color7.color = 0x00CC00;
				gasBar.barColor.transform.colorTransform = obj_color7;
				counter=0;
				Double = false;
				trace("hello123");
			}
			else
			{
				//var obj_color6:ColorTransform = new ColorTransform();
				obj_color7.color = 0xFFFF00;
				trace("hello");
				gasBar.barColor.transform.colorTransform = obj_color7;
			}
		}
		
		function timerListener9 (e:TimerEvent):void
		{
			counter++;
			if(counter == 2)
			{
				var obj_color7:ColorTransform = new ColorTransform();
				obj_color7.color = 0x00CC00;
				gasBar.barColor.transform.colorTransform = obj_color7;
				counter=0;
				Double = false;
				trace("hello123");
			}
			else
			{
				//var obj_color6:ColorTransform = new ColorTransform();
				obj_color7.color = 0xFFFF00;
				trace("hello");
				gasBar.barColor.transform.colorTransform = obj_color7;
			}
		}
		
		function timerListener10 (e:TimerEvent):void
		{
			counter++;
			if(counter == 2)
			{
				var obj_color7:ColorTransform = new ColorTransform();
				obj_color7.color = 0x00CC00;
				gasBar.barColor.transform.colorTransform = obj_color7;
				counter=0;
				Double = false;
				trace("hello123");
			}
			else
			{
				//var obj_color6:ColorTransform = new ColorTransform();
				obj_color7.color = 0xFFFF00;
				trace("hello");
				gasBar.barColor.transform.colorTransform = obj_color7;
			}
		}
		
		function timerListener11 (e:TimerEvent):void
		{
			counter++;
			if(counter == 2)
			{
				var obj_color7:ColorTransform = new ColorTransform();
				obj_color7.color = 0x00CC00;
				gasBar.barColor.transform.colorTransform = obj_color7;
				counter=0;
				Double = false;
				trace("hello123");
			}
			else
			{
				//var obj_color6:ColorTransform = new ColorTransform();
				obj_color7.color = 0xFFFF00;
				trace("hello");
				gasBar.barColor.transform.colorTransform = obj_color7;
			}
		}
		
		var Double:Boolean = false;
		
		function runGame(event:Event):void
		{
 			if (up)      
			{
				//check if below speedMax
				if(!clicker && Double)
				{
					playerCar.speed += 0.1;
					currentHP += 0.1;
					updateHealthBar();
					clicker = true;	
					
				}
				else if(!clicker)
				{
					currentHP += 0.05;
					updateHealthBar();
					clicker = true;
				}
			}
		}
		
		
		
		

		
		

		
		
		

	}
	
}
	
	
	

	
	
	
	

	
	
	
