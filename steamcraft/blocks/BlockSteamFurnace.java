package steamcraft.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import steamcraft.TileEntitySteamFurnace;
import steamcraft.Steamcraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSteamFurnace extends BlockFurnace
{
    public BlockSteamFurnace(int i, boolean flag)
    {
        super(i, flag/*Material.iron*/);
    }
    
    @Override
    public int idDropped(int i, Random random, int j)
    {
        return Steamcraft.steamOvenIdle.blockID;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("steamcraft:steamfurnaceside");
        this.furnaceIconFront = par1IconRegister.registerIcon("steamcraft:"+(this.isActive ? "steamfurnaceactive" : "steamfurnaceidle"));
        this.furnaceIconTop = par1IconRegister.registerIcon("steamcraft:steamfurnacetop");
    }
    @Override
    public void randomDisplayTick(World world, int i, int j, int k, Random random)
    {
        if(!isActive)
        {
            return;
        }
		TileEntitySteamFurnace tileentityfurnace = (TileEntitySteamFurnace)world.getBlockTileEntity(i, j, k);
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
		if(tileentityfurnace.getWater() > 0){
		world.spawnParticle("smoke", f, f1+1, f2, 0.0D, 0.1D, 0.0D);
		world.spawnParticle("smoke", f, f1+1, f2, 0.0D, 0.1D, 0.0D);
		}
    }
	
	public static void playSound(World world, int i, int j, int k, String s){
		world.playSoundEffect((float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, s, 0.5F, 2.6F * 0.8F);
	}

	@Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
    {
        if(world.isRemote)
        {
            return true;
        }
		if(world.getBlockTileEntity(i, j, k) instanceof TileEntitySteamFurnace)
        {
            entityplayer.openGui(Steamcraft.instance, 0,world, i, j, k);
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
            world.setBlock(i, j, k, Steamcraft.steamOvenActive.blockID);
        } else
        {
            world.setBlock(i, j, k, Steamcraft.steamOvenIdle.blockID);
        }
        keepFurnaceInventory = false;
        world.setBlockMetadataWithNotify(i, j, k, l, 2);
        if(tileentity != null)
        {
            tileentity.validate();
            world.setBlockTileEntity(i, j, k, tileentity);
        }
    }

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntitySteamFurnace();
	}
	@Override
	@SideOnly(Side.CLIENT)
    public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return Steamcraft.steamOvenIdle.blockID;
    }
}
