package steamcraft.blocks;

import java.util.Random;

import net.minecraft.block.BlockFurnace;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import steamcraft.TileEntityNukeFurnace;
import steamcraft.mod_Steamcraft;
import steamcraft.inventories.GuiNukeFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockNukeFurnace extends BlockFurnace
{
    public BlockNukeFurnace(int i, boolean flag)
    {
        super(i, flag/* Material.iron*/);
		setTickRandomly(true);
    }
    @Override
	public int tickRate(World world)
    {
        return 40;
    }
	
	@Override
    public int idDropped(int i, Random random, int j)
    {
        return mod_Steamcraft.nukeOvenIdle.blockID;
    }

    @Override
    @SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
	    this.blockIcon = par1IconRegister.registerIcon("steamcraft:nukefurnaceside");
	    this.furnaceIconFront = par1IconRegister.registerIcon(this.isActive ? "nukefurnaceactive" : "nukefurnaceidle");
	    this.furnaceIconTop = par1IconRegister.registerIcon("nukefurnacetop");
	}
    @Override
    public void randomDisplayTick(World world, int i, int j, int k, Random random)
    {
        if(!isActive)
        {
            return;
        }
        int l = world.getBlockMetadata(i, j, k);
        float f = (float)i + 0.5F;
        float f1 = (float)j + 0.0F + (random.nextFloat() * 6F) / 16F;
        float f2 = (float)k + 0.5F;
        float f3 = 0.52F;
        float f4 = random.nextFloat() * 0.6F - 0.3F;
        if(l == 4)
        {
            world.spawnParticle("reddust", f - f3, f1, f2 + f4, -1.0D, 1.0D, 0.0D);
        } else
        if(l == 5)
        {
            world.spawnParticle("reddust", f + f3, f1, f2 + f4, -1.0D, 1.0D, 0.0D);
        } else
        if(l == 2)
        {
            world.spawnParticle("reddust", f + f4, f1, f2 - f3, -1.0D, 1.0D, 0.0D);
        } else
        if(l == 3)
        {
            world.spawnParticle("reddust", f + f4, f1, f2 + f3, -1.0D, 1.0D, 0.0D);
        }
		world.spawnParticle("reddust", f, f1+0.6F, f2, 0.0D, 0.0D, 1.0D);
		world.spawnParticle("reddust", f, f1+0.6F, f2, 0.0D, 0.0D, 1.0D);
        world.scheduleBlockUpdate(i, j, k, blockID, tickRate(world));
    }
    @Override
    public Icon getBlockTextureFromSide(int i)
    {
         if(i == 1)
        {
            return topIndexInTexture;
        }
        if(i == 0)
        {
            return baseIndexInTexture;
        }
        if(i == 3)
        {
			return blockIndexInTexture;
        } else
        {
            return sideIndexInTexture;
        }
    }

    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
        if(world.isRemote)
        {
            return true;
        }
        TileEntityNukeFurnace tileentitynukefurnace = (TileEntityNukeFurnace)world.getBlockTileEntity(i, j, k);
		if(tileentitynukefurnace != null)
        {
            entityplayer.openGui(mod_Steamcraft.instance, 2,world, j,k, k);
		}
        return true;
    }

    public static void updateFurnaceBlockState(boolean flag, World world, int i, int j, int k)
    {
        int l = world.getBlockMetadata(i, j, k);
        TileEntity tileentity = world.getBlockTileEntity(i, j, k);
		keepFurnaceInventory = true;
        if(flag)
        {
			world.playSoundEffect((float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, "mob.ghast.fireball", 1.0F, 0.8F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F);
			world.playSoundEffect((float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, "mob.zombiepig.zpigdeath", 0.1F, 0.1F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.6F);
			world.playSoundEffect((float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, "fire.ignite", 1.5F, 1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
            world.setBlock(i, j, k, mod_Steamcraft.nukeOvenActive.blockID);
        } else
        {
			world.playSoundEffect((float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, "ambient.cave.cave", 0.1F, 0.1F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
            world.setBlock(i, j, k, mod_Steamcraft.nukeOvenIdle.blockID);
        }
		keepFurnaceInventory = false;
        world.setBlockMetadataWithNotify(i, j, k, l, 3);
		if(tileentity != null)
        {
		tileentity.validate();
        world.setBlockTileEntity(i, j, k, tileentity);
		}
    }

    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving)
    {
        int l = MathHelper.floor_double((double)((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
        if(l == 0)
        {
            world.setBlockMetadataWithNotify(i, j, k, 2, 2);
        }
        if(l == 1)
        {
            world.setBlockMetadataWithNotify(i, j, k, 5, 2);
        }
        if(l == 2)
        {
            world.setBlockMetadataWithNotify(i, j, k, 3, 2);
        }
        if(l == 3)
        {
            world.setBlockMetadataWithNotify(i, j, k, 4, 2);
        }
    }
	public static void meltdown(World world, int i, int j, int k){
		//world.playSoundEffect((float)i, (float)j, (float)k, "ambient.weather.thunder", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.9F);
		ModLoader.getMinecraftInstance().thePlayer.triggerAchievement(mod_Steamcraft.ach_RuinedEverything);
		world.createExplosion(null, i, j, k, 25F,false);
	    double d = (double)((float)i + 0.5F) + (double)(0.5F) * 2.0000000000000001D;
	    double d1 = (double)((float)j + 0.7F) + (double)(0.5F) * 2.0000000000000001D;
	    double d2 = (double)((float)k + 0.5F) + (double)(0.5F) * 2.0000000000000001D;
		world.spawnParticle("reddust", d, d1, d2, -1.0D, 1.0D, 0.0D);
	}
	
	public void spawnSmoke(World world, int i, int j, int k, Random random){
		double d = (double)((float)i + 0.5F) + (double)(random.nextFloat() - 0.5F);
	    double d1 = (double)((float)j + 0.7F) + (double)(random.nextFloat() - 0.3F);
	    double d2 = (double)((float)k + 0.5F) + (double)(random.nextFloat() - 0.5F);
		world.spawnParticle("smoke", d, d1, d2, 0.0D, 0.0D, 0.0D);
	}
	@Override
	public void updateTick(World world, int i, int j, int k, Random random)
    {
		TileEntityNukeFurnace tileentityfurnace = (TileEntityNukeFurnace)world.getBlockTileEntity(i, j, k);
		int i1 = world.getBlockId(i+1, j, k);
		int i2 = world.getBlockId(i-1, j, k);
		int j1 = world.getBlockId(i, j+1, k);
		int j2 = world.getBlockId(i, j-1, k);
		int k1 = world.getBlockId(i, j, k+1);
		int k2 = world.getBlockId(i, j, k-1);
		if(tileentityfurnace.getHeat() >= 1000){
		if(i1 == waterStill.blockID){
		tileentityfurnace.addHeat(-10);
			world.setBlock(i+1, j, k, 0, world.getBlockMetadata(i+1, j, k), 3);
			world.playSoundEffect((float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
			spawnSmoke(world, i+1, j, k, random);
			spawnSmoke(world, i+1, j, k, random);
			spawnSmoke(world, i+1, j, k, random);
			spawnSmoke(world, i+1, j, k, random);
		}
		if(i2 == waterStill.blockID){
			tileentityfurnace.addHeat(-10);
			world.setBlock(i-1, j, k, 0, world.getBlockMetadata(i-1, j, k), 3);
			world.playSoundEffect((float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
			spawnSmoke(world, i-1, j, k, random);
			spawnSmoke(world, i-1, j, k, random);
			spawnSmoke(world, i-1, j, k, random);
			spawnSmoke(world, i-1, j, k, random);
		}
		if(j1 == waterStill.blockID){
			tileentityfurnace.addHeat(-10);
			world.setBlock(i, j+1, k, 0, world.getBlockMetadata(i, j+1, k), 3);
			world.playSoundEffect((float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
			spawnSmoke(world, i, j+1, k, random);
			spawnSmoke(world, i, j+1, k, random);
			spawnSmoke(world, i, j+1, k, random);
			spawnSmoke(world, i, j+1, k, random);
		}
		if(k1 == waterStill.blockID){
			tileentityfurnace.addHeat(-10);
			world.setBlock(i, j, k+1, 0, world.getBlockMetadata(i, j, k+1), 3);
			world.playSoundEffect((float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
			spawnSmoke(world, i, j, k+1, random);
			spawnSmoke(world, i, j, k+1, random);
			spawnSmoke(world, i, j, k+1, random);
			spawnSmoke(world, i, j, k+1, random);
		}
		if(k2 == waterStill.blockID){
			tileentityfurnace.addHeat(-10);
			world.setBlock(i, j, k-1, 0, world.getBlockMetadata(i, j, k-1), 3);
			world.playSoundEffect((float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
			spawnSmoke(world, i, j, k-1, random);
			spawnSmoke(world, i, j, k-1, random);
			spawnSmoke(world, i, j, k-1, random);
			spawnSmoke(world, i, j, k-1, random);
		}
		if(i1 == waterMoving.blockID){
			tileentityfurnace.addHeat(-5);
			world.setBlock(i+1, j, k, 0, world.getBlockMetadata(i+1, j, k), 3);
			world.playSoundEffect((float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
			spawnSmoke(world, i+1, j, k, random);
			spawnSmoke(world, i+1, j, k, random);
			spawnSmoke(world, i+1, j, k, random);
			spawnSmoke(world, i+1, j, k, random);
		}
		if(i2 == waterMoving.blockID){
			tileentityfurnace.addHeat(-5);
			world.setBlock(i-1, j, k, 0, world.getBlockMetadata(i-1, j, k), 3);
			world.playSoundEffect((float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
			spawnSmoke(world, i-1, j, k, random);
			spawnSmoke(world, i-1, j, k, random);
			spawnSmoke(world, i-1, j, k, random);
			spawnSmoke(world, i-1, j, k, random);
		}
		if(j1 == waterMoving.blockID){
			tileentityfurnace.addHeat(-5);
			world.setBlock(i, j+1, k, 0, world.getBlockMetadata(i, j+1, k), 3);
			world.playSoundEffect((float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
			spawnSmoke(world, i, j+1, k, random);
			spawnSmoke(world, i, j+1, k, random);
			spawnSmoke(world, i, j+1, k, random);
			spawnSmoke(world, i, j+1, k, random);
		}
		if(k1 == waterMoving.blockID){
			tileentityfurnace.addHeat(-5);
			world.setBlock(i, j, k+1, 0, world.getBlockMetadata(i, j, k+1), 3);
			world.playSoundEffect((float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
			spawnSmoke(world, i, j, k+1, random);
			spawnSmoke(world, i, j, k+1, random);
			spawnSmoke(world, i, j, k+1, random);
			spawnSmoke(world, i, j, k+1, random);
		}
		if(k2 == waterMoving.blockID){
			tileentityfurnace.addHeat(-5);
			world.setBlock(i, j, k-1, 0, world.getBlockMetadata(i, j, k-1), 3);
			world.playSoundEffect((float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
			spawnSmoke(world, i, j, k-1, random);
			spawnSmoke(world, i, j, k-1, random);
			spawnSmoke(world, i, j, k-1, random);
			spawnSmoke(world, i, j, k-1, random);
		}
	}
		if(i1 == lavaStill.blockID || i1 == lavaMoving.blockID || i2 == lavaStill.blockID || i2 == lavaMoving.blockID || j1 == lavaStill.blockID || j1 == lavaMoving.blockID || j2 == lavaStill.blockID || j2 == lavaMoving.blockID || k1 == lavaStill.blockID || k1 == lavaMoving.blockID || k2 == lavaStill.blockID || k2 == lavaMoving.blockID){
			tileentityfurnace.addHeat(160);
		}
		if(i1 == fire.blockID || i2 == fire.blockID || j2 == fire.blockID || k1 == fire.blockID || k2 == fire.blockID){
			tileentityfurnace.addHeat(80);
		}
		world.scheduleBlockUpdate(i, j, k, blockID, tickRate(world));
	}
	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        super.onNeighborBlockChange(world, i, j, k, l);
        world.scheduleBlockUpdate(i, j, k, blockID, tickRate(world));
    }
	
	public void onBlockRemoval(World world, int i, int j, int k)
    {
        if(!keepFurnaceInventory)
        {
            TileEntityNukeFurnace tileentityfurnace = (TileEntityNukeFurnace)world.getBlockTileEntity(i, j, k);
			 if(tileentityfurnace != null)
            {
				 label0:
            for(int l = 0; l < tileentityfurnace.getSizeInventory(); l++)
            {
                ItemStack itemstack = tileentityfurnace.getStackInSlot(l);
                if(itemstack == null)
                {
                    continue;
                }
                float f = furnaceRand.nextFloat() * 0.8F + 0.1F;
                float f1 = furnaceRand.nextFloat() * 0.8F + 0.1F;
                float f2 = furnaceRand.nextFloat() * 0.8F + 0.1F;
                do
                {
                    if(itemstack.stackSize <= 0)
                    {
                        continue label0;
                    }
                    int i1 = furnaceRand.nextInt(21) + 10;
                    if(i1 > itemstack.stackSize)
                    {
                        i1 = itemstack.stackSize;
                    }
                    itemstack.stackSize -= i1;
                    EntityItem entityitem = new EntityItem(world, (float)i + f, (float)j + f1, (float)k + f2, new ItemStack(itemstack.itemID, i1, itemstack.getItemDamage()));
                    float f3 = 0.05F;
                    entityitem.motionX = (float)furnaceRand.nextGaussian() * f3;
                    entityitem.motionY = (float)furnaceRand.nextGaussian() * f3 + 0.2F;
                    entityitem.motionZ = (float)furnaceRand.nextGaussian() * f3;
                    world.spawnEntityInWorld(entityitem);
                } while(true);
            }
			}

        }
        super.onBlockRemoval(world, i, j, k);
    }

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityNukeFurnace();
	}
}
