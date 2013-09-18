package steamcraft.render;

import steamcraft.Steamcraft;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

public class RenderCopperWire extends RenderBlocks
{
    public RenderCopperWire()
    {
        //overrideBlockTexture = 0;
    }

    public boolean renderBlockCopperWire(Block block, int i, int j, int k, IBlockAccess iblockaccess)
    {
        Tessellator tessellator = Tessellator.instance;
        int l = iblockaccess.getBlockMetadata(i, j, k);
        Icon icon = BlockRedstoneWire.getRedstoneWireIcon("cross");
        Icon icon1 = BlockRedstoneWire.getRedstoneWireIcon("line");
        Icon icon2 = BlockRedstoneWire.getRedstoneWireIcon("cross_overlay");
        Icon icon3 = BlockRedstoneWire.getRedstoneWireIcon("line_overlay");
        tessellator.setBrightness(block.getMixedBrightnessForBlock(iblockaccess, i, j, k));
        float f = 1.0F;
        float f1 = (float)l / 15F;
        float f2 = -f1 * 0.4F + 0.5F;
		float f3 = f1 * f1 * 0.5F + 0.2F;
        float f4 = f1 * f1 * 0.8F + 0.2F;
        if(l == 0)
        {
            f2 = 0.86F;
			f3 = 0.23F;
			f4 = 0.14F;
        }
        tessellator.setColorOpaque_F(f2, f3, f4);
        double d0 = 0.015625D;
        double d1 = 0.015625D;
        boolean flag = BlockRedstoneWire.isPowerProviderOrWire(iblockaccess, i - 1, j, k, 1) || !iblockaccess.isBlockNormalCube(i - 1, j, k) && BlockRedstoneWire.isPowerProviderOrWire(iblockaccess, i - 1, j - 1, k, -1);
        boolean flag1 = BlockRedstoneWire.isPowerProviderOrWire(iblockaccess, i + 1, j, k, 3) || !iblockaccess.isBlockNormalCube(i + 1, j, k) && BlockRedstoneWire.isPowerProviderOrWire(iblockaccess, i + 1, j - 1, k, -1);
        boolean flag2 = BlockRedstoneWire.isPowerProviderOrWire(iblockaccess, i, j, k - 1, 2) || !iblockaccess.isBlockNormalCube(i, j, k - 1) && BlockRedstoneWire.isPowerProviderOrWire(iblockaccess, i, j - 1, k - 1, -1);
        boolean flag3 = BlockRedstoneWire.isPowerProviderOrWire(iblockaccess, i, j, k + 1, 0) || !iblockaccess.isBlockNormalCube(i, j, k + 1) && BlockRedstoneWire.isPowerProviderOrWire(iblockaccess, i, j - 1, k + 1, -1);
        if(!iblockaccess.isBlockNormalCube(i, j + 1, k))
        {
            if(iblockaccess.isBlockNormalCube(i - 1, j, k) && BlockRedstoneWire.isPowerProviderOrWire(iblockaccess, i - 1, j + 1, k, -1))
            {
                flag = true;
            }
            if(iblockaccess.isBlockNormalCube(i + 1, j, k) && BlockRedstoneWire.isPowerProviderOrWire(iblockaccess, i + 1, j + 1, k, -1))
            {
                flag1 = true;
            }
            if(iblockaccess.isBlockNormalCube(i, j, k - 1) && BlockRedstoneWire.isPowerProviderOrWire(iblockaccess, i, j + 1, k - 1, -1))
            {
                flag2 = true;
            }
            if(iblockaccess.isBlockNormalCube(i, j, k + 1) && BlockRedstoneWire.isPowerProviderOrWire(iblockaccess, i, j + 1, k + 1, -1))
            {
                flag3 = true;
            }
        }
        float f5 = i + 0;
        float f6 = i + 1;
        float f7 = k + 0;
        float f8 = k + 1;
        byte byte0 = 0;
        if((flag || flag1) && !flag2 && !flag3)
        {
            byte0 = 1;
        }
        if((flag2 || flag3) && !flag1 && !flag)
        {
            byte0 = 2;
        }
        if(byte0 == 0)
        {
        	int j1 = 0;
            int k1 = 0;
            int l1 = 16;
            int i2 = 16;
            boolean flag4 = true;

            if (!flag)
            {
                f5 += 0.3125F;
            }

            if (!flag)
            {
                j1 += 5;
            }

            if (!flag1)
            {
                f6 -= 0.3125F;
            }

            if (!flag1)
            {
                l1 -= 5;
            }

            if (!flag2)
            {
                f7 += 0.3125F;
            }

            if (!flag2)
            {
                k1 += 5;
            }

            if (!flag3)
            {
                f8 -= 0.3125F;
            }

            if (!flag3)
            {
                i2 -= 5;
            }
            tessellator.addVertexWithUV((double)f6, (double)j + 0.015625D, (double)f8, (double)icon.getInterpolatedU((double)l1), (double)icon.getInterpolatedV((double)i2));
            tessellator.addVertexWithUV((double)f6, (double)j + 0.015625D, (double)f7, (double)icon.getInterpolatedU((double)l1), (double)icon.getInterpolatedV((double)k1));
            tessellator.addVertexWithUV((double)f5, (double)j + 0.015625D, (double)f7, (double)icon.getInterpolatedU((double)j1), (double)icon.getInterpolatedV((double)k1));
            tessellator.addVertexWithUV((double)f5, (double)j + 0.015625D, (double)f8, (double)icon.getInterpolatedU((double)j1), (double)icon.getInterpolatedV((double)i2));
            tessellator.setColorOpaque_F(f, f, f);
            tessellator.addVertexWithUV((double)f6, (double)j + 0.015625D, (double)f8, (double)icon2.getInterpolatedU((double)l1), (double)icon2.getInterpolatedV((double)i2));
            tessellator.addVertexWithUV((double)f6, (double)j + 0.015625D, (double)f7, (double)icon2.getInterpolatedU((double)l1), (double)icon2.getInterpolatedV((double)k1));
            tessellator.addVertexWithUV((double)f5, (double)j + 0.015625D, (double)f7, (double)icon2.getInterpolatedU((double)j1), (double)icon2.getInterpolatedV((double)k1));
            tessellator.addVertexWithUV((double)f5, (double)j + 0.015625D, (double)f8, (double)icon2.getInterpolatedU((double)j1), (double)icon2.getInterpolatedV((double)i2));
        } 
        else if(byte0 == 1)
        {
        	tessellator.addVertexWithUV((double)f6, (double)j + 0.015625D, (double)f8, (double)icon1.getMaxU(), (double)icon1.getMaxV());
            tessellator.addVertexWithUV((double)f6, (double)j + 0.015625D, (double)f7, (double)icon1.getMaxU(), (double)icon1.getMinV());
            tessellator.addVertexWithUV((double)f5, (double)j + 0.015625D, (double)f7, (double)icon1.getMinU(), (double)icon1.getMinV());
            tessellator.addVertexWithUV((double)f5, (double)j + 0.015625D, (double)f8, (double)icon1.getMinU(), (double)icon1.getMaxV());
            tessellator.setColorOpaque_F(f, f, f);
            tessellator.addVertexWithUV((double)f6, (double)j + 0.015625D, (double)f8, (double)icon3.getMaxU(), (double)icon3.getMaxV());
            tessellator.addVertexWithUV((double)f6, (double)j + 0.015625D, (double)f7, (double)icon3.getMaxU(), (double)icon3.getMinV());
            tessellator.addVertexWithUV((double)f5, (double)j + 0.015625D, (double)f7, (double)icon3.getMinU(), (double)icon3.getMinV());
            tessellator.addVertexWithUV((double)f5, (double)j + 0.015625D, (double)f8, (double)icon3.getMinU(), (double)icon3.getMaxV());
        }
        else
        {
            tessellator.addVertexWithUV((double)f6, (double)j + 0.015625D, (double)f8, (double)icon1.getMaxU(), (double)icon1.getMaxV());
            tessellator.addVertexWithUV((double)f6, (double)j + 0.015625D, (double)f7, (double)icon1.getMinU(), (double)icon1.getMaxV());
            tessellator.addVertexWithUV((double)f5, (double)j + 0.015625D, (double)f7, (double)icon1.getMinU(), (double)icon1.getMinV());
            tessellator.addVertexWithUV((double)f5, (double)j + 0.015625D, (double)f8, (double)icon1.getMaxU(), (double)icon1.getMinV());
            tessellator.setColorOpaque_F(f, f, f);
            tessellator.addVertexWithUV((double)f6, (double)j + 0.015625D, (double)f8, (double)icon3.getMaxU(), (double)icon3.getMaxV());
            tessellator.addVertexWithUV((double)f6, (double)j + 0.015625D, (double)f7, (double)icon3.getMinU(), (double)icon3.getMaxV());
            tessellator.addVertexWithUV((double)f5, (double)j + 0.015625D, (double)f7, (double)icon3.getMinU(), (double)icon3.getMinV());
            tessellator.addVertexWithUV((double)f5, (double)j + 0.015625D, (double)f8, (double)icon3.getMaxU(), (double)icon3.getMinV());
        }
        if(!iblockaccess.isBlockNormalCube(i, j + 1, k))
        {
            if(iblockaccess.isBlockNormalCube(i - 1, j, k) && iblockaccess.getBlockId(i - 1, j + 1, k) == Steamcraft.redstoneWire.blockID)
            {
                tessellator.setColorOpaque_F(f * f2, f * f3, f * f4);
                tessellator.addVertexWithUV((double)i + 0.015625D, (double)((float)(j + 1) + 0.021875F), (double)(k + 1), (double)icon1.getMaxU(), (double)icon1.getMinV());
                tessellator.addVertexWithUV((double)i + 0.015625D, (double)(j + 0), (double)(k + 1), (double)icon1.getMinU(), (double)icon1.getMinV());
                tessellator.addVertexWithUV((double)i + 0.015625D, (double)(j + 0), (double)(k + 0), (double)icon1.getMinU(), (double)icon1.getMaxV());
                tessellator.addVertexWithUV((double)i + 0.015625D, (double)((float)(j + 1) + 0.021875F), (double)(k + 0), (double)icon1.getMaxU(), (double)icon1.getMaxV());
                tessellator.setColorOpaque_F(f, f, f);
                tessellator.addVertexWithUV((double)i + 0.015625D, (double)((float)(j + 1) + 0.021875F), (double)(k + 1), (double)icon3.getMaxU(), (double)icon3.getMinV());
                tessellator.addVertexWithUV((double)i + 0.015625D, (double)(j + 0), (double)(k + 1), (double)icon3.getMinU(), (double)icon3.getMinV());
                tessellator.addVertexWithUV((double)i + 0.015625D, (double)(j + 0), (double)(k + 0), (double)icon3.getMinU(), (double)icon3.getMaxV());
                tessellator.addVertexWithUV((double)i + 0.015625D, (double)((float)(j + 1) + 0.021875F), (double)(k + 0), (double)icon3.getMaxU(), (double)icon3.getMaxV());
            }
            if(iblockaccess.isBlockNormalCube(i + 1, j, k) && iblockaccess.getBlockId(i + 1, j + 1, k) == Steamcraft.redstoneWire.blockID)
            {
            	tessellator.setColorOpaque_F(f * f2, f * f3, f * f4);
                tessellator.addVertexWithUV((double)(i + 1) - 0.015625D, (double)(j + 0), (double)(k + 1), (double)icon1.getMinU(), (double)icon1.getMaxV());
                tessellator.addVertexWithUV((double)(i + 1) - 0.015625D, (double)((float)(j + 1) + 0.021875F), (double)(k + 1), (double)icon1.getMaxU(), (double)icon1.getMaxV());
                tessellator.addVertexWithUV((double)(i + 1) - 0.015625D, (double)((float)(j + 1) + 0.021875F), (double)(k + 0), (double)icon1.getMaxU(), (double)icon1.getMinV());
                tessellator.addVertexWithUV((double)(i + 1) - 0.015625D, (double)(j + 0), (double)(k + 0), (double)icon1.getMinU(), (double)icon1.getMinV());
                tessellator.setColorOpaque_F(f, f, f);
                tessellator.addVertexWithUV((double)(i + 1) - 0.015625D, (double)(j + 0), (double)(k + 1), (double)icon3.getMinU(), (double)icon3.getMaxV());
                tessellator.addVertexWithUV((double)(i + 1) - 0.015625D, (double)((float)(j + 1) + 0.021875F), (double)(k + 1), (double)icon3.getMaxU(), (double)icon3.getMaxV());
                tessellator.addVertexWithUV((double)(i + 1) - 0.015625D, (double)((float)(j + 1) + 0.021875F), (double)(k + 0), (double)icon3.getMaxU(), (double)icon3.getMinV());
                tessellator.addVertexWithUV((double)(i + 1) - 0.015625D, (double)(j + 0), (double)(k + 0), (double)icon3.getMinU(), (double)icon3.getMinV());
            }
            if(iblockaccess.isBlockNormalCube(i, j, k - 1) && iblockaccess.getBlockId(i, j + 1, k - 1) == Steamcraft.redstoneWire.blockID)
            {
            	tessellator.setColorOpaque_F(f * f2, f * f3, f * f4);
                tessellator.addVertexWithUV((double)(i + 1), (double)(j + 0), (double)k + 0.015625D, (double)icon1.getMinU(), (double)icon1.getMaxV());
                tessellator.addVertexWithUV((double)(i + 1), (double)((float)(j + 1) + 0.021875F), (double)k + 0.015625D, (double)icon1.getMaxU(), (double)icon1.getMaxV());
                tessellator.addVertexWithUV((double)(i + 0), (double)((float)(j + 1) + 0.021875F), (double)k + 0.015625D, (double)icon1.getMaxU(), (double)icon1.getMinV());
                tessellator.addVertexWithUV((double)(i + 0), (double)(j + 0), (double)k + 0.015625D, (double)icon1.getMinU(), (double)icon1.getMinV());
                tessellator.setColorOpaque_F(f, f, f);
                tessellator.addVertexWithUV((double)(i + 1), (double)(j + 0), (double)k + 0.015625D, (double)icon3.getMinU(), (double)icon3.getMaxV());
                tessellator.addVertexWithUV((double)(i + 1), (double)((float)(j + 1) + 0.021875F), (double)k + 0.015625D, (double)icon3.getMaxU(), (double)icon3.getMaxV());
                tessellator.addVertexWithUV((double)(i + 0), (double)((float)(j + 1) + 0.021875F), (double)k + 0.015625D, (double)icon3.getMaxU(), (double)icon3.getMinV());
                tessellator.addVertexWithUV((double)(i + 0), (double)(j + 0), (double)k + 0.015625D, (double)icon3.getMinU(), (double)icon3.getMinV());
            }
            if(iblockaccess.isBlockNormalCube(i, j, k + 1) && iblockaccess.getBlockId(i, j + 1, k + 1) == Steamcraft.redstoneWire.blockID)
            {
            	tessellator.setColorOpaque_F(f * f2, f * f3, f * f4);
                tessellator.addVertexWithUV((double)(i + 1), (double)((float)(j + 1) + 0.021875F), (double)(k + 1) - 0.015625D, (double)icon1.getMaxU(), (double)icon1.getMinV());
                tessellator.addVertexWithUV((double)(i + 1), (double)(j + 0), (double)(k + 1) - 0.015625D, (double)icon1.getMinU(), (double)icon1.getMinV());
                tessellator.addVertexWithUV((double)(i + 0), (double)(j + 0), (double)(k + 1) - 0.015625D, (double)icon1.getMinU(), (double)icon1.getMaxV());
                tessellator.addVertexWithUV((double)(i + 0), (double)((float)(j + 1) + 0.021875F), (double)(k + 1) - 0.015625D, (double)icon1.getMaxU(), (double)icon1.getMaxV());
                tessellator.setColorOpaque_F(f, f, f);
                tessellator.addVertexWithUV((double)(i + 1), (double)((float)(j + 1) + 0.021875F), (double)(k + 1) - 0.015625D, (double)icon3.getMaxU(), (double)icon3.getMinV());
                tessellator.addVertexWithUV((double)(i + 1), (double)(j + 0), (double)(k + 1) - 0.015625D, (double)icon3.getMinU(), (double)icon3.getMinV());
                tessellator.addVertexWithUV((double)(i + 0), (double)(j + 0), (double)(k + 1) - 0.015625D, (double)icon3.getMinU(), (double)icon3.getMaxV());
                tessellator.addVertexWithUV((double)(i + 0), (double)((float)(j + 1) + 0.021875F), (double)(k + 1) - 0.015625D, (double)icon3.getMaxU(), (double)icon3.getMaxV());
            }
        }
        return true;
    }
}
