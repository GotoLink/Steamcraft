package steamcraft.blocks;

import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import steamcraft.Steamcraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSCTeaPlant extends BlockCrops
{
    public static int modelID;
	private Icon[] icons;
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
		if(j < 1){
			return icons[0];
		}else if(j < 7){
			return icons[1];
		}else{
			return icons[2];
		}
    }
    @Override
	public int getRenderType()
    {
        return modelID;
    }
    @Override
    protected int getSeedItem()
    {
        return Steamcraft.teaSeed.itemID;
    }
    @Override
    protected int getCropItem()
    {
        return Steamcraft.teaLeaves.itemID;
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.icons = new Icon[3];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = par1IconRegister.registerIcon(this.getTextureName() + "_stage_" + i);
        }
    }
}
