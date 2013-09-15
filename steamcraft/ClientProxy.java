package steamcraft;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import steamcraft.render.RenderCopperWire;
import steamcraft.render.RenderHighwayman;
import steamcraft.render.RenderMusketBall;
import steamcraft.render.RenderTeaPlant;
import steamcraft.render.TileEntityLampRenderer;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy implements ISimpleBlockRenderingHandler{

	public static int CopperModelID;
	public static int TeaPlantModelID;
	public static Minecraft minecraft = Minecraft.getMinecraft();
	public void registerRenderers()
	{
		CopperModelID=RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(CopperModelID,this);
        TeaPlantModelID=RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(TeaPlantModelID,this);
        RenderingRegistry.registerEntityRenderingHandler(EntityMusketBall.class, new RenderMusketBall());
        RenderingRegistry.registerEntityRenderingHandler(EntityHighwayman.class, new RenderHighwayman(new ModelBiped(), new ModelBiped(0.5F), 0.5F));
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLamp.class, new TileEntityLampRenderer());
    }
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		if(modelId == CopperModelID)
        {
            return (new RenderCopperWire()).renderBlockCopperWire(block, x, y, z, world);
        }
		if(modelId == TeaPlantModelID)
        {
            return (new RenderTeaPlant()).renderBlockTeaPlant(block, x, y, z, world);
        }
		return false;
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
	}
	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}
	@Override
	public int getRenderId() {
		return 0;
	}
	
	public void drawTexturedModalRect(int i, int j, int k, int l, int i1, int j1)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(i + 0, j + j1, 0.0F, (float)(k + 0) * f, (float)(l + j1) * f1);
        tessellator.addVertexWithUV(i + i1, j + j1, 0.0F, (float)(k + i1) * f, (float)(l + j1) * f1);
        tessellator.addVertexWithUV(i + i1, j + 0, 0.0F, (float)(k + i1) * f, (float)(l + 0) * f1);
        tessellator.addVertexWithUV(i + 0, j + 0, 0.0F, (float)(k + 0) * f, (float)(l + 0) * f1);
        tessellator.draw();
    }

	private void renderOverlay(int i, int j, Minecraft mc)
    {
		mc.entityRenderer.setupOverlayRendering();
		GL11.glEnable(3042 /*GL_BLEND*/);
		GL11.glClearDepth(1.0);
		GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
		GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(goggles);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(0.0D, j, -90D, 0.0D, 1.0D);
        tessellator.addVertexWithUV(i, j, -90D, 1.0D, 1.0D);
        tessellator.addVertexWithUV(i, 0.0D, -90D, 1.0D, 0.0D);
        tessellator.addVertexWithUV(0.0D, 0.0D, -90D, 0.0D, 0.0D);
        tessellator.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
        GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(3042 /*GL_BLEND*/);
		
		if(!mc.gameSettings.hideGUI || mc.currentScreen != null)
        {
			int k = (Mouse.getX() * i) / mc.displayWidth;
	        int i1 = j - (Mouse.getY() * j) / mc.displayHeight - 1;
			mc.ingameGUI.renderGameOverlay(0.0F, mc.currentScreen != null, k, i1);
		}
	 }
	public void renderThings(EntityPlayer entityPlayer)
	{
		ScaledResolution scaledresolution = new ScaledResolution(minecraft.gameSettings, minecraft.displayWidth, minecraft.displayHeight);
	    int k = scaledresolution.getScaledWidth();
	    int l = scaledresolution.getScaledHeight();
	    short aqualungAir = entityPlayer.getEntityData().getShort("Aqualung");
		minecraft.renderEngine.bindTexture(Gui.icons);
	    /*GL11.glEnable(3042);
		GL11.glBlendFunc(775, 769);*/
		int l7 = l - 39;
		int k8 = l7 - 10;
		int k6 = k / 2 + 91;
		int k10 = (int)Math.ceil(((double)(aqualungAir - 2) * 10D) / 600D);
		int k11 = (int)Math.ceil(((double)aqualungAir * 10D) / 600D) - k10;
		if(minecraft.currentScreen == null){
        for(int j12 = 0; j12 < k10 + k11; j12++)
            {
                if(j12 < k10)
                {
                    drawTexturedModalRect(k6 - j12 * 8 - 9, k8 - 9, 16, 18, 9, 9);
                } else
                {
                    drawTexturedModalRect(k6 - j12 * 8 - 9, k8 - 9, 25, 18, 9, 9);
                }
            }
		}
	}
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
    {
        fontrenderer.drawStringWithShadow(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
    }
	
	public void drawString(FontRenderer fontrenderer, String s, int i, int j, int k)
    {
        fontrenderer.drawStringWithShadow(s, i, j, k);
    }
	
	public void renderOverlay(ItemStack helmetSlot) {
		if(minecraft.gameSettings.thirdPersonView==0 && helmetSlot.itemID == mod_Steamcraft.brassGoggles.itemID && minecraft.currentScreen == null)
        {
		   	ScaledResolution scaledresolution = new ScaledResolution(minecraft.gameSettings, minecraft.displayWidth, minecraft.displayHeight);
		   	renderOverlay(scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight(), minecraft);
		   	//if the player is wearing a helmet, perform the following actions
        }
	}
	public final static ResourceLocation goggles = new ResourceLocation("streamcraft","%blur%/misc/goggles.png");
}