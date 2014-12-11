package steamcraft;

import net.minecraft.item.Item;
import net.minecraft.stats.Achievement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class HandlerRegistry {
	private static Map<String, DataHandler> handlers = new HashMap<String, DataHandler>();
	private static List<Item> drills = new ArrayList<Item>();
    private static Map<String, Achievement> achievements = new HashMap<String, Achievement>();

    public static void addDrill(Item drill) {
		drills.add(drill);
	}

    public static boolean isDrill(Item item){
        return drills.contains(item);
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

    public static void addAchievement(String name, Achievement ach){
        achievements.put(name, ach);
    }

    public static Achievement getAchievement(String name){
        return achievements.get(name);
    }

    public static Achievement[] getAchievements(){
        return achievements.values().toArray(new Achievement[achievements.size()]);
    }
}
