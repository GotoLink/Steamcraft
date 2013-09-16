package steamcraft.blocks;

import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDiode extends BlockRedstoneRepeater
{
    private Icon iconInverterIdle;
	private Icon iconInverterActive;
	private Icon iconDiodeIdle;
	private Icon iconDiodeActive;
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
}
