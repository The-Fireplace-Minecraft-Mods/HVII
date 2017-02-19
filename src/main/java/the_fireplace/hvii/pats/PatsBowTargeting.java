package the_fireplace.hvii.pats;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

/**
 * @author The_Fireplace
 */
public class PatsBowTargeting extends PatsRendererBase {

    @Override
    public boolean isThrown(ItemStack stack) {
        return false;
    }

    @Override
    public void handleRender(Minecraft mc, boolean primaryRenderEnabled, boolean secondaryRenderEnabled, int projectileTickCount, EnumParticleTypes primaryParticle, EnumParticleTypes secondaryParticle, boolean extensiveSecondary, float inaccuracyBoost) {
        super.handleRender(mc, primaryRenderEnabled, secondaryRenderEnabled, projectileTickCount, primaryParticle, secondaryParticle, extensiveSecondary, inaccuracyBoost);
        if(mc.player.getItemInUseCount() > 0){
            float velocity = ItemBow.getArrowVelocity(mc.player.getItemInUseCount() > mc.player.getItemInUseMaxCount() ? mc.player.getItemInUseMaxCount() : mc.player.getItemInUseCount());
            if ((double)velocity >= 0.1D){
                resetPos();
                setAim(mc.player, mc.player.rotationPitch, mc.player.rotationYaw, velocity*3.0f, 0.0F+inaccuracyBoost);
                for(int i = 0; i<projectileTickCount && primaryRenderEnabled; i++){
                    updatePrimary(i, projectileTickCount);
                }
                //Secondary renders
                if(extensiveSecondary){
                    resetPos();
                    setAimX(mc.player, mc.player.rotationPitch, mc.player.rotationYaw, velocity*3.0f, 1.0F+inaccuracyBoost, false, false, false);
                    for(int i = 0; i<projectileTickCount && secondaryRenderEnabled; i++){
                        updateSecondary(i, projectileTickCount);
                    }
                    resetPos();
                    setAimX(mc.player, mc.player.rotationPitch, mc.player.rotationYaw, velocity*3.0f, 1.0F+inaccuracyBoost, true, false, false);
                    for(int i = 0; i<projectileTickCount && secondaryRenderEnabled; i++){
                        updateSecondary(i, projectileTickCount);
                    }
                    resetPos();
                    setAimX(mc.player, mc.player.rotationPitch, mc.player.rotationYaw, velocity*3.0f, 1.0F+inaccuracyBoost, false, false, true);
                    for(int i = 0; i<projectileTickCount && secondaryRenderEnabled; i++){
                        updateSecondary(i, projectileTickCount);
                    }
                    resetPos();
                    setAimX(mc.player, mc.player.rotationPitch, mc.player.rotationYaw, velocity*3.0f, 1.0F+inaccuracyBoost, false, true, false);
                    for(int i = 0; i<projectileTickCount && secondaryRenderEnabled; i++){
                        updateSecondary(i, projectileTickCount);
                    }
                    resetPos();
                    setAimX(mc.player, mc.player.rotationPitch, mc.player.rotationYaw, velocity*3.0f, 1.0F+inaccuracyBoost, true, true, false);
                    for(int i = 0; i<projectileTickCount && secondaryRenderEnabled; i++){
                        updateSecondary(i, projectileTickCount);
                    }
                    resetPos();
                    setAimX(mc.player, mc.player.rotationPitch, mc.player.rotationYaw, velocity*3.0f, 1.0F+inaccuracyBoost, true, false, true);
                    for(int i = 0; i<projectileTickCount && secondaryRenderEnabled; i++){
                        updateSecondary(i, projectileTickCount);
                    }
                    resetPos();
                    setAimX(mc.player, mc.player.rotationPitch, mc.player.rotationYaw, velocity*3.0f, 1.0F+inaccuracyBoost, false, true, true);
                    for(int i = 0; i<projectileTickCount && secondaryRenderEnabled; i++){
                        updateSecondary(i, projectileTickCount);
                    }
                    resetPos();
                    setAimX(mc.player, mc.player.rotationPitch, mc.player.rotationYaw, velocity*3.0f, 1.0F+inaccuracyBoost, true, true, true);
                    for(int i = 0; i<projectileTickCount && secondaryRenderEnabled; i++){
                        updateSecondary(i, projectileTickCount);
                    }
                }else{
                    resetPos();
                    setAim(mc.player, mc.player.rotationPitch, mc.player.rotationYaw, velocity*3.0f, 1.0F+inaccuracyBoost);
                    for(int i = 0; i<projectileTickCount && secondaryRenderEnabled; i++){
                        updateSecondary(i, projectileTickCount);
                    }
                }
            }
        }
    }

    public void resetPos(){
        posX=mc.player.posX;
        posY=mc.player.posY+mc.player.eyeHeight;
        posZ=mc.player.posZ;
    }

