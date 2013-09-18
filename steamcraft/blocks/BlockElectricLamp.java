package steamcraft.blocks;

import java.util.Random;

import net.minecraft.world.World;
import steamcraft.Steamcraft;

public class BlockElectricLamp extends BlockWirelessLamp
{
    public BlockElectricLamp(int i, Class class1, boolean flag)
    {
        super(i, class1, flag);
        setTickRandomly(true);
    }
    @Override
    public int tickRate(World world)
    {
        return 2;
    }
    @Override
    protected int getActiveBlock() 
	{
		return Steamcraft.torchElectricActive.blockID;
	}
    @Override
    protected int getIdleBlock() 
    {
		return Steamcraft.torchElectricIdle.blockID;
	}
	@Override
    public int idDropped(int i, Random random, int j)
    {
        return Steamcraft.electricLamp.itemID;
    }
}
