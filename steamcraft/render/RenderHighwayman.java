package steamcraft.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import steamcraft.EntityHighwayman;

public final class RenderHighwayman extends RenderBiped {
	public static final ResourceLocation cloak = new ResourceLocation("steamcraft", "textures/mob/highwaymancloak.png");
	public static final ResourceLocation man = new ResourceLocation("steamcraft", "textures/mob/highwayman.png");

	public RenderHighwayman(ModelBase modelbase, ModelBase modelbase1, float f) {
		super((ModelBiped) modelbase, f);
		setRenderPassModel(modelbase1);
	}

	protected void renderSpecials(EntityHighwayman entityplayer, int i, float f) {
		renderManager.renderEngine.bindTexture(cloak);
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 0.0F, 0.125F);
		double d = (entityplayer.posX + (entityplayer.prevPosX - entityplayer.posX) * f) - (entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * f);
		double d1 = (entityplayer.posY + (entityplayer.prevPosY - entityplayer.posY) * f) - (entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) * f);
		double d2 = (entityplayer.posZ + (entityplayer.prevPosZ - entityplayer.posZ) * f) - (entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * f);
		float f8 = entityplayer.prevRenderYawOffset + (entityplayer.renderYawOffset - entityplayer.prevRenderYawOffset) * f / 2;
		double d3 = MathHelper.sin((f8 * 3.141593F) / 180F);
		double d4 = -MathHelper.cos((f8 * 3.141593F) / 180F);
		float f9 = (float) d1 * 10F;
		if (f9 < -6F) {
			f9 = -6F;
		}
		if (f9 > 32F) {
			f9 = 32F;
		}
		float f10 = (float) (d * d3 + d2 * d4) * 100F;
		float f11 = (float) (d * d4 - d2 * d3) * 100F;
		if (f10 < 0.0F) {
			f10 = 0.0F;
		}
		f9 += MathHelper.sin((entityplayer.prevDistanceWalkedModified + (entityplayer.distanceWalkedModified - entityplayer.prevDistanceWalkedModified) * f) * 6F) * 32F;
		if (entityplayer.isSneaking()) {
			f9 += 25F;
		}
		GL11.glRotatef(6F + f10 / 2.0F + f9 / 2, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(f11 / 2.0F, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(-f11 / 2.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
		modelBipedMain.renderCloak(0.0625F);
		GL11.glPopMatrix();
		renderManager.renderEngine.bindTexture(man);
	}

	@Override
	protected int shouldRenderPass(EntityLivingBase entityliving, int i, float f) {
		renderSpecials((EntityHighwayman) entityliving, i, f);
		return -1;
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLiving par1EntityLiving) {
		return man;
	}
}
