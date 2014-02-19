package steamcraft.inventories;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import steamcraft.TileEntitySteamFurnace;

public class GuiSteamFurnace extends GuiContainer {
	public GuiSteamFurnace(InventoryPlayer inventoryplayer, TileEntitySteamFurnace tileentitysteamfurnace) {
		super(new ContainerSteamFurnace(inventoryplayer, tileentitysteamfurnace));
		furnaceInventory = tileentitysteamfurnace;
	}

	@Override
	protected void func_146979_b(int i, int j) {
        field_146289_q.drawString("Steam Furnace", 60, 6, 0x404040);
        field_146289_q.drawString("Inventory", 8, (field_147000_g - 96) + 2, 0x404040);
	}

	@Override
	protected void func_146976_a(float f, int i, int j) {
        field_146297_k.renderEngine.bindTexture(gui);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(field_147003_i, field_147009_r, 0, 0, field_146999_f, field_147000_g);
		if (furnaceInventory.isBurning()) {
			int l = furnaceInventory.getBurnTimeLeftScaled(12);
			drawTexturedModalRect(field_147003_i + 56, (field_147009_r + 36 + 12) - l, 176, 12 - l, 14, l + 2);
		}
		int i1 = furnaceInventory.getCookProgressScaled(24);
		drawTexturedModalRect(field_147003_i + 79, field_147009_r + 34, 176, 14, i1 + 1, 16);
		int i2 = furnaceInventory.getWaterScaled(31);
		drawTexturedModalRect(field_147003_i + 32, (field_147009_r + 16 + 31) - i2, 176, (31 + 31 - i2), 8, i2 + 2);
	}

	private TileEntitySteamFurnace furnaceInventory;
	private static final ResourceLocation gui = new ResourceLocation("steamcraft", "textures/gui/steamfurnace.png");
}
