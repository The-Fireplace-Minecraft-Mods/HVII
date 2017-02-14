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
import the_fireplace.hvii.config.GuiChooseEntities;

/**
 * @author The_Fireplace
 */
@SideOnly(Side.CLIENT)
public class KeyHandlerHVII {
	//Key Index
	public static final int FULLBRIGHT = 0;
	public static final int SKINTOGGLE = 1;
	public static final int CHOOSEMOBS = 2;
	//Descriptions, use language file to localize later
	private static final String[] desc = 
		{"key.fullbright.desc", "key.skintoggle.desc", "key.choosemobs.desc"};
	//Default Key Values
	private static final int[] keyValues = 
		{Keyboard.KEY_F, Keyboard.KEY_C, Keyboard.KEY_U};
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
	}
}
