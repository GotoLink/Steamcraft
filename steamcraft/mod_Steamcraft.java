package steamcraft;

import java.util.Random;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockOreStorage;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLogic;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import steamcraft.blocks.BlockBattery;
import steamcraft.blocks.BlockBrassLog;
import steamcraft.blocks.BlockChemFurnace;
import steamcraft.blocks.BlockDiode;
import steamcraft.blocks.BlockElectricLamp;
import steamcraft.blocks.BlockInverter;
import steamcraft.blocks.BlockLamp;
import steamcraft.blocks.BlockNukeFurnace;
import steamcraft.blocks.BlockPoweredRail;
import steamcraft.blocks.BlockRoof;
import steamcraft.blocks.BlockSCCopperWire;
import steamcraft.blocks.BlockSCFarmland;
import steamcraft.blocks.BlockSCFence;
import steamcraft.blocks.BlockSCOre;
import steamcraft.blocks.BlockSCStairs;
import steamcraft.blocks.BlockSCTallGrass;
import steamcraft.blocks.BlockSCTeaPlant;
import steamcraft.blocks.BlockSCTorch;
import steamcraft.blocks.BlockSteamFurnace;
import steamcraft.blocks.BlockTeslaCoil;
import steamcraft.blocks.BlockTeslaReceiver;
import steamcraft.blocks.BlockTorchPhosphorus;
import steamcraft.blocks.BlockUraniteOre;
import steamcraft.blocks.BlockUranium;
import steamcraft.blocks.BlockWirelessLamp;
import steamcraft.items.ItemChisel;
import steamcraft.items.ItemCoreDrill;
import steamcraft.items.ItemElectricLamp;
import steamcraft.items.ItemFirearm;
import steamcraft.items.ItemKettle;
import steamcraft.items.ItemSCArmor;
import steamcraft.items.ItemSCAxe;
import steamcraft.items.ItemSCDrill;
import steamcraft.items.ItemSCHoe;
import steamcraft.items.ItemSCPickaxe;
import steamcraft.items.ItemSCSword;
import steamcraft.items.ItemTeacup;
import cpw.mods.fml.client.registry.RenderingRegistry;
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
@Mod(modid="steamcraft",name="SteamCraft",version="alpha")
@NetworkMod(clientSideRequired=true)
public class mod_Steamcraft implements ICraftingHandler,IPickupNotifier,IWorldGenerator, IFuelHandler
{
	@Instance(value="steamcraft")
	public static mod_Steamcraft instance;
	@SidedProxy(clientSide="steamcraft.ClientProxy",serverSide="steamcraft.CommonProxy")
	public static CommonProxy proxy;
	
	public static Achievement ach_CarryingYou,ach_BlackMagic,ach_SpiralNemesis;
	public static Achievement ach_Fallout,ach_WhoTheHellDoYouThinkIAm,ach_ItsAlive;
	public static Achievement ach_MasterCraftsman,ach_RuinedEverything,ach_JethroTull;
	public static Achievement ach_LockStockAndBarrel,ach_TimeForACuppa;
	
	public static EnumToolMaterial OBSIDIAN = EnumHelper.addToolMaterial("OBSIDIAN", 5, 5, 210, 7F, 4);
	public static EnumToolMaterial ETHERIUM = EnumHelper.addToolMaterial("ETHERIUM", 6, 6, -1, 8F, 3);
	public static EnumToolMaterial STEAM = EnumHelper.addToolMaterial("STEAM", 7, 2, 321, 12F, 5);
	
	public static Block torchElectricIdle,torchElectricActive,torchTeslaIdle,torchTeslaActive;
	public static Block teslaReceiver,teslaReceiverActive;
	public static Block steamOvenIdle,steamOvenActive;
	public static Block chemOvenIdle,chemOvenActive;
	public static Block nukeOvenIdle,nukeOvenActive;
	public static Block battery,brimstone;
	public static Block borniteOre,orePhosphate,oreUranite,oreQuartz,oreQuartzActive,oreVolucite;
	public static Block torchPhosphorus;
	public static Block roofTile;
	
	public static Block oreSCDiamond,oreSCCoal;
	
	public static Block blockCastIron;
	public static Block blockVolucite;
	public static Block blockBrass;
	public static Block blockUranium;
	
	public static Block decorIron,decorGold,decorDiamond,decorCastIron,decorBrass;
	public static Block decorVolucite,decorLapis,carvedStone,decorQuartz,decorUranium;
	
	public static Block railingCastIron,gateCastIron;
	
	public static Block stairSCSingle,stairSCDouble;
	
	public static Block lamp,lampoff;
	public static Block woodBrass;
	public static Block leavesLamp;
	public static Block wirelessLampIdle,wirelessLampActive;
	
	public static Block stairCompactStone,stairRoof;
	
	public static Block teaPlant;
	
	public static Item quartz,etherium,chemicSalt,bornite,obsidianSlate;
	public static Item ingotBrass,ingotCastIron;
	public static Item lightBulb;
	public static Item phosphorus,uraniumStone,uranium;
	public static Item reactorCore,coreDrill,drillBase;
	
	public static Item pickaxeObsidian,shovelObsidian,axeObsidian,hoeObsidian;
	public static Item swordObsidian,drillObsidian;
	
	public static Item helmetObsidian,plateObsidian,legsObsidian,bootsObsidian;
	
	public static Item brassGoggles,aqualung,rollerSkates,legBraces;
	
	public static Item pickaxeEtherium,shovelEtherium,axeEtherium,hoeEtherium;
	public static Item swordEtherium,drillEtherium;
	
	public static Item helmetEtherium,plateEtherium,legsEtherium,bootsEtherium;
	
	public static Item swordSteam,shovelSteam,pickaxeSteam;
	public static Item drillSteam,axeSteam,hoeSteam;
	
	public static Item drillSteel,drillWood,drillStone,drillDiamond,drillGold;
	
	public static Item chisel,spanner;
	
	public static Item smoothBarrel,rifledBarrel,woodenStock,percussionLock;
	public static Item flintlockMusket,matchlockMusket,percussionCapMusket;
	public static Item flintlockRifle,matchlockRifle,percussionCapRifle;
	public static Item musketRound,percussionRound;
	
	public static Item electricLamp,wirelessLamp;
	
	public static Item teaSeed,teaLeaves,coldKettle,hotKettle;
	public static Item emptyTeacup,fullTeacup,emptyKettle;
	
	public static int EtheriumRenderIndex = RenderingRegistry.addNewArmourRendererPrefix("etherium");
	public static int BrassRenderIndex = RenderingRegistry.addNewArmourRendererPrefix("brass");
	public static int ObsidianRenderIndex = RenderingRegistry.addNewArmourRendererPrefix("obsidian");
	
	public static Material solidcircuit = new MaterialLogic(MapColor.airColor);
	public static Material staticcircuit = new MaterialLogic(MapColor.airColor).setImmovableMobility();
	private Logger logger;
	
