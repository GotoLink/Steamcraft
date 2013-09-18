package steamcraft.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public class RenderTeaPlant extends RenderBlocks
{
    public RenderTeaPlant()
    {
        //overrideBlockTexture = 0;
    }

    public boolean renderBlockTeaPlant(Block block, int i, int j, int k, IBlockAccess blockaccess)
    {
    	Tessellator tessellator = Tessellator.instance;
    	tessellator.setBrightness(block.getMixedBrightnessForBlock(blockaccess, i, j, k));
        float f = 1.0F;
        int l = block.colorMultiplier(blockaccess, i, j, k);
        float f1 = (float)(l >> 16 & 0xff) / 255F;
        float f2 = (float)(l >> 8 & 0xff) / 255F;
        float f3 = (float)(l & 0xff) / 255F;
        if(EntityRenderer.anaglyphEnable)
        {
            float f4 = (f1 * 30F + f2 * 59F + f3 * 11F) / 100F;
            float f5 = (f1 * 30F + f2 * 70F) / 100F;
            float f6 = (f1 * 30F + f3 * 70F) / 100F;
            f1 = f4;
            f2 = f5;
            f3 = f6;
        }
        tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
        double d = i;
        double d1 = j;
        double d2 = k;
        
            long l1 = (long)(i * 0x2fc20f) ^ (long)k * 0x6ebfff5L ^ (long)j;
            l1 = l1 * l1 * 0x285b825L + l1 * 11L;
			float growth = blockaccess.getBlockMetadata(i,j,k);
			if(growth != 0 && growth != 7){
            d1 -= (-1*growth/15)+0.4;
			}
        drawCrossedSquares(block, blockaccess.getBlockMetadata(i, j, k), d, d1, d2, 1.0F);
		return true;
    }
}
