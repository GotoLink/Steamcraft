package steamcraft;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLogic;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import steamcraft.blocks.*;
import steamcraft.items.*;
import cpw.mods.fml.common.ICraftingHandler;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.IPickupNotifier;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = "steamcraft", name = "SteamCraft", version = "0.3")
@NetworkMod(clientSideRequired = true)
public class Steamcraft implements ICraftingHandler, IPickupNotifier, IWorldGenerator, IFuelHandler {
	@Instance(value = "steamcraft")
	public static Steamcraft instance;
	@SidedProxy(clientSide = "steamcraft.ClientProxy", serverSide = "steamcraft.CommonProxy")
	public static CommonProxy proxy;
	public static Map<String, Achievement> achs = new HashMap<String, Achievement>();
	public static final int[] REDUCTION_AMOUNTS = new int[] { 3, 8, 6, 3 };
	public static final EnumArmorMaterial ARMORBRASS = EnumHelper.addArmorMaterial("BRASS", 5, REDUCTION_AMOUNTS, 0);
	public static final EnumArmorMaterial ARMORETHERIUM = EnumHelper.addArmorMaterial("ETHERIUM", -1, REDUCTION_AMOUNTS, 5);
	public static final EnumArmorMaterial ARMOROBSIDIAN = EnumHelper.addArmorMaterial("OBSIDIAN", 20, REDUCTION_AMOUNTS, 10);
	public static final String[] ARMOR_NAMES = { "etherium", "brass", "obsidian" };
	//harvestLevel, maxUses,efficiencyOnProperMaterial,damageVsEntity,enchantability;
	public static final EnumToolMaterial TOOLETHERIUM = EnumHelper.addToolMaterial("ETHERIUM", 6, -1, 8F, 3, 8);
	public static final EnumToolMaterial TOOLOBSIDIAN = EnumHelper.addToolMaterial("OBSIDIAN", 5, 210, 7F, 4, 5);
	public static final EnumToolMaterial TOOLSTEAM = EnumHelper.addToolMaterial("STEAM", 2, 321, 12F, 5, 0);
	private static final String[] FIREARM_PARTS = { "musketcartridge", "percussioncap", "percussionlock", "smoothbarrel", "rifledbarrel", "woodenstock" };
	private static final String[] MATERIALS = { "etherium", "sulphur", "copper", "obsidianslate", "ingotbrass", "ingotcastiron", "lightbulb", "phosphorus", "uraniumstone", "uraniumpellet",
			"reactorcore", "coredrillbase", "ingotzinc" };
	public static final Material solidcircuit = new MaterialLogic(MapColor.airColor);
	public static final Material staticcircuit = new StaticMaterial(MapColor.airColor);
	public static final CreativeTabs steamTab = new CreativeTabs("Steamcraft") {
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return HandlerRegistry.getItem("steamcraft:coreDrill").get();
		}
	};
	public static Map<Object, Object[]> data = new HashMap<Object, Object[]>();
	public static ItemStack flintlockMusket, matchlockMusket, percussionCapMusket;
	public static ItemStack flintlockRifle, matchlockRifle, percussionCapRifle;
	public static Item spanner;
	public static Item firearm, part;
	public static Item material;
	private static final WorldGenerator netherGen = new WorldGenNetherTrees();
	private static final WorldGenerator hideoutGen = new WorldGenHighwaymanHideout();
	private static WorldGenerator brimstoneGen, zincGen, bornGen, phosphGen, uranGen, volucGen;
	public static int genNetherTree = 20, genHideout = 8, genBrin = 12, genZinc = 6, genBorn = 20, genPhos = 3, genUr = 2, genVol = 1;

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if (world.provider.isHellWorld)
			generateNether(world, random, chunkX * 16, chunkZ * 16);
		else
			generateSurface(world, random, chunkX * 16, chunkZ * 16);
	}

	public void generateNether(World world, Random rand, int k, int l) {
		if (genNetherTree > 0) {
			for (int i = 0; i < genNetherTree; i++) {
				int x = k + rand.nextInt(16);
				int y = rand.nextInt(128);
				int z = l + rand.nextInt(16);
				netherGen.generate(world, rand, x, y, z);
			}
		}
	}

	public void generateSurface(World world, Random rand, int k, int l) {
		int x, y, z;
		if (genBrin > 0) {
			for (int i = 0; i < genBrin; i++) {
				x = k + rand.nextInt(16);
				y = rand.nextInt(64);
				z = l + rand.nextInt(16);
				brimstoneGen.generate(world, rand, x, y, z);
			}
		}
		if (genZinc > 0) {
			for (int i = 0; i < genZinc; i++) {
				x = k + rand.nextInt(16);
				y = rand.nextInt(64);
				z = l + rand.nextInt(16);
				zincGen.generate(world, rand, x, y, z);
			}
		}
		if (genBorn > 0) {
			for (int i = 0; i < genBorn; i++) {
				x = k + rand.nextInt(16);
				y = rand.nextInt(48);
				z = l + rand.nextInt(16);
				bornGen.generate(world, rand, x, y, z);
			}
		}
		if (genPhos > 0) {
			for (int i = 0; i < genPhos; i++) {
				x = k + rand.nextInt(16);
				y = rand.nextInt(36);
				z = l + rand.nextInt(16);
				phosphGen.generate(world, rand, x, y, z);
			}
		}
		if (genUr > 0) {
			for (int i = 0; i < genUr; i++) {
				x = k + rand.nextInt(16);
				y = rand.nextInt(24);
				z = l + rand.nextInt(16);
				uranGen.generate(world, rand, x, y, z);
			}
		}
		if (genVol > 0) {
			for (int i = 0; i < genVol; i++) {
				x = k + rand.nextInt(16);
				y = rand.nextInt(16) + 12;
				z = l + rand.nextInt(16);
				volucGen.generate(world, rand, x, y, z);
			}
		}
		if (genHideout > 0) {
			for (int i = 0; i < genHideout; i++) {
				x = k + rand.nextInt(16) + 8;
				y = rand.nextInt(128);
				z = l + rand.nextInt(16) + 8;
				hideoutGen.generate(world, rand, x, y, z);
			}
		}
	}

	@Override
	public int getBurnTime(ItemStack fuel) {
		if (TileEntityChemFurnace.fuels.containsKey(fuel.itemID)) {
			return TileEntityChemFurnace.fuels.get(fuel.itemID);
		} else if (fuel.itemID == material.itemID) {
			switch (fuel.getItemDamage()) {
			case 1:
				return 1000;
			case 2:
				return 200;
			case 7:
				return 1600;
			case 9:
				return 3200;
			}
		}
		return 0;
	}

	@EventHandler
	public void load(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		config.addCustomCategoryComment("Generation", "TPC:Tries per chunk");
		genNetherTree = config.get("Generation", "Nether_Trees_TPC", genNetherTree).getInt();
		genHideout = config.get("Generation", "Highwayman_Hideout_TPC", genHideout).getInt();
		genBrin = config.get("Generation", "Brimstone_TPC", genBrin).getInt();
		genZinc = config.get("Generation", "ZincOre_TPC", genZinc).getInt();
		genBorn = config.get("Generation", "Bornite_TPC", genBorn).getInt();
		genPhos = config.get("Generation", "PhosphateOre_TPC", genPhos).getInt();
		genUr = config.get("Generation", "Uranite_TPC", genUr).getInt();
		genVol = config.get("Generation", "Volucite_TPC", genVol).getInt();
		material = new ItemHandler(new MultiItem(config.getItem("Materials", 25010).getInt(), MATERIALS), "steamcraft:mat", "steamcraft:").get();
		new ItemHandler(new Item(config.getItem("Chisel", 25049).getInt()).setFull3D().setMaxDamage(64).setMaxStackSize(1), "steamcraft:chisel", "steamcraft:tools/chisel").addRecipe(true, "#", "#",
				"X", Character.valueOf('#'), Item.ingotIron, Character.valueOf('X'), "ingotBrass").register();
		new BlockHandler(new BlockCopperWire(config.getBlock("CopperWire", 2493).getInt()).setStepSound(Block.soundPowderFootstep), "copperwire", "redstone_dust", "wireCopper").setValues(0.0F)
				.setOutput(4, 0).addRecipe(false, "dustCopper").register();
		new BlockHandler(new BlockInverter(config.getBlock("Inverter", 2494).getInt(), false).setStepSound(Block.soundWoodFootstep), "steamcraft:inverteridle", "steamcraft:inverteridle").setValues(
				0.0F).register();
		new BlockHandler(new BlockInverter(config.getBlock("InverterON", 2495).getInt(), true).setStepSound(Block.soundWoodFootstep), "steamcraft:inverteractive", "steamcraft:inverteractive",
				"torchCopper").setValues(0.0F, 0.5F).setOutput(4, 0)
				.addRecipe(true, "X", "#", "I", Character.valueOf('#'), "stickWood", Character.valueOf('X'), "wireCopper", Character.valueOf('I'), "battery").register();
		new BlockHandler(new BlockDiode(config.getBlock("Repeater", 2496).getInt(), false).setStepSound(Block.soundWoodFootstep), "steamcraft:diodeidle", "steamcraft:diodeidle").setValues(0.0F)
				.addRecipe(true, "#X#", "IRI", Character.valueOf('#'), "torchCopper", Character.valueOf('X'), "wireCopper", Character.valueOf('I'), "stone", Character.valueOf('R'), Item.netherQuartz)
				.register();
		new BlockHandler(new BlockDiode(config.getBlock("RepeaterON", 2497).getInt(), true).setStepSound(Block.soundWoodFootstep), "steamcraft:diodeactive", "steamcraft:diodeactive").setValues(0.0F,
				0.625F).register();
		new BlockHandler(new BlockPoweredRail(config.getBlock("Rail", 2498).getInt(), true).setStepSound(Block.soundMetalFootstep), "steamcraft:rail", "steamcraft:rail").setValues(0.7F).register();
		new BlockHandler(new BlockNetherSapling(config.getBlock("NetherSapling", 2499).getInt()), "steamcraft.nethersapling", "steamcraft:nethersapling", "saplingNether").register();
		new BlockHandler(new BlockElectricLamp(config.getBlock("ElectricLamp", 2500).getInt(), TileEntityLamp.class, false), "steamcraft:electricLampOff", "steamcraft:electriclamp").setValues(0.0F)
				.register();
		new BlockHandler(new BlockElectricLamp(config.getBlock("ElectricLampON", 2501).getInt(), TileEntityLamp.class, true), "steamcraft:electricLampOn", "steamcraft:electriclamp").setValues(0.0F,
				1.0F).register();
		new BlockHandler(new BlockTeslaCoil(config.getBlock("TeslaCoil", 2502).getInt(), false), "steamcraft:teslaCoil", "steamcraft:teslaidle")
				.setValues(0.0F)
				.addRecipe(true, " X ", "I#I", "ITI", Character.valueOf('#'), Item.ingotGold, Character.valueOf('X'), "itemLightBulb", Character.valueOf('I'), "wireCopper", Character.valueOf('T'),
						Item.netherQuartz).register();
		new BlockHandler(new BlockTeslaCoil(config.getBlock("TeslaCoilON", 2053).getInt(), true), "steamcraft:teslaCoilOn", "steamcraft:teslaactive").setValues(0.0F, 0.625F)
				.addAchievement("itsalive", 1, 2, AchievementList.acquireIron).register();
		new BlockHandler(new BlockTeslaReceiver(config.getBlock("Receiver", 2504).getInt()).setStepSound(Block.soundMetalFootstep), "steamcraft:receiver", "steamcraft:receiver", "teslaReceiver")
				.setValues(0.5F)
				.addRecipe(true, "#X#", "ITI", Character.valueOf('#'), "ingotCastIron", Character.valueOf('X'), Item.ingotGold, Character.valueOf('I'), "wireCopper", Character.valueOf('T'),
						Item.netherQuartz).register();
		new BlockHandler(new BlockOre(config.getBlock("ZincOre", 2505).getInt()).setResistance(5F).setStepSound(Block.soundStoneFootstep), "steamcraft:orezinc", "steamcraft:zincore", "oreZinc")
				.setValues(2.5F).setHarvest("pickaxe", 1).setHarvest("drill", 1).addSmelt(new ItemStack(material, 1, 12), 1.0F).register();
		new BlockHandler(new BlockTeslaReceiver(config.getBlock("ReceiverON", 2506).getInt()).setStepSound(Block.soundMetalFootstep), "steamcraft:receiverOn", "steamcraft:receiveractive").setValues(
				0.5F, 0.625F).register();
		new BlockHandler(new BlockSteamFurnace(config.getBlock("SteamFurnace", 2507).getInt(), false).setStepSound(Block.soundMetalFootstep), "steamcraft:steamFurnace", "steamcraft:steamfurnaceidle",
				"furnaceSteam").setValues(4F)
				.addRecipe(true, "# #", "#X#", "#I#", Character.valueOf('#'), "ingotBrass", Character.valueOf('X'), Item.bucketEmpty, Character.valueOf('I'), Block.furnaceIdle).register();
		new BlockHandler(new BlockSteamFurnace(config.getBlock("SteamFurnaceON", 2508).getInt(), true).setStepSound(Block.soundMetalFootstep), "steamcraft:steamFurnaceOn",
				"steamcraft:steamfurnaceactive").setValues(4F, 0.875F).register();
		new BlockHandler(new BlockChemFurnace(config.getBlock("ChemicalFurnace", 2509).getInt(), false).setStepSound(Block.soundMetalFootstep), "steamcraft:chemFurnace", "steamcraft:chemfurnaceidle",
				"furnaceChemical").setValues(4.5F)
				.addRecipe(true, "###", "#X#", "#I#", Character.valueOf('#'), "ingotCastIron", Character.valueOf('X'), Item.diamond, Character.valueOf('I'), "furnaceSteam").register();
		new BlockHandler(new BlockChemFurnace(config.getBlock("ChemicalFurnaceON", 2510).getInt(), true).setStepSound(Block.soundMetalFootstep), "steamcraft:chemFurnaceOn",
				"steamcraft:chemfurnaceactive").setValues(4.5F, 0.875F).register();
		new BlockHandler(new BlockNukeFurnace(config.getBlock("NuclearFurnace", 2511).getInt(), false).setStepSound(Block.soundMetalFootstep), "steamcraft:nukeFurnace", "steamcraft:nukefurnaceidle",
				"furnaceNuke").setValues(5F)
				.addRecipe(true, "#I#", "#X#", "#I#", Character.valueOf('#'), Item.ingotIron, Character.valueOf('X'), "itemReactorCore", Character.valueOf('I'), "gemEtherium")
				.addAchievement("fallout", 0, 1, AchievementList.acquireIron).register();
		new BlockHandler(new BlockNukeFurnace(config.getBlock("NuclearFurnaceON", 2512).getInt(), true).setStepSound(Block.soundMetalFootstep), "steamcraft:nukeFurnaceOn",
				"steamcraft:nukefurnaceactive").setValues(5F, 0.9375F).register();
		new BlockHandler(new BlockBattery(config.getBlock("Battery", 2513).getInt()).setStepSound(Block.soundMetalFootstep), "steamcraft:battery", "steamcraft:battery", "battery")
				.setValues(0.5F, 0.625F).addRecipe(true, "###", "IXI", Character.valueOf('#'), Item.ingotIron, Character.valueOf('X'), Item.netherQuartz, Character.valueOf('I'), "wireCopper")
				.register();
		new BlockHandler(new BlockOre(config.getBlock("BrimstoneOre", 2514).getInt()).setResistance(5F).setStepSound(Block.soundStoneFootstep), "steamcraft:brimstone", "steamcraft:brimstone",
				"oreBrimstone").setValues(3F).setHarvest("pickaxe", 2).addSmelt(new ItemStack(material, 1, 1), 1.0F).register();
		new BlockHandler(new BlockOre(config.getBlock("PhosphateOre", 2515).getInt()).setResistance(5F).setStepSound(Block.soundStoneFootstep), "steamcraft:orePhosphate", "steamcraft:phosphate",
				"orePhosphate").setValues(2.5F, 0.75F).setHarvest("pickaxe", 2).addSmelt(new ItemStack(material, 1, 7), 1.0F).register();
		new BlockHandler(new BlockUraniteOre(config.getBlock("UraniteOre", 2516).getInt()).setResistance(6F).setStepSound(Block.soundStoneFootstep), "steamcraft:oreUranite", "steamcraft:uranite",
				"oreUranite").setValues(10F, 0.625F).setHarvest("pickaxe", 2).addSmelt(new ItemStack(material, 1, 8), 1.0F).register();
		new BlockHandler(new BlockOre(config.getBlock("BorniteOre", 2517).getInt()).setResistance(5F).setStepSound(Block.soundStoneFootstep), "steamcraft:oreBornite", "steamcraft:bornite",
				"oreCopper").setValues(3F).setHarvest("pickaxe", 2).addSmelt(new ItemStack(material, 1, 2), 1.0F).register();
		new BlockHandler(new BlockSCOre(config.getBlock("VoluciteOre", 2518).getInt()).setResistance(6000000F).setStepSound(Block.soundStoneFootstep), "steamcraft:oreVolucite",
				"steamcraft:voluciteore", "oreVolucite").setValues(50F).setHarvest("pickaxe", 5).addSmelt(new ItemStack(material, 1, 0), 1.0F).register();
		new BlockHandler(new BlockTorchPhosphorus(config.getBlock("PhosphorusTorch", 2519).getInt()).setStepSound(Block.soundWoodFootstep), "steamcraft:torchPhosphorus", "steamcraft:torchphosphorus")
				.setValues(0.0F, 1.0F).setOutput(4, 0).addRecipe(true, "X", "#", Character.valueOf('#'), "stickWood", Character.valueOf('X'), "ingotPosphate").register();
		BlockHandler roofTile = new BlockHandler(new Block(config.getBlock("SlateTiles", 2520).getInt(), Material.rock).setResistance(10F).setStepSound(Block.soundStoneFootstep),
				"steamcraft:roofTile", "steamcraft:slatetiles", "stairFlint").setValues(2F).setHarvest("pickaxe", 0).setHarvest("drill", 0);
		roofTile.setOutput(4, 0).addRecipe(true, "###", "###", "###", Character.valueOf('#'), Item.flint).register();
		DataHandler decor = new BlockHandler(new BlockDecor(config.getBlock("CarvedBlock", 2521).getInt()), ItemBlockDecor.class, "steamcraft:decor", "steamcraft:decor").setHarvest("pickaxe", 0)
				.setHarvest("drill", 0).addAchievement("mastercraftsman", 1, 3, AchievementList.acquireIron);
		int[] dmgs = { 5, 0, 4, 8 };
		ItemStack in = new ItemStack(material, 9);
		for (int i = 0; i < 4; i++) {
			in.setItemDamage(dmgs[i]);
			decor.setOutput(1, i).addRecipe(true, "###", "###", "###", Character.valueOf('#'), in);
			GameRegistry.addRecipe(in, "#", Character.valueOf('#'), new ItemStack(decor.getID(), 1, i));
		}
		ItemStack[] in2 = { new ItemStack(Block.blockIron), new ItemStack(Block.blockGold), new ItemStack(Block.blockDiamond), new ItemStack(decor.getID(), 1, 0), new ItemStack(decor.getID(), 1, 1),
				new ItemStack(decor.getID(), 1, 2), new ItemStack(Block.blockLapis), new ItemStack(Block.stone), new ItemStack(decor.getID(), 1, 3) };
		for (int i = 4; i < 13; i++) {
			decor.setOutput(1, i);
			decor.addRecipe(false, in2[i - 4], "chisel");
			decor.addSmelt(in2[i - 4], i, 1.0F);
		}
		decor.register();
		BlockHandler fencegate = new BlockHandler(new BlockSCFenceGate(config.getBlock("CastIronGate", 2536).getInt()).setResistance(20F).setStepSound(Block.soundMetalFootstep),
				"steamcraft:gateCastIron", "steamcraft:castironblock", "gateCastIron").setValues(7F);
		fencegate.addRecipe(true, "#X#", "#X#", Character.valueOf('#'), "ingotCastIron", Character.valueOf('X'), "railCastIron").register();
		new BlockHandler(new BlockSCFence(config.getBlock("CastIronRailing", 2537).getInt(), Material.iron, fencegate.get(), true).setResistance(20F).setStepSound(Block.soundMetalFootstep),
				"steamcraft:railingCastIron", "steamcraft:castironblock", "railCastIron").setValues(7F).setHarvest("pickaxe", 0).setHarvest("drill", 0).setOutput(2, 0)
				.addRecipe(true, "###", "###", Character.valueOf('#'), "ingotCastIron").register();
		new BlockHandler(new BlockLamp(config.getBlock("LampBlock", 2538).getInt(), true), "steamcraft:lampOn", "steamcraft:lampblock").setHarvest("pickaxe", 0).setHarvest("drill", 0)
				.addRecipe(true, "#X#", "XIX", "#X#", Character.valueOf('#'), "ingotCastIron", Character.valueOf('X'), Block.glass, Character.valueOf('I'), Item.glowstone).register();
		new BlockHandler(new BlockLamp(config.getBlock("LampBlockOFF", 2539).getInt(), false), "steamcraft:lamp", "steamcraft:lampblock").register();
		new BlockHandler(new BlockBrassLog(config.getBlock("BrassLog", 2540).getInt()).setStepSound(Block.soundMetalFootstep), "steamcraft:logBrass", "steamcraft:brasslog").setValues(5F)
				.setHarvest("pickaxe", 2).setHarvest("drill", 2).setOutput(4, 0).addRecipe(true, "###", "#I#", "###", Character.valueOf('#'), "ingotBrass", Character.valueOf('I'), "logWood")
				.register();
		new BlockHandler(new BlockNetherLeaves(config.getBlock("BrassLeaves", 2541).getInt(), Material.wood).setLightOpacity(1).setStepSound(Block.soundGlassFootstep), "steamcraft:leavesLamp",
				"steamcraft:brassleaves").setValues(2F, 0.9375F).setHarvest("pickaxe", 0).setHarvest("drill", 0).setOutput(4, 0)
				.addRecipe(true, "#X#", "XIX", "#X#", Character.valueOf('#'), "ingotBrass", Character.valueOf('X'), Block.glass, Character.valueOf('I'), Item.glowstone).register();
		new BlockHandler(new BlockWirelessLamp(config.getBlock("WirelessLamp", 2542).getInt(), TileEntityLamp.class, false), "steamcraft:wirelessLampOff", "steamcraft:wirelesslamp").setValues(0.0F)
				.register();
		new BlockHandler(new BlockWirelessLamp(config.getBlock("WirelessLampON", 2543).getInt(), TileEntityLamp.class, true), "steamcraft:wirelessLampOn", "steamcraft:wirelesslamp").setValues(0.0F,
				1.0F).register();
		new BlockHandler(new BlockSCStairs(config.getBlock("SlateStairs", 2545).getInt(), roofTile.get(), 0, Item.flint.itemID, 2), "steamcraft:stairsRoof", "steamcraft:slatetiles").setOutput(4, 0)
				.addRecipe(true, "#  ", "## ", "###", Character.valueOf('#'), Item.flint).register();
		new BlockHandler(new BlockTeaPlant(config.getBlock("TeaPlant", 2546).getInt()).setStepSound(Block.soundGrassFootstep), "steamcraft:teaplant", "steamcraft:teaplant", "plantTea")
				.setValues(0.0F).register();
		new ItemHandler(new ItemCoreDrill(config.getItem("CoreDrill", 25011).getInt()), "steamcraft:coreDrill", "steamcraft:coredrill")
				.addRecipe(true, "X", "#", "I", Character.valueOf('#'), "ingotPosphate", Character.valueOf('X'), "drillGold", Character.valueOf('I'), "itemDrillBase")
				.addAchievement("heavenpiercing", 3, -1, achs.get("spiralnemesis")).register();
		new ItemHandler(new ItemSCPickaxe(config.getItem("ObsidianPick", 25013).getInt(), TOOLOBSIDIAN), "steamcraft:pickaxeObsidian", "steamcraft:tools/obsidianpick").setTool("pickaxe", 5)
				.addAchievement("blackmagic", 3, 2, AchievementList.buildBetterPickaxe).addPick("slateObsidian").register();
		new ItemHandler(new ItemSpade(config.getItem("ObsidianSpade", 25014).getInt(), TOOLOBSIDIAN), "steamcraft:shovelObsidian", "steamcraft:tools/obsidianspade").setTool("shovel", 5).addShovel(
				"slateObsidian");
		new ItemHandler(new ItemSCAxe(config.getItem("ObsidianAxe", 25015).getInt(), TOOLOBSIDIAN), "steamcraft:hatchetObsidian", "steamcraft:tools/obsidianaxe").setTool("axe", 5).addAxe(
				"slateObsidian");
		new ItemHandler(new ItemHoe(config.getItem("ObsidianHoe", 25016).getInt(), TOOLOBSIDIAN), "steamcraft:hoeObsidian", "steamcraft:tools/obsidianhoe").addHoe("slateObsidian");
		new ItemHandler(new ItemSCSword(config.getItem("ObsidianSword", 25017).getInt(), TOOLOBSIDIAN), "steamcraft:swordObsidian", "steamcraft:tools/obsidiansword").addSword("slateObsidian");
		new ItemHandler(new ItemSCDrill(config.getItem("ObsidianDrill", 25018).getInt(), TOOLOBSIDIAN), "steamcraft:drillObsidian", "steamcraft:tools/obsidiandrill", "drillObsidian").setTool("drill",
				5).addDrill("slateObsidian");
		new ItemHandler(new ItemSCArmor(config.getItem("ObsidianHelmet", 25019).getInt(), ARMOROBSIDIAN, 2, 0), "steamcraft:helmetObsidian", "steamcraft:armour/obsidianhelmet")
				.addHelmet("slateObsidian");
		new ItemHandler(new ItemSCArmor(config.getItem("ObsidianPlate", 25020).getInt(), ARMOROBSIDIAN, 2, 1), "steamcraft:chestplateObsidian", "steamcraft:armour/obsidianplate")
				.addPlate("slateObsidian");
		new ItemHandler(new ItemSCArmor(config.getItem("ObsidianLegs", 25021).getInt(), ARMOROBSIDIAN, 2, 2), "steamcraft:leggingsObsidian", "steamcraft:armour/obsidianlegs")
				.addLegs("slateObsidian");
		new ItemHandler(new ItemSCArmor(config.getItem("ObsidianBoots", 25022).getInt(), ARMOROBSIDIAN, 2, 3), "steamcraft:bootsObsidian", "steamcraft:armour/obsidianboots")
				.addBoots("slateObsidian");
		new ItemHandler(new ItemSCArmor(config.getItem("BrassGoggles", 25023).getInt(), ARMORBRASS, 1, 0), "steamcraft:brassGoggles", "steamcraft:armour/brassgoggles").addRecipe(true,
				"X#X", "# #", Character.valueOf('X'), Block.glass, Character.valueOf('#'), "ingotBrass").register();
		new ItemHandler(new ItemSCArmor(config.getItem("Aqualung", 25024).getInt(), ARMORBRASS, 1, 1), "steamcraft:aqualung", "steamcraft:armour/aqualung")
				.addRecipe(true, "XTX", "X X", "X#X", Character.valueOf('X'), "ingotBrass", Character.valueOf('#'), Block.pistonBase, Character.valueOf('T'), Block.glass)
				.addAchievement("jethrotull", 0, 3, AchievementList.acquireIron).register();
		new ItemHandler(new ItemSCArmor(config.getItem("RollerSkates", 25025).getInt(), ARMORBRASS, 1, 3), "steamcraft:rollerSkates", "steamcraft:armour/rollerskates").addRecipe(true,
				"X X", "X X", "# #", Character.valueOf('X'), "ingotBrass", Character.valueOf('#'), Item.ingotIron).register();
		new ItemHandler(new ItemSCArmor(config.getItem("PneumaticBraces", 25026).getInt(), ARMORBRASS, 1, 2), "steamcraft:legBraces", "steamcraft:armour/pneumaticbraces").addRecipe(
				true, "XXX", "X X", "# #", Character.valueOf('X'), "ingotBrass", Character.valueOf('#'), Block.pistonBase).register();
		new ItemHandler(new ItemSCPickaxe(config.getItem("EtheriumPick", 25027).getInt(), TOOLETHERIUM), "steamcraft:pickaxeEtherium", "steamcraft:tools/etheriumpick").setTool("pickaxe", 6).addPick(
				"gemEtherium");
		new ItemHandler(new ItemSpade(config.getItem("EtheriumSpade", 25028).getInt(), TOOLETHERIUM), "steamcraft:shovelEtherium", "steamcraft:tools/etheriumspade").setTool("shovel", 6).addShovel(
				"gemEtherium");
		new ItemHandler(new ItemSCAxe(config.getItem("EtheriumAxe", 25029).getInt(), TOOLETHERIUM), "steamcraft:hatchetEtherium", "steamcraft:tools/etheriumaxe").setTool("axe", 6).addAxe(
				"gemEtherium");
		new ItemHandler(new ItemHoe(config.getItem("EtheriumHoe", 25030).getInt(), TOOLETHERIUM), "steamcraft:hoeEtherium", "steamcraft:tools/etheriumhoe").addHoe("gemEtherium");
		new ItemHandler(new ItemSCSword(config.getItem("EtheriumSword", 25031).getInt(), TOOLETHERIUM), "steamcraft:swordEtherium", "steamcraft:tools/etheriumsword").addSword("gemEtherium");
		new ItemHandler(new ItemSCDrill(config.getItem("EtheriumDrill", 25032).getInt(), TOOLETHERIUM), "steamcraft:drillEtherium", "steamcraft:tools/etheriumdrill", "drillEtherium").setTool("drill",
				6).addDrill("gemEtherium");
		new ItemHandler(new ItemSCArmor(config.getItem("EtheriumHelmet", 25033).getInt(), ARMORETHERIUM, 0, 0), "steamcraft:helmetEtherium", "steamcraft:armour/etheriumhelmet")
				.addHelmet("gemEtherium");
		new ItemHandler(new ItemSCArmor(config.getItem("EtheriumPlate", 25034).getInt(), ARMORETHERIUM, 0, 1), "steamcraft:chestplateEtherium", "steamcraft:armour/etheriumplate")
				.addPlate("gemEtherium");
		new ItemHandler(new ItemSCArmor(config.getItem("EtheriumLegs", 25036).getInt(), ARMORETHERIUM, 0, 2), "steamcraft:leggingsEtherium", "steamcraft:armour/etheriumlegs")
				.addLegs("gemEtherium");
		new ItemHandler(new ItemSCArmor(config.getItem("EtheriumBoots", 25037).getInt(), ARMORETHERIUM, 0, 3), "steamcraft:bootsEtherium", "steamcraft:armour/etheriumboots")
				.addBoots("gemEtherium");
		new ItemHandler(new ItemSCPickaxe(config.getItem("SteamPick", 25038).getInt(), TOOLSTEAM), "steamcraft:pickaxeSteam", "steamcraft:tools/steampick").setTool("pickaxe", 7).addRecipe(true,
				"XIX", " # ", " # ", Character.valueOf('#'), "stickWood", Character.valueOf('X'), "ingotBrass", Character.valueOf('I'), "furnaceSteam");
		new ItemHandler(new ItemSCSpade(config.getItem("SteamSpade", 25039).getInt(), TOOLSTEAM), "steamcraft:shovelSteam", "steamcraft:tools/steamspade").setTool("shovel", 7).addRecipe(true, "X",
				"#", "I", Character.valueOf('#'), "stickWood", Character.valueOf('X'), "ingotBrass", Character.valueOf('I'), "furnaceSteam");
		new ItemHandler(new ItemSCAxe(config.getItem("SteamAxe", 25040).getInt(), TOOLSTEAM), "steamcraft:hatchetSteam", "steamcraft:tools/steamaxe").setTool("axe", 7).addRecipe(true, "X ", "XI",
				"# ", Character.valueOf('#'), "stickWood", Character.valueOf('X'), "ingotBrass", Character.valueOf('I'), "furnaceSteam");
		new ItemHandler(new ItemSCHoe(config.getItem("SteamHoe", 25041).getInt(), TOOLSTEAM), "steamcraft:hoeSteam", "steamcraft:tools/steamhoe").addRecipe(true, "XI", " #", " #",
				Character.valueOf('#'), "stickWood", Character.valueOf('X'), "ingotBrass", Character.valueOf('I'), "furnaceSteam");
		new ItemHandler(new ItemSCSword(config.getItem("SteamSword", 25042).getInt(), TOOLSTEAM), "steamcraft:swordSteam", "steamcraft:tools/steamsword").addRecipe(true, "X", "I", "#",
				Character.valueOf('#'), "stickWood", Character.valueOf('X'), "ingotBrass", Character.valueOf('I'), "furnaceSteam");
		new ItemHandler(new ItemSCDrill(config.getItem("SteamDrill", 25043).getInt(), TOOLSTEAM), "steamcraft:drillSteam", "steamcraft:tools/steamdrill", "drillSteam").setTool("drill", 7).addRecipe(
				true, "XXX", "XIX", "XX#", Character.valueOf('#'), "stickWood", Character.valueOf('X'), "ingotBrass", Character.valueOf('I'), "furnaceSteam");
		new ItemHandler(new ItemSCDrill(config.getItem("IronDrill", 25044).getInt(), EnumToolMaterial.IRON), "steamcraft:drillIron", "steamcraft:tools/irondrill", "drillIron").setTool("drill", 2)
				.addDrill(Item.ingotIron).addAchievement("spiralnemesis", 2, -1, AchievementList.buildWorkBench);
		new ItemHandler(new ItemSCDrill(config.getItem("WoodenDrill", 25045).getInt(), EnumToolMaterial.WOOD), "steamcraft:drillWood", "steamcraft:tools/wooddrill", "drillWood").setTool("drill", 0)
				.addDrill("plankWood");
		new ItemHandler(new ItemSCDrill(config.getItem("StoneDrill", 25046).getInt(), EnumToolMaterial.STONE), "steamcraft:drillStone", "steamcraft:tools/stonedrill", "drillStone")
				.setTool("drill", 1).addDrill("cobblestone");
		new ItemHandler(new ItemSCDrill(config.getItem("DiamondDrill", 25047).getInt(), EnumToolMaterial.EMERALD), "steamcraft:drillDiamond", "steamcraft:tools/diamonddrill", "drillDiamond").setTool(
				"drill", 3).addDrill(Item.diamond);
		new ItemHandler(new ItemSCDrill(config.getItem("GoldenDrill", 25048).getInt(), EnumToolMaterial.GOLD), "steamcraft:drillGold", "steamcraft:tools/golddrill", "drillGold").setTool("drill", 0)
				.addDrill(Item.ingotGold);
		spanner = new ItemHandler(new Item(config.getItem("Spanner", 25050).getInt()).setFull3D().setMaxDamage(3).setMaxStackSize(1), "steamcraft:spanner"
				, "steamcraft:tools/spanner").get();
		part = new ItemHandler(new MultiItem(config.getItem("FirearmParts", 25056).getInt(), FIREARM_PARTS), "steamcraft:part", "steamcraft:").get();
		firearm = new ItemHandler(new ItemFirearm(config.getItem("Firearm", 25057).getInt()), "steamcraft:firearm", "steamcraft:tools/").get();

        flintlockMusket = new ItemStack(firearm, 1, 0);
		ItemFirearm.setFirePower(flintlockMusket, 8);
		ItemFirearm.setMaxDamage(flintlockMusket, 100);
		matchlockMusket = new ItemStack(firearm, 1, 0);
		ItemFirearm.setFirePower(matchlockMusket, 6);
		ItemFirearm.setMaxDamage(matchlockMusket, 200);
		percussionCapMusket = new ItemStack(firearm, 1, 0);
		ItemFirearm.setFirePower(percussionCapMusket, 10);
		ItemFirearm.setMaxDamage(percussionCapMusket, 50);
		ItemFirearm.setPercussion(percussionCapMusket);
		flintlockRifle = new ItemStack(firearm, 1, 0);
		ItemFirearm.setFirePower(flintlockRifle, 10);
		ItemFirearm.setMaxDamage(flintlockRifle, 120);
		ItemFirearm.setRifled(flintlockRifle);
		matchlockRifle = new ItemStack(firearm, 1, 0);
		ItemFirearm.setFirePower(matchlockRifle, 8);
		ItemFirearm.setMaxDamage(matchlockRifle, 240);
		ItemFirearm.setRifled(matchlockRifle);
		percussionCapRifle = new ItemStack(firearm, 1, 0);
		ItemFirearm.setFirePower(percussionCapRifle, 12);
		ItemFirearm.setMaxDamage(percussionCapRifle, 60);
		ItemFirearm.setRifled(percussionCapRifle);
		ItemFirearm.setPercussion(percussionCapRifle);
		EntityHighwayman.heldItems[0] = flintlockMusket;
		EntityHighwayman.heldItems[1] = flintlockRifle;
		EntityHighwayman.heldItems[2] = matchlockMusket;
		EntityHighwayman.heldItems[3] = matchlockRifle;
		EntityHighwayman.heldItems[4] = percussionCapMusket;
		EntityHighwayman.heldItems[5] = percussionCapRifle;
		new ItemHandler(new ItemElectricLamp(config.getItem("ElectricLamp", 25063).getInt(), HandlerRegistry.getBlock("steamcraft:electricLampOff").getID()), "steamcraft:electricLamp",
				"steamcraft:electriclamp", "lampElectric").addRecipe(true, "I", "#", "X", Character.valueOf('#'), "dustCopper", Character.valueOf('X'), "ingotCastIron", Character.valueOf('I'),
				"itemLightBulb").register();
		new ItemHandler(new ItemElectricLamp(config.getItem("WirelessLamp", 25064).getInt(), HandlerRegistry.getBlock("steamcraft:wirelessLampOff").getID()), "steamcraft:wirelessLamp",
				"steamcraft:wirelesslamp").addRecipe(true, "#", "X", Character.valueOf('#'), "lampElectric", Character.valueOf('X'), "teslaReceiver").register();
		new ItemHandler(new ItemSeeds(config.getItem("TeaSeed", 25065).getInt(), HandlerRegistry.getBlock("steamcraft:teaplant").getID(), Block.tilledField.blockID), "steamcraft:teaSeeds",
				"steamcraft:teaseed", "seedTea").addSeed(5).register();
		new ItemHandler(new Item(config.getItem("TeaLeaf", 25066).getInt()), "steamcraft:teaLeaf", "steamcraft:tealeaves", "teaLeaves").register();
		ItemHandler kettle = new ItemHandler(new ItemKettle(config.getItem("KettleHot", 25068).getInt()), "steamcraft:kettleHot", "steamcraft:kettle", "kettleHot");
		kettle.register();
		new ItemHandler(new ItemKettle(config.getItem("KettleCold", 25067).getInt()), "steamcraft:kettle", "steamcraft:kettle", "kettleFull").addSmelt(new ItemStack(kettle.get()), 1.0F)
				.addRecipe(false, "kettle", Item.bucketWater, "teaLeaves").register();
		new ItemHandler(new ItemTeacup(config.getItem("TeacupEmpty", 25069).getInt(), 0), "steamcraft:teacupEmpty", "steamcraft:teacupempty", "teacup").addRecipe(true, "# #", " # ",
				Character.valueOf('#'), Item.clay).register();
		new ItemHandler(new ItemTeacup(config.getItem("TeacupFull", 25070).getInt(), 4), "steamcraft:teacup", "steamcraft:teacupfull", "teacupFull").addAchievement("timeforacuppa", -1, 2,
				AchievementList.acquireIron).register();
		new ItemHandler(new ItemKettle(config.getItem("KettleEmpty", 25071).getInt()), "steamcraft:kettleempty", "steamcraft:kettle", "kettle").addRecipe(true, "#  ", "###", " ##",
				Character.valueOf('#'), "ingotCastIron").register();
		if (config.hasChanged())
			config.save();
		brimstoneGen = new WorldGenMinable(HandlerRegistry.getBlock("steamcraft:brimstone").getID(), 8);
		zincGen = new WorldGenMinable(HandlerRegistry.getBlock("steamcraft:orezinc").getID(), 16);
		bornGen = new WorldGenMinable(HandlerRegistry.getBlock("steamcraft:oreBornite").getID(), 8);
		phosphGen = new WorldGenMinable(HandlerRegistry.getBlock("steamcraft:orePhosphate").getID(), 6);
		uranGen = new WorldGenMinable(HandlerRegistry.getBlock("steamcraft:oreUranite").getID(), 4);
		volucGen = new WorldGenMinable(HandlerRegistry.getBlock("steamcraft:oreVolucite").getID(), 3);
		data.put(new ItemStack(material, 1, 0), new Object[] { "Etherium Crystal", "gemEtherium" });
		data.put(new ItemStack(material, 1, 1), new Object[] { "Sulphur", "oreSulphur" });
		data.put(new ItemStack(material, 1, 2), new Object[] { "Purified Copper", "dustCopper" });
		data.put(new ItemStack(material, 1, 3), new Object[] { "Obsidian Slate", "slateObsidian" });
		data.put(new ItemStack(material, 1, 4), new Object[] { "Brass Ingot", "ingotBrass" });
		data.put(new ItemStack(material, 1, 5), new Object[] { "Cast Iron Ingot", "ingotCastIron" });
		data.put(new ItemStack(material, 1, 6), new Object[] { "Light Bulb", "itemLightBulb" });
		data.put(new ItemStack(material, 1, 7), new Object[] { "Phosphorus", "ingotPosphate" });
		data.put(new ItemStack(material, 1, 8), new Object[] { "Uranium", "oreUranium" });
		data.put(new ItemStack(material, 1, 9), new Object[] { "Uranium Pellet", "ingotUranium" });
		data.put(new ItemStack(material, 1, 10), new Object[] { "Reactor Core", "itemReactorCore" });
		data.put(new ItemStack(material, 1, 11), new Object[] { "Drill Base", "itemDrillBase" });
		data.put(new ItemStack(material, 1, 12), new Object[] { "Zinc Ingot", "ingotZinc" });
		data.put(new ItemStack(HandlerRegistry.getItem("steamcraft:chisel").get(), 1, OreDictionary.WILDCARD_VALUE), new Object[] { "Chisel", "chisel" });
		data.put(new ItemStack(spanner, 1, OreDictionary.WILDCARD_VALUE), new Object[] { "Spanner", "spanner" });
		data.put(new ItemStack(part, 1, 0), new Object[] { "Musket Cartridge", "cartridgeMusket" });
		data.put(new ItemStack(part, 1, 1), new Object[] { "Percussion Cap", "percussionCap" });
		data.put(new ItemStack(part, 1, 2), new Object[] { "Percussion Lock", "percussionLock" });
		data.put(new ItemStack(part, 1, 3), new Object[] { "Smoothbore Barrel", "barrel" });
		data.put(new ItemStack(part, 1, 4), new Object[] { "Rifled Barrel", "barrelRifled" });
		data.put(new ItemStack(part, 1, 5), new Object[] { "Wooden Stock", "woodenStock" });
		data.put(flintlockMusket, new Object[] { "Flintlock Musket", "musketFlintlock" });
		data.put(matchlockMusket, new Object[] { "Matchlock Musket", "musketMatchlock" });
		data.put(percussionCapMusket, new Object[] { "Percussion Musket", "musketPercussion" });
		data.put(flintlockRifle, new Object[] { "Flintlock Rifle", "rifleFlintlock" });
		data.put(matchlockRifle, new Object[] { "Matchlock Rifle", "rifleMatchLock" });
		data.put(percussionCapRifle, new Object[] { "Percussion Rifle", "riflePercussion" });
	}

	@Override
	public void notifyPickup(EntityItem item, EntityPlayer player) {
		if (item.getEntityItem().isItemEqual(new ItemStack(material))) {
			player.triggerAchievement(achs.get("carryingyou"));
		}
	}

	@Override
	public void onCrafting(EntityPlayer player, ItemStack item, IInventory craftMatrix) {
		if (item.itemID == HandlerRegistry.getItem("steamcraft:pickaxeObsidian").getID()) {
			player.triggerAchievement(achs.get("blackmagic"));
			return;
		}
		if (HandlerRegistry.drills.contains(item.itemID)) {
			player.triggerAchievement(achs.get("spiralnemesis"));
			return;
		}
		if (item.itemID == HandlerRegistry.getItem("steamcraft:coreDrill").getID()) {
			player.triggerAchievement(achs.get("heavenpiercing"));
			return;
		}
		if (item.itemID == BlockTeslaCoil.getTeslaIdle()) {
			player.triggerAchievement(achs.get("itsalive"));
			return;
		}
		if (item.itemID == HandlerRegistry.getBlock("steamcraft:decor").getID()) {
			player.triggerAchievement(achs.get("mastercraftsman"));
			return;
		}
		if (item.itemID == ItemSCArmor.getAqualung()) {
			player.triggerAchievement(achs.get("jethrotull"));
			return;
		}
		if (item.itemID == firearm.itemID) {
			player.triggerAchievement(achs.get("lockstockbarrel"));
			return;
		}
		int repairDmg = -1;
		ItemStack spaner = null;
		for (int i = 0; i < craftMatrix.getSizeInventory(); i++) {
			ItemStack itemstack1 = craftMatrix.getStackInSlot(i);
			if (itemstack1 != null) {
				if (itemstack1.getItem().itemID != spanner.itemID && itemstack1.isItemStackDamageable()) {
					repairDmg = itemstack1.getItemDamage();
				}
				if (itemstack1.getItem().itemID == HandlerRegistry.getItem("steamcraft:chisel").getID() && itemstack1.getItemDamage() + 1 < itemstack1.getMaxDamage()) {
					if (item.isItemEqual(new ItemStack(part, 1, 4))) {
						player.inventory.addItemStackToInventory(new ItemStack(itemstack1.getItem(), 1, itemstack1.getItemDamage() + 16));
					} else {
						player.inventory.addItemStackToInventory(new ItemStack(itemstack1.getItem(), 1, itemstack1.getItemDamage() + 1));
					}
				}
				if (itemstack1.getItem().itemID == spanner.itemID) {
					if (spaner != null) {
						return;
					}
					spaner = itemstack1.copy();
				}
			}
		}
		if (spaner != null && repairDmg >= 0 && item.getItem().isRepairable()) {
			ItemStack stack;
			if (repairDmg == 0) {
				stack = spaner;
				if (!player.inventory.addItemStackToInventory(stack))
					player.dropPlayerItem(stack);
			} else if (spaner.getItemDamage() + 1 < spaner.getMaxDamage()) {
				stack = new ItemStack(spanner, 1, spaner.getItemDamage() + 1);
				if (!player.inventory.addItemStackToInventory(stack))
					player.dropPlayerItem(stack);
			}
		}
	}

	@Override
	public void onSmelting(EntityPlayer player, ItemStack item) {
	}

	@EventHandler
	public void registering(FMLInitializationEvent event) {
		proxy.registerRenderers();
		NetworkRegistry.instance().registerGuiHandler(this, proxy);
		GameRegistry.registerCraftingHandler(this);
		GameRegistry.registerFuelHandler(this);
		GameRegistry.registerPickupHandler(this);
		GameRegistry.registerWorldGenerator(this);
		TickRegistry.registerTickHandler(proxy, Side.SERVER);
		TickRegistry.registerTickHandler(proxy, Side.CLIENT);
		for (Object obj : data.keySet()) {
			LanguageRegistry.addName(obj, (String) data.get(obj)[0]);
			if (obj instanceof ItemStack) {
				OreDictionary.registerOre((String) data.get(obj)[1], (ItemStack) obj);
			}
		}
		String[] toolsets = { "pickaxe", "drill" };
		MinecraftForge.setBlockHarvestLevel(Block.obsidian, toolsets[0], 3);
		MinecraftForge.setBlockHarvestLevel(Block.blockNetherQuartz, toolsets[0], 2);
		MinecraftForge.setBlockHarvestLevel(Block.stoneBrick, toolsets[0], 0);
		Block[] blocks = { Block.stoneBrick, Block.dirt, Block.sand, Block.gravel, Block.grass, Block.cobblestone, Block.stoneDoubleSlab, Block.stoneSingleSlab, Block.stone, Block.sandStone,
				Block.cobblestoneMossy, Block.oreCoal, Block.ice, Block.netherrack, Block.rail, Block.railDetector, Block.railPowered, Block.railActivator };
		for (Block block : blocks) {
			MinecraftForge.setBlockHarvestLevel(block, toolsets[1], 0);
		}
		blocks = new Block[] { Block.oreEmerald, Block.blockDiamond, Block.oreDiamond, Block.oreGold, Block.blockGold, Block.blockNetherQuartz, Block.oreRedstone, Block.oreRedstoneGlowing };
		for (Block block : blocks) {
			MinecraftForge.setBlockHarvestLevel(block, toolsets[1], 2);
		}
		blocks = new Block[] { Block.oreIron, Block.blockIron, Block.oreLapis, Block.blockLapis };
		for (Block block : blocks) {
			MinecraftForge.setBlockHarvestLevel(block, toolsets[1], 1);
		}
		blocks = new Block[] { Block.oreRedstone, Block.obsidian, Block.oreRedstoneGlowing };
		for (Block block : blocks) {
			MinecraftForge.removeBlockEffectiveness(block, toolsets[1]);
		}
		EntityRegistry.registerModEntity(EntityMusketBall.class, "MusketBall", 1, this, 120, 1, true);
		EntityRegistry.registerModEntity(EntityHighwayman.class, "Highwayman", 2, this, 120, 1, true);
		EntityRegistry.addSpawn(EntityHighwayman.class, 5, 1, 5, EnumCreatureType.monster, WorldType.base12Biomes);
		GameRegistry.addSmelting(Item.ingotIron.itemID, new ItemStack(material, 1, 5), 1.0F);
		GameRegistry.addSmelting(Block.gravel.blockID, new ItemStack(Item.flint), 1.0F);
		GameRegistry.addSmelting(Block.stoneBrick.blockID, new ItemStack(Block.stone), 1.0F);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(material, 4, 3), "#", "#", Character.valueOf('#'), Block.obsidian));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.obsidian), "##", "##", Character.valueOf('#'), "slateObsidian"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.redstone, 8), "###", Character.valueOf('#'), "dustCopper"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(material, 4, 6), "I", "#", "X", Character.valueOf('#'), "wireCopper", Character.valueOf('X'), Item.ingotIron, Character.valueOf('I'),
				Block.glass));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(material, 1, 10), "###", "#X#", "###", Character.valueOf('#'), Block.obsidian, Character.valueOf('X'), "furnaceChemical"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(material, 1, 11), "#X#", "# #", " # ", Character.valueOf('#'), Item.ingotIron, Character.valueOf('X'), "gemEtherium"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(material, 1, 4), "ingotZinc", "dustCopper"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(material, 2, 9), "oreUranium"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(spanner), "# #", "#X#", " # ", Character.valueOf('#'), "ingotBrass", Character.valueOf('X'), "gemEtherium"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.music), "###", "#X#", "###", Character.valueOf('#'), "plankWood", Character.valueOf('X'), Item.netherQuartz));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.railPowered, 6), "XRX", "X#X", "XRX", Character.valueOf('X'), Item.ingotGold, Character.valueOf('R'), "wireCopper", Character
				.valueOf('#'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.railDetector, 6), "XRX", "X#X", "XRX", Character.valueOf('X'), Item.ingotIron, Character.valueOf('R'), "wireCopper", Character
				.valueOf('#'), Block.pressurePlateStone));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.lever), "X", "#", "I", Character.valueOf('#'), "cobblestone", Character.valueOf('X'), "stickWood", Character.valueOf('I'),
				"battery"));
		GameRegistry.addRecipe(new ItemStack(Item.pocketSundial), " # ", "#X#", " # ", Character.valueOf('#'), Item.ingotGold, Character.valueOf('X'), Item.netherQuartz);
		GameRegistry.addRecipe(new ItemStack(Item.compass), " # ", "#X#", " # ", Character.valueOf('#'), Item.ingotIron, Character.valueOf('X'), Item.netherQuartz);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.stoneButton), "#", "#", "X", Character.valueOf('#'), "stone", Character.valueOf('X'), "battery"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.pressurePlateStone), "##", "X ", Character.valueOf('#'), "stone", Character.valueOf('X'), "battery"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.pressurePlatePlanks), "##", "X ", Character.valueOf('#'), "plankWood", Character.valueOf('X'), "battery"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.dispenser), "###", "#X#", "#R#", Character.valueOf('#'), "cobblestone", Character.valueOf('X'), Item.bow,
				Character.valueOf('R'), Item.netherQuartz));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.gunpowder), "#X#", Character.valueOf('#'), "oreSulphur", Character.valueOf('X'), Item.coal));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.glowstone, 4), "X#X", "#I#", "X#X", Character.valueOf('#'), "ingotPosphate", Character.valueOf('X'), "oreSulphur", Character
				.valueOf('I'), "oreUranium"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.pistonBase), "TTT", "#X#", "#R#", Character.valueOf('#'), "cobblestone", Character.valueOf('X'), Item.ingotIron, Character
				.valueOf('R'), "dustCopper", Character.valueOf('T'), "plankWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(part, 1, 2), "X ", "##", Character.valueOf('#'), Item.ingotIron, Character.valueOf('X'), "ingotBrass"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(part, 1, 5), "#  ", " # ", "  #", Character.valueOf('#'), "plankWood"));
		GameRegistry.addRecipe(new ItemStack(part, 1, 3), "#  ", " # ", "  #", Character.valueOf('#'), Item.ingotIron);
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(part, 1, 4), "itemBarrel", "chisel"));
		flintlockMusket.setItemDamage(flintlockMusket.getMaxDamage() - 1);
		GameRegistry.addRecipe(new ShapedOreRecipe(flintlockMusket, "X", "#", "T", Character.valueOf('#'), "itemBarrel", Character.valueOf('X'), new ItemStack(Item.flintAndSteel, 1,
				OreDictionary.WILDCARD_VALUE), Character.valueOf('T'), "itemWoodenStock"));
		matchlockMusket.setItemDamage(matchlockMusket.getMaxDamage() - 1);
		GameRegistry.addRecipe(new ShapedOreRecipe(matchlockMusket, "X", "#", "T", Character.valueOf('#'), "itemBarrel", Character.valueOf('X'), Item.silk, Character.valueOf('T'), "itemWoodenStock"));
		percussionCapMusket.setItemDamage(percussionCapMusket.getMaxDamage() - 1);
		GameRegistry.addRecipe(new ShapedOreRecipe(percussionCapMusket, "X", "#", "T", Character.valueOf('#'), "itemBarrel", Character.valueOf('X'), "itemPercussionLock", Character.valueOf('T'),
				"itemWoodenStock"));
		flintlockRifle.setItemDamage(flintlockRifle.getMaxDamage() - 1);
		GameRegistry.addRecipe(new ShapedOreRecipe(flintlockRifle, "X", "#", "T", Character.valueOf('#'), "itemBarrelRifled", Character.valueOf('X'), new ItemStack(Item.flintAndSteel, 1,
				OreDictionary.WILDCARD_VALUE), Character.valueOf('T'), "itemWoodenStock"));
		matchlockRifle.setItemDamage(matchlockRifle.getMaxDamage() - 1);
		GameRegistry.addRecipe(new ShapedOreRecipe(matchlockRifle, "X", "#", "T", Character.valueOf('#'), "itemBarrelRifled", Character.valueOf('X'), Item.silk, Character.valueOf('T'),
				"itemWoodenStock"));
		percussionCapRifle.setItemDamage(percussionCapRifle.getMaxDamage() - 1);
		GameRegistry.addRecipe(new ShapedOreRecipe(percussionCapRifle, "X", "#", "T", Character.valueOf('#'), "itemBarrelRifled", Character.valueOf('X'), "itemPercussionLock", Character.valueOf('T'),
				"itemWoodenStock"));
		GameRegistry.addRecipe(new ItemStack(part, 8), "X", "#", "T", Character.valueOf('#'), Item.gunpowder, Character.valueOf('X'), Item.ingotIron, Character.valueOf('T'), Item.paper);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(part, 8, 1), "T", "#", "X", Character.valueOf('#'), Item.gunpowder, Character.valueOf('X'), "ingotBrass", Character.valueOf('T'),
				Item.paper));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Item.slimeBall), Item.bucketWater, "oreSulphur"));
		addSpannerRecipes();
		if (event.getSide().isClient())
			addAchievements();
	}

	private void addSpannerRecipes() {
		for (Item item : Item.itemsList) {
			if (item != null && item.isRepairable()) {
				ItemStack itemstack = new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE);
				GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(item), itemstack, "spanner"));
			}
		}
	}

	public static void addAchievements() {
		achs.put("carryingyou", new Achievement(AchievementList.achievementList.size(), "carryingyou", 4, 2, material, achs.get("blackmagic")).registerAchievement());
		achs.put("ruinedeverything", new Achievement(AchievementList.achievementList.size(), "ruinedeverything", 0, 0, new ItemStack(material, 1, 8), achs.get("fallout")).registerAchievement());
		achs.put("lockstockbarrel", new Achievement(AchievementList.achievementList.size(), "lockstockbarrel", -1, 3, firearm, AchievementList.acquireIron).registerAchievement());
		AchievementPage.registerAchievementPage(new AchievementPage("Steamcraft", achs.values().toArray(new Achievement[achs.size()])));
	}
}
