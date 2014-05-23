package steamcraft.items;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import steamcraft.HandlerRegistry;
import steamcraft.Steamcraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSCArmor extends ItemArmor {
	public final static ResourceLocation goggles = new ResourceLocation("steamcraft", "misc/goggles.png");
	private static Item goggle = null;

	public ItemSCArmor(ArmorMaterial j, int k, int l) {
		super(j, k, l);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		ItemSCArmor item = (ItemSCArmor) stack.getItem();
		return String.format("steamcraft:textures/models/armor/%s_layer_%d.png", Steamcraft.ARMOR_NAMES[item.renderIndex], (slot == 2 ? 2 : 1));
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		if (itemStack.getItem() == getBraces()) {
			if (player.fallDistance > 0.0F) {
				player.fallDistance -= 0.5F;
			}
			return;
		}
		if (itemStack.getItem() == getSkates()) {
			if (!player.isInsideOfMaterial(Material.water) && !player.isInWater() && player.onGround) {
				player.moveEntityWithHeading(player.moveStrafing, player.moveForward * 0.8F);
				player.stepHeight = 0.0F;
			}
			return;
		}
		if (itemStack.getItem() == getAqualung()) {
			if (!player.isInsideOfMaterial(Material.water)) {
				player.getEntityData().setShort("Aqualung", (short) 600);
			} else if (player.getAir() == 0) {
				itemStack.damageItem(1, player);
			}
			if (player.getEntityData().getShort("Aqualung") > 0 && player.isInsideOfMaterial(Material.water) && player.isEntityAlive()) {
				player.getEntityData().setShort("Aqualung", (short) decreaseAirSupply(player, player.getEntityData().getShort("Aqualung")));
				player.setAir(300);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution, float partialTicks, boolean hasScreen, int mouseX, int mouseY) {
		if (stack.getItem() == getGoggle() && !hasScreen) {//if the player is wearing goggles, perform the following actions
			GL11.glEnable(3042 /* GL_BLEND */);
			GL11.glClearDepth(1.0);
			GL11.glDisable(2929 /* GL_DEPTH_TEST */);
			GL11.glDisable(3008 /* GL_ALPHA_TEST */);
			GL11.glDepthMask(false);
			GL11.glBlendFunc(770, 771);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			Minecraft.getMinecraft().getTextureManager().bindTexture(goggles);
			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(0.0D, resolution.getScaledHeight(), -90D, 0.0D, 1.0D);
			tessellator.addVertexWithUV(resolution.getScaledWidth(), resolution.getScaledHeight(), -90D, 1.0D, 1.0D);
			tessellator.addVertexWithUV(resolution.getScaledWidth(), 0.0D, -90D, 1.0D, 0.0D);
			tessellator.addVertexWithUV(0.0D, 0.0D, -90D, 0.0D, 0.0D);
			tessellator.draw();
			GL11.glDepthMask(true);
			GL11.glEnable(2929 /* GL_DEPTH_TEST */);
			GL11.glEnable(3008 /* GL_ALPHA_TEST */);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(3042 /* GL_BLEND */);
		}
	}

	public static Item getAqualung() {
		return HandlerRegistry.getItem("steamcraft:aqualung").get();
	}

	public static Item getBraces() {
		return HandlerRegistry.getItem("steamcraft:legBraces").get();
	}

	public static Item getSkates() {
		return HandlerRegistry.getItem("steamcraft:rollerSkates").get();
	}

	protected static int decreaseAirSupply(EntityPlayer entityPlayer, int par1) {
		int j = EnchantmentHelper.getRespiration(entityPlayer);
		return j > 0 && new Random().nextInt(j + 1) > 0 ? par1 : par1 - 1;
	}

	private static Item getGoggle() {
		if (goggle == null)
			goggle = HandlerRegistry.getItem("steamcraft:brassGoggles").get();
		return goggle;
	}
}
