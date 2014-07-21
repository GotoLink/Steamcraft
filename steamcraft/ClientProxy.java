package steamcraft;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.IBlockAccess;
import steamcraft.blocks.BlockCopperWire;
import steamcraft.blocks.BlockTeaPlant;
import steamcraft.render.RenderCopperWire;
import steamcraft.render.RenderHighwayman;
import steamcraft.render.RenderMusketBall;
import steamcraft.render.RenderTeaPlant;
import steamcraft.render.TileEntityLampRenderer;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy implements ISimpleBlockRenderingHandler {
	public static final Minecraft minecraft = Minecraft.getMinecraft();
    public static final RenderCopperWire copperRender = new RenderCopperWire();
    public static final RenderTeaPlant teaRender = new RenderTeaPlant();

	@Override
	public int getRenderId() {
		return -1;
	}

	@Override
	public void registerRenderers() {
		BlockCopperWire.modelID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(BlockCopperWire.modelID, this);
		BlockTeaPlant.modelID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(BlockTeaPlant.modelID, this);
		RenderingRegistry.registerEntityRenderingHandler(EntityMusketBall.class, new RenderMusketBall());
		RenderingRegistry.registerEntityRenderingHandler(EntityHighwayman.class, new RenderHighwayman(new ModelBiped(), new ModelBiped(0.5F), 0.5F));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLamp.class, new TileEntityLampRenderer());
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		if (modelId == BlockCopperWire.modelID) {
			return copperRender.renderBlockCopperWire(block, x, y, z, world);
		}
		if (modelId == BlockTeaPlant.modelID) {
			return teaRender.renderBlockTeaPlant(block, x, y, z, world);
		}
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory(int i) {
		return false;
	}

	@SubscribeEvent
	public void tickRender(TickEvent.RenderTickEvent event) {
		if (event.phase == TickEvent.Phase.END)
			onRenderTick();
	}

	public static void drawTexturedModalRect(int i, int j, int k, int l, int i1, int j1) {
		float f = 0.00390625F;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(i + 0, j + j1, 0.0F, (k + 0) * f, (l + j1) * f);
		tessellator.addVertexWithUV(i + i1, j + j1, 0.0F, (k + i1) * f, (l + j1) * f);
		tessellator.addVertexWithUV(i + i1, j + 0, 0.0F, (k + i1) * f, (l + 0) * f);
		tessellator.addVertexWithUV(i + 0, j + 0, 0.0F, (k + 0) * f, (l + 0) * f);
		tessellator.draw();
	}

	public static void onRenderTick() {
		if (minecraft.thePlayer != null && minecraft.theWorld != null) {
			if (minecraft.thePlayer.getEntityData().getShort("Aqualung") > 0 && minecraft.thePlayer.isInsideOfMaterial(Material.water) && minecraft.thePlayer.isEntityAlive()) {
				renderThings(minecraft.thePlayer);
			}
		}
	}

	public static void renderThings(EntityPlayer entityPlayer) {
		ScaledResolution scaledresolution = new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
		int k = scaledresolution.getScaledWidth();
		int l = scaledresolution.getScaledHeight();
		short aqualungAir = entityPlayer.getEntityData().getShort("Aqualung");
		minecraft.renderEngine.bindTexture(Gui.icons);
		/*
		 * GL11.glEnable(3042); GL11.glBlendFunc(775, 769);
		 */
		int l7 = l - 39;
		int k8 = l7 - 10;
		int k6 = k / 2 + 91;
		int k10 = (int) Math.ceil(((aqualungAir - 2) * 10D) / 600D);
		int k11 = (int) Math.ceil((aqualungAir * 10D) / 600D) - k10;
		if (minecraft.currentScreen == null) {
			for (int j12 = 0; j12 < k10 + k11; j12++) {
				if (j12 < k10) {
					drawTexturedModalRect(k6 - j12 * 8 - 9, k8 - 9, 16, 18, 9, 9);
				} else {
					drawTexturedModalRect(k6 - j12 * 8 - 9, k8 - 9, 25, 18, 9, 9);
				}
			}
		}
	}
}
