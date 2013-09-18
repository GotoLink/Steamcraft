package steamcraft.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneLogic;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import steamcraft.Steamcraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDiode extends BlockRedstoneRepeater
{
    public static Icon iconInverterIdle;
	public static Icon iconInverterActive;
	private static Icon iconDiodeIdle;
	private static Icon iconDiodeActive;
	public BlockDiode(int i, boolean flag)
    {
        super(i, flag);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int i, int j)
    {
    	if(i == 0)
        {
            return !isRepeaterPowered ? iconInverterIdle : iconInverterActive;
        }
        if(i == 1)
        {
            return !isRepeaterPowered ? iconDiodeIdle : iconDiodeActive;
        } 
        return super.getIcon(i, j);
    }
    @Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        super.registerIcons(par1IconRegister);
        iconInverterIdle = par1IconRegister.registerIcon("steamcraft:inverteridle");
        iconInverterActive = par1IconRegister.registerIcon("steamcraft:inverteractive");
        iconDiodeIdle = par1IconRegister.registerIcon("steamcraft:diodeidle");
        iconDiodeActive = par1IconRegister.registerIcon("steamcraft:diodeactive");
    }
    @Override
    protected BlockRedstoneLogic func_94485_e()
    {
        return (BlockRedstoneLogic) Steamcraft.redstoneRepeaterActive;
    }
    @Override
    protected BlockRedstoneLogic func_94484_i()
    {
        return (BlockRedstoneLogic) Steamcraft.redstoneRepeaterIdle;
    }
    @Override
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return Steamcraft.redstoneRepeaterIdle.blockID;
    }
    @Override
    @SideOnly(Side.CLIENT)
    public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return Steamcraft.redstoneRepeaterIdle.blockID;
    }
}
