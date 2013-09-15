package steamcraft.render;

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
       /* Tessellator tessellator = Tessellator.instance;
        char c = '\244';
        float f = ModLoader.getMinecraftInstance().theWorld.getBlockMetadata(i, j, k);
        f /= 30F;
        if(f > 0.0F)
        {
            f += 0.5F;
        } else
        {
            f = 0.2F;
        }
        World world = ModLoader.getMinecraftInstance().theWorld;
        TileEntityColor tileentitycolor = null;
        if(world.getBlockTileEntity(i, j, k) instanceof TileEntityColor)
        {
            tileentitycolor = (TileEntityColor)world.getBlockTileEntity(i, j, k);
        }
        int l = 0;
        if(tileentitycolor != null)
        {
            l = tileentitycolor.Color;
        }
        int i1 = l;
        int ai[] = RSConfig.getCOLOR(i1);
        float f1 = ai[0];
        float f2 = ai[1];
        float f3 = ai[2];
        tessellator.setColorOpaque_F((f * f1) / 255F, (f * f2) / 255F, (f * f3) / 255F);
        int j1 = (c & 0xf) << 4;
        int k1 = c & 0xf0;
        double d = (float)j1 / 256F;
        double d1 = ((float)j1 + 15.99F) / 256F;
        double d2 = (float)k1 / 256F;
        double d3 = ((float)k1 + 15.99F) / 256F;
        float f4 = 0.0F;
        float f5 = 0.03125F;
        boolean flag = BlockRedstoneWire.isPowerProviderOrWire(iblockaccess, i - 1, j, k) && BlockColoredRedstone.hasSameColor(i, j, k, i - 1, j, k) || !iblockaccess.isBlockOpaqueCube(i - 1, j, k) && BlockRedstoneWire.isPowerProviderOrWire(iblockaccess, i - 1, j - 1, k) && BlockColoredRedstone.hasSameColor(i, j, k, i - 1, j - 1, k);
        boolean flag1 = BlockRedstoneWire.isPowerProviderOrWire(iblockaccess, i + 1, j, k) && BlockColoredRedstone.hasSameColor(i, j, k, i + 1, j, k) || !iblockaccess.isBlockOpaqueCube(i + 1, j, k) && BlockRedstoneWire.isPowerProviderOrWire(iblockaccess, i + 1, j - 1, k) && BlockColoredRedstone.hasSameColor(i, j, k, i + 1, j - 1, k);
        boolean flag2 = BlockRedstoneWire.isPowerProviderOrWire(iblockaccess, i, j, k - 1) && BlockColoredRedstone.hasSameColor(i, j, k, i, j, k - 1) || !iblockaccess.isBlockOpaqueCube(i, j, k - 1) && BlockRedstoneWire.isPowerProviderOrWire(iblockaccess, i, j - 1, k - 1) && BlockColoredRedstone.hasSameColor(i, j, k, i, j - 1, k - 1);
        boolean flag3 = BlockRedstoneWire.isPowerProviderOrWire(iblockaccess, i, j, k + 1) && BlockColoredRedstone.hasSameColor(i, j, k, i, j, k + 1) || !iblockaccess.isBlockOpaqueCube(i, j, k + 1) && BlockRedstoneWire.isPowerProviderOrWire(iblockaccess, i, j - 1, k + 1) && BlockColoredRedstone.hasSameColor(i, j, k, i, j - 1, k + 1);
        */
        Tessellator tessellator = Tessellator.instance;
        int l = iblockaccess.getBlockMetadata(i, j, k);
        Icon i1 = block.getIcon(1, l);
        if(overrideBlockTexture >= 0)
        {
            i1 = overrideBlockTexture;
        }
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
        int j1 = (i1 & 0xf) << 4;
        int k1 = i1 & 0xf0;
        double d = (float)j1 / 256F;
        double d2 = ((float)j1 + 15.99F) / 256F;
        double d4 = (float)k1 / 256F;
        double d6 = ((float)k1 + 15.99F) / 256F;
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
        if(byte0 != 0)
        {
            d = (float)(j1 + 16) / 256F;
            d2 = ((float)(j1 + 16) + 15.99F) / 256F;
            d4 = (float)k1 / 256F;
            d6 = ((float)k1 + 15.99F) / 256F;
        }
        if(byte0 == 0)
        {
            if(flag1 || flag2 || flag3 || flag)
            {
                if(!flag)
                {
                    f5 += 0.3125F;
                }
                if(!flag)
                {
                    d += 0.01953125D;
                }
                if(!flag1)
                {
                    f6 -= 0.3125F;
                }
                if(!flag1)
                {
                    d2 -= 0.01953125D;
                }
                if(!flag2)
                {
                    f7 += 0.3125F;
                }
                if(!flag2)
                {
                    d4 += 0.01953125D;
                }
                if(!flag3)
                {
                    f8 -= 0.3125F;
                }
                if(!flag3)
                {
                    d6 -= 0.01953125D;
                }
            }
            tessellator.addVertexWithUV(f6, (double)j + 0.015625D, f8, d2, d6);
            tessellator.addVertexWithUV(f6, (double)j + 0.015625D, f7, d2, d4);
            tessellator.addVertexWithUV(f5, (double)j + 0.015625D, f7, d, d4);
            tessellator.addVertexWithUV(f5, (double)j + 0.015625D, f8, d, d6);
            tessellator.setColorOpaque_F(f, f, f);
            tessellator.addVertexWithUV(f6, (double)j + 0.015625D, f8, d2, d6 + 0.0625D);
            tessellator.addVertexWithUV(f6, (double)j + 0.015625D, f7, d2, d4 + 0.0625D);
            tessellator.addVertexWithUV(f5, (double)j + 0.015625D, f7, d, d4 + 0.0625D);
            tessellator.addVertexWithUV(f5, (double)j + 0.015625D, f8, d, d6 + 0.0625D);
        } else
        if(byte0 == 1)
        {
            tessellator.addVertexWithUV(f6, (double)j + 0.015625D, f8, d2, d6);
            tessellator.addVertexWithUV(f6, (double)j + 0.015625D, f7, d2, d4);
            tessellator.addVertexWithUV(f5, (double)j + 0.015625D, f7, d, d4);
            tessellator.addVertexWithUV(f5, (double)j + 0.015625D, f8, d, d6);
            tessellator.setColorOpaque_F(f, f, f);
            tessellator.addVertexWithUV(f6, (double)j + 0.015625D, f8, d2, d6 + 0.0625D);
            tessellator.addVertexWithUV(f6, (double)j + 0.015625D, f7, d2, d4 + 0.0625D);
            tessellator.addVertexWithUV(f5, (double)j + 0.015625D, f7, d, d4 + 0.0625D);
            tessellator.addVertexWithUV(f5, (double)j + 0.015625D, f8, d, d6 + 0.0625D);
        } else
        if(byte0 == 2)
        {
            tessellator.addVertexWithUV(f6, (double)j + 0.015625D, f8, d2, d6);
            tessellator.addVertexWithUV(f6, (double)j + 0.015625D, f7, d, d6);
            tessellator.addVertexWithUV(f5, (double)j + 0.015625D, f7, d, d4);
            tessellator.addVertexWithUV(f5, (double)j + 0.015625D, f8, d2, d4);
            tessellator.setColorOpaque_F(f, f, f);
            tessellator.addVertexWithUV(f6, (double)j + 0.015625D, f8, d2, d6 + 0.0625D);
            tessellator.addVertexWithUV(f6, (double)j + 0.015625D, f7, d, d6 + 0.0625D);
            tessellator.addVertexWithUV(f5, (double)j + 0.015625D, f7, d, d4 + 0.0625D);
            tessellator.addVertexWithUV(f5, (double)j + 0.015625D, f8, d2, d4 + 0.0625D);
        }
        if(!iblockaccess.isBlockNormalCube(i, j + 1, k))
        {
            double d1 = (float)(j1 + 16) / 256F;
            double d3 = ((float)(j1 + 16) + 15.99F) / 256F;
            double d5 = (float)k1 / 256F;
            double d7 = ((float)k1 + 15.99F) / 256F;
            if(iblockaccess.isBlockNormalCube(i - 1, j, k) && iblockaccess.getBlockId(i - 1, j + 1, k) == Block.redstoneWire.blockID)
            {
                tessellator.setColorOpaque_F(f * f2, f * f3, f * f4);
                tessellator.addVertexWithUV((double)i + 0.015625D, (float)(j + 1) + 0.021875F, k + 1, d3, d5);
                tessellator.addVertexWithUV((double)i + 0.015625D, j + 0, k + 1, d1, d5);
                tessellator.addVertexWithUV((double)i + 0.015625D, j + 0, k + 0, d1, d7);
                tessellator.addVertexWithUV((double)i + 0.015625D, (float)(j + 1) + 0.021875F, k + 0, d3, d7);
                tessellator.setColorOpaque_F(f, f, f);
                tessellator.addVertexWithUV((double)i + 0.015625D, (float)(j + 1) + 0.021875F, k + 1, d3, d5 + 0.0625D);
                tessellator.addVertexWithUV((double)i + 0.015625D, j + 0, k + 1, d1, d5 + 0.0625D);
                tessellator.addVertexWithUV((double)i + 0.015625D, j + 0, k + 0, d1, d7 + 0.0625D);
                tessellator.addVertexWithUV((double)i + 0.015625D, (float)(j + 1) + 0.021875F, k + 0, d3, d7 + 0.0625D);
            }
            if(iblockaccess.isBlockNormalCube(i + 1, j, k) && iblockaccess.getBlockId(i + 1, j + 1, k) == Block.redstoneWire.blockID)
            {
                tessellator.setColorOpaque_F(f * f2, f * f3, f * f4);
                tessellator.addVertexWithUV((double)(i + 1) - 0.015625D, j + 0, k + 1, d1, d7);
                tessellator.addVertexWithUV((double)(i + 1) - 0.015625D, (float)(j + 1) + 0.021875F, k + 1, d3, d7);
                tessellator.addVertexWithUV((double)(i + 1) - 0.015625D, (float)(j + 1) + 0.021875F, k + 0, d3, d5);
                tessellator.addVertexWithUV((double)(i + 1) - 0.015625D, j + 0, k + 0, d1, d5);
                tessellator.setColorOpaque_F(f, f, f);
                tessellator.addVertexWithUV((double)(i + 1) - 0.015625D, j + 0, k + 1, d1, d7 + 0.0625D);
                tessellator.addVertexWithUV((double)(i + 1) - 0.015625D, (float)(j + 1) + 0.021875F, k + 1, d3, d7 + 0.0625D);
                tessellator.addVertexWithUV((double)(i + 1) - 0.015625D, (float)(j + 1) + 0.021875F, k + 0, d3, d5 + 0.0625D);
                tessellator.addVertexWithUV((double)(i + 1) - 0.015625D, j + 0, k + 0, d1, d5 + 0.0625D);
            }
            if(iblockaccess.isBlockNormalCube(i, j, k - 1) && iblockaccess.getBlockId(i, j + 1, k - 1) == Block.redstoneWire.blockID)
            {
                tessellator.setColorOpaque_F(f * f2, f * f3, f * f4);
                tessellator.addVertexWithUV(i + 1, j + 0, (double)k + 0.015625D, d1, d7);
                tessellator.addVertexWithUV(i + 1, (float)(j + 1) + 0.021875F, (double)k + 0.015625D, d3, d7);
                tessellator.addVertexWithUV(i + 0, (float)(j + 1) + 0.021875F, (double)k + 0.015625D, d3, d5);
                tessellator.addVertexWithUV(i + 0, j + 0, (double)k + 0.015625D, d1, d5);
                tessellator.setColorOpaque_F(f, f, f);
                tessellator.addVertexWithUV(i + 1, j + 0, (double)k + 0.015625D, d1, d7 + 0.0625D);
                tessellator.addVertexWithUV(i + 1, (float)(j + 1) + 0.021875F, (double)k + 0.015625D, d3, d7 + 0.0625D);
                tessellator.addVertexWithUV(i + 0, (float)(j + 1) + 0.021875F, (double)k + 0.015625D, d3, d5 + 0.0625D);
                tessellator.addVertexWithUV(i + 0, j + 0, (double)k + 0.015625D, d1, d5 + 0.0625D);
            }
            if(iblockaccess.isBlockNormalCube(i, j, k + 1) && iblockaccess.getBlockId(i, j + 1, k + 1) == Block.redstoneWire.blockID)
            {
                tessellator.setColorOpaque_F(f * f2, f * f3, f * f4);
                tessellator.addVertexWithUV(i + 1, (float)(j + 1) + 0.021875F, (double)(k + 1) - 0.015625D, d3, d5);
                tessellator.addVertexWithUV(i + 1, j + 0, (double)(k + 1) - 0.015625D, d1, d5);
                tessellator.addVertexWithUV(i + 0, j + 0, (double)(k + 1) - 0.015625D, d1, d7);
                tessellator.addVertexWithUV(i + 0, (float)(j + 1) + 0.021875F, (double)(k + 1) - 0.015625D, d3, d7);
                tessellator.setColorOpaque_F(f, f, f);
                tessellator.addVertexWithUV(i + 1, (float)(j + 1) + 0.021875F, (double)(k + 1) - 0.015625D, d3, d5 + 0.0625D);
                tessellator.addVertexWithUV(i + 1, j + 0, (double)(k + 1) - 0.015625D, d1, d5 + 0.0625D);
                tessellator.addVertexWithUV(i + 0, j + 0, (double)(k + 1) - 0.015625D, d1, d7 + 0.0625D);
                tessellator.addVertexWithUV(i + 0, (float)(j + 1) + 0.021875F, (double)(k + 1) - 0.015625D, d3, d7 + 0.0625D);
            }
        }
        return true;
    }
}
