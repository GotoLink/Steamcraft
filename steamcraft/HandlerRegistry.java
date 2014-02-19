package steamcraft;

import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandlerRegistry {
	public static Map<String, DataHandler> handlers = new HashMap<String, DataHandler>();
	public static List<Item> drills = new ArrayList<Item>();

	public static void addDrill(Item drill) {
		drills.add(drill);
	}

	public static BlockHandler getBlock(String name) {
		return (BlockHandler) handlers.get("tile." + name);
	}

	public static ItemHandler getItem(String name) {
		return (ItemHandler) handlers.get("item." + name);
	}

	public static void register(DataHandler data) {
		handlers.put(data.getName(), data);
	}
}
