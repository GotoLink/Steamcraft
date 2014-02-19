package steamcraft;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockHandler extends DataHandler<Block> {
	private Block block = null;

	public BlockHandler(Block bloc, Class<? extends ItemBlock> itemclass, String... names) {
		this.block = bloc;
		if (block != null) {
			block.func_149663_c(names[0]).func_149658_d(names[1]).func_149647_a(Steamcraft.steamTab);//finalizing the block
            this.output = new ItemStack(bloc, 1, 0);
			if (itemclass != null) {
				GameRegistry.registerBlock(block, itemclass, names[0]);
			} else {
				GameRegistry.registerBlock(block, names[0]);//registering...
			}
			if (names.length > 2) {
				OreDictionary.registerOre(names[2], block);
			}
			if (block.hasTileEntity(0)) {
				GameRegistry.registerTileEntity(block.createTileEntity(null, 0).getClass(), block.func_149739_a());
			}
		}
	}

	public BlockHandler(Block bloc, String... names) {
		this.block = bloc;
		if (block != null) {
            this.output = new ItemStack(bloc, 1, 0);
			block.func_149663_c(names[0]).func_149658_d(names[1]).func_149647_a(Steamcraft.steamTab);//finalizing the block
			GameRegistry.registerBlock(block, names[0]);//registering...
			if (names.length > 2) {
				OreDictionary.registerOre(names[2], block);
			}
			if (block.hasTileEntity(0)) {
				GameRegistry.registerTileEntity(block.createTileEntity(null, 0).getClass(), block.func_149739_a());
			}
		}
	}

    @Override
    public DataHandler addSmelt(ItemStack stack, float xp) {
        FurnaceRecipes.smelting().func_151393_a(get(), stack, xp);
        return this;
    }

    @Override
    public DataHandler addSmelt(ItemStack stack, int meta, float xp) {
        FurnaceRecipes.smelting().func_151394_a(new ItemStack(get(), 1, meta), stack, xp);
        return this;
    }

    public Block get() {
		return block;
	}

	@Override
	public String getName() {
		if (block != null) {
			return block.func_149739_a();
		}
		return null;
	}

    @Override
    public DataHandler setOutput(int size, int damage) {
        this.output = new ItemStack(get(), size, damage);
        return this;
    }

	public BlockHandler setHarvest(String tool, int level) {
        block.setHarvestLevel(tool, level);
		return this;
	}

	public BlockHandler setHarvest(String tool, int meta, int level) {
        block.setHarvestLevel(tool, level, meta);
		return this;
	}

	public BlockHandler setValues(float... sets) {
		switch (sets.length) {
		case 2:
			block.func_149715_a(sets[1]);
		case 1:
			block.func_149711_c(sets[0]);
			break;
		}
		return this;
	}
}
