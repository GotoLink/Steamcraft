package steamcraft;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public abstract class DataHandler {
	protected ItemStack output = new ItemStack(getID(), 1, 0);

	public DataHandler addAchievement(String name, int j, int k, Achievement parent) {
		Steamcraft.achs.put(name, new Achievement(AchievementList.achievementList.size(), name, j, k, output.copy(), parent).registerAchievement());
		return this;
	}

	public DataHandler addAxe(String input) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output.copy(), "XX", "X#", " #", Character.valueOf('#'), "stickWood", Character.valueOf('X'), input));
		return this;
	}

	public DataHandler addBoots(String input) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output.copy(), "X X", "X X", Character.valueOf('X'), input));
		return this;
	}

	public DataHandler addDrill(Object input) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output.copy(), "XXX", "XXX", "XX#", Character.valueOf('#'), "stickWood", Character.valueOf('X'), input));
		return this;
	}

	public DataHandler addHelmet(String input) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output.copy(), "XXX", "X X", Character.valueOf('X'), input));
		return this;
	}

	public DataHandler addHoe(String input) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output.copy(), "XX", " #", " #", Character.valueOf('#'), "stickWood", Character.valueOf('X'), input));
		return this;
	}

	public DataHandler addLegs(String input) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output.copy(), "XXX", "X X", "X X", Character.valueOf('X'), input));
		return this;
	}

	public DataHandler addPick(String input) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output.copy(), "XXX", " # ", " # ", Character.valueOf('#'), "stickWood", Character.valueOf('X'), input));
		return this;
	}

	public DataHandler addPlate(String input) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output.copy(), "X X", "XXX", "XXX", Character.valueOf('X'), input));
		return this;
	}

	public DataHandler addRecipe(boolean shaped, Object... inputs) {
		IRecipe recipe;
		if (shaped) {
			recipe = new ShapedOreRecipe(output.copy(), inputs);
		} else {
			recipe = new ShapelessOreRecipe(output.copy(), inputs);
		}
		GameRegistry.addRecipe(recipe);
		return this;
	}

	public DataHandler addSeed(int weight) {
		MinecraftForge.addGrassSeed(output, weight);
		return this;
	}

	public DataHandler addShovel(String input) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output.copy(), "X", "#", "#", Character.valueOf('#'), "stickWood", Character.valueOf('X'), input));
		return this;
	}

	public DataHandler addSmelt(ItemStack stack, float xp) {
		GameRegistry.addSmelting(getID(), stack, xp);
		return this;
	}

	public DataHandler addSmelt(ItemStack stack, int meta, float xp) {
		FurnaceRecipes.smelting().addSmelting(getID(), meta, stack, xp);
		return this;
	}

	public DataHandler addSword(String input) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output.copy(), "X", "X", "#", Character.valueOf('#'), "stickWood", Character.valueOf('X'), input));
		return this;
	}

	public abstract int getID();

	public abstract String getName();

	public void register() {
		HandlerRegistry.register(this);
	}

	public DataHandler setOutput(int size, int damage) {
		output = new ItemStack(getID(), size, damage);
		return this;
	}
}
