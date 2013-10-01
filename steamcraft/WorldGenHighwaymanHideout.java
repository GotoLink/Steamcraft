package steamcraft;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenHighwaymanHideout extends WorldGenerator
{
    public WorldGenHighwaymanHideout()
    {
    }
    @Override
    public boolean generate(World world, Random random, int i, int j, int k)
    {
        byte byte0 = 3;
        int l = random.nextInt(2) + 2;
        int i1 = random.nextInt(2) + 2;
        int j1 = 0;
        for(int k1 = i - l - 1; k1 <= i + l + 1; k1++)
        {
            for(int j2 = j - 1; j2 <= j + byte0 + 1; j2++)
            {
                for(int i3 = k - i1 - 1; i3 <= k + i1 + 1; i3++)
                {
                    Material material = world.getBlockMaterial(k1, j2, i3);
                    if(j2 == j - 1 && !material.isSolid())
                    {
                        return false;
                    }
                    if(j2 == j + byte0 + 1 && !material.isSolid())
                    {
                        return false;
                    }
                    if((k1 == i - l - 1 || k1 == i + l + 1 || i3 == k - i1 - 1 || i3 == k + i1 + 1) && j2 == j && world.isAirBlock(k1, j2, i3) && world.isAirBlock(k1, j2 + 1, i3))
                    {
                        j1++;
                    }
                }

            }

        }

        if(j1 < 1 || j1 > 5)
        {
            return false;
        }
        for(int l1 = i - l - 1; l1 <= i + l + 1; l1++)
        {
            for(int k2 = j + byte0; k2 >= j - 1; k2--)
            {
                for(int j3 = k - i1 - 1; j3 <= k + i1 + 1; j3++)
                {
                    if(l1 == i - l - 1 || k2 == j - 1 || j3 == k - i1 - 1 || l1 == i + l + 1 || k2 == j + byte0 + 1 || j3 == k + i1 + 1)
                    {
                        if(k2 >= 0 && !world.getBlockMaterial(l1, k2 - 1, j3).isSolid())
                        {
                            world.setBlockToAir(l1, k2, j3);
                            continue;
                        }
                        if(!world.getBlockMaterial(l1, k2, j3).isSolid())
                        {
                            continue;
                        }
                        if(k2 != j - 1 && random.nextInt(4) == 0)
                        {
                            world.setBlock(l1, k2, j3, Block.thinGlass.blockID);
                        } else
                        {
                        	int randomBlockType = random.nextInt(6);
                        	if(randomBlockType > 3){
                        		randomBlockType = 0;
                        	}
                            world.setBlock(l1, k2, j3, Block.stoneBrick.blockID, randomBlockType,3);
                        }
                    } else
                    {
                        world.setBlockToAir(l1, k2, j3);
                    }
                }
            }
        }

        for(int i2 = 0; i2 < 2; i2++)
        {
        	label0:
            for(int l2 = 0; l2 < 3; l2++)
            {
                int k3 = (i + random.nextInt(l * 2 + 1)) - l;
                int l3 = j;
                int i4 = (k + random.nextInt(i1 * 2 + 1)) - i1;
                if(!world.isAirBlock(k3, l3, i4))
                {
                    continue;
                }
                int j4 = 0;
                if(world.getBlockMaterial(k3 - 1, l3, i4).isSolid())
                {
                    j4++;
                }
                if(world.getBlockMaterial(k3 + 1, l3, i4).isSolid())
                {
                    j4++;
                }
                if(world.getBlockMaterial(k3, l3, i4 - 1).isSolid())
                {
                    j4++;
                }
                if(world.getBlockMaterial(k3, l3, i4 + 1).isSolid())
                {
                    j4++;
                }
                if(j4 != 1)
                {
                    continue;
                }
                world.setBlock(k3, l3, i4, Block.chest.blockID);
                TileEntityChest tileentitychest = (TileEntityChest)world.getBlockTileEntity(k3, l3, i4);
                int k4 = 0;
                do
                {
                    if(k4 >= 8)
                    {
                        break label0;
                    }
                    ItemStack itemstack = pickCheckLootItem(random);
                    if(itemstack != null)
                    {
                        tileentitychest.setInventorySlotContents(random.nextInt(tileentitychest.getSizeInventory()), itemstack);
                    }
                    k4++;
                } while(true);
            }
        }

        world.setBlock(i, j, k, Block.mobSpawner.blockID);
        TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner)world.getBlockTileEntity(i, j, k);
        tileentitymobspawner.getSpawnerLogic().setMobID(pickMobSpawner(random));
        return true;
    }

    private ItemStack pickCheckLootItem(Random random)
    {
        int i = random.nextInt(19);
        if(i == 0)
        {
            return new ItemStack(Item.saddle);
        }
        if(i == 1)
        {
            return new ItemStack(Item.ingotGold, random.nextInt(4) + 1);
        }
        if(i == 2)
        {
            return new ItemStack(Item.bread);
        }
        if(i == 3)
        {
            return new ItemStack(Item.wheat, random.nextInt(4) + 1);
        }
        if(i == 4)
        {
            return new ItemStack(Item.gunpowder, random.nextInt(4) + 1);
        }
        if(i == 5)
        {
            return new ItemStack(Item.silk, random.nextInt(4) + 1);
        }
        if(i == 6)
        {
            return new ItemStack(Steamcraft.part, random.nextInt(8) + 1, 0);
        }
        if(i == 7 && random.nextInt(100) == 0)
        {
            return new ItemStack(Item.appleGold);
        }
        if(i == 8 && random.nextInt(2) == 0)
        {
            return new ItemStack(Item.redstone, random.nextInt(8) + 1);
        }
        if(i == 9 && random.nextInt(10) == 0)
        {
            return new ItemStack(Item.itemsList[Item.record13.itemID + random.nextInt(2)]);
        }
        if(i == 10)
        {
            return new ItemStack(Item.dyePowder, 1, 3);
        }
		if(i == 11)
        {
            return new ItemStack(Steamcraft.part, random.nextInt(8) + 1, 1);
        }
		if(i == 12 && random.nextInt(3) == 0)
        {
            return Steamcraft.matchlockMusket;
        }
		if(i == 13 && random.nextInt(3) == 0)
        {
            return Steamcraft.matchlockRifle;
        }
		if(i == 14 && random.nextInt(4) == 0)
        {
            return Steamcraft.flintlockMusket;
        }
		if(i == 15 && random.nextInt(4) == 0)
        {
            return Steamcraft.flintlockRifle;
        }
		if(i == 16 && random.nextInt(8) == 0)
        {
            return Steamcraft.percussionCapMusket;
        }
		if(i == 17 && random.nextInt(8) == 0)
        {
            return Steamcraft.percussionCapRifle;
        }
		if(i == 18 && random.nextInt(16) == 0)
        {
            return new ItemStack(Item.diamond, random.nextInt(3) + 1);
        }
		else
        {
            return null;
        }
    }

    private String pickMobSpawner(Random random)
    {
        return "Highwayman";
    }
}
