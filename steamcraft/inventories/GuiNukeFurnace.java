package steamcraft.inventories;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import steamcraft.TileEntityNukeFurnace;

public class GuiNukeFurnace extends GuiContainer
{
    public GuiNukeFurnace(InventoryPlayer inventoryplayer, TileEntityNukeFurnace tileentitynukefurnace)
    {
        super(new ContainerNukeFurnace(inventoryplayer, tileentitynukefurnace));
        furnaceInventory = tileentitynukefurnace;
    }
    @Override
    protected void drawGuiContainerForegroundLayer(int i, int j)
    {
        fontRenderer.drawString("Nuclear Reactor", 60, 6, 0x404040);
        fontRenderer.drawString("Inventory", 8, (ySize - 96) + 2, 0x404040);
    }
    @Override
    protected void drawGuiContainerBackgroundLayer(float f,int i, int j)
    {
    	mc.renderEngine.bindTexture(gui);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        if(furnaceInventory.isBurning())
        {
            int l = furnaceInventory.getBurnTimeRemainingScaled(12);
            drawTexturedModalRect(guiLeft + 56, (guiTop + 36 + 12) - l, 176, 12 - l, 14, l + 2);
        }
        int i1 = furnaceInventory.getCookProgressScaled(24);
        drawTexturedModalRect(guiLeft + 79, guiTop + 34, 176, 14, i1 + 1, 16);
		int i2 = furnaceInventory.getHeatScaled(54);
        drawTexturedModalRect(j + 37, guiTop + 16 + 54 - i2, 176, 31 + 54 - i2, 8, i2);
    }

    private TileEntityNukeFurnace furnaceInventory;
    private static final ResourceLocation gui = new ResourceLocation("steamcraft","textures/gui/nukefurnace.png");
}
