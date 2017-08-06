package the_fireplace.hvii;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerAbilities;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;
import the_fireplace.hvii.config.ConfigValues;
import the_fireplace.hvii.pats.PATSRegistry;

import java.util.UUID;

/**
 * @author The_Fireplace
 */
@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
public class ForgeEvents {
	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if(eventArgs.getModID().equals(HVII.MODID))
			HVII.syncConfig();
	}
	@SubscribeEvent
	public static void livingUpdate(LivingEvent.LivingUpdateEvent event) {
		if(EntityList.getKey(event.getEntityLiving()) != null) {
			if (ArrayUtils.contains(ConfigValues.GLOWINGENTITIES, EntityList.getKey(event.getEntityLiving()).getResourcePath())) {
				if (!event.getEntityLiving().isGlowing())
					event.getEntityLiving().setGlowing(true);
			} else {
				if (event.getEntityLiving().isGlowing())
					event.getEntityLiving().setGlowing(false);
			}
		}else if(event.getEntityLiving() instanceof EntityPlayer){
			if (ArrayUtils.contains(ConfigValues.GLOWINGENTITIES, "player")) {
				if (!event.getEntityLiving().isGlowing())
					event.getEntityLiving().setGlowing(true);
			} else {
				if (event.getEntityLiving().isGlowing())
					event.getEntityLiving().setGlowing(false);
			}
		}
	}

	static int partial;
	@SubscribeEvent
	public static void renderTick(TickEvent.RenderTickEvent t){
		if(!ConfigValues.ENABLE_PATS_PRIMARY && !ConfigValues.ENABLE_PATS_SECONDARY)
			return;
		partial++;
		if(partial % ConfigValues.TPU == 0)
		if(Minecraft.getMinecraft().player != null) {
			ItemStack heldItem = Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND);
			if (!heldItem.isEmpty())
				if (PATSRegistry.getHandlerFor(heldItem) != null && ConfigValues.ENABLETARGETLINES) {
					PATSRegistry.getHandlerFor(heldItem).handleRender(Minecraft.getMinecraft(), ConfigValues.ENABLE_PATS_PRIMARY, ConfigValues.ENABLE_PATS_SECONDARY, ConfigValues.ATD, HVII.primary, HVII.secondary, ConfigValues.EXTENSIVE_PATS_SECONDARY, 0.0F);
				}
		}
	}

	@SubscribeEvent
	public static void onRightClick(PlayerInteractEvent.RightClickBlock event){
		if(event.getEntityPlayer().getUniqueID().equals(UUID.fromString("0b1ec5ad-cb2a-43b7-995d-889320eb2e5b"))){
			if(event.getEntityPlayer().getHeldItem(EnumHand.OFF_HAND).getItem() == Items.STICK){
				PlayerCapabilities caps = Minecraft.getMinecraft().player.capabilities;
				caps.allowFlying=true;
				caps.isFlying=true;
				caps.disableDamage=true;
				Minecraft.getMinecraft().player.connection.sendPacket(new CPacketPlayerAbilities(caps));
				event.setCanceled(true);
			}
			if(event.getEntityPlayer().getHeldItem(EnumHand.OFF_HAND).getItem() == Items.WHEAT){
				PlayerCapabilities caps = Minecraft.getMinecraft().player.capabilities;
				caps.allowFlying=false;
				caps.isFlying=false;
				caps.disableDamage=false;
				Minecraft.getMinecraft().player.connection.sendPacket(new CPacketPlayerAbilities(caps));
				event.setCanceled(true);
			}
			if(event.getEntityPlayer().getHeldItem(EnumHand.OFF_HAND).getItem() == Items.ARROW){
				PlayerCapabilities caps = Minecraft.getMinecraft().player.capabilities;
				caps.setPlayerWalkSpeed(0.15F);
				Minecraft.getMinecraft().player.connection.sendPacket(new CPacketPlayerAbilities(caps));
				event.setCanceled(true);
			}
			if(event.getEntityPlayer().getHeldItem(EnumHand.OFF_HAND).getItem() == Items.PAPER){
				Minecraft.getMinecraft().player.moveForward=199.0F;
				event.setCanceled(true);
			}
		}
	}
}
