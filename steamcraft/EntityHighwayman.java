package steamcraft;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import steamcraft.items.ItemFirearm;

public class EntityHighwayman extends EntityMob
{
    public EntityHighwayman(World world)
    {
        super(world);
		if(heldItemNumber == -1){
			heldItemNumber = rand.nextInt(10);
		}
    }
    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth).setAttribute(10);
    }
    @Override
    protected String getHurtSound()
    {
        return "random.hurt";
    }
    @Override
    protected String getDeathSound()
    {
        return "random.hurt";
    }
    @Override
	protected Entity findPlayerToAttack()
    {
		EntityPlayer entityplayer = (EntityPlayer)super.findPlayerToAttack();
        if(entityplayer != null && canEntityBeSeen(entityplayer) && !entityplayer.isDead)
        {
			if(rand.nextInt(2) == 0){
				entityplayer.addChatMessage("Highwayman: Your money or your life!");
			}else{
				entityplayer.addChatMessage("Highwayman: Stand and deliver!");
			}
            return entityplayer;
        } else
        {
            return null;
      }
    }
    @Override
    protected void attackEntity(Entity entity, float f)
    {
        if(f < 10F)
        {
            double d = entity.posX - posX;
            double d1 = entity.posZ - posZ;
            if(attackTime == 0)
            {
                EntityMusketBall entityarrow = new EntityMusketBall(worldObj, this, ((ItemFirearm)defaultHeldItem.getItem()).getFirePower(), ((ItemFirearm)defaultHeldItem.getItem()).getIsRifled());
                entityarrow.posY += 1.3999999761581421D;
                double d2 = (entity.posY + (double)entity.getEyeHeight()) - 0.20000000298023224D - entityarrow.posY;
                float f1 = MathHelper.sqrt_double(d * d + d1 * d1) * 0.2F;
                worldObj.playSoundAtEntity(this, "mob.ghast.fireball", 0.8F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));
				worldObj.playSoundAtEntity(this, "random.explode", 0.4F, 1.0F / (rand.nextFloat() * 0.4F + 0.9F));
                worldObj.spawnEntityInWorld(entityarrow);
                entityarrow.setArrowHeading(d, d2 + (double)f1, d1, 0.6F, 12F);
                attackTime = defaultHeldItem.getMaxDamage();
            }
            rotationYaw = (float)((Math.atan2(d1, d) * 180D) / 3.1415927410125732D) - 90F;
            hasAttacked = true;
        }
    }
    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setShort("weaponNumber", (short)heldItemNumber);
    }
    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
		heldItemNumber = nbttagcompound.getShort("weaponNumber");
    }
    @Override
	public void onStruckByLightning(EntityLightningBolt entitylightningbolt)
    {
        if(!worldObj.isRemote)
        {
            EntityZombie entityzombie = new EntityZombie(worldObj);
            entityzombie.setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
            worldObj.spawnEntityInWorld(entityzombie);
            setDead();
        }
    }
    @Override
    protected int getDropItemId()
    {
		return percussion?mod_Steamcraft.percussionRound.itemID:mod_Steamcraft.musketRound.itemID;
    }
    @Override
	protected void dropFewItems(boolean flag,int fortune)
    {
		/*if(heldItemNumber == -1){
		heldItemNumber = rand.nextInt(10);
		}
		if(heldItemNumber >= 2 && heldItemNumber < 4){
		defaultHeldItem = new ItemStack(mod_Steamcraft.flintlockRifle, 1);
		rifled = true;
		percussion = false;
		}else
		if(heldItemNumber >= 4 && heldItemNumber < 6){
		defaultHeldItem = new ItemStack(mod_Steamcraft.matchlockMusket, 1);
		rifled = false;
		percussion = false;
		}else
		if(heldItemNumber >= 6 && heldItemNumber < 8){
		defaultHeldItem = new ItemStack(mod_Steamcraft.matchlockRifle, 1);
		rifled = true;
		percussion = false;
		}else
		if(heldItemNumber == 8){
		defaultHeldItem = new ItemStack(mod_Steamcraft.percussionCapMusket, 1);
		rifled = false;
		percussion = true;
		}else
		if(heldItemNumber == 9){
		defaultHeldItem = new ItemStack(mod_Steamcraft.percussionCapRifle, 1);
		rifled = true;
		percussion = true;
		}else{
		defaultHeldItem = new ItemStack(mod_Steamcraft.flintlockMusket, 1);
		rifled = false;
		percussion = false;
		}*/
		
		if(!setDrops){
		//System.out.println("Is Percussion:" + percussion);
		//System.out.println("Is Rifle:" + rifled);
		int i = rand.nextInt(8);
        for(int j = 0; j < i; j++)
        {
		dropItem(mod_Steamcraft.musketRound.itemID, 1);
        }
		if(percussion){
		i = rand.nextInt(6);
        for(int j = 0; j < i; j++)
        {
		dropItem(mod_Steamcraft.percussionRound.itemID, 1);
		}
		}
		
		int p = rand.nextInt(32);
		if(p < 4){
            dropItem(defaultHeldItem.getItem().itemID, 1);
        }
		if(p >= 2 && p < 8){
		i = rand.nextInt(4);
        for(int j = 0; j < i; j++)
        {
            dropItem(Item.ingotGold.itemID, 1);
        }
		}
		if(p == 8 || p == 1){
		i = rand.nextInt(2);
        for(int j = 0; j < i; j++)
        {
            dropItem(Item.diamond.itemID, 1);
        }
		}
		setDrops = true;
		}

    }
	
    @Override
    public ItemStack getHeldItem()
    {
       return defaultHeldItem;
    }
   
	public ItemStack setHeldItem()
	{
		if(heldItemNumber == -1){
			heldItemNumber = rand.nextInt(10);
		}
		//System.out.println(heldItemNumber);
	if(heldItemNumber >= 2 && heldItemNumber < 4){
		rifled = true;
		percussion = false;
		return heldItems[1];
		}else
		if(heldItemNumber >= 4 && heldItemNumber < 6){
		rifled = false;
		percussion = false;
		return heldItems[2];
		}else
		if(heldItemNumber >= 6 && heldItemNumber < 8){
		rifled = true;
		percussion = false;
		return heldItems[3];
		}else
		if(heldItemNumber == 8){
		rifled = false;
		percussion = true;
		return heldItems[4];
		}else
		if(heldItemNumber == 9){
		rifled = true;
		percussion = true;
		return heldItems[5];
		}else{
		rifled = false;
		percussion = false;
		return heldItems[0];
		}
	}
	
	public boolean setDrops = false;
	public int heldItemNumber = -1;
	public boolean rifled = false;
	public boolean percussion = false;
    private ItemStack defaultHeldItem = setHeldItem();
	/*public int heldItemRand = -1;
	public static int heldItemNumber = -1;*/
	public static ItemStack heldItems[] = new ItemStack[]{ new ItemStack(mod_Steamcraft.flintlockMusket, 1), new ItemStack(mod_Steamcraft.flintlockRifle, 1), 
	new ItemStack(mod_Steamcraft.matchlockMusket, 1), new ItemStack(mod_Steamcraft.matchlockRifle, 1), 
	new ItemStack(mod_Steamcraft.percussionCapMusket, 1), new ItemStack(mod_Steamcraft.percussionCapRifle, 1)};

	/*public static void setHeldItem(Item item, Item item2, Item item3, Item item4, Item item5, Item item6){
		if(heldItemNumber >= 2 && heldItemNumber < 4){
		defaultHeldItem = new ItemStack(item2, 1);
		rifled = true;
		percussion = false;
		}else
		if(heldItemNumber >= 4 && heldItemNumber < 6){
		defaultHeldItem = new ItemStack(item3, 1);
		rifled = false;
		percussion = false;
		}else
		if(heldItemNumber >= 6 && heldItemNumber < 8){
		defaultHeldItem = new ItemStack(item4, 1);
		rifled = true;
		percussion = false;
		}else
		if(heldItemNumber == 8){
		defaultHeldItem = new ItemStack(item5, 1);
		rifled = false;
		percussion = true;
		}else
		if(heldItemNumber == 9){
		defaultHeldItem = new ItemStack(item6, 1);
		rifled = true;
		percussion = true;
		}else{
		defaultHeldItem = new ItemStack(item, 1);
		rifled = false;
		percussion = false;
		}
	}*/
}
