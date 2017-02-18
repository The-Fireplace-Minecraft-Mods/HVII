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
     * @param primaryParticle
     *  The particle to be used in the main line
     * @param secondaryParticle
     *  The particle to be used in the inaccuracy lines
     * @param extensiveSecondary
     *  Whether or not the inaccuracy lines should be drawn extensively. If false, do the bare minimum. If true, try to make it cover a decent range of possibilities
     * @param inaccuracyBoost
     *  The amount that should be added to the inaccuracy. Normally 0.0f, included because some special cases will need it.
     */
    void handleRender(Minecraft mc, boolean primaryRenderEnabled, boolean secondaryRenderEnabled, int projectileTickCount, EnumParticleTypes primaryParticle, EnumParticleTypes secondaryParticle, boolean extensiveSecondary, float inaccuracyBoost);

    /**
     * Check whether or not the stack being used should consider NBT Data when checking it against the stacks registered in the F.A.T.S. Registry
     * @param stack
     *  The stack being checked
     * @return
     *  true if NBT Data should be ignored, otherwise return false.
     */
    boolean ignoresNbt(ItemStack stack);

    /**
     * Check if the stack is a thrown item
     * @param stack
     *  The stack to check
     * @return
     *  true if the item is thrown(Projectile launches immediately on right click) or false if it isn't
     */
    boolean isThrown(ItemStack stack);
}
