package the_fireplace.hvii;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;
import the_fireplace.hvii.config.ConfigValues;
import the_fireplace.hvii.fats.FATSRegistry;

/**
 * @author The_Fireplace
 */
@SideOnly(Side.CLIENT)
public class ForgeEvents {
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if(eventArgs.getModID().equals(HVII.MODID))
			HVII.syncConfig();
	}
	@SubscribeEvent
	public void livingUpdate(LivingEvent.LivingUpdateEvent event) {
		if (ArrayUtils.contains(ConfigValues.GLOWINGENTITIES, event.getEntity().getClass().getSimpleName())){
			if (!event.getEntityLiving().isGlowing())
				event.getEntityLiving().setGlowing(true);
		}else{
			if(event.getEntityLiving().isGlowing())
				event.getEntityLiving().setGlowing(false);
		}
	}

	int partial;
	@SubscribeEvent
	public void renderTick(TickEvent.RenderTickEvent t){
		if(!ConfigValues.ENABLE_FATS_PRIMARY && !ConfigValues.ENABLE_FATS_SECONDARY)
			return;
		partial++;
		if(partial % ConfigValues.TPU == 0)
		if(Minecraft.getMinecraft().player != null) {
			ItemStack heldItem = Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND);
			if (heldItem != null)
				if (FATSRegistry.getHandlerFor(heldItem) != null) {
					if(!FATSRegistry.getHandlerFor(heldItem).isThrown(heldItem) || FATSRegistry.showThrown)
						FATSRegistry.getHandlerFor(heldItem).handleRender(Minecraft.getMinecraft(), ConfigValues.ENABLE_FATS_PRIMARY, ConfigValues.ENABLE_FATS_SECONDARY, ConfigValues.ATD, HVII.primary, HVII.secondary, ConfigValues.EXTENSIVE_FATS_SECONDARY, 0.0F);
				}
		}
	}
}
