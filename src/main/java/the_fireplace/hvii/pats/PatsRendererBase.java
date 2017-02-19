package the_fireplace.hvii.pats;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;

/**
 * @author The_Fireplace
 */
public abstract class PatsRendererBase implements IPatsRenderer {
    //Variables for use by extending renderers
    Minecraft mc;
    double posX;
    double posY;
    double posZ;
    double motionX;
    double motionZ;
    double motionY;
    float rotationYaw;
    float rotationPitch;
    float prevRotationYaw;
    float prevRotationPitch;

    EnumParticleTypes primary;
    EnumParticleTypes secondary;
    @Override
    public void handleRender(Minecraft mc, boolean primaryRenderEnabled, boolean secondaryRenderEnabled, int projectileTickCount, EnumParticleTypes primaryParticle, EnumParticleTypes secondaryParticle, boolean extensiveSecondary, float inaccuracyBoost) {
        this.mc=mc;
        primary=primaryParticle;
        secondary=secondaryParticle;
    }

    @Override
    public boolean ignoresNbt(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isThrown(ItemStack stack) {
        return true;
    }
}
