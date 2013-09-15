package steamcraft.blocks;

import net.minecraft.block.BlockCrops;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import steamcraft.mod_Steamcraft;

public class BlockSCTeaPlant extends BlockCrops
{
    public BlockSCTeaPlant(int i)
    {
        super(i);
    }
    @Override
    public void fertilize(World world, int i, int j, int k)
    {
        world.setBlockMetadataWithNotify(i, j, k, 7, 2);
    }
    @Override
    public Icon getIcon(int i, int j)
    {
        if(j < 0)
        {
            j = 7;
        }
		if(j < 1){
		return 88;
		}else if(j < 7){
		return mod_Steamcraft.YoungTeaPlant;
		}else{
        return blockIndexInTexture;
		}
    }
    @Override
	public int getRenderType()
    {
        return mod_Steamcraft.TeaPlantModelID;
    }
    @Override
    protected int getSeedItem()
    {
        return mod_Steamcraft.teaSeed.itemID;
    }
    @Override
    protected int getCropItem()
    {
        return mod_Steamcraft.teaLeaves.itemID;
    }
}
