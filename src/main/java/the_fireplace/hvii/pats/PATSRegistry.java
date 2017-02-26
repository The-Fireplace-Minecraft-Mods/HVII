package the_fireplace.hvii.pats;

import com.google.common.collect.Maps;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * @author The_Fireplace
 */
@SideOnly(Side.CLIENT)
public final class PATSRegistry {
    private static Map<ItemStack, IPatsRenderer> patsHandlers = Maps.newHashMap();

    /**
     * Registers a F.A.T.S. Handler for an ItemStack
     * @param stack
     *  A stack containing the Item and metadata. The count doesn't matter. To use any meta, set meta to OreDictionary.WILDCARD_VALUE
     * @param handler
     *  An instance of the Fats Renderer to use for that stack.
     */
    public static void registerHandler(@Nonnull ItemStack stack, @Nonnull IPatsRenderer handler){
        patsHandlers.putIfAbsent(stack, handler);
    }

    /**
     *
     * @param stack
     *  The itemstack to check for a handler for
     * @return
     *  The handler for that stack, or null if there isn't one.
     */
    @Nullable
    public static IPatsRenderer getHandlerFor(@Nonnull ItemStack stack){
        IPatsRenderer renderer;
        renderer=patsHandlers.get(stack);
        if(renderer == null){
            for(ItemStack key:patsHandlers.keySet()){
                if(key.getItem().equals(stack.getItem()))
                    if(key.getMetadata() == OreDictionary.WILDCARD_VALUE || key.getMetadata() == stack.getMetadata())
                        if(patsHandlers.get(key).ignoresNbt(stack))
                            renderer = patsHandlers.get(key);
                        else if((stack.getTagCompound() == null && key.getTagCompound() == null) || (stack.getTagCompound() != null && stack.getTagCompound().equals(key.getTagCompound())))
                            renderer = patsHandlers.get(key);
            }
        }
        return renderer;
    }
}
