package the_fireplace.hvii.fats;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;

/**
 * @author The_Fireplace
 */
public interface IFatsRenderer {
    /**
     * Handles the rendering of F.A.T.S.
     * @param mc
     *  The Minecraft instance
     * @param primaryRenderEnabled
     *  Whether or not rendering of the primary(most accurate) line is enabled
     * @param secondaryRenderEnabled
     *  Whether or not rendering of the secondary(least accurate) lines is enabled
     * @param projectileTickCount
     *  The number of ticks of projectile movement to calculate
     */
    void handleRender(Minecraft mc, boolean primaryRenderEnabled, boolean secondaryRenderEnabled, int projectileTickCount, EnumParticleTypes primaryParticle, EnumParticleTypes secondaryParticle, boolean extensiveSecondary);

    boolean ignoresNbt(ItemStack stack);
}
