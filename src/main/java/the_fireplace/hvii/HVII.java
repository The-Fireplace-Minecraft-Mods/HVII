package the_fireplace.hvii;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import the_fireplace.hvii.config.ConfigValues;
import the_fireplace.hvii.pats.PATSRegistry;
import the_fireplace.hvii.pats.PatsBowTargeting;
import the_fireplace.hvii.pats.PatsGenericThrowableTargeting;
import the_fireplace.hvii.keybind.KeyHandlerHVII;
/**
 * @author The_Fireplace
 */
@Mod(modid= HVII.MODID, name= HVII.MODNAME, guiFactory = "the_fireplace.hvii.config.HVIIGuiFactory", canBeDeactivated=true, clientSideOnly=true, updateJSON = "http://thefireplace.bitnamiapp.com/jsons/hvii.json")
public class HVII {
	public static final String MODID = "hvii";
	public static final String MODNAME = "Hacker Voice: I'm In (HVII)";

	public static Configuration config;

	//Skin toggling
	public static Property ENABLECAPETOGGLE_PROPERTY;
	public static Property ENABLEHATTOGGLE_PROPERTY;
	public static Property ENABLECHESTTOGGLE_PROPERTY;
	public static Property ENABLELEFTARMTOGGLE_PROPERTY;
	public static Property ENABLELEFTLEGTOGGLE_PROPERTY;
	public static Property ENABLERIGHTARMTOGGLE_PROPERTY;
	public static Property ENABLERIGHTLEGTOGGLE_PROPERTY;
	//P.A.T.S.
	public static Property TPU_PROPERTY;
	public static Property ATD_PROPERTY;
	public static Property PATS_PARTICLE1_PROPERTY;
	public static Property PATS_PARTICLE2_PROPERTY;
	public static Property ENABLE_PATS_PRIMARY_PROPERTY;
	public static Property ENABLE_PATS_SECONDARY_PROPERTY;
	public static Property EXTENSIVE_PATS_SECONDARY_PROPERTY;
	public static Property PATS_KEY_BEHAVIOR_PROPERTY;
	//General
	public static Property NORMALBRIGHTNESS_PROPERTY;
	//Hidden
	public static Property GLOWINGENTITIES_PROPERTY;
	public static Property ENABLETARGETLINES_PROPERTY;

	public static EnumParticleTypes primary;
	public static EnumParticleTypes secondary;

