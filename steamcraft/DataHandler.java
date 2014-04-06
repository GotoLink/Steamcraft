package steamcraft;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public abstract class DataHandler<e> {
	protected ItemStack output;
    public DataHandler addAchievement(String name, int j, int k, String parent){
        StatBase stat = StatList.func_151177_a(parent);
        if(stat instanceof Achievement){
            addAchievement(name, j, k, (Achievement) stat);
        }
        return this;
    }

	public DataHandler addAchievement(String name, int j, int k, Achievement parent) {
        StatBase stat = StatList.func_151177_a(name);
        Achievement achievement;
        if(stat instanceof Achievement){
            achievement = (Achievement) stat;
        }else{
            achievement = new Achievement(name, name, j, k, output.copy(), parent).registerStat();
        }
        HandlerRegistry.addAchievement(name, achievement);
		return this;
	}

	public DataHandler addAxe(String input) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output.copy(), "XX", "X#", " #",'#', "stickWood",'X', input));
		return this;
	}

	public DataHandler addBoots(String input) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output.copy(), "X X", "X X",'X', input));
		return this;
	}

	public DataHandler addDrill(Object input) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output.copy(), "XXX", "XXX", "XX#",'#', "stickWood",'X', input));
		return this;
	}

	public DataHandler addHelmet(String input) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output.copy(), "XXX", "X X",'X', input));
		return this;
	}

	public DataHandler addHoe(String input) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output.copy(), "XX", " #", " #",'#', "stickWood",'X', input));
		return this;
	}

	public DataHandler addLegs(String input) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output.copy(), "XXX", "X X", "X X",'X', input));
		return this;
	}

	public DataHandler addPick(String input) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output.copy(), "XXX", " # ", " # ",'#', "stickWood",'X', input));
		return this;
	}

	public DataHandler addPlate(String input) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output.copy(), "X X", "XXX", "XXX",'X', input));
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
		MinecraftForge.addGrassSeed(output.copy(), weight);
		return this;
	}

	public DataHandler addShovel(String input) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output.copy(), "X", "#", "#",'#', "stickWood",'X', input));
		return this;
	}

	public abstract DataHandler addSmelt(ItemStack stack, float xp);

	public abstract DataHandler addSmelt(ItemStack stack, int meta, float xp);

	public DataHandler addSword(String input) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output.copy(), "X", "X", "#",'#', "stickWood",'X', input));
		return this;
	}

	public abstract e get();

	public abstract String getName();

    public abstract Item getItem();

	public void register() {
		HandlerRegistry.register(this);
	}

    public DataHandler setOutput(int size, int damage) {
        this.output = new ItemStack(getItem(), size, damage);
        return this;
    }

    public DataHandler setOutput(ItemStack stack) {
        this.output = stack;
        return this;
    }
}
