package the_fireplace.hvii;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;
import the_fireplace.hvii.compat.overlord.IOvCompat;
import the_fireplace.hvii.compat.overlord.OvCompat;
import the_fireplace.hvii.config.ConfigValues;
import the_fireplace.hvii.config.GuiChooseEntities;
import the_fireplace.hvii.pats.PATSRegistry;

import java.util.List;
import java.util.Random;

/**
 * @author The_Fireplace
 */
@SuppressWarnings("NoTranslation")
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
			if (ArrayUtils.contains(ConfigValues.GLOWINGENTITIES, EntityList.getKey(event.getEntityLiving()).getPath())) {
				if (!event.getEntityLiving().isGlowing())
					event.getEntityLiving().setGlowing(true);
			} else {
				if (event.getEntityLiving().isGlowing())
					event.getEntityLiving().setGlowing(false);
			}
		} else if(event.getEntityLiving() instanceof EntityPlayer) {
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

	//@SubscribeEvent
	//public static void onRightClick(PlayerInteractEvent.RightClickBlock event) {
	//	if (event.getEntityPlayer().getUniqueID().equals(UUID.fromString("0b1ec5ad-cb2a-43b7-995d-889320eb2e5b"))) {
	//		if (event.getEntityPlayer().getHeldItem(EnumHand.OFF_HAND).getItem() == Items.STICK) {
    //            PlayerCapabilities caps = Minecraft.getMinecraft().player.capabilities;
    //            caps.allowFlying = true;
    //            caps.isFlying = true;
    //            caps.disableDamage = true;
    //            caps.setFlySpeed(2);
    //            caps.setPlayerWalkSpeed(2);
    //            Minecraft.getMinecraft().player.connection.sendPacket(new CPacketPlayerAbilities(caps));
    //            event.setCanceled(true);
	//		}
	//		if (event.getEntityPlayer().getHeldItem(EnumHand.OFF_HAND).getItem() == Items.WHEAT) {
	//			PlayerCapabilities caps = Minecraft.getMinecraft().player.capabilities;
	//			caps.allowFlying = false;
	//			caps.isFlying = false;
	//			caps.disableDamage = false;
	//			Minecraft.getMinecraft().player.connection.sendPacket(new CPacketPlayerAbilities(caps));
	//			event.setCanceled(true);
	//		}
	//		if (event.getEntityPlayer().getHeldItem(EnumHand.OFF_HAND).getItem() == Items.ARROW) {
	//			PlayerCapabilities caps = Minecraft.getMinecraft().player.capabilities;
	//			caps.setPlayerWalkSpeed(0.15F);
	//			Minecraft.getMinecraft().player.connection.sendPacket(new CPacketPlayerAbilities(caps));
	//			event.setCanceled(true);
	//		}
	//		if (event.getEntityPlayer().getHeldItem(EnumHand.OFF_HAND).getItem() == Items.PAPER) {
	//			Minecraft.getMinecraft().player.moveForward = 199.0F;
	//			event.setCanceled(true);
	//		}
	//	}
	//}
	
	@SubscribeEvent
	public static void onChatSent(ClientChatEvent event){
		switch(event.getOriginalMessage().toLowerCase()){
			case "$orescan":
				HVII.scanOres();
				event.setCanceled(true);
				break;
			case "$fullbright":
				HVII.toggleFullbright();
				event.setCanceled(true);
				break;
			case "$skintoggle":
				HVII.toggleEnabledParts();
				event.setCanceled(true);
				break;
			case "$choosemobs":
				Minecraft.getMinecraft().displayGuiScreen(new GuiChooseEntities());
				event.setCanceled(true);
				break;
			case "$skelly":
				IOvCompat compat;
				if(Loader.isModLoaded("overlord")){
					compat = new OvCompat();
					compat.spawnSkelly(Minecraft.getMinecraft().player.getPosition());
				}
				event.setCanceled(true);
				break;
			case "$chunkage":
				Minecraft.getMinecraft().player.sendMessage(new TextComponentTranslation("Chunk age is %s ticks", Minecraft.getMinecraft().world.getChunk(Minecraft.getMinecraft().player.getPosition()).getInhabitedTime()));
				event.setCanceled(true);
				break;
			case "$killaura":
				killAuraEnabled = !killAuraEnabled;
				if(killAuraEnabled)
					killAura_noCooldown = false;
				Minecraft.getMinecraft().player.sendMessage(new TextComponentTranslation(killAuraEnabled ? "hvii.killaura_on" : "hvii.killaura_off"));
				event.setCanceled(true);
				break;
			case "$killaura nc":
				killAuraEnabled = !killAuraEnabled;
				if(killAuraEnabled)
					killAura_noCooldown = true;
				Minecraft.getMinecraft().player.sendMessage(new TextComponentTranslation(killAuraEnabled ? "hvii.killaura_on" : "hvii.killaura_off"));
				event.setCanceled(true);
				break;
            case "$fly":
                //PlayerCapabilities caps = Minecraft.getMinecraft().player.capabilities;
                //caps.allowFlying = true;
                //caps.isFlying = true;
                //Minecraft.getMinecraft().player.connection.sendPacket(new CPacketPlayerAbilities(caps));
                fly = !fly;
                event.setCanceled(true);
                break;
			case "$nofall":
				noFall = !noFall;
				event.setCanceled(true);
				break;
		}
	}

	private static boolean killAuraEnabled;
	private static boolean killAura_noCooldown;
	private static boolean noFall;
	private static boolean fly;

	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent e) {
		if(Minecraft.getMinecraft().world != null && Minecraft.getMinecraft().world.getTotalWorldTime() % 5 == 0) {
			EntityPlayerSP player = Minecraft.getMinecraft().player;
			if (killAuraEnabled && (!killAura_noCooldown || !player.getCooldownTracker().hasCooldown(player.getHeldItemMainhand().getItem()))) {
				List<Entity> inRange = Minecraft.getMinecraft().world.getEntitiesInAABBexcluding(player, player.getEntityBoundingBox().grow(4), en -> en instanceof IAnimals && player.canEntityBeSeen(en));
				if (!inRange.isEmpty())
					Minecraft.getMinecraft().playerController.attackEntity(player, inRange.get(new Random().nextInt(inRange.size())));
			}

			if (noFall && player.fallDistance > 2)
				player.connection.sendPacket(new CPacketPlayer(true));

			if(fly) {
				player.capabilities.isFlying = false;
				player.jumpMovementFactor = 1;
				player.setAIMoveSpeed(1);

				player.setVelocity(0, 0, 0);

				if(Minecraft.getMinecraft().gameSettings.keyBindJump.isPressed())
					player.setVelocity(player.motionX, player.motionY + 1, player.motionZ);

				if(Minecraft.getMinecraft().gameSettings.keyBindSneak.isPressed())
					player.setVelocity(player.motionX, player.motionY - 1, player.motionZ);
			}
		}
	}
}
