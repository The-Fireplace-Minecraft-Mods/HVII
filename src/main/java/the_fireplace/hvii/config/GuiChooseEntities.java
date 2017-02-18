package the_fireplace.hvii.config;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.ArrayUtils;
import the_fireplace.hvii.HVII;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author The_Fireplace
 */
@SuppressWarnings("unchecked")
public class GuiChooseEntities extends GuiScreen {
    protected int xSize = 176;
    protected int ySize = 166;
    protected int guiLeft;
    protected int guiTop;

    private int page;
    private final int max_page;
    private final ArrayList<Class<? extends Entity>> allMobs;
    private String[] tmpMobs;
    private final Class<? extends Entity>[] currentlyShownMobs;

    private static final ResourceLocation resource = new ResourceLocation(HVII.MODID, "textures/gui/guichooseentities.png");

    public GuiChooseEntities(){
        super();

        height=ySize;
        width=xSize;

        allMobs = new ArrayList<>();
        for (final Map.Entry<String, Class<? extends Entity>> entry : EntityList.NAME_TO_CLASS.entrySet()) {
            allMobs.add(entry.getValue());
        }
        allMobs.remove(EntityLiving.class);
        allMobs.add(EntityPlayerMP.class);
        currentlyShownMobs = new Class[8];
        page = 0;
        max_page = (allMobs.size() / 8)
                + (((allMobs.size() % 8) == 0) ? -1 : 0);
        tmpMobs = ConfigValues.GLOWINGENTITIES;
    }

    @Override
    public void initGui() {
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        buttonList.add(new GuiButton(0, 131, 9, 10, 20, "<"));
        buttonList.add(new GuiButton(1, 131, 45, 10, 20, ">"));

        for (int i = 2; i < 10; i++) {
            buttonList.add(new ToggleButton(i, 8, 8+18*(i-2)));
        }

        buttonList.add(new GuiButton(10, 131, 100, 40, 20, I18n.format("gui.done")));

        setMobs();

        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button){
        if (button.enabled)
        {
            switch(button.id){
                case 0:
                    page--;
                    setMobs();
                    break;
                case 1:
                    page++;
                    setMobs();
                    break;
                case 10:
                    mc.displayGuiScreen(null);
                    break;
                default:
                    if(button instanceof ToggleButton){
                        boolean flag = ((ToggleButton) button).toggled;
                        if(flag){
                            tmpMobs = ArrayUtils.remove(tmpMobs, ArrayUtils.indexOf(tmpMobs, currentlyShownMobs[button.id-2].getSimpleName()));
                        }else{
                            tmpMobs = ArrayUtils.add(tmpMobs, currentlyShownMobs[button.id-2].getSimpleName());
                        }
                        ((ToggleButton) button).toggled=!flag;
                    }else{
                        System.out.println("Error: Button "+button.id+" is not a ToggleButton.");
                    }
            }
        }
    }

    @Override
    public void onGuiClosed()
    {
        HVII.GLOWINGENTITIES_PROPERTY.set(tmpMobs);
        HVII.syncConfig();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        guiLeft = (width - xSize) / 2;
        guiTop = (height - ySize) / 2;
        int i = this.guiLeft;
        int j = this.guiTop;
        GlStateManager.pushMatrix();
        GlStateManager.translate(guiLeft, guiTop, 0.0f);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(resource);
        drawTexturedModalRect(0, 0, 0, 0, xSize, ySize);
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        super.drawScreen(mouseX-guiLeft, mouseY-guiTop, partialTicks);
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.translate((float)i, (float)j, 0.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableRescaleNormal();
        GlStateManager.popMatrix();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();

        for (int x=0;x<8;x++){
            drawString(fontRendererObj, currentlyShownMobs[x] == EntityPlayerMP.class ? "Player" : EntityList.getEntityStringFromClass(currentlyShownMobs[x]), guiLeft+24, guiTop+10+x*18, 0xFFFFFF);
        }
    }

    private void setMobs() {
        if (page < 0) {
            page = 0;
        }

        if (page > max_page) {
            page = max_page;
        }

        buttonList.get(0).enabled = page != 0;
        buttonList.get(1).enabled = page != max_page;

        final int length = allMobs.size();
        final Class[] keys = allMobs.toArray(new Class[]{});
        final int page4 = page * 8;

        if ((page == max_page) && (length != ((max_page - 1) * 8))) {
            int tmp;

            for (int i = 2; i < 10; i++) {
                tmp = i + page4;

                if (tmp < length) {
                    currentlyShownMobs[i-2] = keys[tmp];
                    buttonList.get(i).enabled = true;
                    buttonList.get(i).visible = true;
                    ((ToggleButton)buttonList.get(i)).toggled= ArrayUtils.contains(tmpMobs, currentlyShownMobs[i-2].getSimpleName());
                } else {
                    currentlyShownMobs[i-2] = null;
                    buttonList.get(i).enabled = false;
                    buttonList.get(i).visible = false;
                    ((ToggleButton)buttonList.get(i)).toggled=false;
                }
            }
        } else {
            for (int i = 2; i < 10; i++) {
                currentlyShownMobs[i-2] = keys[i + page4];
                buttonList.get(i).enabled = true;
                buttonList.get(i).visible = true;
                ((ToggleButton)buttonList.get(i)).toggled= ArrayUtils.contains(tmpMobs, currentlyShownMobs[i-2].getSimpleName());
            }
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
    {
        super.mouseClickMove(mouseX-guiLeft, mouseY-guiTop, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX-guiLeft, mouseY-guiTop, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        super.mouseReleased(mouseX-guiLeft, mouseY-guiTop, state);
    }
}