	@EventHandler
	public void load(FMLPreInitializationEvent event)
	{	
		logger = event.getModLog();
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		/*BorniteOre =  "/bornite.png";	
		RoofTilesTex =  "/slatetiles.png";	
		CastIronTex = "/castironblock.png";*/
			
		torchElectricIdle = new BlockElectricLamp(config.getBlock("ElectricLamp",2500).getInt(), TileEntityLamp.class, false).setHardness(0.0F).setUnlocalizedName("steamcraft:electricLamp").setTextureName("steamcraft:castironblock").disableStats();
		torchElectricActive = new BlockElectricLamp(config.getBlock("ElectricLampON", 2501).getInt(), TileEntityLamp.class, true).setHardness(0.0F).setLightValue(1.0F).setUnlocalizedName("steamcraft:electricLamp").setTextureName("steamcraft:castironblock").disableStats();
		torchTeslaIdle = new BlockTeslaCoil(config.getBlock("TeslaCoil",2502).getInt(),  false).setHardness(0.0F).setUnlocalizedName("steamcraft:teslaCoil").setTextureName("steamcraft:teslaidle");
		torchTeslaActive = new BlockTeslaCoil(config.getBlock("TeslaCoilON",2053).getInt(),  true).setHardness(0.0F).setLightValue(0.625F).setUnlocalizedName("steamcraft:teslaCoil").setTextureName("steamcraft:teslaactive");
		teslaReceiver = new BlockTeslaReceiver(config.getBlock("Receiver",2504).getInt()).setHardness(0.5F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("steamcraft:receiver").setTextureName("steamcraft:receiver");
		teslaReceiverActive = new BlockTeslaReceiver(config.getBlock("ReceiverON",2506).getInt()).setHardness(0.5F).setLightValue(0.625F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("steamcraft:receiver").setTextureName("steamcraft:receiveractive");
		steamOvenIdle = new BlockSteamFurnace(config.getBlock("SteamFurnace",2507).getInt(),false).setHardness(4F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("steamcraft:steamFurnace").setTextureName("steamcraft:steamfurnaceidle");
		steamOvenActive = new BlockSteamFurnace(config.getBlock("SteamFurnaceON",2508).getInt(), true).setHardness(4F).setStepSound(Block.soundMetalFootstep).setLightValue(0.875F).setUnlocalizedName("steamcraft:steamFurnace").setTextureName("steamcraft:steamfurnaceactive");
		chemOvenIdle = new BlockChemFurnace(config.getBlock("ChemicalFurnace",2509).getInt(), false).setHardness(4.5F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("steamcraft:chemFurnace").setTextureName("steamcraft:chemfurnaceidle");
		chemOvenActive = new BlockChemFurnace(config.getBlock("ChemicalFurnaceON",2510).getInt(), true).setHardness(4.5F).setStepSound(Block.soundMetalFootstep).setLightValue(0.875F).setUnlocalizedName("steamcraft:chemFurnace").setTextureName("steamcraft:chemfurnaceactive");
		nukeOvenIdle = new BlockNukeFurnace(config.getBlock("NuclearFurnace",2511).getInt(),  false).setHardness(5F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("steamcraft:nukeFurnace").setTextureName("steamcraft:nukefurnaceidle");
		nukeOvenActive = new BlockNukeFurnace(config.getBlock("NuclearFurnaceON",2512).getInt(),  true).setHardness(5F).setStepSound(Block.soundMetalFootstep).setLightValue(0.9375F).setUnlocalizedName("steamcraft:nukeFurnace").setTextureName("steamcraft:nukefurnaceactive");
		battery = new BlockBattery(config.getBlock("Battery",2513).getInt()).setHardness(0.5F).setLightValue(0.625F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("steamcraft:battery").setTextureName("steamcraft:battery");
		brimstone = new BlockOre(config.getBlock("BrimstoneOre",2514).getInt()).setHardness(3F).setResistance(5F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("steamcraft:brimstone").setTextureName("steamcraft:brimstone");
		orePhosphate = new BlockOre(config.getBlock("PhosphateOre",2515).getInt()).setHardness(2.5F).setResistance(5F).setStepSound(Block.soundStoneFootstep).setLightValue(0.75F).setUnlocalizedName("steamcraft:orePhosphate").setTextureName("steamcraft:phosphate");
		oreUranite = new BlockUraniteOre(config.getBlock("UraniteOre",2516).getInt()).setHardness(10F).setResistance(6F).setLightValue(0.625F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("steamcraft:oreUranite").setTextureName("steamcraft:uranite");
		borniteOre = new BlockOre(config.getBlock("BorniteOre",2517).getInt() ).setHardness(3F).setResistance(5F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("steamcraft:oreBornite").setTextureName("steamcraft:bornite");
		oreVolucite = new BlockSCOre(config.getBlock("VoluciteOre",2518).getInt()).setHardness(50F).setResistance(6000000F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("steamcraft:oreVolucite").setTextureName("steamcraft:voluciteore");
		torchPhosphorus = new BlockTorchPhosphorus(config.getBlock("PhosphorusTorch",2519).getInt()).setHardness(0.0F).setLightValue(1.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("steamcraft:torchPhosphorus").setTextureName("steamcraft:torchphosphorus");
		roofTile = new BlockRoof(config.getBlock("SlateTiles",2520).getInt()).setHardness(2F).setResistance(10F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("steamcraft:roofTile").setTextureName("steamcraft:slatetiles");
		
		blockCastIron = new BlockOreStorage(config.getBlock("CastIronBlock",2521).getInt()).setHardness(7F).setResistance(20F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("steamcraft:blockCastIron").setTextureName("steamcraft:castironblock");
		blockVolucite = new BlockOreStorage(config.getBlock("VoluciteBlock",2522).getInt()).setHardness(50F).setResistance(6000000F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("steamcraft:blockVolucite").setTextureName("steamcraft:voluciteblock");
		blockBrass = new BlockOreStorage(config.getBlock("BrassBlock",2523).getInt()).setHardness(5F).setResistance(10F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("steamcraft:blockBrass").setTextureName("steamcraft:brassblock");
		blockUranium = new BlockUranium(config.getBlock("UraniumBlock",2525).getInt()).setHardness(10F).setResistance(6F).setLightValue(0.625F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("steamcraft:blockUranium").setTextureName("steamcraft:uraniumblock");
		
		decorIron = new BlockOreStorage(config.getBlock("EngrIron",2526).getInt()).setHardness(5F).setResistance(10F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("steamcraft:decorIron").setTextureName("steamcraft:engriron");
		decorGold = new BlockOreStorage(config.getBlock("EngrGold",2527).getInt()).setHardness(3F).setResistance(10F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("steamcraft:decorGold").setTextureName("steamcraft:engrgold");
		decorDiamond = new BlockOreStorage(config.getBlock("EngrDiamond",2528).getInt()).setHardness(5F).setResistance(10F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("steamcraft:decorDiamond").setTextureName("steamcraft:engrdiamond");
		decorCastIron = new BlockOreStorage(config.getBlock("EngrCastIron",2529).getInt()).setHardness(7F).setResistance(20F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("steamcraft:decorCastIron").setTextureName("steamcraft:engrcastiron");
		decorVolucite = new BlockOreStorage(config.getBlock("EngrVolucite",2530).getInt()).setHardness(50F).setResistance(6000000F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("steamcraft:decorVolucite").setTextureName("steamcraft:engrvolucite");
		decorBrass = new BlockOreStorage(config.getBlock("EngrBrass",2531).getInt()).setHardness(5F).setResistance(10F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("steamcraft:decorBrass").setTextureName("steamcraft:engrbrass");
		decorLapis = new BlockOreStorage(config.getBlock("EngrLapis",2532).getInt()).setHardness(3F).setResistance(5F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("steamcraft:decorLapis").setTextureName("steamcraft:engrlapis");
		carvedStone = new Block(config.getBlock("CarvedStone",2533).getInt(), Material.rock).setHardness(2F).setResistance(10F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("steamcraft:stoneWall").setTextureName("steamcraft:carvedstone");
		decorQuartz = new BlockQuartz(config.getBlock("EngrQuartz",2534).getInt()).setHardness(10F).setResistance(6F).setLightValue(0.3125F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("steamcraft:decorQuartz").setTextureName("steamcraft:engrquartz");
		decorUranium = new BlockUranium(config.getBlock("EngrUranium",2535).getInt()).setHardness(10F).setResistance(6F).setLightValue(0.625F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("steamcraft:decorUranium").setTextureName("steamcraft:engruranium");
		
		gateCastIron = new BlockFenceGate(config.getBlock("CastIronGate",2536).getInt() ).setHardness(7F).setResistance(20F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("steamcraft:gateCastIron").setTextureName("steamcraft:castironblock");
		railingCastIron = new BlockSCFence(config.getBlock("CastIronRailing",2537).getInt(),  Material.iron, mod_Steamcraft.gateCastIron, true).setHardness(7F).setResistance(20F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("steamcraft:railingCastIron").setTextureName("steamcraft:castironblock");
		
		lamp = new BlockLamp(config.getBlock("LampBlock",2538).getInt(),  Material.wood).setHardness(2.0F).setStepSound(Block.soundGlassFootstep).setLightValue(1.0F).setUnlocalizedName("steamcraft:lamp").setTextureName("steamcraft:lampblock");
		lampoff = new BlockLamp(config.getBlock("LampBlockOFF",2539).getInt() , Material.wood).setHardness(2.0F).setStepSound(Block.soundGlassFootstep).setLightValue(0.0F).setUnlocalizedName("steamcraft:lamp").setTextureName("steamcraft:lampblock");
		woodBrass = new BlockBrassLog(config.getBlock("BrassLog",2540).getInt()).setHardness(5F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("steamcraft:logBrass").setTextureName("steamcraft:brasslog");
		leavesLamp = new Block(config.getBlock("BrassLeaves",2541).getInt(), Material.wood).setHardness(2F).setLightOpacity(1).setLightValue(0.9375F).setStepSound(Block.soundGlassFootstep).setUnlocalizedName("steamcraft:leavesLamp").setTextureName("steamcraft:brassleaves");
		wirelessLampIdle = new BlockWirelessLamp(config.getBlock("WirelessLamp",2542).getInt() , TileEntityLamp.class, false).setHardness(0.0F).setUnlocalizedName("steamcraft:wirelessLamp").disableStats().setTextureName("steamcraft:castironblock");
		wirelessLampActive = new BlockWirelessLamp(config.getBlock("WirelessLampON",2543).getInt() , TileEntityLamp.class, true).setHardness(0.0F).setLightValue(1.0F).setUnlocalizedName("steamcraft:wirelessLamp").disableStats().setTextureName("steamcraft:castironblock");
		
		stairRoof = new BlockSCStairs(config.getBlock("SlateStairs",2545).getInt(),roofTile,0, Item.flint.itemID, 2).setUnlocalizedName("steamcraft:stairsRoof").setTextureName("steamcraft:slatetiles");
		
		teaPlant = new BlockSCTeaPlant(config.getBlock("TeaPlant",2546).getInt()).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("steamcraft:teaplant").disableStats().setTextureName("steamcraft:teaplantgrown");
		
		etherium = new Item(config.getItem("Etherium",25000).getInt()).setUnlocalizedName("steamcraft:etherium").setTextureName("steamcraft:etherium");
		chemicSalt = new Item(config.getItem("Sulphur",25001).getInt()).setUnlocalizedName("steamcraft:chemicSalt").setTextureName("steamcraft:sulphur");
		bornite = new Item(config.getItem("Copper",25002).getInt()).setUnlocalizedName("steamcraft:bornite").setTextureName("steamcraft:copper");
		obsidianSlate = new Item(config.getItem("ObsidianSlate",25003).getInt()).setUnlocalizedName("steamcraft:obsidianSlate").setTextureName("steamcraft:obsidianslate");
		ingotBrass = new Item(config.getItem("BrassIngot",25004).getInt()).setUnlocalizedName("steamcraft:ingotBrass").setTextureName("steamcraft:ingotbrass");
		ingotCastIron = new Item(config.getItem("CastIronIngot",25005).getInt()).setUnlocalizedName("steamcraft:ingotCastIron").setTextureName("steamcraft:ingotcastiron");
		lightBulb = new Item(config.getItem("LightBulb",25006).getInt()).setUnlocalizedName("steamcraft:lightBulb").setTextureName("steamcraft:lightbulb");
		phosphorus = new Item(config.getItem("Phosphorus",25007).getInt()).setUnlocalizedName("steamcraft:phosphorus").setTextureName("steamcraft:phosphorus");
		uraniumStone = new Item(config.getItem("Uranium",25008).getInt()).setUnlocalizedName("steamcraft:uraniumStone").setTextureName("steamcraft:uraniumstone");
		uranium = new Item(config.getItem("UraniumPellet",25009).getInt()).setUnlocalizedName("steamcraft:uranium").setTextureName("steamcraft:uraniumpellet");
		reactorCore = new Item(config.getItem("ReactorCore",25010).getInt()).setUnlocalizedName("steamcraft:reactorCore").setTextureName("steamcraft:reactorcore");
		coreDrill = new ItemCoreDrill(config.getItem("CoreDrill",25011).getInt()).setUnlocalizedName("steamcraft:coreDrill").setTextureName("steamcraft:coredrill");
		drillBase = new Item(config.getItem("DrillBase",25012).getInt()).setUnlocalizedName("steamcraft:drillBase").setTextureName("steamcraft:coredrillbase");
		
		pickaxeObsidian = new ItemSCPickaxe(config.getItem("ObsidianPick",25013).getInt(), OBSIDIAN).setUnlocalizedName("steamcraft:pickaxeObsidian").setTextureName("steamcraft:tools.obsidianpick");
		shovelObsidian = new ItemSpade(config.getItem("ObsidianSpade",25014).getInt(), OBSIDIAN).setUnlocalizedName("steamcraft:shovelObsidian").setTextureName("steamcraft:tools.obsidianspade");
		axeObsidian = new ItemSCAxe(config.getItem("ObsidianAxe",25015).getInt(), OBSIDIAN).setUnlocalizedName("steamcraft:hatchetObsidian").setTextureName("steamcraft:tools.obsidianaxe");
		hoeObsidian = new ItemSCHoe(config.getItem("ObsidianHoe",25016).getInt(), OBSIDIAN).setUnlocalizedName("steamcraft:hoeObsidian").setTextureName("steamcraft:tools.obsidianhoe");
		swordObsidian = new ItemSCSword(config.getItem("ObsidianSword",25017).getInt(), OBSIDIAN).setUnlocalizedName("steamcraft:swordObsidian").setTextureName("steamcraft:tools.obsidiansword");
		drillObsidian = new ItemSCDrill(config.getItem("ObsidianDrill",25018).getInt(), OBSIDIAN).setUnlocalizedName("steamcraft:drillObsidian").setTextureName("steamcraft:tools.obsidiandrill");
		
		helmetObsidian = new ItemSCArmor(config.getItem("ObsidianHelmet",25019).getInt(), 3, ObsidianRenderIndex, 0).setUnlocalizedName("steamcraft:helmetObsidian").setTextureName("steamcraft:armour.obsidianhelmet");
		plateObsidian = new ItemSCArmor(config.getItem("ObsidianPlate",25020).getInt(), 3, ObsidianRenderIndex, 1).setUnlocalizedName("steamcraft:chestplateObsidian").setTextureName("steamcraft:armour.obsidianplate");
		legsObsidian = new ItemSCArmor(config.getItem("ObsidianLegs",25021).getInt(), 3, ObsidianRenderIndex, 2).setUnlocalizedName("steamcraft:leggingsObsidian").setTextureName("steamcraft:armour.obsidianlegs");
		bootsObsidian = new ItemSCArmor(config.getItem("ObsidianBoots",25022).getInt(), 3, ObsidianRenderIndex, 3).setUnlocalizedName("steamcraft:bootsObsidian").setTextureName("steamcraft:armour.obsidianboots");
		
		brassGoggles = new ItemSCArmor(config.getItem("BrassGoggles",25023).getInt(), 1, BrassRenderIndex, 0).setUnlocalizedName("steamcraft:brassGoggles").setTextureName("steamcraft:armour.brassgoggles");
		aqualung = new ItemSCArmor(config.getItem("Aqualung",25024).getInt(), 1, BrassRenderIndex, 1).setUnlocalizedName("steamcraft:aqualung").setTextureName("steamcraft:armour.aqualung");
		rollerSkates = new ItemSCArmor(config.getItem("RollerSkates",25025).getInt(), 1, BrassRenderIndex, 3).setUnlocalizedName("steamcraft:rollerSkates").setTextureName("steamcraft:armour.rollerskates");
		legBraces = new ItemSCArmor(config.getItem("PneumaticBraces",25026).getInt(), 1, BrassRenderIndex, 2).setUnlocalizedName("steamcraft:legBraces").setTextureName("steamcraft:armour.pneumaticbraces");
		
		pickaxeEtherium = new ItemSCPickaxe(config.getItem("EtheriumPick",25027).getInt(), ETHERIUM).setUnlocalizedName("steamcraft:pickaxeEtherium").setTextureName("steamcraft:tools.etheriumpick");
		shovelEtherium = new ItemSpade(config.getItem("EtheriumSpade",25028).getInt(), ETHERIUM).setUnlocalizedName("steamcraft:shovelEtherium").setTextureName("steamcraft:tools.etheriumspade");
		axeEtherium = new ItemSCAxe(config.getItem("EtheriumAxe",25029).getInt(),ETHERIUM).setUnlocalizedName("steamcraft:hatchetEtherium").setTextureName("steamcraft:tools.etheriumaxe");
		hoeEtherium = new ItemSCHoe(config.getItem("EtheriumHoe",25030).getInt(),ETHERIUM).setUnlocalizedName("steamcraft:hoeEtherium").setTextureName("steamcraft:tools.etheriumhoe");
		swordEtherium = new ItemSCSword(config.getItem("EtheriumSword",25031).getInt(),ETHERIUM).setUnlocalizedName("steamcraft:swordEtherium").setTextureName("steamcraft:tools.etheriumsword");
		drillEtherium = new ItemSCDrill(config.getItem("EtheriumDrill",25032).getInt(),ETHERIUM).setUnlocalizedName("steamcraft:drillEtherium").setTextureName("steamcraft:tools.etheriumdrill");
		
		helmetEtherium = new ItemSCArmor(config.getItem("EtheriumHelmet",25033).getInt(), 0, EtheriumRenderIndex, 0).setUnlocalizedName("steamcraft:helmetEtherium").setTextureName("steamcraft:armour.etheriumhelmet");
		plateEtherium = new ItemSCArmor(config.getItem("EtheriumPlate",25034).getInt(), 0, EtheriumRenderIndex, 1).setUnlocalizedName("steamcraft:chestplateEtherium").setTextureName("steamcraft:armour.etheriumplate");
		legsEtherium = new ItemSCArmor(config.getItem("EtheriumLegs",25036).getInt(), 0, EtheriumRenderIndex, 2).setUnlocalizedName("steamcraft:leggingsEtherium").setTextureName("steamcraft:armour.etheriumlegs");
		bootsEtherium = new ItemSCArmor(config.getItem("EtheriumBoots",25037).getInt(), 0, EtheriumRenderIndex, 3).setUnlocalizedName("steamcraft:bootsEtherium").setTextureName("steamcraft:armour.etheriumboots");
		
		pickaxeSteam = new ItemSCPickaxe(config.getItem("SteamPick",25038).getInt(),STEAM).setUnlocalizedName("steamcraft:pickaxeSteam").setTextureName("steamcraft:tools.steampick");
		shovelSteam = new ItemSpade(config.getItem("SteamSpade",25039).getInt(),STEAM).setUnlocalizedName("steamcraft:shovelSteam").setTextureName("steamcraft:tools.steamspade");
		axeSteam = new ItemSCAxe(config.getItem("SteamAxe",25040).getInt(),STEAM).setUnlocalizedName("steamcraft:hatchetSteam").setTextureName("steamcraft:tools.steamaxe");
		hoeSteam = new ItemSCHoe(config.getItem("SteamHoe",25041).getInt(),STEAM).setUnlocalizedName("steamcraft:hoeSteam").setTextureName("steamcraft:tools.steamhoe");
		swordSteam =new ItemSCSword(config.getItem("SteamSword",25042).getInt(),STEAM).setUnlocalizedName("steamcraft:swordSteam").setTextureName("steamcraft:tools.steamsword");
		drillSteam = new ItemSCDrill(config.getItem("SteamDrill",25043).getInt(),STEAM).setUnlocalizedName("steamcraft:drillSteam").setTextureName("steamcraft:tools.steamdrill");
		
		
		drillSteel = new ItemSCDrill(config.getItem("IronDrill",25044).getInt(), EnumToolMaterial.IRON).setUnlocalizedName("steamcraft:drillIron").setTextureName("steamcraft:tools.irondrill");
		drillWood = new ItemSCDrill(config.getItem("WoodenDrill",25045).getInt(), EnumToolMaterial.WOOD).setUnlocalizedName("steamcraft:drillWood").setTextureName("steamcraft:tools.woorddrill");
		drillStone = new ItemSCDrill(config.getItem("StoneDrill",25046).getInt(), EnumToolMaterial.STONE).setUnlocalizedName("steamcraft:drillStone").setTextureName("steamcraft:tools.stonedrill");
		drillDiamond = new ItemSCDrill(config.getItem("DiamondDrill",25047).getInt(), EnumToolMaterial.EMERALD).setUnlocalizedName("steamcraft:drillDiamond").setTextureName("steamcraft:tools.diamonddrill");
		drillGold = new ItemSCDrill(config.getItem("GoldenDrill",25048).getInt(), EnumToolMaterial.GOLD).setUnlocalizedName("steamcraft:drillGold").setTextureName("steamcraft:tools.golddrill");
		
		
		chisel = new ItemChisel(config.getItem("Chisel",25049).getInt(), 64).setUnlocalizedName("steamcraft:chisel").setTextureName("steamcraft:tools.chisel");
		spanner = new ItemChisel(config.getItem("Spanner",25050).getInt(), 3).setUnlocalizedName("steamcraft:spanner").setTextureName("steamcraft:tools.spanner");				
		
		musketRound = new Item(config.getItem("MusketCartridge",25051).getInt()).setUnlocalizedName("steamcraft:musketRound").setTextureName("steamcraft:musketcratridge");
		percussionRound = new Item(config.getItem("PercussionCap",25052).getInt()).setUnlocalizedName("steamcraft:percussionRound").setTextureName("steamcraft:percussioncap");
		
		percussionLock = new Item(config.getItem("PercussionLock",25053).getInt()).setUnlocalizedName("steamcraft:percussionLock").setTextureName("steamcraft:percussionlock");
		smoothBarrel = new Item(config.getItem("SmoothBarrel",25054).getInt()).setUnlocalizedName("steamcraft:smoothBarrel").setTextureName("steamcraft:smoothbarrel");
		rifledBarrel = new Item(config.getItem("RifledBarrel",25055).getInt()).setUnlocalizedName("steamcraft:rifledBarrel").setTextureName("steamcraft:rifledbarrel");
		woodenStock = new Item(config.getItem("WoodStock",25056).getInt()).setUnlocalizedName("steamcraft:woodenStock").setTextureName("steamcraft:woodenstock");
		flintlockMusket = new ItemFirearm(config.getItem("FlintlockMusket",25057).getInt(), 100, musketRound, musketRound, 8, false).setUnlocalizedName("steamcraft:flintlockMusket").setTextureName("steamcraft:tools.flintlockmusket");
		matchlockMusket = new ItemFirearm(config.getItem("MatchlockMusket",25058).getInt(), 200, musketRound, musketRound, 6, false).setUnlocalizedName("steamcraft:matchlockMusket").setTextureName("steamcraft:tools.matchlockmusket");
		percussionCapMusket = new ItemFirearm(config.getItem("PercussionMusket",25059).getInt(), 50, percussionRound, musketRound, 10, false).setUnlocalizedName("steamcraft:percussionMusket").setTextureName("steamcraft:tools.percussionmusket");
		flintlockRifle = new ItemFirearm(config.getItem("FlintlockRifle",25060).getInt(), 120, musketRound, musketRound, 10, true).setUnlocalizedName("steamcraft:flintlockRifle").setTextureName("steamcraft:tools.flintlockrifle");
		matchlockRifle = new ItemFirearm(config.getItem("MatchlockRifle",25061).getInt(), 240, musketRound, musketRound, 8, true).setUnlocalizedName("steamcraft:matchlockRifle").setTextureName("steamcraft:tools.matchlockrifle");
		percussionCapRifle = new ItemFirearm(config.getItem("PercussionRifle",25062).getInt(), 60, percussionRound, musketRound, 12, true).setUnlocalizedName("steamcraft:percussionRifle").setTextureName("steamcraft:tools.percussionrifle");
		
		electricLamp = new ItemElectricLamp(config.getItem("ElectricLamp",25063).getInt(), torchElectricIdle).setUnlocalizedName("steamcraft:electricLamp").setTextureName("steamcraft:electriclamp");
		wirelessLamp = new ItemElectricLamp(config.getItem("WirelessLamp",25064).getInt(), wirelessLampIdle).setUnlocalizedName("steamcraft:wirelessLamp").setTextureName("steamcraft:wirelesslamp");
		
		teaSeed = new ItemSeeds(config.getItem("TeaSeed",25065).getInt(), teaPlant.blockID, Block.tilledField.blockID).setUnlocalizedName("steamcraft:teaSeeds").setTextureName("steamcraft:teaseed");
		teaLeaves = new Item(config.getItem("TeaLeaf",25066).getInt()).setUnlocalizedName("steamcraft:teaLeaf").setTextureName("steamcraft:tealeaves");
		coldKettle = new ItemKettle(config.getItem("KettleCold",25067).getInt(), 300).setUnlocalizedName("steamcraft:kettle").setTextureName("steamcraft:kettle");
		hotKettle = new ItemKettle(config.getItem("KettleHot",25068).getInt(), 300).setUnlocalizedName("steamcraft:kettleHot").setTextureName("steamcraft:kettle");
		emptyTeacup = new ItemTeacup(config.getItem("TeacupEmpty",25069).getInt(), 0).setUnlocalizedName("steamcraft:teacupEmpty").setTextureName("steamcraft:teacupempty");
		fullTeacup = new ItemTeacup(config.getItem("TeacupFull",25070).getInt(), 4).setUnlocalizedName("steamcraft:teacup").setTextureName("steamcraft:teacupfull");
		emptyKettle = new ItemKettle(config.getItem("KettleEmpty",25071).getInt(), 300).setUnlocalizedName("steamcraft:kettleempty").setTextureName("steamcraft:kettle");
	}
	@EventHandler
	public void registering(FMLInitializationEvent event)
	{
		proxy.registerRenderers();
		NetworkRegistry.instance().registerGuiHandler(this, proxy);
		GameRegistry.registerCraftingHandler(this);
		GameRegistry.registerFuelHandler(this);
		GameRegistry.registerPickupHandler(this);
		GameRegistry.registerWorldGenerator(this);
		TickRegistry.registerTickHandler(proxy, Side.SERVER);
		TickRegistry.registerTickHandler(proxy, Side.CLIENT);
		
		Block.blocksList[Block.redstoneWire.blockID] = null;
		Block redstoneWire = new BlockSCCopperWire(Block.redstoneWire.blockID).setHardness(0.0F).setStepSound(Block.soundPowderFootstep).setUnlocalizedName("redstoneDust").disableStats();
		Block.blocksList[Block.torchRedstoneIdle.blockID] = null;
		Block torchRedstoneIdle = new BlockInverter(Block.torchRedstoneIdle.blockID,  false).setHardness(0.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("notGate");
		Block.blocksList[Block.torchRedstoneActive.blockID] = null;
		Block torchRedstoneActive = new BlockInverter(Block.torchRedstoneActive.blockID,  true).setHardness(0.0F).setLightValue(0.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("notGate");
		Block.blocksList[Block.redstoneRepeaterIdle.blockID] = null;
		Block redstoneRepeaterIdle = new BlockDiode(Block.redstoneRepeaterIdle.blockID,  false).setHardness(0.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("diode").disableStats();
		Block.blocksList[Block.redstoneRepeaterActive.blockID] = null;
		Block redstoneRepeaterActive = new BlockDiode(Block.redstoneRepeaterActive.blockID,  true).setHardness(0.0F).setLightValue(0.625F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("diode").disableStats();
		Block.blocksList[Block.railPowered.blockID] = null;
		Block railPowered = new BlockPoweredRail(Block.railPowered.blockID,  true).setHardness(0.7F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("goldenRail");
		Block.blocksList[Block.oreDiamond.blockID] = null;
		Block oreDiamond = new BlockSCOre(56).setHardness(3F).setResistance(5F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("oreDiamond");
		oreSCDiamond = oreDiamond;
		Block.blocksList[Block.oreCoal.blockID] = null;
		Block oreCoal = new BlockSCOre(16).setHardness(3F).setResistance(5F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("oreCoal");
		oreSCCoal = oreCoal;
		Block.blocksList[Block.tallGrass.blockID] = null;
		Block tallGrass = new BlockSCTallGrass(31).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("tallgrass");
		Block.blocksList[Block.tilledField.blockID] = null;
		Block tilledField = new BlockSCFarmland(60).setHardness(0.6F).setStepSound(Block.soundGravelFootstep).setUnlocalizedName("farmland");
		
		Item.itemsList[Block.torchRedstoneIdle.blockID] = new ItemBlock(Block.torchRedstoneIdle.blockID - 256);
		Item.itemsList[Block.torchRedstoneActive.blockID] = new ItemBlock(Block.torchRedstoneActive.blockID - 256);
		Item.itemsList[Block.redstoneRepeaterIdle.blockID] = new ItemBlock(Block.redstoneRepeaterIdle.blockID - 256);
		Item.itemsList[Block.redstoneRepeaterActive.blockID] = new ItemBlock(Block.redstoneRepeaterActive.blockID - 256);
		Item.itemsList[Block.railPowered.blockID] = new ItemBlock(Block.railPowered.blockID - 256);
		Item.itemsList[Block.railDetector.blockID] = new ItemBlock(Block.railDetector.blockID - 256);
		
		LanguageRegistry.instance().addName(torchElectricIdle, "Electric Lamp");
		LanguageRegistry.instance().addName(torchElectricActive, "Electric Lamp");
		LanguageRegistry.instance().addName(electricLamp, "Electric Lamp");			
		LanguageRegistry.instance().addName(torchTeslaIdle, "Tesla Coil");
		LanguageRegistry.instance().addName(torchTeslaActive, "Tesla Coil");
		LanguageRegistry.instance().addName(teslaReceiver, "Tesla Receiver");
		LanguageRegistry.instance().addName(teslaReceiverActive, "Tesla Receiver");
		LanguageRegistry.instance().addName(steamOvenIdle, "Steam Furnace");
		LanguageRegistry.instance().addName(steamOvenActive, "Steam Furnace");
		LanguageRegistry.instance().addName(battery, "Battery");
		LanguageRegistry.instance().addName(Item.redstone, "Copper Wire");
		LanguageRegistry.instance().addName(Block.torchRedstoneIdle, "Inverter");
		LanguageRegistry.instance().addName(Block.torchRedstoneActive, "Inverter");
		LanguageRegistry.instance().addName(Block.redstoneRepeaterIdle, "Diode");
		LanguageRegistry.instance().addName(Block.redstoneRepeaterActive, "Diode");
		LanguageRegistry.instance().addName(Item.redstoneRepeater, "Diode");
		LanguageRegistry.instance().addName(brimstone, "Brimstone");
		LanguageRegistry.instance().addName(borniteOre, "Bornite");
		LanguageRegistry.instance().addName(orePhosphate, "Phosphate Ore");
		LanguageRegistry.instance().addName(oreUranite, "Uranite");
		LanguageRegistry.instance().addName(borniteOre, "Bornite");
		LanguageRegistry.instance().addName(quartz, "Quartz");
		LanguageRegistry.instance().addName(oreVolucite, "Volucite");
		LanguageRegistry.instance().addName(etherium, "Etherium Crystal");
		LanguageRegistry.instance().addName(chemicSalt, "Sulphur");
		LanguageRegistry.instance().addName(bornite, "Purified Copper");
		LanguageRegistry.instance().addName(obsidianSlate, "Obsidian Slate");
		LanguageRegistry.instance().addName(ingotBrass, "Brass Ingot");
		LanguageRegistry.instance().addName(ingotCastIron, "Cast Iron Ingot");
		LanguageRegistry.instance().addName(lightBulb, "Light Bulb");
		LanguageRegistry.instance().addName(phosphorus, "Phosphorus");
		LanguageRegistry.instance().addName(torchPhosphorus, "Phosphorus Torch");
		LanguageRegistry.instance().addName(uraniumStone, "Uranium");
		LanguageRegistry.instance().addName(uranium, "Uranium Pellet");
		LanguageRegistry.instance().addName(chemOvenIdle, "Chemical Furnace");
		LanguageRegistry.instance().addName(chemOvenActive, "Chemical Furnace");
		LanguageRegistry.instance().addName(nukeOvenIdle, "Nuclear Reactor");
		LanguageRegistry.instance().addName(nukeOvenActive, "Nuclear Reactor");
		LanguageRegistry.instance().addName(reactorCore, "Reactor Core");
		LanguageRegistry.instance().addName(coreDrill, "Core Drill");
		LanguageRegistry.instance().addName(drillBase, "Drill Base");
		LanguageRegistry.instance().addName(chisel, "Chisel");
		LanguageRegistry.instance().addName(spanner, "Spanner");
		LanguageRegistry.instance().addName(roofTile, "Slate Tiles");
		LanguageRegistry.instance().addName(railingCastIron, "Cast Iron Railing");
		LanguageRegistry.instance().addName(gateCastIron, "Cast Iron Gate");
		
		LanguageRegistry.instance().addName(teaPlant, "Tea Plant");
		LanguageRegistry.instance().addName(teaSeed, "Tea Seeds");
		LanguageRegistry.instance().addName(teaLeaves, "Tea Leaves");
		LanguageRegistry.instance().addName(coldKettle, "Kettle of Cold Tea");
		LanguageRegistry.instance().addName(hotKettle, "Kettle of Tea");
		LanguageRegistry.instance().addName(emptyTeacup, "Teacup");
		LanguageRegistry.instance().addName(fullTeacup, "Cup of Tea");
		LanguageRegistry.instance().addName(emptyKettle, "Empty Kettle");
		
		LanguageRegistry.instance().addName(blockCastIron, "Block of Cast Iron");
		LanguageRegistry.instance().addName(blockVolucite, "Block of Volucite");
		LanguageRegistry.instance().addName(blockBrass, "Block of Brass");
		LanguageRegistry.instance().addName(blockUranium, "Block of Uranium");
		
		LanguageRegistry.instance().addName(decorIron, "Engraved Iron Block");
		LanguageRegistry.instance().addName(decorGold, "Engraved Gold Block");
		LanguageRegistry.instance().addName(decorDiamond, "Engraved Diamond Block");
		LanguageRegistry.instance().addName(decorCastIron, "Engraved Cast Iron Block");
		LanguageRegistry.instance().addName(decorVolucite, "Engraved Volucite Block");
		LanguageRegistry.instance().addName(decorBrass, "Engraved Brass Block");
		LanguageRegistry.instance().addName(decorLapis, "Engraved Lapis Lazuli Block");
		LanguageRegistry.instance().addName(carvedStone, "Carved Stone");
		LanguageRegistry.instance().addName(decorQuartz, "Engraved Quartz Block");
		LanguageRegistry.instance().addName(decorUranium, "Engraved Uranium Block");

		LanguageRegistry.instance().addName(lamp, "Lamp Block");
		LanguageRegistry.instance().addName(lampoff, "Lamp Block");
		LanguageRegistry.instance().addName(woodBrass, "Brass Log");
		LanguageRegistry.instance().addName(leavesLamp, "Luminous Brass Leaves");
		LanguageRegistry.instance().addName(wirelessLampIdle, "Wireless Lamp");
		LanguageRegistry.instance().addName(wirelessLampActive, "Wireless Lamp");
		LanguageRegistry.instance().addName(wirelessLamp, "Wireless Lamp");
		
		LanguageRegistry.instance().addName(stairCompactStone, "Stone Stairs");
		LanguageRegistry.instance().addName(stairRoof, "Angled Slate Tiles");
		
		LanguageRegistry.instance().addName(pickaxeObsidian, "Obsidian Pickaxe");
		LanguageRegistry.instance().addName(shovelObsidian, "Obsidian Shovel");
		LanguageRegistry.instance().addName(axeObsidian, "Obsidian Axe");
		LanguageRegistry.instance().addName(hoeObsidian, "Obsidian Hoe");
		LanguageRegistry.instance().addName(swordObsidian, "Obsidian Sword");
		LanguageRegistry.instance().addName(drillObsidian, "Obsidian Drill");
		LanguageRegistry.instance().addName(pickaxeEtherium, "Etherium Pickaxe");
		LanguageRegistry.instance().addName(shovelEtherium, "Etherium Shovel");
		LanguageRegistry.instance().addName(axeEtherium, "Etherium Axe");
		LanguageRegistry.instance().addName(hoeEtherium, "Etherium Hoe");
		LanguageRegistry.instance().addName(swordEtherium, "Etherium Sword");
		LanguageRegistry.instance().addName(drillEtherium, "Etherium Drill");
		LanguageRegistry.instance().addName(pickaxeSteam, "Steam Pickaxe");
		LanguageRegistry.instance().addName(shovelSteam, "Steam Shovel");
		LanguageRegistry.instance().addName(axeSteam, "Steam Chainsaw");
		LanguageRegistry.instance().addName(hoeSteam, "Steam Hoe");
		LanguageRegistry.instance().addName(swordSteam, "Steam Sword");
		LanguageRegistry.instance().addName(drillSteam, "Steam Drill");
		
		LanguageRegistry.instance().addName(helmetObsidian, "Obsidian Helmet");
		LanguageRegistry.instance().addName(plateObsidian, "Obsidian Chest Plate");
		LanguageRegistry.instance().addName(legsObsidian, "Obsidian Leggings");
		LanguageRegistry.instance().addName(bootsObsidian, "Obsidian Boots");
		
		LanguageRegistry.instance().addName(brassGoggles, "Brass Goggles");
		LanguageRegistry.instance().addName(aqualung, "Aqualung");
		LanguageRegistry.instance().addName(rollerSkates, "Roller Skates");
		LanguageRegistry.instance().addName(legBraces, "Pneumatic Braces");
		
		LanguageRegistry.instance().addName(helmetEtherium, "Etherium Helmet");
		LanguageRegistry.instance().addName(plateEtherium, "Etherium Chest Plate");
		LanguageRegistry.instance().addName(legsEtherium, "Etherium Leggings");
		LanguageRegistry.instance().addName(bootsEtherium, "Etherium Boots");
		
		LanguageRegistry.instance().addName(drillSteel, "Iron Drill");
		LanguageRegistry.instance().addName(drillWood, "Wooden Drill");
		LanguageRegistry.instance().addName(drillStone, "Stone Drill");
		LanguageRegistry.instance().addName(drillDiamond, "Diamond Drill");
		LanguageRegistry.instance().addName(drillGold, "Golden Drill");
		
		LanguageRegistry.instance().addName(percussionLock, "Percussion Lock");
		LanguageRegistry.instance().addName(woodenStock, "Wooden Stock");
		LanguageRegistry.instance().addName(smoothBarrel, "Smoothbore Barrel");
		LanguageRegistry.instance().addName(rifledBarrel, "Rifled Barrel");
		LanguageRegistry.instance().addName(flintlockMusket, "Flintlock Musket");
		LanguageRegistry.instance().addName(matchlockMusket, "Matchlock Musket");
		LanguageRegistry.instance().addName(percussionCapMusket, "Percussion Musket");
		LanguageRegistry.instance().addName(flintlockRifle, "Flintlock Rifle");
		LanguageRegistry.instance().addName(matchlockRifle, "Matchlock Rifle");
		LanguageRegistry.instance().addName(percussionCapRifle, "Percussion Rifle");
		
		LanguageRegistry.instance().addName(musketRound, "Musket Cartidge");
		LanguageRegistry.instance().addName(percussionRound, "Percussion Cap");
		
		GameRegistry.registerBlock(torchElectricIdle,"Electric Lamp");
		GameRegistry.registerBlock(torchElectricActive,"Electric Lamp");
		GameRegistry.registerBlock(torchTeslaIdle,"Tesla Coil");
		GameRegistry.registerBlock(torchTeslaActive,"Tesla Coil");
		GameRegistry.registerBlock(teslaReceiver, "Tesla Receiver");
		GameRegistry.registerBlock(teslaReceiverActive, "Tesla Receiver");
		GameRegistry.registerBlock(steamOvenIdle, "Steam Furnace");
		GameRegistry.registerBlock(steamOvenActive, "Steam Furnace");
		GameRegistry.registerBlock(battery,"Battery");
		GameRegistry.registerBlock(brimstone,"Brimstone");
		GameRegistry.registerBlock(orePhosphate,"PhosphateOre");
		GameRegistry.registerBlock(oreUranite,"Uranite");
		GameRegistry.registerBlock(borniteOre,"Copper");
		GameRegistry.registerBlock(oreVolucite,"Volucite");
		GameRegistry.registerBlock(torchPhosphorus,"PhoshoreTorch");
		GameRegistry.registerBlock(chemOvenIdle,"ChemFurnace");
		GameRegistry.registerBlock(chemOvenActive,"ChemFurnace");
		GameRegistry.registerBlock(nukeOvenIdle,"NukeFurnace");
		GameRegistry.registerBlock(nukeOvenActive,"NukeFurnace");
		GameRegistry.registerBlock(roofTile,"Roof");
		GameRegistry.registerBlock(blockCastIron,"CastIronBlock");
		GameRegistry.registerBlock(blockVolucite,"VoluciteBlock");
		GameRegistry.registerBlock(blockBrass,"BrassBlock");
		GameRegistry.registerBlock(lamp,"LampBlock");
		GameRegistry.registerBlock(lampoff,"LampBlock");
		GameRegistry.registerBlock(woodBrass,"BrassWood");
		GameRegistry.registerBlock(leavesLamp,"BrassLeaves");
		GameRegistry.registerBlock(railingCastIron,"IronCastRail");
		GameRegistry.registerBlock(gateCastIron,"IronCastGate");
		GameRegistry.registerBlock(decorIron,"CravedIron");
		GameRegistry.registerBlock(decorGold,"CravedGold");
		GameRegistry.registerBlock(decorDiamond,"CravedDiamond");
		GameRegistry.registerBlock(decorCastIron,"CravedCastIron");
		GameRegistry.registerBlock(decorVolucite,"CravedVolucite");
		GameRegistry.registerBlock(decorBrass,"CravedBrass");
		GameRegistry.registerBlock(decorLapis,"CravedLapis");
		GameRegistry.registerBlock(carvedStone,"CravedStone");
		GameRegistry.registerBlock(stairRoof,"StairRoof");
		GameRegistry.registerBlock(blockUranium,"Uranium");
		GameRegistry.registerBlock(decorQuartz,"CravedQuartz");
		GameRegistry.registerBlock(decorUranium,"CravedUranium");
		GameRegistry.registerBlock(wirelessLampIdle,"WirelessLamp");
		GameRegistry.registerBlock(wirelessLampActive,"WirelessLamp");
		GameRegistry.registerBlock(teaPlant,"TeaPlant");

		EntityRegistry.registerModEntity(EntityMusketBall.class, "MusketBall", 1, this, 120, 1, true);
		EntityRegistry.registerModEntity(EntityHighwayman.class, "Highwayman", 2, this, 120, 1, true);
		
		EntityRegistry.addSpawn(EntityHighwayman.class, 5, 4, 5, EnumCreatureType.monster, WorldType.base12Biomes);

		GameRegistry.registerTileEntity(TileEntitySteamFurnace.class, "Steam Furnace");
		GameRegistry.registerTileEntity(TileEntityChemFurnace.class, "Chemical Furnace");
		GameRegistry.registerTileEntity(TileEntityNukeFurnace.class, "Nuclear Furnace");
		GameRegistry.registerTileEntity(TileEntityLamp.class, "Electric Lamp");
		
		GameRegistry.addSmelting(borniteOre.blockID, new ItemStack(bornite),1.0F);
		GameRegistry.addSmelting(brimstone.blockID, new ItemStack(chemicSalt),1.0F);
		GameRegistry.addSmelting(oreVolucite.blockID, new ItemStack(etherium),1.0F);
		GameRegistry.addSmelting(Item.ingotIron.itemID, new ItemStack(ingotCastIron),1.0F);
		GameRegistry.addSmelting(orePhosphate.blockID, new ItemStack(phosphorus),1.0F);
		GameRegistry.addSmelting(oreUranite.blockID, new ItemStack(uraniumStone),1.0F);
		GameRegistry.addSmelting(Block.gravel.blockID, new ItemStack(Item.flint),1.0F);
		GameRegistry.addSmelting(coldKettle.itemID, new ItemStack(hotKettle, 1, 1),1.0F);
		GameRegistry.addSmelting(Block.stoneBrick.blockID, new ItemStack(Block.stone),1.0F);
		
		GameRegistry.addRecipe(new ItemStack(obsidianSlate, 2), new Object[]{
                                  "#", Character.valueOf('#'), Block.obsidian
        });
		GameRegistry.addRecipe(new ItemStack(Block.obsidian, 1), new Object[] {
            "##", "##", Character.valueOf('#'), obsidianSlate
        });
		GameRegistry.addRecipe(new ItemStack(steamOvenIdle, 1), new Object[]{
                                "# #", "#X#", "#I#", Character.valueOf('#'), ingotBrass, Character.valueOf('X'), Item.bucketEmpty, Character.valueOf('I'), Block.furnaceIdle
        });
		GameRegistry.addRecipe(new ItemStack(Item.redstone, 8), new Object[]{
                                "###", Character.valueOf('#'), bornite
        });
		GameRegistry.addRecipe(new ItemStack(lightBulb, 4), new Object[]{
                                "I", "#", "X", Character.valueOf('#'), Item.redstone, Character.valueOf('X'), Item.ingotIron, Character.valueOf('I'), Block.glass
        });
		GameRegistry.addRecipe(new ItemStack(electricLamp, 1), new Object[]{
                                "I", "#", "X", Character.valueOf('#'), bornite, Character.valueOf('X'), ingotCastIron, Character.valueOf('I'), lightBulb
        });
		GameRegistry.addRecipe(new ItemStack(torchPhosphorus, 4), new Object[]{
                                "X", "#", Character.valueOf('#'), Item.stick, Character.valueOf('X'), phosphorus
        });
		GameRegistry.addRecipe(new ItemStack(battery, 1), new Object[]{
                                "###", "IXI", Character.valueOf('#'), Item.ingotIron, Character.valueOf('X'), quartz, Character.valueOf('I'), Item.redstone
        });
		GameRegistry.addRecipe(new ItemStack(chemOvenIdle, 1), new Object[]{
                                "###", "#X#", "#I#", Character.valueOf('#'), ingotCastIron, Character.valueOf('X'), Item.diamond, Character.valueOf('I'), steamOvenIdle
        });
		GameRegistry.addRecipe(new ItemStack(nukeOvenIdle, 1), new Object[]{
                                "#I#", "#X#", "#I#", Character.valueOf('#'), Item.ingotIron, Character.valueOf('X'), reactorCore, Character.valueOf('I'), etherium
        });
		GameRegistry.addRecipe(new ItemStack(reactorCore, 1), new Object[]{
                                "###", "#X#", "###", Character.valueOf('#'), Block.obsidian, Character.valueOf('X'), chemOvenIdle
        });
		GameRegistry.addRecipe(new ItemStack(drillBase, 1), new Object[]{
                                "#X#", "# #", " # ", Character.valueOf('#'), Item.ingotIron, Character.valueOf('X'), etherium
        });
		GameRegistry.addRecipe(new ItemStack(coreDrill, 1), new Object[]{
                                "X", "#", "I", Character.valueOf('#'), phosphorus, Character.valueOf('X'), new ItemStack(drillGold, 1, -1), Character.valueOf('I'), drillBase
        });
		GameRegistry.addRecipe(new ItemStack(roofTile, 4), new Object[] {
            "###", "###", "###", Character.valueOf('#'), Item.flint
        });
		GameRegistry.addRecipe(new ItemStack(lamp), new Object[] {
            "#X#", "XIX", "#X#", Character.valueOf('#'), ingotCastIron, Character.valueOf('X'), Block.glass, Character.valueOf('I'), Item.glowstone
        });
	/*	ModLoader.AddRecipe(new ItemStack(woodBrass, 4), new Object[] {
            "###", "#I#", "###", Character.valueOf('#'), ingotBrass, Character.valueOf('I'), Block.wood
        });
		ModLoader.AddRecipe(new ItemStack(leavesLamp, 4), new Object[] {
            "#X#", "XIX", "#X#", Character.valueOf('#'), ingotBrass, Character.valueOf('X'), Block.glass, Character.valueOf('I'), Item.lightStoneDust
        });*/
		GameRegistry.addRecipe(new ItemStack(wirelessLamp, 1), new Object[] {
            "#", "X", Character.valueOf('#'), electricLamp, Character.valueOf('X'), teslaReceiver
        });
		GameRegistry.addRecipe(new ItemStack(railingCastIron, 2), new Object[] {
            "###", "###", Character.valueOf('#'), ingotCastIron
        });
		GameRegistry.addRecipe(new ItemStack(gateCastIron, 1), new Object[] {
            "#X#", "#X#", Character.valueOf('#'), ingotCastIron, Character.valueOf('X'), railingCastIron
        });
	
		GameRegistry.addShapelessRecipe(new ItemStack(ingotBrass, 1), new Object[]{
                                 Item.ingotIron, bornite
        });
		GameRegistry.addShapelessRecipe(new ItemStack(uranium, 2), new Object[]{
                                 uraniumStone
        });
		
		GameRegistry.addRecipe(new ItemStack(pickaxeEtherium, 1), new Object[]{
                                 "XXX", " # ", " # ", Character.valueOf('#'), Item.stick, Character.valueOf('X'), etherium
        });
		GameRegistry.addRecipe(new ItemStack(shovelEtherium, 1), new Object[]{
                                 "X", "#", "#", Character.valueOf('#'), Item.stick, Character.valueOf('X'), etherium
        });
		GameRegistry.addRecipe(new ItemStack(axeEtherium, 1), new Object[]{
                                 "XX", "X#", " #", Character.valueOf('#'), Item.stick, Character.valueOf('X'), etherium
        });
		GameRegistry.addRecipe(new ItemStack(hoeEtherium, 1), new Object[]{
                                 "XX", " #", " #", Character.valueOf('#'), Item.stick, Character.valueOf('X'), etherium
        });
		GameRegistry.addRecipe(new ItemStack(swordEtherium, 1), new Object[]{
                                 "X", "X", "#", Character.valueOf('#'), Item.stick, Character.valueOf('X'), etherium
        });
		GameRegistry.addRecipe(new ItemStack(pickaxeObsidian, 1), new Object[]{
                                 "XXX", " # ", " # ", Character.valueOf('#'), Item.stick, Character.valueOf('X'), obsidianSlate
        });
		GameRegistry.addRecipe(new ItemStack(shovelObsidian, 1), new Object[]{
                                 "X", "#", "#", Character.valueOf('#'), Item.stick, Character.valueOf('X'), obsidianSlate
        });
		GameRegistry.addRecipe(new ItemStack(axeObsidian, 1), new Object[]{
                                 "XX", "X#", " #", Character.valueOf('#'), Item.stick, Character.valueOf('X'), obsidianSlate
        });
		GameRegistry.addRecipe(new ItemStack(hoeObsidian, 1), new Object[]{
                                 "XX", " #", " #", Character.valueOf('#'), Item.stick, Character.valueOf('X'), obsidianSlate
        });
		GameRegistry.addRecipe(new ItemStack(swordObsidian, 1), new Object[]{
                                 "X", "X", "#", Character.valueOf('#'), Item.stick, Character.valueOf('X'), obsidianSlate
        });
		GameRegistry.addRecipe(new ItemStack(pickaxeSteam, 1), new Object[]{
                                 "XIX", " # ", " # ", Character.valueOf('#'), Item.stick, Character.valueOf('X'), ingotBrass, Character.valueOf('I'), steamOvenIdle
        });
		GameRegistry.addRecipe(new ItemStack(shovelSteam, 1), new Object[]{
                                 "X", "#", "I", Character.valueOf('#'), Item.stick, Character.valueOf('X'), ingotBrass, Character.valueOf('I'), steamOvenIdle
        });
		GameRegistry.addRecipe(new ItemStack(axeSteam, 1), new Object[]{
                                 "X ", "XI", "# ", Character.valueOf('#'), Item.stick, Character.valueOf('X'), ingotBrass, Character.valueOf('I'), steamOvenIdle
        });
		GameRegistry.addRecipe(new ItemStack(hoeSteam, 1), new Object[]{
                                 "XI", " #", " #", Character.valueOf('#'), Item.stick, Character.valueOf('X'), ingotBrass, Character.valueOf('I'), steamOvenIdle
        });
		GameRegistry.addRecipe(new ItemStack(swordSteam, 1), new Object[]{
                                 "X", "I", "#", Character.valueOf('#'), Item.stick, Character.valueOf('X'), ingotBrass, Character.valueOf('I'), steamOvenIdle
        });
		GameRegistry.addRecipe(new ItemStack(drillSteam, 1), new Object[]{
                                 "XXX", "XIX", "XX#", Character.valueOf('#'), Item.stick, Character.valueOf('X'), ingotBrass, Character.valueOf('I'), steamOvenIdle
        });
		
		
		GameRegistry.addRecipe(new ItemStack(helmetObsidian, 1), new Object[]{
                                 "XXX", "X X", Character.valueOf('X'), obsidianSlate
        });
		GameRegistry.addRecipe(new ItemStack(plateObsidian, 1), new Object[]{
                                 "X X", "XXX", "XXX", Character.valueOf('X'), obsidianSlate
        });
		GameRegistry.addRecipe(new ItemStack(legsObsidian, 1), new Object[]{
                                 "XXX", "X X", "X X", Character.valueOf('X'), obsidianSlate
        });
		GameRegistry.addRecipe(new ItemStack(bootsObsidian, 1), new Object[]{
                                 "X X", "X X", Character.valueOf('X'), obsidianSlate
        });
		GameRegistry.addRecipe(new ItemStack(helmetEtherium, 1), new Object[]{
                                 "XXX", "X X", Character.valueOf('X'), etherium
        });
		GameRegistry.addRecipe(new ItemStack(plateEtherium, 1), new Object[]{
                                 "X X", "XXX", "XXX", Character.valueOf('X'), etherium
        });
		GameRegistry.addRecipe(new ItemStack(legsEtherium, 1), new Object[]{
                                 "XXX", "X X", "X X", Character.valueOf('X'), etherium
        });
		GameRegistry.addRecipe(new ItemStack(bootsEtherium, 1), new Object[]{
                                 "X X", "X X", Character.valueOf('X'), etherium
        });
		
		GameRegistry.addRecipe(new ItemStack(brassGoggles, 1), new Object[]{
                                 "X#X", "# #", Character.valueOf('X'), Block.glass, Character.valueOf('#'), ingotBrass
        });
		
		GameRegistry.addRecipe(new ItemStack(aqualung, 1), new Object[]{
                                 "XTX", "X X", "X#X", Character.valueOf('X'), ingotBrass, Character.valueOf('#'), Block.pistonBase, Character.valueOf('T'), Block.glass
        });
		
		GameRegistry.addRecipe(new ItemStack(rollerSkates, 1), new Object[]{
                                 "X X", "X X", "# #", Character.valueOf('X'), ingotBrass, Character.valueOf('#'), Item.ingotIron
        });
		
		GameRegistry.addRecipe(new ItemStack(legBraces, 1), new Object[]{
                                 "XXX", "X X", "# #", Character.valueOf('X'), ingotBrass, Character.valueOf('#'), Block.pistonBase
        });
		
		GameRegistry.addRecipe(new ItemStack(spanner, 1), new Object[]{
                                 "# #", "###", " # ", Character.valueOf('#'), ingotBrass
        });
		
		GameRegistry.addRecipe(new ItemStack(chisel, 1), new Object[]{
                                 "#", "#", "X", Character.valueOf('#'), Item.ingotIron, Character.valueOf('X'), ingotBrass
        });
		
		GameRegistry.addRecipe(new ItemStack(Block.music, 1), new Object[] {
            "###", "#X#", "###", Character.valueOf('#'), Block.planks, Character.valueOf('X'), quartz
        });
		GameRegistry.addRecipe(new ItemStack(Block.railPowered, 6), new Object[] {
            "XRX", "X#X", "XRX", Character.valueOf('X'), Item.ingotGold, Character.valueOf('R'), Item.redstone, Character.valueOf('#'), Item.stick
        });
		GameRegistry.addRecipe(new ItemStack(Block.railDetector, 6), new Object[] {
            "XRX", "X#X", "XRX", Character.valueOf('X'), Item.ingotIron, Character.valueOf('R'), Item.redstone, Character.valueOf('#'), Block.pressurePlateStone
        });
		GameRegistry.addRecipe(new ItemStack(Block.lever, 1), new Object[] {
            "X", "#", "I", Character.valueOf('#'), Block.cobblestone, Character.valueOf('X'), Item.stick, Character.valueOf('I'), battery
        });
		GameRegistry.addRecipe(new ItemStack(Block.torchRedstoneActive, 4), new Object[] {
            "X", "#", "I", Character.valueOf('#'), Item.stick, Character.valueOf('X'), Item.redstone, Character.valueOf('I'), battery
        });
		GameRegistry.addRecipe(new ItemStack(Item.redstoneRepeater, 1), new Object[] {
            "#X#", "IRI", Character.valueOf('#'), Block.torchRedstoneActive, Character.valueOf('X'), Item.redstone, Character.valueOf('I'), Block.stone, Character.valueOf('R'), quartz
        });
		GameRegistry.addRecipe(new ItemStack(Item.pocketSundial, 1), new Object[] {
            " # ", "#X#", " # ", Character.valueOf('#'), Item.ingotGold, Character.valueOf('X'), quartz
        });
		GameRegistry.addRecipe(new ItemStack(Item.compass, 1), new Object[] {
            " # ", "#X#", " # ", Character.valueOf('#'), Item.ingotIron, Character.valueOf('X'), quartz
        });
		GameRegistry.addRecipe(new ItemStack(Block.stoneButton, 1), new Object[] {
            "#", "#", "X", Character.valueOf('#'), Block.stone, Character.valueOf('X'), battery
        });
		GameRegistry.addRecipe(new ItemStack(Block.pressurePlateStone, 1), new Object[] {
            "##", "X ", Character.valueOf('#'), Block.stone, Character.valueOf('X'), battery
        });
		GameRegistry.addRecipe(new ItemStack(Block.pressurePlatePlanks, 1), new Object[] {
            "##", "X ", Character.valueOf('#'), Block.planks, Character.valueOf('X'), battery
        });
		GameRegistry.addRecipe(new ItemStack(Block.dispenser, 1), new Object[] {
            "###", "#X#", "#R#", Character.valueOf('#'), Block.cobblestone, Character.valueOf('X'), Item.bow, Character.valueOf('R'), quartz
        });
		GameRegistry.addRecipe(new ItemStack(Item.gunpowder, 1), new Object[] {
            "#X#", Character.valueOf('#'), chemicSalt, Character.valueOf('X'), Item.coal
        });
		GameRegistry.addRecipe(new ItemStack(torchTeslaIdle, 1), new Object[] {
            " X ", "I#I", "ITI", Character.valueOf('#'), Item.ingotGold, Character.valueOf('X'), lightBulb, Character.valueOf('I'), Item.redstone, Character.valueOf('T'), quartz
        });
		GameRegistry.addRecipe(new ItemStack(Item.glowstone, 4), new Object[] {
           "X#X", "#I#", "X#X", Character.valueOf('#'), phosphorus, Character.valueOf('X'), chemicSalt, Character.valueOf('I'), uraniumStone
        });
		GameRegistry.addRecipe(new ItemStack(teslaReceiver, 1), new Object[] {
           "#X#", "ITI", Character.valueOf('#'), ingotCastIron, Character.valueOf('X'), Item.ingotGold, Character.valueOf('I'), Item.redstone, Character.valueOf('T'), quartz
        });
		GameRegistry.addRecipe(new ItemStack(Block.pistonBase, 1), new Object[] {
            "TTT", "#X#", "#R#", Character.valueOf('#'), Block.cobblestone, Character.valueOf('X'), Item.ingotIron, Character.valueOf('R'), bornite, Character.valueOf('T'), 
            Block.planks
        });
		
		GameRegistry.addRecipe(new ItemStack(percussionLock, 1), new Object[] {
            "X ", "##", Character.valueOf('#'), Item.ingotIron, Character.valueOf('X'), ingotBrass
        });
		GameRegistry.addRecipe(new ItemStack(woodenStock, 1), new Object[] {
            "#  ", " # ", "  #", Character.valueOf('#'), Block.planks
        });
		GameRegistry.addRecipe(new ItemStack(smoothBarrel, 1), new Object[] {
            "#  ", " # ", "  #", Character.valueOf('#'), Item.ingotIron
        });
		GameRegistry.addShapelessRecipe(new ItemStack(rifledBarrel, 1), new Object[] {
            new ItemStack(smoothBarrel, 1), new ItemStack(chisel, 1, -1)
		});
		GameRegistry.addRecipe(new ItemStack(flintlockMusket, 1, new ItemStack(flintlockMusket, 1).getMaxDamage()-1), new Object[] {
            "X", "#", "T", Character.valueOf('#'), smoothBarrel, Character.valueOf('X'), new ItemStack(Item.flintAndSteel, 1, -1), Character.valueOf('T'), woodenStock 
        });
		GameRegistry.addRecipe(new ItemStack(matchlockMusket, 1, new ItemStack(matchlockMusket, 1).getMaxDamage()-1), new Object[] {
            "X", "#", "T", Character.valueOf('#'), smoothBarrel, Character.valueOf('X'), Item.silk, Character.valueOf('T'), woodenStock 
        });
		GameRegistry.addRecipe(new ItemStack(percussionCapMusket, 1, new ItemStack(percussionCapMusket, 1).getMaxDamage()-1), new Object[] {
            "X", "#", "T", Character.valueOf('#'), smoothBarrel, Character.valueOf('X'), percussionLock, Character.valueOf('T'), woodenStock 
        });
		GameRegistry.addRecipe(new ItemStack(flintlockRifle, 1, new ItemStack(flintlockRifle, 1).getMaxDamage()-1), new Object[] {
            "X", "#", "T", Character.valueOf('#'), rifledBarrel, Character.valueOf('X'), new ItemStack(Item.flintAndSteel, 1, -1), Character.valueOf('T'), woodenStock 
        });
		GameRegistry.addRecipe(new ItemStack(matchlockRifle, 1, new ItemStack(matchlockRifle, 1).getMaxDamage()-1), new Object[] {
            "X", "#", "T", Character.valueOf('#'), rifledBarrel, Character.valueOf('X'), Item.silk, Character.valueOf('T'), woodenStock 
        });
		GameRegistry.addRecipe(new ItemStack(percussionCapRifle, 1, new ItemStack(percussionCapRifle, 1).getMaxDamage()-1), new Object[] {
            "X", "#", "T", Character.valueOf('#'), rifledBarrel, Character.valueOf('X'), percussionLock, Character.valueOf('T'), woodenStock 
        });
		
		GameRegistry.addRecipe(new ItemStack(musketRound, 8), new Object[] {
            "X", "#", "T", Character.valueOf('#'), Item.gunpowder, Character.valueOf('X'), Item.ingotIron, Character.valueOf('T'), Item.paper 
        });
		GameRegistry.addRecipe(new ItemStack(percussionRound, 8), new Object[] {
            "T", "#", "X", Character.valueOf('#'), Item.gunpowder, Character.valueOf('X'), ingotBrass, Character.valueOf('T'), Item.paper
        });
		
		GameRegistry.addShapelessRecipe(new ItemStack(Item.slimeBall, 1), new Object[] {
			Item.bucketWater, chemicSalt
		});
		
		
		GameRegistry.addRecipe(new ItemStack(emptyKettle, 1, new ItemStack(emptyKettle, 1).getMaxDamage()-1), new Object[]{
                                  "#  ", "###", " ##", Character.valueOf('#'), ingotCastIron
        });
		GameRegistry.addRecipe(new ItemStack(emptyTeacup, 1), new Object[]{
                                  "# #", " # ", Character.valueOf('#'), Item.clay
        });
		GameRegistry.addShapelessRecipe(new ItemStack(coldKettle, 1, 1), new Object[]{
                                 new ItemStack(emptyKettle, 1, -1), Item.bucketWater, teaLeaves
        });
		
		DrillRecipeItems = (new Object[][] {
			new Object[] {
                Block.planks, Block.cobblestone, Item.ingotIron, Item.diamond, Item.ingotGold, obsidianSlate, etherium
			}, new Object[] {
                drillWood, drillStone, drillSteel, drillDiamond, drillGold, drillObsidian, drillEtherium
            }
			});
			
		for(int i = 0; i < DrillRecipeItems[0].length; i ++){
			GameRegistry.addRecipe(new ItemStack((Item) DrillRecipeItems[1][i], 1), new Object[]{
                                 "XXX", "XXX", "XX#", Character.valueOf('#'), Item.stick, Character.valueOf('X'), DrillRecipeItems[0][i]
        });
		}
		
		SpannerRecipeItems = (new Object[][] {
			new Object[] {
                Item.pickaxeWood, Item.pickaxeStone, Item.pickaxeIron, Item.pickaxeDiamond, Item.pickaxeGold, pickaxeObsidian, pickaxeSteam
            }, new Object[] {
                Item.shovelWood, Item.shovelStone, Item.shovelIron, Item.shovelDiamond, Item.shovelGold, shovelObsidian, shovelSteam
            }, new Object[] {
                Item.axeWood, Item.axeStone, Item.axeIron, Item.axeDiamond, Item.axeGold, axeObsidian, axeSteam
            }, new Object[] {
                Item.hoeWood, Item.hoeStone, Item.hoeIron, Item.hoeDiamond, Item.hoeGold, hoeObsidian, hoeSteam
            }, new Object[] {
                drillWood, drillStone, drillSteel, drillDiamond, drillGold, drillObsidian, drillSteam
            }, new Object[] {
                Item.swordWood, Item.swordStone, Item.swordIron, Item.swordDiamond, Item.swordGold, swordObsidian, swordSteam
            }, new Object[] {
                Item.helmetLeather, Item.helmetChain, Item.helmetIron, Item.helmetDiamond, Item.helmetGold, helmetObsidian, brassGoggles
            }, new Object[] {
                Item.plateLeather, Item.plateChain, Item.plateIron, Item.plateDiamond, Item.plateGold, plateObsidian, aqualung
            }, new Object[] {
                Item.legsLeather, Item.legsChain, Item.legsIron, Item.legsDiamond, Item.legsGold, legsObsidian, legBraces
            }, new Object[] {
                Item.bootsLeather, Item.bootsChain, Item.bootsIron, Item.bootsDiamond, Item.bootsGold, bootsObsidian, rollerSkates
            }
        });
		
		StoreBlockRecipeItems = (new Object[][] {
            new Object[] {
                blockCastIron, new ItemStack(ingotCastIron, 9)
            }, new Object[] {
                blockVolucite, new ItemStack(etherium, 9)
            }, new Object[] {
                blockBrass, new ItemStack(ingotBrass, 9)
            }, new Object[] {
                blockUranium, new ItemStack(uraniumStone, 9)
            }
        });
		
		DecorBlockRecipeItems = (new Object[][] {
            new Object[] {
               decorGold, new ItemStack(Block.blockGold)
            }, new Object[] {
               decorIron, new ItemStack(Block.blockIron)
            }, new Object[] {
                decorDiamond, new ItemStack(Block.blockDiamond)
            }, new Object[] {
                decorCastIron, new ItemStack(blockCastIron)
            }, new Object[] {
                decorVolucite, new ItemStack(blockVolucite)
            }, new Object[] {
                decorBrass, new ItemStack(blockBrass)
            }, new Object[] {
                decorLapis, new ItemStack(Block.blockLapis)
            }, new Object[] {
               carvedStone, new ItemStack(Block.stone)
            }, new Object[] {
                decorUranium, new ItemStack(blockUranium)
            }
        });
		
		StairRecipeItems = (new Object[][] {
            new Object[] {
              new ItemStack(Block.stone), stairCompactStone
            }, new Object[] {
               new ItemStack(Item.flint), stairRoof
            }
        });
		
		addSpannerRecipes();
		addStorageBlockRecipes();
		addDecorBlockRecipes();
		addStairRecipes();
		
		//ItemPickaxe.addSteamcraftBlocks();
		ItemSCPickaxe.addSteamcraftBlocks();
		ItemSCDrill.addSteamcraftBlocks();
		
		//EntityHighwayman.setHeldItem(flintlockMusket, flintlockRifle, matchlockMusket, matchlockRifle, percussionCapMusket, percussionCapRifle);
		
		addAchievements();
}

	private Object[][] DrillRecipeItems,SpannerRecipeItems,StoreBlockRecipeItems;
	private Object[][] DecorBlockRecipeItems,StairRecipeItems;

 /*public void AddRecipes(CraftingManager craftingmanager)
        {
                craftingmanager.addRecipe(new ItemStack(obsidianSlate, 1), new Object[]{
                                  "#", Character.valueOf('#'), Block.obsidian
        });
  }*/
    
	private void addStairRecipes(){
		for(int i = 0; i < StairRecipeItems.length; i++){
			Block block = (Block)StairRecipeItems[i][1];
            ItemStack itemstack = (ItemStack)StairRecipeItems[i][0];
            GameRegistry.addRecipe(new ItemStack(block,3), new Object[] {
            "#  ", "## ", "###", Character.valueOf('#'), itemstack
				});
			}
		}
  
	private void addDecorBlockRecipes(){
		for(int i = 0; i < DecorBlockRecipeItems.length; i++){
			Block block = (Block)DecorBlockRecipeItems[i][0];
            ItemStack itemstack = (ItemStack)DecorBlockRecipeItems[i][1];
            GameRegistry.addShapelessRecipe(new ItemStack(block), new Object[] {
            itemstack, new ItemStack(chisel, 1, -1)
				});
            GameRegistry.addSmelting(block.blockID, itemstack,1.0F);
			}
		}
  
	private void addSpannerRecipes(){
		for(int i = 0; i < SpannerRecipeItems[0].length; i++)
        {
            for(int j = 0; j < SpannerRecipeItems.length; j++)
            {
			Item item = (Item)SpannerRecipeItems[j][i];
			ItemStack itemstack = new ItemStack(item, 1, -1);
			GameRegistry.addShapelessRecipe(new ItemStack(item), new Object[] {
                    itemstack, new ItemStack(spanner, 1, -1)
                });
            }

        }
	}

	private void addStorageBlockRecipes()
    {
        for(int i = 0; i < StoreBlockRecipeItems.length; i++)
        {
            Block block = (Block)StoreBlockRecipeItems[i][0];
            ItemStack itemstack = (ItemStack)StoreBlockRecipeItems[i][1];
            GameRegistry.addRecipe(new ItemStack(block), new Object[] {
                "###", "###", "###", Character.valueOf('#'), itemstack
            });
            GameRegistry.addRecipe(itemstack, new Object[] {
                "#", Character.valueOf('#'), block
            });
        }

    }

	public static void addAchievements(){
		ach_BlackMagic = (new Achievement(AchievementList.achievementList.size(),"Black Magic",6,4, pickaxeObsidian, AchievementList.buildBetterPickaxe)).registerAchievement();
		ModLoader.addAchievementDesc(ach_BlackMagic, "Black Magic", "Construct an obsidian pickaxe");
		ach_CarryingYou = (new Achievement(AchievementList.achievementList.size(),"Carrying You",8,4, etherium, ach_BlackMagic)).registerAchievement();
		ModLoader.addAchievementDesc(ach_CarryingYou, "Carrying You", "Mine some volucite");
		ach_SpiralNemesis = (new Achievement(AchievementList.achievementList.size(),"Spiral Nemesis",4,5, drillSteel, AchievementList.buildWorkBench)).registerAchievement();
		ModLoader.addAchievementDesc(ach_SpiralNemesis, "Spiral Nemesis", "Construct a drill");
		ach_Fallout = (new Achievement(AchievementList.achievementList.size(),"Fallout",-1,6, nukeOvenIdle, AchievementList.acquireIron)).registerAchievement();
		ModLoader.addAchievementDesc(ach_Fallout, "Fallout", "Smelt something with a nuclear reactor");
		ach_WhoTheHellDoYouThinkIAm = (new Achievement(AchievementList.achievementList.size(),"Who The Hell Do You Think I Am?",2,-7, coreDrill, ach_SpiralNemesis)).setSpecial().registerAchievement();
		ModLoader.addAchievementDesc(ach_WhoTheHellDoYouThinkIAm, "Heaven Piercing", "WHO THE HELL DO YOU THINK I AM?");
		ach_ItsAlive = (new Achievement(AchievementList.achievementList.size(),"ItsAlive",-2,4, torchTeslaActive, AchievementList.acquireIron)).registerAchievement();
		ModLoader.addAchievementDesc(ach_ItsAlive, "It's Alive!", "Construct a tesla coil");
		ach_MasterCraftsman = (new Achievement(AchievementList.achievementList.size(),"MasterCraftsman",1,7, decorCastIron, AchievementList.acquireIron)).registerAchievement();
		ModLoader.addAchievementDesc(ach_MasterCraftsman, "Master Craftsman", "Engrave a block");
		ach_RuinedEverything = (new Achievement(AchievementList.achievementList.size(),"RuinedEverything",-3,6, uraniumStone, ach_Fallout)).registerAchievement();
		ModLoader.addAchievementDesc(ach_RuinedEverything, "Ruined Everything", "Melt down a nuclear reactor");
		ach_JethroTull = (new Achievement(AchievementList.achievementList.size(),"JethroTull",1,2, aqualung, AchievementList.acquireIron)).registerAchievement();
		ModLoader.addAchievementDesc(ach_JethroTull, "Jethro Tull", "Construct an aqualung");
		ach_LockStockAndBarrel = (new Achievement(AchievementList.achievementList.size(),"LockStockAndBarrel",-3,3, flintlockMusket, AchievementList.acquireIron)).registerAchievement();
		ModLoader.addAchievementDesc(ach_LockStockAndBarrel, "Lock, Stock and Barrel", "Construct a musket or rifle");
		ach_TimeForACuppa = (new Achievement(AchievementList.achievementList.size(),"TimeForACuppa",0,5, fullTeacup, AchievementList.acquireIron)).registerAchievement();
		ModLoader.addAchievementDesc(ach_TimeForACuppa, "Time For A Cuppa!", "Pour yourself a cup of tea");
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if(world.provider.isHellWorld)
			generateNether(world,random,chunkX,chunkZ);
		else
			generateSurface(world,random,chunkX,chunkZ);
	}
	
	public void generateSurface(World world, Random rand, int k, int l)
	{
		for(int j29 = 0; j29 < 12; j29++)
        {
            int k30 = k + rand.nextInt(16);
            int l30 = rand.nextInt(64);
            int j30 = l + rand.nextInt(16);
            (new WorldGenMinable(brimstone.blockID, 8)).generate(world, rand, k30, l30, j30);
        }
		for(int j3 = 0; j3 < 6; j3++)
        {
            int k6 = k + rand.nextInt(16);
            int l9 = rand.nextInt(64);
            int j14 = l + rand.nextInt(16);
            (new WorldGenMinable(Block.oreIron.blockID, 16)).generate(world, rand, k6, l9, j14);
        }
		for(int l3 = 0; l3 < 20; l3++)
        {
            int i7 = k + rand.nextInt(16);
            int j10 = rand.nextInt(48);
            int l14 = l + rand.nextInt(16);
            (new WorldGenMinable(borniteOre.blockID, 8)).generate(world, rand, i7, j10, l14);
        }
		
		 for(int i29 = 0; i29 < 3; i29++)
        {
            int k31 = k + rand.nextInt(16);
            int l31 = rand.nextInt(36);
            int j31 = l + rand.nextInt(16);
            (new WorldGenMinable(orePhosphate.blockID, 6)).generate(world, rand, k31, l31, j31);
        }
		for(int i32 = 0; i32 < 2; i32++)
        {
            int j33 = k + rand.nextInt(16);
            int k33 = rand.nextInt(24);
            int i33 = l + rand.nextInt(16);
            (new WorldGenMinable(oreUranite.blockID, 4)).generate(world, rand, j33, k33, i33);
        }
		for(int i33 = 0; i33 < 4; i33++)
        {
            int j34 = k + rand.nextInt(16);
            int k34 = rand.nextInt(48);
            int i34 = l + rand.nextInt(16);
            (new WorldGenMinable(Block.oreRedstone.blockID, 4)).generate(world, rand, j34, k34, i34);
        }
		
		for(int i31 = 0; i31 < 1; i31++)
        {
            int j32 = k + rand.nextInt(16);
            int k32 = rand.nextInt(16)+12;
            int i32 = l + rand.nextInt(16);
            (new WorldGenMinable(oreVolucite.blockID, 3)).generate(world, rand, j32, k32, i32);
        }
		for(int k1 = 0; k1 < 8; k1++)
        {
            int j5 = k + rand.nextInt(16) + 8;
            int k8 = rand.nextInt(128);
            int j11 = l + rand.nextInt(16) + 8;
            (new WorldGenHighwaymanHideout()).generate(world, rand, j5, k8, j11);
        }
	}
	
	public void generateNether(World world, Random rand, int k, int l)
	{
		for(int i32 = 0; i32 < 20; i32++)
        {
            int j33 = k + rand.nextInt(16);
            int k33 = rand.nextInt(128);
            int i33 = l + rand.nextInt(16);
            (new WorldGenSCNetherTrees()).generate(world, rand, j33, k33, i33);
        }
	}

	@Override
	public void notifyPickup(EntityItem item, EntityPlayer player) {
		if(item.getEntityItem().itemID == etherium.itemID){
            player.triggerAchievement(ach_CarryingYou);
         }
	}

	@Override
	public void onCrafting(EntityPlayer player, ItemStack item, IInventory craftMatrix) {
		if(item.itemID == pickaxeObsidian.itemID){
            player.triggerAchievement(ach_BlackMagic);
         }
		 if(item.itemID == drillWood.itemID || item.itemID == drillStone.itemID || item.itemID == drillSteel.itemID || item.itemID == drillDiamond.itemID || item.itemID == drillGold.itemID || item.itemID == drillObsidian.itemID || item.itemID == drillEtherium.itemID){
            player.triggerAchievement(ach_SpiralNemesis);
         }
		 if(item.itemID == coreDrill.itemID){
            player.triggerAchievement(ach_WhoTheHellDoYouThinkIAm);
         }
		 if(item.itemID == torchTeslaIdle.blockID){
            player.triggerAchievement(ach_ItsAlive);
         }
		 if(item.itemID == decorIron.blockID || item.itemID == decorCastIron.blockID || item.itemID == decorBrass.blockID || item.itemID == decorGold.blockID || item.itemID == decorLapis.blockID || item.itemID == decorDiamond.blockID || item.itemID == decorVolucite.blockID || item.itemID == decorQuartz.blockID || item.itemID == decorUranium.blockID || item.itemID == carvedStone.blockID){
            player.triggerAchievement(ach_MasterCraftsman);
         }
		 if(item.itemID == aqualung.itemID){
            player.triggerAchievement(ach_JethroTull);
         }
		 if(item.itemID == matchlockMusket.itemID || item.itemID == flintlockMusket.itemID || item.itemID == percussionCapMusket.itemID || item.itemID == matchlockRifle.itemID || item.itemID == flintlockRifle.itemID || item.itemID == percussionCapRifle.itemID){
            player.triggerAchievement(ach_LockStockAndBarrel);
         }
		 int repairDmg = -1000;
			int spannerpos = -1;
			int spannerdmg = -1;
			int spannermaxdmg = -1;
	        for(int i = 0; i < craftMatrix.getSizeInventory(); i++)
	        {
	        	ItemStack itemstack1 = craftMatrix.getStackInSlot(i);
	            if(itemstack1 != null)
	            {
	            	if(itemstack1.getItem() != spanner && itemstack1.isItemStackDamageable()){
	            		repairDmg = itemstack1.getItemDamage();
	            	}
	            	if(itemstack1.getItem() != spanner){
	            		craftMatrix.decrStackSize(i, 1);
	            	}
	            	if(itemstack1.getItem() == chisel && itemstack1.getItemDamage()+1 < itemstack1.getMaxDamage()){
	            		if(item.getItem() == rifledBarrel){
	            			craftMatrix.setInventorySlotContents(i, new ItemStack(itemstack1.getItem(), 1, itemstack1.getItemDamage() +16));
	            		}else{
	            			craftMatrix.setInventorySlotContents(i, new ItemStack(itemstack1.getItem(), 1, itemstack1.getItemDamage() +1));
	            		}
	            	}
	            	if(itemstack1.getItem() == spanner){
	            		spannerpos = i;
	            		spannerdmg = itemstack1.getItemDamage();
	            		spannermaxdmg = itemstack1.getMaxDamage();
	            	}
	                if(itemstack1.getItem().hasContainerItem())
	                {
	                    craftMatrix.setInventorySlotContents(i, new ItemStack(itemstack1.getItem().getContainerItem()));
	                }
	            }
	        }
			if(spannerpos > -1 && repairDmg >= 0){
				craftMatrix.decrStackSize(spannerpos, 1);
				if(repairDmg > 0 && spannerdmg+1 < spannermaxdmg){
					craftMatrix.setInventorySlotContents(spannerpos, new ItemStack(spanner, 1, spannerdmg+1));
				}
				else if(repairDmg == 0)
				{
					craftMatrix.setInventorySlotContents(spannerpos, new ItemStack(spanner, 1, spannerdmg));
				}
			}
	}

	@Override
	public void onSmelting(EntityPlayer player, ItemStack item) {
	}
	@Override
	public int getBurnTime(ItemStack fuel) {
		if(TileEntityChemFurnace.fuels.containsKey(fuel.itemID))
			return (int) TileEntityChemFurnace.fuels.get(fuel.itemID);
		else if(fuel.itemID==uranium.itemID)
		{
			return 3200;
		}
		return 0;
	}
	
}