package steamcraft.blocks;

import steamcraft.mod_Steamcraft;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRail;
import net.minecraft.util.Icon;

public class BlockPoweredRail extends BlockRail
{
    public BlockPoweredRail(int i, boolean flag)
    {
        super(i);
		isPowered = flag;
    }
    @Override
	public Icon getIcon(int i, int j)
    {
        if(isPowered)
        {
            if(blockID == Block.railPowered.blockID && (j & 8) == 0)
            {
                return mod_Steamcraft.PoweredRailOFF;
            }
        } else
        if(j >= 6)
        {
            return mod_Steamcraft.PoweredRailOFF;
        }
        return mod_Steamcraft.PoweredRailON;
	}
	
	private final boolean isPowered;
}
