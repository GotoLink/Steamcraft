package steamcraft;

import java.util.List;

import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityMusketBall extends Entity {
	public EntityMusketBall(World world) {
		super(world);
		xTile = -1;
		yTile = -1;
		zTile = -1;
		inTile = Blocks.air;
		damagePower = 4;
		inData = 0;
		doesArrowBelongToPlayer = false;
		arrowShake = 0;
		ticksInAir = 0;
		setSize(0.5F, 0.5F);
	}

	public EntityMusketBall(World world, double d, double d1, double d2) {
		this(world);
		setPosition(d, d1, d2);
		yOffset = 0.0F;
	}

	public EntityMusketBall(World world, EntityLivingBase entityliving, int power, boolean rifled) {
		super(world);
		xTile = -1;
		yTile = -1;
		zTile = -1;
		inTile = Blocks.air;
		damagePower = power;
		inData = 0;
		doesArrowBelongToPlayer = false;
		arrowShake = 0;
		ticksInAir = 0;
		isRifled = rifled;
		owner = entityliving;
		doesArrowBelongToPlayer = entityliving instanceof EntityPlayer;
		setSize(0.5F, 0.5F);
		setLocationAndAngles(entityliving.posX, entityliving.posY + entityliving.getEyeHeight(), entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
		posX -= MathHelper.cos((rotationYaw / 180F) * 3.141593F) * 0.16F;
		posY -= 0.10000000149011612D;
		posZ -= MathHelper.sin((rotationYaw / 180F) * 3.141593F) * 0.16F;
		setPosition(posX, posY, posZ);
		yOffset = 0.0F;
		motionX = -2 * MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
		motionZ = 2 * MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
		motionY = -2 * MathHelper.sin((rotationPitch / 180F) * 3.141593F);
		muzzleX = -0.5 * MathHelper.sin(((rotationYaw + 25) / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
		muzzleZ = 0.5 * MathHelper.cos(((rotationYaw + 25) / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
		world.spawnParticle("flame", entityliving.posX + muzzleX, entityliving.posY + 0.25 * motionY, entityliving.posZ + muzzleZ, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("smoke", entityliving.posX + muzzleX + 0.1D, entityliving.posY + 0.25 * motionY, entityliving.posZ + muzzleZ, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("smoke", entityliving.posX + muzzleX, entityliving.posY + 0.25 * motionY, entityliving.posZ + muzzleZ + 0.1D, 0.0D, 0.0D, 0.0D);
		setArrowHeading(motionX, motionY, motionZ, 1.0F, 1.0F);
	}

	@Override
	protected void entityInit() {
	}

	public void setArrowHeading(double d, double d1, double d2, float f, float f1) {
		float f2 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
		d /= f2;
		d1 /= f2;
		d2 /= f2;
		d += rand.nextGaussian() * 0.0074999998323619366D * f1;
		d1 += rand.nextGaussian() * 0.0074999998323619366D * f1;
		d2 += rand.nextGaussian() * 0.0074999998323619366D * f1;
		d *= f;
		d1 *= f;
		d2 *= f;
		motionX = d;
		motionY = d1;
		motionZ = d2;
		float f3 = MathHelper.sqrt_double(d * d + d2 * d2);
		prevRotationYaw = rotationYaw = (float) ((Math.atan2(d, d2) * 180D) / 3.1415927410125732D);
		prevRotationPitch = rotationPitch = (float) ((Math.atan2(d1, f3) * 180D) / 3.1415927410125732D);
	}

	@Override
	public void setVelocity(double d, double d1, double d2) {
		motionX = d;
		motionY = d1;
		motionZ = d2;
		if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt_double(d * d + d2 * d2);
			prevRotationYaw = rotationYaw = (float) ((Math.atan2(d, d2) * 180D) / 3.1415927410125732D);
			prevRotationPitch = rotationPitch = (float) ((Math.atan2(d1, f) * 180D) / 3.1415927410125732D);
			prevRotationPitch = rotationPitch;
			prevRotationYaw = rotationYaw;
			setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
		}
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
			prevRotationYaw = rotationYaw = (float) ((Math.atan2(motionX, motionZ) * 180D) / 3.1415927410125732D);
			prevRotationPitch = rotationPitch = (float) ((Math.atan2(motionY, f) * 180D) / 3.1415927410125732D);
		}
		Block i = worldObj.getBlock(xTile, yTile, zTile);
		if (i != Blocks.air) {
			i.setBlockBoundsBasedOnState(worldObj, xTile, yTile, zTile);
			AxisAlignedBB axisalignedbb = i.getCollisionBoundingBoxFromPool(worldObj, xTile, yTile, zTile);
			if (axisalignedbb != null && axisalignedbb.isVecInside(Vec3.createVectorHelper(posX, posY, posZ))) {
				setDead();
			}
		}
		if (arrowShake > 0) {
			arrowShake--;
		}
		ticksInAir++;
		Vec3 vec3d = Vec3.createVectorHelper(posX, posY, posZ);
		Vec3 vec3d1 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
		MovingObjectPosition movingobjectposition = worldObj.func_147447_a(vec3d, vec3d1, false, true, true);
		vec3d = Vec3.createVectorHelper(posX, posY, posZ);
		vec3d1 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
		if (movingobjectposition != null) {
			vec3d1 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
		}
		Entity entity = null;
		List<?> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
		double d = 0.0D;
		for (int l = 0; l < list.size(); l++) {
			Entity entity1 = (Entity) list.get(l);
			if (!entity1.canBeCollidedWith() || entity1 == owner && ticksInAir < 5) {
				continue;
			}
			float f4 = 0.3F;
			AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand(f4, f4, f4);
			MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec3d, vec3d1);
			if (movingobjectposition1 == null) {
				continue;
			}
			double d1 = vec3d.distanceTo(movingobjectposition1.hitVec);
			if (d1 < d || d == 0.0D) {
				entity = entity1;
				d = d1;
			}
		}
		if (entity != null) {
			movingobjectposition = new MovingObjectPosition(entity);
		}
		if (movingobjectposition != null) {
			if (movingobjectposition.entityHit != null) {
				if (movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, owner), damagePower)) {
					setDead();
				} else {
					motionX *= -0.10000000149011612D;
					motionY *= -0.10000000149011612D;
					motionZ *= -0.10000000149011612D;
					rotationYaw += 180F;
					prevRotationYaw += 180F;
					ticksInAir = 0;
				}
			} else {
				xTile = movingobjectposition.blockX;
				yTile = movingobjectposition.blockY;
				zTile = movingobjectposition.blockZ;
				inTile = worldObj.getBlock(xTile, yTile, zTile);
				inData = worldObj.getBlockMetadata(xTile, yTile, zTile);
				motionX = (float) (movingobjectposition.hitVec.xCoord - posX);
				motionY = (float) (movingobjectposition.hitVec.yCoord - posY);
				motionZ = (float) (movingobjectposition.hitVec.zCoord - posZ);
				float f1 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
				posX -= (motionX / f1) * 0.05000000074505806D;
				posY -= (motionY / f1) * 0.05000000074505806D;
				posZ -= (motionZ / f1) * 0.05000000074505806D;
				worldObj.spawnParticle("smoke", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
				setDead();
				arrowShake = 7;
			}
		}
		posX += motionX;
		posY += motionY;
		posZ += motionZ;
		worldObj.spawnParticle("smoke", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
		float f2 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
		rotationYaw = (float) ((Math.atan2(motionX, motionZ) * 180D) / 3.1415927410125732D);
		for (rotationPitch = (float) ((Math.atan2(motionY, f2) * 180D) / 3.1415927410125732D); rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) {
		}
		for (; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) {
		}
		for (; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) {
		}
		for (; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) {
		}
		rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
		rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
		float f3 = 0.99F;
		float f5 = 0.03F;
		if (isRifled) {
			f5 = 0.001F;
		}
		if (isInWater()) {
			for (int i1 = 0; i1 < 4; i1++) {
				float f6 = 0.25F;
				worldObj.spawnParticle("bubble", posX - motionX * f6, posY - motionY * f6, posZ - motionZ * f6, motionX, motionY, motionZ);
			}
			f3 = 0.8F;
		}
		motionX *= f3;
		motionY *= f3;
		motionZ *= f3;
		motionY -= f5;
		setPosition(posX, posY, posZ);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setShort("xTile", (short) xTile);
		nbttagcompound.setShort("yTile", (short) yTile);
		nbttagcompound.setShort("zTile", (short) zTile);
		nbttagcompound.setInteger("inTile", GameData.blockRegistry.getId(inTile));
		nbttagcompound.setByte("inData", (byte) inData);
		nbttagcompound.setByte("shake", (byte) arrowShake);
		nbttagcompound.setBoolean("player", doesArrowBelongToPlayer);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		xTile = nbttagcompound.getShort("xTile");
		yTile = nbttagcompound.getShort("yTile");
		zTile = nbttagcompound.getShort("zTile");
		inTile = GameData.blockRegistry.get(nbttagcompound.getInteger("inTile"));
		inData = nbttagcompound.getByte("inData") & 0xff;
		arrowShake = nbttagcompound.getByte("shake") & 0xff;
		doesArrowBelongToPlayer = nbttagcompound.getBoolean("player");
	}

	@Override
	public float getShadowSize() {
		return 0.0F;
	}

	private int xTile;
	private int yTile;
	private int zTile;
	private double muzzleX;
	private double muzzleZ;
	private Block inTile;
	private int inData;
	public boolean doesArrowBelongToPlayer;
	public boolean isRifled;
	public int arrowShake;
	public int damagePower;
	public EntityLivingBase owner;
	private int ticksInAir;
}
