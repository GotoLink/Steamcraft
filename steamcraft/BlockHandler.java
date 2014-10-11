package steamcraft;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockHandler extends DataHandler<Block> {
	private Block block = null;
    private Class<? extends ItemBlock> itemclass;
    private String oreName;

	public BlockHandler(Block bloc, Class<? extends ItemBlock> itemclass, String... names) {
        super(names[0]);
		this.block = bloc;
		if (block != null) {
			block.setBlockName(names[0]).setBlockTextureName(names[1]).setCreativeTab(Steamcraft.steamTab);//finalizing the block
            this.itemclass = itemclass;
            if (names.length > 2) {
                oreName = names[2];
            }
			if (block.hasTileEntity(0)) {
				GameRegistry.registerTileEntity(block.createTileEntity(null, 0).getClass(), block.getUnlocalizedName());
			}
		}
	}

	public BlockHandler(Block bloc, String... names) {
        super(names[0]);
		this.block = bloc;
		if (block != null) {
			block.setBlockName(names[0]).setBlockTextureName(names[1]).setCreativeTab(Steamcraft.steamTab);//finalizing the block
			if (names.length > 2) {
                oreName = names[2];
            }
			if (block.hasTileEntity(0)) {
				GameRegistry.registerTileEntity(block.createTileEntity(null, 0).getClass(), block.getUnlocalizedName());
			}
		}
	}

    @Override
    public DataHandler register(boolean asInternal) {
        if (block != null) {
            if (itemclass != null) {
                GameRegistry.registerBlock(block, itemclass, registryName);
            } else {
                GameRegistry.registerBlock(block, registryName);//registering...
            }
            if (oreName != null) {
                OreDictionary.registerOre(oreName, block);
            }
        }
        return super.register(asInternal);
    }

    public Block get() {
		return block;
	}

    public Item getItem(){
        return Item.getItemFromBlock(block);
    }

	@Override
	public String getName() {
		if (block != null) {
			return block.getUnlocalizedName();
		}
		return null;
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
			block.setLightLevel(sets[1]);
		case 1:
			block.setHardness(sets[0]);
			break;
		}
		return this;
	}
}
