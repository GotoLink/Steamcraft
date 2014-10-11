package steamcraft;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public abstract class DataHandler<e> {
    protected final String registryName;
	protected ItemStack output;
    private boolean registered = false;

    protected DataHandler(String name) {
        registryName = name.substring(name.indexOf(":")+1);
    }

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
            if(output==null)
                setOutput(1, 0);
            achievement = new Achievement(name, name, j, k, output.copy(), parent).registerStat();
        }
        HandlerRegistry.addAchievement(name, achievement);
		return this;
	}

	public DataHandler addAxe(String input) {
        addRecipe(true, "XX", "X#", " #",'#', "stickWood",'X', input);
		return this;
	}

	public DataHandler addBoots(String input) {
        addRecipe(true, "X X", "X X",'X', input);
		return this;
	}

	public DataHandler addDrill(Object input) {
        addRecipe(true, "XXX", "XXX", "XX#",'#', "stickWood",'X', input);
		return this;
	}

	public DataHandler addHelmet(String input) {
        addRecipe(true, "XXX", "X X",'X', input);
		return this;
	}

	public DataHandler addHoe(String input) {
        addRecipe(true, "XX", " #", " #",'#', "stickWood",'X', input);
		return this;
	}

	public DataHandler addLegs(String input) {
		addRecipe(true, "XXX", "X X", "X X",'X', input);
		return this;
	}

	public DataHandler addPick(String input) {
		addRecipe(true, "XXX", " # ", " # ",'#', "stickWood",'X', input);
		return this;
	}

	public DataHandler addPlate(String input) {
		addRecipe(true, "X X", "XXX", "XXX",'X', input);
		return this;
	}

	public DataHandler addRecipe(boolean shaped, Object... inputs) {
        if(output==null)
            setOutput(1, 0);
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
        if(output==null)
            setOutput(1, 0);
		MinecraftForge.addGrassSeed(output.copy(), weight);
		return this;
	}

	public DataHandler addShovel(String input) {
		addRecipe(true, "X", "#", "#",'#', "stickWood",'X', input);
		return this;
	}

    public DataHandler addSmelt(ItemStack stack, float xp) {
        checkRegistry();
        FurnaceRecipes.smelting().func_151396_a(getItem(), stack, xp);
        return this;
    }

    public DataHandler addSmelt(ItemStack stack, int meta, float xp) {
        checkRegistry();
        FurnaceRecipes.smelting().func_151394_a(new ItemStack(getItem(), 1, meta), stack, xp);
        return this;
    }

	public DataHandler addSword(String input) {
		addRecipe(true, "X", "X", "#",'#', "stickWood",'X', input);
		return this;
	}

	public abstract e get();

	public abstract String getName();

    public abstract Item getItem();

	public DataHandler register(boolean asInternal) {
		if(asInternal)
            HandlerRegistry.register(this);
        registered = true;
        setOutput(1, 0);
        return this;
	}

    public DataHandler setOutput(int size, int damage) {
        checkRegistry();
        this.output = new ItemStack(getItem(), size, damage);
        return this;
    }

    public DataHandler setOutput(ItemStack stack) {
        checkRegistry();
        this.output = stack;
        return this;
    }

    private void checkRegistry(){
        if(!registered)
            register(false);
    }
}
