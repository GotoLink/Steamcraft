package steamcraft;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

public class ItemHandler extends DataHandler {
	private Item item = null;

	public ItemHandler(Item it, String... names) {
		this.item = it;
		if (item != null) {
			item.setUnlocalizedName(names[0]).setTextureName(names[1]).setCreativeTab(Steamcraft.steamTab);//finalizing the item
			GameRegistry.registerItem(item, names[0]);
			if (names.length > 2) {
				OreDictionary.registerOre(names[2], item);
			}
		}
	}

	public Item get() {
		return item;
	}

	@Override
	public int getID() {
		if (item != null) {
			return item.itemID;
		}
		return -1;
	}

	@Override
	public String getName() {
		if (item != null) {
			return item.getUnlocalizedName();
		}
		return null;
	}

	public ItemHandler setTool(String tool, int level) {
		if (tool.equals("drill")) {
			HandlerRegistry.addDrill(getID());
		}
		MinecraftForge.setToolClass(item, tool, level);
		return this;
	}
}
