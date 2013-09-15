package steamcraft.items;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import steamcraft.EntityMusketBall;

public class ItemFirearm extends Item
{
    public ItemFirearm(int i, int j, Item ammo, Item ammob, int k, boolean rifled)
    {
        super(i);
		setMaxDamage(j);
		Ammunition = ammo;
		Ammunitionb = ammob;
		isWeaponRifled = rifled;
		weaponPower = k;
        maxStackSize = 1;
    }
    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
		if(itemstack.getItemDamage() == 0 && entityplayer.isInsideOfMaterial(Material.water) == false){
		spawnBullet(itemstack, world, entityplayer);
		}
		return itemstack;
	}
	
	public void spawnBullet(ItemStack itemstack, World world, EntityPlayer entityplayer){
		world.playSoundAtEntity(entityplayer, "mob.ghast.fireball", 0.8F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
		world.playSoundAtEntity(entityplayer, "random.explode", 0.4F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.9F));
		itemstack.setItemDamage(itemstack.getMaxDamage()-1);
            if(!world.isRemote)
            {
                world.spawnEntityInWorld(new EntityMusketBall(world, entityplayer, weaponPower, isWeaponRifled));
            }
	}
	
	public int getFirePower(){
	return weaponPower;
	}
	
	public Item getAmmoA(){
	return Ammunition;
	}
	
	public Item getAmmoB(){
	return Ammunitionb;
	}
	
	public boolean getIsRifled(){
	return isWeaponRifled;
	}
	@Override
	public boolean isFull3D()
    {
        return true;
    }
	private Item Ammunition;
	private Item Ammunitionb;
	public int weaponPower;
	public boolean isWeaponRifled;
}
