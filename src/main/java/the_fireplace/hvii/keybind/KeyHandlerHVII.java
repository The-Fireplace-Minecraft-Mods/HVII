package the_fireplace.hvii.keybind;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import the_fireplace.hvii.HVII;
import the_fireplace.hvii.config.ConfigValues;
import the_fireplace.hvii.config.GuiChooseEntities;
import the_fireplace.hvii.fats.FATSRegistry;

/**
 * @author The_Fireplace
 */
@SideOnly(Side.CLIENT)
public class KeyHandlerHVII {
	//Key Index
	public static final int FULLBRIGHT = 0;
	public static final int SKINTOGGLE = 1;
	public static final int CHOOSEMOBS = 2;
	public static final int FATSTHROWN = 3;
	//Descriptions, use language file to localize later
	private static final String[] desc = 
		{"key.fullbright.desc", "key.skintoggle.desc", "key.choosemobs.desc", "key.fatsthrown.desc"};
	//Default Key Values
	private static final int[] keyValues = 
		{Keyboard.KEY_L, Keyboard.KEY_C, Keyboard.KEY_U, Keyboard.KEY_G};
	private final KeyBinding[] keys;
	public KeyHandlerHVII(){
		keys = new KeyBinding[desc.length];
		for(int i = 0; i < desc.length; ++i){
			keys[i] = new KeyBinding(desc[i], keyValues[i], "key.hvii.category");
			ClientRegistry.registerKeyBinding(keys[i]);
		}
	}

	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event){
		if(keys[FULLBRIGHT].isPressed()){
			HVII.toggleFullbright();
		}
		if(keys[SKINTOGGLE].isPressed()){
			HVII.toggleEnabledParts();
		}
		if(keys[CHOOSEMOBS].isPressed()){
			Minecraft.getMinecraft().displayGuiScreen(new GuiChooseEntities());
		}
		if(keys[FATSTHROWN].isPressed() && ConfigValues.FATS_THROWN_KEY_BEHAVIOR.equals("toggle")){
			FATSRegistry.showThrown = !FATSRegistry.showThrown;
		}else if(keys[FATSTHROWN].isKeyDown()){
			switch(ConfigValues.FATS_THROWN_KEY_BEHAVIOR){
				case "enable":
					if(!FATSRegistry.showThrown)
						FATSRegistry.showThrown=true;
					break;
				case "disable":
					if(FATSRegistry.showThrown)
						FATSRegistry.showThrown=false;
			}
		}else if(!keys[FATSTHROWN].isKeyDown()){
			switch(ConfigValues.FATS_THROWN_KEY_BEHAVIOR){
				case "enable":
					if(FATSRegistry.showThrown)
						FATSRegistry.showThrown=false;
					break;
				case "disable":
					if(!FATSRegistry.showThrown)
						FATSRegistry.showThrown=true;
			}
		}
	}
}