	@SideOnly(Side.CLIENT)
	public static void syncConfig(){
		//Skin toggling
		ConfigValues.ENABLECAPETOGGLE = ENABLECAPETOGGLE_PROPERTY.getBoolean();
		ConfigValues.ENABLEJACKETTOGGLE = ENABLECHESTTOGGLE_PROPERTY.getBoolean();
		ConfigValues.ENABLELEFTSLEEVETOGGLE = ENABLELEFTARMTOGGLE_PROPERTY.getBoolean();
		ConfigValues.ENABLERIGHTSLEEVETOGGLE = ENABLERIGHTARMTOGGLE_PROPERTY.getBoolean();
		ConfigValues.ENABLELEFTPANTSLEGTOGGLE = ENABLELEFTLEGTOGGLE_PROPERTY.getBoolean();
		ConfigValues.ENABLERIGHTPANTSLEGTOGGLE = ENABLERIGHTLEGTOGGLE_PROPERTY.getBoolean();
		ConfigValues.ENABLEHATTOGGLE = ENABLEHATTOGGLE_PROPERTY.getBoolean();
		//P.A.T.S.
		ConfigValues.TPU = TPU_PROPERTY.getInt();
		ConfigValues.ATD = ATD_PROPERTY.getInt();
		ConfigValues.PATS_PARTICLE1 = PATS_PARTICLE1_PROPERTY.getString();
		ConfigValues.PATS_PARTICLE2 = PATS_PARTICLE2_PROPERTY.getString();
		ConfigValues.ENABLE_PATS_PRIMARY = ENABLE_PATS_PRIMARY_PROPERTY.getBoolean();
		ConfigValues.ENABLE_PATS_SECONDARY = ENABLE_PATS_SECONDARY_PROPERTY.getBoolean();
		ConfigValues.EXTENSIVE_PATS_SECONDARY = EXTENSIVE_PATS_SECONDARY_PROPERTY.getBoolean();
		ConfigValues.PATS_KEY_BEHAVIOR = PATS_KEY_BEHAVIOR_PROPERTY.getString();
		//General
		ConfigValues.NORMALBRIGHTNESS = NORMALBRIGHTNESS_PROPERTY.getInt();
		//Hidden
		ConfigValues.GLOWINGENTITIES = GLOWINGENTITIES_PROPERTY.getStringList();
		ConfigValues.ENABLETARGETLINES = ENABLETARGETLINES_PROPERTY.getBoolean();
		if(config.hasChanged()){
			config.save();
		}

		try{
			primary = EnumParticleTypes.valueOf(ConfigValues.PATS_PARTICLE1.toUpperCase());
		}catch(IllegalArgumentException e){
			e.printStackTrace();
			primary = EnumParticleTypes.FLAME;
		}
		try{
			secondary = EnumParticleTypes.valueOf(ConfigValues.PATS_PARTICLE2.toUpperCase());
		}catch(IllegalArgumentException e){
			e.printStackTrace();
			secondary = EnumParticleTypes.SMOKE_NORMAL;
		}
	}
	@EventHandler
	@SideOnly(Side.CLIENT)
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
		TPU_PROPERTY = config.get("pats", ConfigValues.TPU_NAME, ConfigValues.TPU_DEFAULT, I18n.format(ConfigValues.TPU_NAME+".tooltip"));
		ATD_PROPERTY = config.get("pats", ConfigValues.ATD_NAME, ConfigValues.ATD_DEFAULT, I18n.format(ConfigValues.ATD_NAME+".tooltip"));
		PATS_PARTICLE1_PROPERTY = config.get("pats", ConfigValues.PATS_PARTICLE1_NAME, ConfigValues.PATS_PARTICLE1_DEFAULT, I18n.format(ConfigValues.PATS_PARTICLE1_NAME+".tooltip"));
		PATS_PARTICLE2_PROPERTY = config.get("pats", ConfigValues.PATS_PARTICLE2_NAME, ConfigValues.PATS_PARTICLE2_DEFAULT, I18n.format(ConfigValues.PATS_PARTICLE2_NAME+".tooltip"));
		ENABLE_PATS_PRIMARY_PROPERTY = config.get("pats", ConfigValues.ENABLE_PATS_PRIMARY_NAME, ConfigValues.ENABLE_PATS_PRIMARY_DEFAULT, I18n.format(ConfigValues.ENABLE_PATS_PRIMARY_NAME+".tooltip"));
		ENABLE_PATS_SECONDARY_PROPERTY = config.get("pats", ConfigValues.ENABLE_PATS_SECONDARY_NAME, ConfigValues.ENABLE_PATS_SECONDARY_DEFAULT, I18n.format(ConfigValues.ENABLE_PATS_SECONDARY_NAME +".tooltip"));
		EXTENSIVE_PATS_SECONDARY_PROPERTY = config.get("pats", ConfigValues.EXTENSIVE_PATS_SECONDARY_NAME, ConfigValues.EXTENSIVE_PATS_SECONDARY_DEFAULT, I18n.format(ConfigValues.EXTENSIVE_PATS_SECONDARY_NAME +".tooltip"));
		PATS_KEY_BEHAVIOR_PROPERTY = config.get("pats", ConfigValues.PATS_KEY_BEHAVIOR_NAME, ConfigValues.PATS_KEY_BEHAVIOR_DEFAULT, I18n.format(ConfigValues.PATS_KEY_BEHAVIOR_NAME +".tooltip"));
		ENABLETARGETLINES_PROPERTY = config.get("hidden", ConfigValues.ENABLETARGETLINES_NAME, ConfigValues.ENABLETARGETLINES_DEFAULT, I18n.format(ConfigValues.ENABLETARGETLINES_NAME +".tooltip"));
		TPU_PROPERTY.setMinValue(1);
		ATD_PROPERTY.setMinValue(1);
		PATS_KEY_BEHAVIOR_PROPERTY.setValidValues(new String[]{"toggle","enable","disable"});
		syncConfig();

		MinecraftForge.EVENT_BUS.register(new ForgeEvents());
		MinecraftForge.EVENT_BUS.register(new KeyHandlerHVII());
		PATSRegistry.registerHandler(new ItemStack(Items.BOW, 1, OreDictionary.WILDCARD_VALUE), new PatsBowTargeting());
		PATSRegistry.registerHandler(new ItemStack(Items.SNOWBALL), new PatsGenericThrowableTargeting(1.5F,0.0F, 0.03F));
		PATSRegistry.registerHandler(new ItemStack(Items.EGG), new PatsGenericThrowableTargeting(1.5F, 0.0F, 0.03F));
		PATSRegistry.registerHandler(new ItemStack(Items.ENDER_PEARL), new PatsGenericThrowableTargeting(1.5F, 0.0F, 0.03F));
		PATSRegistry.registerHandler(new ItemStack(Items.EXPERIENCE_BOTTLE), new PatsGenericThrowableTargeting(0.7F, -20.0F, 0.07F));
		PATSRegistry.registerHandler(new ItemStack(Items.SPLASH_POTION, 1, OreDictionary.WILDCARD_VALUE), new PatsGenericThrowableTargeting(0.5F,-20.0F, 0.05F));
		PATSRegistry.registerHandler(new ItemStack(Items.LINGERING_POTION, 1, OreDictionary.WILDCARD_VALUE), new PatsGenericThrowableTargeting(0.5F, -20.0F, 0.05F));
	}
	/**
	 * Toggles fullbright
	 */
	@SideOnly(Side.CLIENT)
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
	@SideOnly(Side.CLIENT)
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
