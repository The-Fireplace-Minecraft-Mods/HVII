package the_fireplace.hvii.compat.overlord;

import net.minecraft.util.math.BlockPos;
import the_fireplace.overlord.network.PacketDispatcher;
import the_fireplace.overlord.network.packets.DebugSkeletonMessage;

public class OvCompat implements IOvCompat {
	@Override
	public void spawnSkelly(BlockPos pos) {
		PacketDispatcher.sendToServer(new DebugSkeletonMessage(pos));
	}
}
