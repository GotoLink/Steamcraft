package steamcraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandlerRegistry {
	public static Map<String, DataHandler> handlers = new HashMap<String, DataHandler>();
	public static List<Integer> drills = new ArrayList<Integer>();

	public static void addDrill(int id) {
		drills.add(id);
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
