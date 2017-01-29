package the_fireplace.hvii;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import the_fireplace.hvii.config.ConfigValues;
import the_fireplace.hvii.keybind.KeyHandlerHVII;
/**
 * 
 * @author The_Fireplace
 *
 */
@Mod(modid= HVII.MODID, name= HVII.MODNAME, guiFactory = "the_fireplace.hvii.config.HVIIGuiFactory", canBeDeactivated=true, clientSideOnly=true)
public class HVII {
	public static final String MODID = "hvii";
	public static final String MODNAME = "Hacker Voice: I'm In (HVII)";

	public static Configuration config;

	public static Property ENABLECAPETOGGLE_PROPERTY;
	public static Property ENABLEHATTOGGLE_PROPERTY;
	public static Property ENABLECHESTTOGGLE_PROPERTY;
	public static Property ENABLELEFTARMTOGGLE_PROPERTY;
	public static Property ENABLELEFTLEGTOGGLE_PROPERTY;
	public static Property ENABLERIGHTARMTOGGLE_PROPERTY;
	public static Property ENABLERIGHTLEGTOGGLE_PROPERTY;
	public static Property NORMALBRIGHTNESS_PROPERTY;
	public static Property GLOWINGENTITIES_PROPERTY;

	public static void syncConfig(){
		ConfigValues.ENABLECAPETOGGLE = ENABLECAPETOGGLE_PROPERTY.getBoolean();
		ConfigValues.ENABLEJACKETTOGGLE = ENABLECHESTTOGGLE_PROPERTY.getBoolean();
		ConfigValues.ENABLELEFTSLEEVETOGGLE = ENABLELEFTARMTOGGLE_PROPERTY.getBoolean();
		ConfigValues.ENABLERIGHTSLEEVETOGGLE = ENABLERIGHTARMTOGGLE_PROPERTY.getBoolean();
		ConfigValues.ENABLELEFTPANTSLEGTOGGLE = ENABLELEFTLEGTOGGLE_PROPERTY.getBoolean();
		ConfigValues.ENABLERIGHTPANTSLEGTOGGLE = ENABLERIGHTLEGTOGGLE_PROPERTY.getBoolean();
		ConfigValues.ENABLEHATTOGGLE = ENABLEHATTOGGLE_PROPERTY.getBoolean();
		ConfigValues.NORMALBRIGHTNESS = NORMALBRIGHTNESS_PROPERTY.getInt();
		ConfigValues.GLOWINGENTITIES = GLOWINGENTITIES_PROPERTY.getStringList();
		if(config.hasChanged()){
			config.save();
		}
	}
	@EventHandler
	public void PreInit(FMLPreInitializationEvent event){
		config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		ENABLECAPETOGGLE_PROPERTY = config.get("skin", ConfigValues.ENABLECAPETOGGLE_NAME, ConfigValues.ENABLECAPETOGGLE_DEFAULT, I18n.format(ConfigValues.ENABLECAPETOGGLE_NAME +".tooltip"));
		ENABLECHESTTOGGLE_PROPERTY = config.get("skin", ConfigValues.ENABLEJACKETTOGGLE_NAME, ConfigValues.ENABLEJACKETTOGGLE_DEFAULT, I18n.format(ConfigValues.ENABLEJACKETTOGGLE_NAME +".tooltip"));
		ENABLELEFTARMTOGGLE_PROPERTY = config.get("skin", ConfigValues.ENABLELEFTSLEEVETOGGLE_NAME, ConfigValues.ENABLELEFTSLEEVETOGGLE_DEFAULT, I18n.format(ConfigValues.ENABLELEFTSLEEVETOGGLE_NAME +".tooltip"));
		ENABLERIGHTARMTOGGLE_PROPERTY = config.get("skin", ConfigValues.ENABLERIGHTSLEEVETOGGLE_NAME, ConfigValues.ENABLERIGHTSLEEVETOGGLE_DEFAULT, I18n.format(ConfigValues.ENABLERIGHTSLEEVETOGGLE_NAME +".tooltip"));
		ENABLELEFTLEGTOGGLE_PROPERTY = config.get("skin", ConfigValues.ENABLELEFTPANTSLEGTOGGLE_NAME, ConfigValues.ENABLELEFTPANTSLEGTOGGLE_DEFAULT, I18n.format(ConfigValues.ENABLELEFTPANTSLEGTOGGLE_NAME +".tooltip"));
		ENABLERIGHTLEGTOGGLE_PROPERTY = config.get("skin", ConfigValues.ENABLERIGHTPANTSLEGTOGGLE_NAME, ConfigValues.ENABLERIGHTPANTSLEGTOGGLE_DEFAULT, I18n.format(ConfigValues.ENABLERIGHTPANTSLEGTOGGLE_NAME +".tooltip"));
		ENABLEHATTOGGLE_PROPERTY = config.get("skin", ConfigValues.ENABLEHATTOGGLE_NAME, ConfigValues.ENABLEHATTOGGLE_DEFAULT, I18n.format(ConfigValues.ENABLEHATTOGGLE_NAME +".tooltip"));
		NORMALBRIGHTNESS_PROPERTY = config.get(Configuration.CATEGORY_GENERAL, ConfigValues.NORMALBRIGHTNESS_NAME, ConfigValues.NORMALBRIGHTNESS_DEFAULT, I18n.format(ConfigValues.NORMALBRIGHTNESS_NAME+".tooltip"));
		GLOWINGENTITIES_PROPERTY = config.get("hidden", ConfigValues.GLOWINGENTITIES_NAME, ConfigValues.GLOWINGENTITIES_DEFAULT, I18n.format(ConfigValues.GLOWINGENTITIES_NAME+".tooltip"));
		syncConfig();

		MinecraftForge.EVENT_BUS.register(new ForgeEvents());
		MinecraftForge.EVENT_BUS.register(new KeyHandlerHVII());
	}
	/**
	 * Toggles fullbright
	 */
	public static void toggleFullbright(){
		if(Minecraft.getMinecraft().gameSettings.gammaSetting < 1000000){
			Minecraft.getMinecraft().gameSettings.gammaSetting = 1000000;
		}else{
			Minecraft.getMinecraft().gameSettings.gammaSetting = ConfigValues.NORMALBRIGHTNESS;
		}
		Minecraft.getMinecraft().gameSettings.saveOptions();
	}
	/**
	 * Toggles the parts of the skin set to toggle in the settings
	 */
	public static void toggleEnabledParts(){
		if(ConfigValues.ENABLECAPETOGGLE){
			Minecraft.getMinecraft().gameSettings.switchModelPartEnabled(EnumPlayerModelParts.CAPE);
		}
		if(ConfigValues.ENABLEJACKETTOGGLE){
			Minecraft.getMinecraft().gameSettings.switchModelPartEnabled(EnumPlayerModelParts.JACKET);
		}
		if(ConfigValues.ENABLELEFTSLEEVETOGGLE){
			Minecraft.getMinecraft().gameSettings.switchModelPartEnabled(EnumPlayerModelParts.LEFT_SLEEVE);
		}
		if(ConfigValues.ENABLERIGHTSLEEVETOGGLE){
			Minecraft.getMinecraft().gameSettings.switchModelPartEnabled(EnumPlayerModelParts.RIGHT_SLEEVE);
		}
		if(ConfigValues.ENABLELEFTPANTSLEGTOGGLE){
			Minecraft.getMinecraft().gameSettings.switchModelPartEnabled(EnumPlayerModelParts.LEFT_PANTS_LEG);
		}
		if(ConfigValues.ENABLERIGHTPANTSLEGTOGGLE){
			Minecraft.getMinecraft().gameSettings.switchModelPartEnabled(EnumPlayerModelParts.RIGHT_PANTS_LEG);
		}
		if(ConfigValues.ENABLEHATTOGGLE){
			Minecraft.getMinecraft().gameSettings.switchModelPartEnabled(EnumPlayerModelParts.HAT);
		}
		Minecraft.getMinecraft().gameSettings.saveOptions();
	}
}
