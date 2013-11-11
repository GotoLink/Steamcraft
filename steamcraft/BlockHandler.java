package steamcraft;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockHandler extends DataHandler {
	private Block block = null;

	public BlockHandler(Block bloc, Class<? extends ItemBlock> itemclass, String... names) {
		this.block = bloc;
		if (block != null) {
			this.output = new ItemStack(bloc.blockID, 1, 0);
			block.setUnlocalizedName(names[0]).setTextureName(names[1]).setCreativeTab(Steamcraft.steamTab);//finalizing the block
			if (itemclass != null) {
				GameRegistry.registerBlock(block, itemclass, names[0]);
			} else {
				GameRegistry.registerBlock(block, names[0]);//registering...
			}
			if (names.length > 2) {
				OreDictionary.registerOre(names[2], block);
			}
			if (block.hasTileEntity(0)) {
				GameRegistry.registerTileEntity(block.createTileEntity(null, 0).getClass(), block.getUnlocalizedName());
			}
		}
	}

	public BlockHandler(Block bloc, String... names) {
		this.block = bloc;
		if (block != null) {
			block.setUnlocalizedName(names[0]).setTextureName(names[1]).setCreativeTab(Steamcraft.steamTab);//finalizing the block
			GameRegistry.registerBlock(block, names[0]);//registering...
			if (names.length > 2) {
				OreDictionary.registerOre(names[2], block);
			}
			if (block.hasTileEntity(0)) {
				GameRegistry.registerTileEntity(block.createTileEntity(null, 0).getClass(), block.getUnlocalizedName());
			}
		}
	}

	public Block get() {
		return block;
	}

	@Override
	public int getID() {
		if (block != null) {
			return block.blockID;
		}
		return -1;
	}

	@Override
	public String getName() {
		if (block != null) {
			return block.getUnlocalizedName();
		}
		return null;
	}

	public BlockHandler removeEffect(String tool) {
		MinecraftForge.removeBlockEffectiveness(block, tool);
		return this;
	}

	public BlockHandler setHarvest(String tool, int level) {
		MinecraftForge.setBlockHarvestLevel(block, tool, level);
		return this;
	}

	public BlockHandler setHarvest(String tool, int meta, int level) {
		MinecraftForge.setBlockHarvestLevel(block, meta, tool, level);
		return this;
	}

	public BlockHandler setValues(float... sets) {
		switch (sets.length) {
		case 2:
			block.setLightValue(sets[1]);
		case 1:
			block.setHardness(sets[0]);
			break;
		}
		return this;
	}
}
