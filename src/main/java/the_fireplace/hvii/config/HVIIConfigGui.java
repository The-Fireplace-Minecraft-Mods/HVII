package the_fireplace.hvii.config;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.IConfigElement;
import the_fireplace.hvii.HVII;

import java.util.ArrayList;
import java.util.List;

/**
 * @author The_Fireplace
 */
public class HVIIConfigGui extends GuiConfig{

	public HVIIConfigGui(GuiScreen parentScreen) {
		super(parentScreen, getConfigElements(), HVII.MODID, false,
				false, GuiConfig.getAbridgedConfigPath(HVII.config.toString()));
	}

	public static List<IConfigElement> getConfigElements(){
		List<IConfigElement> list = new ArrayList();
		list.add(new DummyConfigElement.DummyCategoryElement("skin", "cfg.hvii.category.skin", SkinEntry.class));
		list.add(new DummyConfigElement.DummyCategoryElement("pats", "cfg.hvii.category.pats", FatsEntry.class));
		list.addAll(new ConfigElement(HVII.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements());
		return list;
	}

	public static class SkinEntry extends GuiConfigEntries.CategoryEntry {

		public SkinEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}
		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen,
					new ConfigElement(HVII.config.getCategory("skin")).getChildElements(), HVII.MODID, false,
					false, GuiConfig.getAbridgedConfigPath(HVII.config.toString()));
		}
	}

	public static class FatsEntry extends GuiConfigEntries.CategoryEntry {

		public FatsEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}
		@Override
		protected GuiScreen buildChildScreen(){
			return new GuiConfig(owningScreen,
					new ConfigElement(HVII.config.getCategory("pats")).getChildElements(), HVII.MODID, false,
					false, GuiConfig.getAbridgedConfigPath(HVII.config.toString()));
		}
	}
}
