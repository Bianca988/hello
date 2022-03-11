import sim.toolkit.EnvelopeHandler;

import sim.toolkit.ToolkitInterface;
import sim.toolkit.ToolkitConstants;
import sim.toolkit.ToolkitException;
import sim.toolkit.ToolkitRegistry;
import sim.toolkit.ProactiveHandler;
import sim.toolkit.ViewHandler;



import javacard.framework.APDU;
import javacard.framework.Applet;
import javacard.framework.ISOException;






public class Second_hello extends Applet implements ToolkitInterface,ToolkitConstants{
	
	private byte helloMenuItem;
	
	static byte[] message = new byte[] {'T', 'h','i' ,'s', ' ','a' , ' ', 't','e','s','t'};
	static byte[] nameInMenu = new byte[] { 'T','e','l','e','t',' ', 's','t','k'};
	
	private Second_hello()
	{
		ToolkitRegistry reg = ToolkitRegistry.getEntry();
		
		helloMenuItem = reg.initMenuEntry(nameInMenu,(short)0,(short)nameInMenu.length, PRO_CMD_SELECT_ITEM, false,(byte)0,(short)0);
		
		//method called by the card when the applet is installed. Instantiate and register here
	}
	public static void install(byte[] bArray, short bOffset, byte bLength)
	{
		Second_hello applet = new Second_hello();
		applet.register();
	}
	
	public void process(APDU apdu) throws ISOException 
	{
		if (selectingApplet())
			return;
	}

	public void processToolkit(byte event) throws ToolkitException 
	{
		EnvelopeHandler envelopeHand = EnvelopeHandler.getTheHandler();
		
		if(event == EVENT_MENU_SELECTION)
		{
			byte selectedIemId = envelopeHand.getItemIdentifier();
			
			if(selectedIemId == helloMenuItem)
			{
				showHello();
			}
		}
		
	}
	
	private void showHello()
	{
		ProactiveHandler proactiveHand = ProactiveHandler.getTheHandler();
		
		proactiveHand.initDisplayText((byte)0, DCS_8_BIT_DATA, message,(short)0,(short)(message.length));
		
		proactiveHand.send();
		return;
	}
	
}