    public void setAim(Entity shooter, float pitch, float yaw, float velocity, float inaccuracy)
    {
        float f = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        float f1 = -MathHelper.sin(pitch * 0.017453292F);
        float f2 = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        this.setThrowableHeading((double)f, (double)f1, (double)f2, velocity, inaccuracy);
        this.motionX += shooter.motionX;
        this.motionZ += shooter.motionZ;

        if (!shooter.onGround)
        {
            this.motionY += shooter.motionY;
        }
    }

    public void setAimX(Entity shooter, float pitch, float yaw, float velocity, float inaccuracy, boolean minusX, boolean minusY, boolean minusZ)
    {
        float f = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        float f1 = -MathHelper.sin(pitch * 0.017453292F);
        float f2 = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        this.setThrowableHeadingX((double)f, (double)f1, (double)f2, velocity, inaccuracy, minusX, minusY, minusZ);
        this.motionX += shooter.motionX;
        this.motionZ += shooter.motionZ;

        if (!shooter.onGround)
        {
            this.motionY += shooter.motionY;
        }
    }

    public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy)
    {
        float f = MathHelper.sqrt(x * x + y * y + z * z);
        x = x / (double)f;
        y = y / (double)f;
        z = z / (double)f;
		x = x + mc.world.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
		y = y + mc.world.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
		z = z + mc.world.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
        x = x * (double)velocity;
        y = y * (double)velocity;
        z = z * (double)velocity;
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
        float f1 = MathHelper.sqrt(x * x + z * z);
        this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180D / Math.PI));
        this.rotationPitch = (float)(MathHelper.atan2(y, (double)f1) * (180D / Math.PI));
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
    }

    public void setThrowableHeadingX(double x, double y, double z, float velocity, float inaccuracy, boolean minusX, boolean minusY, boolean minusZ)
    {
        float f = MathHelper.sqrt(x * x + y * y + z * z);
        x = x / (double)f;
        y = y / (double)f;
        z = z / (double)f;
        x = x + MathHelper.abs((float)mc.world.rand.nextGaussian()) * 0.007499999832361937D * (double)inaccuracy * (minusX ? -1 : 1);
        y = y + MathHelper.abs((float)mc.world.rand.nextGaussian()) * 0.007499999832361937D * (double)inaccuracy * (minusY ? -1 : 1);
        z = z + MathHelper.abs((float)mc.world.rand.nextGaussian()) * 0.007499999832361937D * (double)inaccuracy * (minusZ ? -1 : 1);
        x = x * (double)velocity;
        y = y * (double)velocity;
        z = z * (double)velocity;
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
        float f1 = MathHelper.sqrt(x * x + z * z);
        this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180D / Math.PI));
        this.rotationPitch = (float)(MathHelper.atan2(y, (double)f1) * (180D / Math.PI));
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
    }

    public void updatePrimary(int iteration, int projectileTickCount){
        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posY -= mc.player.eyeHeight*iteration/projectileTickCount;
        this.posZ += this.motionZ;
        this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
        {
            this.prevRotationPitch += 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw < -180.0F)
        {
            this.prevRotationYaw -= 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
        {
            this.prevRotationYaw += 360.0F;
        }

        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
        float f1 = 0.99F;

        if (mc.world.getBlockState(new BlockPos(posX, posY, posZ)).getMaterial() == Material.WATER)
        {
            f1 = 0.6F;
        }

        this.motionX *= (double)f1;
        this.motionY *= (double)f1;
        this.motionZ *= (double)f1;

        this.motionY -= 0.05000000074505806D;

        if(iteration % 2 == 0 && !mc.world.getBlockState(new BlockPos(posX, posY, posZ)).getMaterial().isOpaque())
            mc.world.spawnParticle(primary, posX, posY, posZ, motionX, motionY, motionZ);
    }

    public void updateSecondary(int iteration, int projectileTickCount){
        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posY -= mc.player.eyeHeight*iteration/projectileTickCount;
        this.posZ += this.motionZ;
        this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
        {
            this.prevRotationPitch += 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw < -180.0F)
        {
            this.prevRotationYaw -= 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
        {
            this.prevRotationYaw += 360.0F;
        }

        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
        float f1 = 0.99F;

        if (mc.world.getBlockState(new BlockPos(posX, posY, posZ)).getMaterial() == Material.WATER)
        {
            f1 = 0.6F;
        }

        this.motionX *= (double)f1;
        this.motionY *= (double)f1;
        this.motionZ *= (double)f1;

        this.motionY -= 0.05000000074505806D;

        if(iteration % 2 == 0 && !mc.world.getBlockState(new BlockPos(posX, posY, posZ)).getMaterial().isOpaque())
            mc.world.spawnParticle(secondary, posX, posY, posZ, motionX, motionY, motionZ);
    }
}
