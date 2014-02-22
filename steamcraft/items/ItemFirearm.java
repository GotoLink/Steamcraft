package steamcraft.items;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import steamcraft.EntityMusketBall;
import steamcraft.Steamcraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFirearm extends Item {
	private IIcon[] icons;

	public ItemFirearm() {
		super();
		setMaxStackSize(1);
		setFull3D();
		setHasSubtypes(true);
		setCreativeTab(Steamcraft.steamTab);
	}

	public ItemStack getAmmoA(ItemStack stack) {
		return new ItemStack(Steamcraft.part, 1, hasPercussion(stack) ? 1 : 0);
	}

	public ItemStack getAmmoB() {
		return new ItemStack(Steamcraft.part);
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		if (isRifled(stack)) {
			if (getFirePower(stack) > 10)
				return icons[0];
			else if (getFirePower(stack) > 8)
				return icons[1];
			else
				return icons[2];
		} else {
			if (getFirePower(stack) > 8)
				return icons[3];
			else if (getFirePower(stack) > 6)
				return icons[4];
			else
				return icons[5];
		}
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		if (stack.hasTagCompound()) {
			return stack.getTagCompound().getInteger("maxdmg");
		}
		return getMaxDamage();
	}

	@Override
	public int getRenderPasses(int metadata) {
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		par3List.add(Steamcraft.flintlockMusket);
		par3List.add(Steamcraft.matchlockMusket);
		par3List.add(Steamcraft.percussionCapMusket);
		par3List.add(Steamcraft.flintlockRifle);
		par3List.add(Steamcraft.matchlockRifle);
		par3List.add(Steamcraft.percussionCapRifle);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName() +"-"+ getMaxDamage(stack) + getFirePower(stack) +"-"+ (isRifled(stack)?"rifle":"musket");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (itemstack.getItemDamage() == 0 && !entityplayer.isInsideOfMaterial(Material.water)) {
			spawnBullet(itemstack, world, entityplayer);
		}
		return itemstack;
	}

	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity, int slot, boolean current) {
		if (current && entity instanceof EntityPlayer) {
			EntityPlayer entityPlayer = (EntityPlayer) entity;
			if (itemStack.getItem() instanceof ItemFirearm && itemStack.getItemDamage() > 0) {
				if (itemStack.getItemDamage() < itemStack.getMaxDamage() - 1) {
					itemStack.setItemDamage(itemStack.getItemDamage() - 1);
				}
				if (itemStack.getItemDamage() == itemStack.getMaxDamage() - 1) {
					ItemFirearm heldFirearm = (ItemFirearm) itemStack.getItem();
					if(heldFirearm.getAmmoA(itemStack).isItemEqual(heldFirearm.getAmmoB())){
						if (entityPlayer.inventory.hasItem(heldFirearm.getAmmoA(itemStack).getItem())) {
							itemStack.setItemDamage(itemStack.getItemDamage() - 1);
						}
					}
					else if (getStackPosition(entityPlayer.inventory, heldFirearm.getAmmoA(itemStack)) > -1
							&& getStackPosition(entityPlayer.inventory, heldFirearm.getAmmoB()) > -1) {
						if (entityPlayer.inventory.hasItem(heldFirearm.getAmmoA(itemStack).getItem()) && entityPlayer.inventory.hasItem(heldFirearm.getAmmoB().getItem())) {
							itemStack.setItemDamage(itemStack.getItemDamage() - 1);
						}
					}
				}
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		icons = new IIcon[6];
		icons[0] = par1IconRegister.registerIcon(getIconString() + "percussionrifle");
		icons[1] = par1IconRegister.registerIcon(getIconString() + "flintlockrifle");
		icons[2] = par1IconRegister.registerIcon(getIconString() + "matchlockrifle");
		icons[3] = par1IconRegister.registerIcon(getIconString() + "percussionmusket");
		icons[4] = par1IconRegister.registerIcon(getIconString() + "flintlockmusket");
		icons[5] = par1IconRegister.registerIcon(getIconString() + "matchlockmusket");
	}
	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	public static int getFirePower(ItemStack stack) {
		if (stack.hasTagCompound())
			return stack.getTagCompound().getInteger("power");
		else
			return 0;
	}

	public static int getStackPosition(InventoryPlayer inventory, ItemStack itemStack) {
		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			if (inventory.getStackInSlot(i) != null && itemStack.isItemEqual(inventory.getStackInSlot(i))) {
				return i;
			}
		}
		return -1;
	}

	public static boolean hasPercussion(ItemStack stack) {
		if (stack.hasTagCompound())
			return stack.getTagCompound().getBoolean("percussion");
		else
			return false;
	}

	public static boolean isRifled(ItemStack stack) {
		if (stack.hasTagCompound())
			return stack.getTagCompound().getBoolean("rifled");
		else
			return false;
	}

	public static void setFirePower(ItemStack stack, int power) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setInteger("power", power);
	}

	public static void setMaxDamage(ItemStack stack, int max) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setInteger("maxdmg", max);
	}

	public static void setPercussion(ItemStack stack) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setBoolean("percussion", true);
	}

	public static void setRifled(ItemStack stack) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setBoolean("rifled", true);
	}

	public static void spawnBullet(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		world.playSoundAtEntity(entityplayer, "mob.ghast.fireball", 0.8F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
		world.playSoundAtEntity(entityplayer, "random.explode", 0.4F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.9F));
		itemstack.setItemDamage(itemstack.getMaxDamage() - 1);
		if (!world.isRemote) {
			world.spawnEntityInWorld(new EntityMusketBall(world, entityplayer, getFirePower(itemstack), isRifled(itemstack)));
		}
	}
}
