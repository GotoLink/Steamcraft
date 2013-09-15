package steamcraft.blocks;

import java.util.Random;

import net.minecraft.block.BlockFurnace;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import steamcraft.TileEntityChemFurnace;
import steamcraft.mod_Steamcraft;
import steamcraft.inventories.GuiChemFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockChemFurnace extends BlockFurnace
{
    public BlockChemFurnace(int i, boolean flag)
    {
        super(i, flag/*Material.iron*/);
    }
    @Override
    public int idDropped(int i, Random random, int j)
    {
        return mod_Steamcraft.chemOvenIdle.blockID;
    }
    @Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
	    this.blockIcon = par1IconRegister.registerIcon("steamcraft:chemfurnaceside");
	    this.furnaceIconFront = par1IconRegister.registerIcon(this.isActive ? "chemfurnaceactive" : "chemfurnaceidle");
	    this.furnaceIconTop = par1IconRegister.registerIcon("chemfurnacetop");
	}

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
            world.spawnParticle("smoke", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
            world.spawnParticle("flame", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
        } else
        if(l == 5)
        {
            world.spawnParticle("smoke", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
            world.spawnParticle("flame", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
        } else
        if(l == 2)
        {
            world.spawnParticle("smoke", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
            world.spawnParticle("flame", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
        } else
        if(l == 3)
        {
            world.spawnParticle("smoke", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
            world.spawnParticle("flame", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
        }
    }

    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
        if(world.isRemote)
        {
            return true;
        }
        TileEntityChemFurnace tileentitychemfurnace = (TileEntityChemFurnace)world.getBlockTileEntity(i, j, k);
		if(tileentitychemfurnace != null){
            entityplayer.openGui(mod_Steamcraft.instance, 1,world, j,k, k);
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
            world.setBlock(i, j, k, mod_Steamcraft.chemOvenActive.blockID);
        } else
        {
            world.setBlock(i, j, k, mod_Steamcraft.chemOvenIdle.blockID);
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
	
	public void onBlockRemoval(World world, int i, int j, int k)
    {
        if(!keepFurnaceInventory)
        {
            TileEntityChemFurnace tileentityfurnace = (TileEntityChemFurnace)world.getBlockTileEntity(i, j, k);
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
		return new TileEntityChemFurnace();
	}
}
