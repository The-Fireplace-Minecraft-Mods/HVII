package the_fireplace.hvii;

import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.ArrayUtils;
import the_fireplace.hvii.config.ConfigValues;

/**
 * 
 * @author The_Fireplace
 *
 */
public class ForgeEvents {
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if(eventArgs.getModID().equals(HVII.MODID))
			HVII.syncConfig();
	}
	@SubscribeEvent
	public void livingUpdate(LivingEvent.LivingUpdateEvent event){
		if(ArrayUtils.contains(ConfigValues.GLOWINGENTITIES, event.getEntity().getClass().getSimpleName()) && !event.getEntityLiving().isGlowing())
			event.getEntityLiving().setGlowing(true);
	}
}
