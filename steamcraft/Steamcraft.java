package steamcraft;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

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
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.*;
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

@Mod(modid="steamcraft",name="SteamCraft",version="0.2")
@NetworkMod(clientSideRequired=true)
public class Steamcraft implements ICraftingHandler,IPickupNotifier,IWorldGenerator,IFuelHandler
{
	@Instance(value="steamcraft")
	public static Steamcraft instance;
	@SidedProxy(clientSide="steamcraft.ClientProxy",serverSide="steamcraft.CommonProxy")
	public static CommonProxy proxy;
	
	public static Achievement[] achs = new Achievement[11];
	//harvestLevel, maxUses,efficiencyOnProperMaterial,damageVsEntity,enchantability;
	public static final EnumToolMaterial TOOLOBSIDIAN = EnumHelper.addToolMaterial("OBSIDIAN", 5, 210, 7F, 4, 5);
	public static final EnumToolMaterial TOOLETHERIUM = EnumHelper.addToolMaterial("ETHERIUM", 6, -1, 8F, 3, 8);
	public static final EnumToolMaterial TOOLSTEAM = EnumHelper.addToolMaterial("STEAM", 2, 321, 12F, 5, 0);
	
	public static Block torchElectricIdle,torchElectricActive,torchTeslaIdle,torchTeslaActive;
	public static Block torchPhosphorus,teslaReceiver,teslaReceiverActive,battery;
	public static Block redstoneWire,torchRedstoneIdle,torchRedstoneActive,wirelessLampIdle;
	public static Block redstoneRepeaterIdle,redstoneRepeaterActive,railPowered,wirelessLampActive;
	
	public static Block steamOvenIdle,steamOvenActive,chemOvenIdle,chemOvenActive;
	public static Block nukeOvenIdle,nukeOvenActive;
	public static Block borniteOre,orePhosphate,oreUranite,oreQuartz,
						oreQuartzActive,oreVolucite,brimstone;
	
	public static Block decorBlock,railingCastIron,gateCastIron;
	
	public static Block lamp,lampoff,woodBrass,leavesLamp;
	
	public static Block stairRoof,roofTile;
	
	public static Block teaPlant;
	
	public static Item material;
	
	public static Item pickaxeObsidian,shovelObsidian,axeObsidian,hoeObsidian;
	public static Item swordObsidian,drillObsidian;
	public static Item helmetObsidian,plateObsidian,legsObsidian,bootsObsidian;
	
	public static Item brassGoggles,aqualung,rollerSkates,legBraces;
	
	public static Item pickaxeEtherium,shovelEtherium,axeEtherium,hoeEtherium;
	public static Item swordEtherium,drillEtherium;
	public static Item helmetEtherium,plateEtherium,legsEtherium,bootsEtherium;
	
	public static Item swordSteam,shovelSteam,pickaxeSteam;
	public static Item drillSteam,axeSteam,hoeSteam;
	public static Item coreDrill;
	public static Item drillSteel,drillWood,drillStone,drillDiamond,drillGold;
	
	public static Item chisel,spanner;
	
	public static Item firearm, part;
	public static ItemStack flintlockMusket,matchlockMusket,percussionCapMusket;
	public static ItemStack flintlockRifle,matchlockRifle,percussionCapRifle;
	
	public static Item electricLamp,wirelessLamp;
	
	public static Item teaSeed,teaLeaves,coldKettle,hotKettle;
	public static Item emptyTeacup,fullTeacup,emptyKettle;
	
	public static final Material solidcircuit = new MaterialLogic(MapColor.airColor);
	public static final Material staticcircuit = new StaticMaterial(MapColor.airColor);
	@SuppressWarnings("unused")
	private Logger logger;
	
	private Object[][] DrillRecipeItems,SpannerRecipeItems,
						StoreBlockRecipeItems,DecorBlockRecipeItems;
	private static final String[] materials = {
		"etherium","sulphur","copper","obsidianslate","ingotbrass","ingotcastiron","lightbulb",
		"phosphorus","uraniumstone","uraniumpellet","reactorcore","coredrillbase"};
	private static final String[] firearmParts = 
		{"musketcartridge","percussioncap","percussionlock",
		"smoothbarrel","rifledbarrel","woodenstock"};
	
	private static final String[] decorBlockNames = {
		"Block of Cast Iron","Block of Volucite","Block of Brass","Block of Uranium",
		"Engraved Iron Block","Engraved Gold Block","Engraved Diamond Block",
		"Engraved Cast Iron Block","Engraved Volucite Block","Engraved Brass Block",
		"Engraved Lapis Lazuli Block","Carved Stone","Engraved Uranium Block"};
	
	public static final String[] armorNames = {"etherium","brass","obsidian"};
	public static final int[] REDUCTION_AMOUNTS= new int[]{3,8,6,3};
	public static final EnumArmorMaterial ARMORETHERIUM = EnumHelper.addArmorMaterial("ETHERIUM", -1, REDUCTION_AMOUNTS, 5);
	public static final EnumArmorMaterial ARMOROBSIDIAN = EnumHelper.addArmorMaterial("OBSIDIAN", 20, REDUCTION_AMOUNTS, 10);
	public static final EnumArmorMaterial ARMORBRASS = EnumHelper.addArmorMaterial("BRASS", 5, REDUCTION_AMOUNTS, 0);
	public static int[] armorIndexes = new int[armorNames.length];
	public static Map armorMap = new HashMap();
	public static Map<Object,Object[]> data = new HashMap();
	public static final CreativeTabs steamTab = new SteamTab();
	
	@EventHandler
	public void load(FMLPreInitializationEvent event)
	{	
		logger = event.getModLog();
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		redstoneWire = new BlockSCCopperWire(config.getBlock("CopperWire",2493).getInt()).setHardness(0.0F).setStepSound(Block.soundPowderFootstep).setUnlocalizedName("steamcraft:copperwire").setTextureName("redstone_dust");
		torchRedstoneIdle = new BlockInverter(config.getBlock("Inverter",2494).getInt(),  false).setHardness(0.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("steamcraft:inverteridle").setTextureName("steamcraft:inverteridle");
		torchRedstoneActive = new BlockInverter(config.getBlock("InverterON",2495).getInt(),  true).setHardness(0.0F).setLightValue(0.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("steamcraft:inverteractive").setTextureName("steamcraft:inverteractive");
		redstoneRepeaterIdle = new BlockDiode(config.getBlock("Repeater",2496).getInt(),  false).setHardness(0.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("steamcraft:diodeidle").setTextureName("steamcraft:diodeidle");
		redstoneRepeaterActive = new BlockDiode(config.getBlock("RepeaterON",2497).getInt(),  true).setHardness(0.0F).setLightValue(0.625F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("steamcraft:diodeactive").setTextureName("steamcraft:diodeactive");
		railPowered = new BlockPoweredRail(config.getBlock("Rail",2498).getInt(),  true).setHardness(0.7F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("steamcraft:rail").setTextureName("steamcraft:rail");
		
		torchElectricIdle = new BlockElectricLamp(config.getBlock("ElectricLamp",2500).getInt(), TileEntityLamp.class, false).setHardness(0.0F).setUnlocalizedName("steamcraft:electricLamp").setTextureName("steamcraft:electriclamp");
		torchElectricActive = new BlockElectricLamp(config.getBlock("ElectricLampON", 2501).getInt(), TileEntityLamp.class, true).setHardness(0.0F).setLightValue(1.0F).setUnlocalizedName("steamcraft:electricLampOn").setTextureName("steamcraft:electriclamp");
		torchTeslaIdle = new BlockTeslaCoil(config.getBlock("TeslaCoil",2502).getInt(),  false).setHardness(0.0F).setUnlocalizedName("steamcraft:teslaCoil").setTextureName("steamcraft:teslaidle");
		torchTeslaActive = new BlockTeslaCoil(config.getBlock("TeslaCoilON",2053).getInt(),  true).setHardness(0.0F).setLightValue(0.625F).setUnlocalizedName("steamcraft:teslaCoilOn").setTextureName("steamcraft:teslaactive");
		teslaReceiver = new BlockTeslaReceiver(config.getBlock("Receiver",2504).getInt()).setHardness(0.5F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("steamcraft:receiver").setTextureName("steamcraft:receiver");
		teslaReceiverActive = new BlockTeslaReceiver(config.getBlock("ReceiverON",2506).getInt()).setHardness(0.5F).setLightValue(0.625F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("steamcraft:receiverOn").setTextureName("steamcraft:receiveractive");
		steamOvenIdle = new BlockSteamFurnace(config.getBlock("SteamFurnace",2507).getInt(),false).setHardness(4F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("steamcraft:steamFurnace").setTextureName("steamcraft:steamfurnaceidle");
		steamOvenActive = new BlockSteamFurnace(config.getBlock("SteamFurnaceON",2508).getInt(), true).setHardness(4F).setStepSound(Block.soundMetalFootstep).setLightValue(0.875F).setUnlocalizedName("steamcraft:steamFurnaceOn").setTextureName("steamcraft:steamfurnaceactive");
		chemOvenIdle = new BlockChemFurnace(config.getBlock("ChemicalFurnace",2509).getInt(), false).setHardness(4.5F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("steamcraft:chemFurnace").setTextureName("steamcraft:chemfurnaceidle");
		chemOvenActive = new BlockChemFurnace(config.getBlock("ChemicalFurnaceON",2510).getInt(), true).setHardness(4.5F).setStepSound(Block.soundMetalFootstep).setLightValue(0.875F).setUnlocalizedName("steamcraft:chemFurnaceOn").setTextureName("steamcraft:chemfurnaceactive");
		nukeOvenIdle = new BlockNukeFurnace(config.getBlock("NuclearFurnace",2511).getInt(),  false).setHardness(5F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("steamcraft:nukeFurnace").setTextureName("steamcraft:nukefurnaceidle");
		nukeOvenActive = new BlockNukeFurnace(config.getBlock("NuclearFurnaceON",2512).getInt(),  true).setHardness(5F).setStepSound(Block.soundMetalFootstep).setLightValue(0.9375F).setUnlocalizedName("steamcraft:nukeFurnaceOn").setTextureName("steamcraft:nukefurnaceactive");
		battery = new BlockBattery(config.getBlock("Battery",2513).getInt()).setHardness(0.5F).setLightValue(0.625F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("steamcraft:battery").setTextureName("steamcraft:battery");
		brimstone = new BlockOre(config.getBlock("BrimstoneOre",2514).getInt()).setHardness(3F).setResistance(5F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("steamcraft:brimstone").setTextureName("steamcraft:brimstone");
		orePhosphate = new BlockOre(config.getBlock("PhosphateOre",2515).getInt()).setHardness(2.5F).setResistance(5F).setStepSound(Block.soundStoneFootstep).setLightValue(0.75F).setUnlocalizedName("steamcraft:orePhosphate").setTextureName("steamcraft:phosphate");
		oreUranite = new BlockUraniteOre(config.getBlock("UraniteOre",2516).getInt()).setHardness(10F).setResistance(6F).setLightValue(0.625F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("steamcraft:oreUranite").setTextureName("steamcraft:uranite");
		borniteOre = new BlockOre(config.getBlock("BorniteOre",2517).getInt() ).setHardness(3F).setResistance(5F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("steamcraft:oreBornite").setTextureName("steamcraft:bornite");
		oreVolucite = new BlockSCOre(config.getBlock("VoluciteOre",2518).getInt()).setHardness(50F).setResistance(6000000F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("steamcraft:oreVolucite").setTextureName("steamcraft:voluciteore");
		torchPhosphorus = new BlockTorchPhosphorus(config.getBlock("PhosphorusTorch",2519).getInt()).setHardness(0.0F).setLightValue(1.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("steamcraft:torchPhosphorus").setTextureName("steamcraft:torchphosphorus");
		roofTile = new Block(config.getBlock("SlateTiles",2520).getInt(),Material.rock).setHardness(2F).setResistance(10F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("steamcraft:roofTile").setTextureName("steamcraft:slatetiles");
		
		decorBlock = new BlockDecor(config.getBlock("CarvedBlock", 2521).getInt());
		
		gateCastIron = new BlockSCFenceGate(config.getBlock("CastIronGate",2536).getInt() ).setHardness(7F).setResistance(20F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("steamcraft:gateCastIron").setTextureName("steamcraft:castironblock");
		railingCastIron = new BlockSCFence(config.getBlock("CastIronRailing",2537).getInt(),  Material.iron, Steamcraft.gateCastIron, true).setHardness(7F).setResistance(20F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("steamcraft:railingCastIron").setTextureName("steamcraft:castironblock");
		
		lamp = new BlockLamp(config.getBlock("LampBlock",2538).getInt(), true).setUnlocalizedName("steamcraft:lampOn").setTextureName("steamcraft:lampblock");
		lampoff = new BlockLamp(config.getBlock("LampBlockOFF",2539).getInt() , false).setUnlocalizedName("steamcraft:lamp").setTextureName("steamcraft:lampblock");
		woodBrass = new BlockBrassLog(config.getBlock("BrassLog",2540).getInt()).setHardness(5F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("steamcraft:logBrass").setTextureName("steamcraft:brasslog");
		leavesLamp = new Block(config.getBlock("BrassLeaves",2541).getInt(), Material.wood).setHardness(2F).setLightOpacity(1).setLightValue(0.9375F).setStepSound(Block.soundGlassFootstep).setUnlocalizedName("steamcraft:leavesLamp").setTextureName("steamcraft:brassleaves");
		wirelessLampIdle = new BlockWirelessLamp(config.getBlock("WirelessLamp",2542).getInt() , TileEntityLamp.class, false).setHardness(0.0F).setUnlocalizedName("steamcraft:wirelessLamp").setTextureName("steamcraft:wirelesslamp");
		wirelessLampActive = new BlockWirelessLamp(config.getBlock("WirelessLampON",2543).getInt() , TileEntityLamp.class, true).setHardness(0.0F).setLightValue(1.0F).setUnlocalizedName("steamcraft:wirelessLampOn").setTextureName("steamcraft:wirelesslamp");
		
		stairRoof = new BlockSCStairs(config.getBlock("SlateStairs",2545).getInt(),roofTile,0, Item.flint.itemID, 2).setUnlocalizedName("steamcraft:stairsRoof").setTextureName("steamcraft:slatetiles");
		
		teaPlant = new BlockSCTeaPlant(config.getBlock("TeaPlant",2546).getInt()).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("steamcraft:teaplant").setTextureName("steamcraft:teaplant");
				
		material = new MultiItem(config.getItem("Materials",25010).getInt(), materials).setUnlocalizedName("steamcraft:").setTextureName("steamcraft:");
		
		coreDrill = new ItemCoreDrill(config.getItem("CoreDrill",25011).getInt()).setUnlocalizedName("steamcraft:coreDrill").setTextureName("steamcraft:coredrill");
		
		pickaxeObsidian = new ItemSCPickaxe(config.getItem("ObsidianPick",25013).getInt(), TOOLOBSIDIAN).setUnlocalizedName("steamcraft:pickaxeObsidian").setTextureName("steamcraft:tools/obsidianpick");
		shovelObsidian = new ItemSpade(config.getItem("ObsidianSpade",25014).getInt(), TOOLOBSIDIAN).setUnlocalizedName("steamcraft:shovelObsidian").setTextureName("steamcraft:tools/obsidianspade");
		axeObsidian = new ItemSCAxe(config.getItem("ObsidianAxe",25015).getInt(), TOOLOBSIDIAN).setUnlocalizedName("steamcraft:hatchetObsidian").setTextureName("steamcraft:tools/obsidianaxe");
		hoeObsidian = new ItemSCHoe(config.getItem("ObsidianHoe",25016).getInt(), TOOLOBSIDIAN).setUnlocalizedName("steamcraft:hoeObsidian").setTextureName("steamcraft:tools/obsidianhoe");
		swordObsidian = new ItemSCSword(config.getItem("ObsidianSword",25017).getInt(), TOOLOBSIDIAN).setUnlocalizedName("steamcraft:swordObsidian").setTextureName("steamcraft:tools/obsidiansword");
		
		drillObsidian = new ItemSCDrill(config.getItem("ObsidianDrill",25018).getInt(), TOOLOBSIDIAN).setUnlocalizedName("steamcraft:drillObsidian").setTextureName("steamcraft:tools/obsidiandrill");
		
		for(int i =0; i<armorNames.length; i++)
		{
			armorIndexes[i] = proxy.registerArmor(armorNames[i]);
			armorMap.put(armorIndexes[i],armorNames[i]);
		}
		helmetObsidian = new ItemSCArmor(config.getItem("ObsidianHelmet",25019).getInt(), ARMOROBSIDIAN, armorIndexes[2], 0).setUnlocalizedName("steamcraft:helmetObsidian").setTextureName("steamcraft:armour/obsidianhelmet");
		plateObsidian = new ItemSCArmor(config.getItem("ObsidianPlate",25020).getInt(), ARMOROBSIDIAN, armorIndexes[2], 1).setUnlocalizedName("steamcraft:chestplateObsidian").setTextureName("steamcraft:armour/obsidianplate");
		legsObsidian = new ItemSCArmor(config.getItem("ObsidianLegs",25021).getInt(), ARMOROBSIDIAN, armorIndexes[2], 2).setUnlocalizedName("steamcraft:leggingsObsidian").setTextureName("steamcraft:armour/obsidianlegs");
		bootsObsidian = new ItemSCArmor(config.getItem("ObsidianBoots",25022).getInt(), ARMOROBSIDIAN, armorIndexes[2], 3).setUnlocalizedName("steamcraft:bootsObsidian").setTextureName("steamcraft:armour/obsidianboots");
		
		brassGoggles = new ItemSCArmor(config.getItem("BrassGoggles",25023).getInt(), ARMORBRASS, armorIndexes[1], 0).setUnlocalizedName("steamcraft:brassGoggles").setTextureName("steamcraft:armour/brassgoggles");
		aqualung = new ItemSCArmor(config.getItem("Aqualung",25024).getInt(), ARMORBRASS, armorIndexes[1], 1).setUnlocalizedName("steamcraft:aqualung").setTextureName("steamcraft:armour/aqualung");
		rollerSkates = new ItemSCArmor(config.getItem("RollerSkates",25025).getInt(), ARMORBRASS, armorIndexes[1], 3).setUnlocalizedName("steamcraft:rollerSkates").setTextureName("steamcraft:armour/rollerskates");
		legBraces = new ItemSCArmor(config.getItem("PneumaticBraces",25026).getInt(), ARMORBRASS, armorIndexes[1], 2).setUnlocalizedName("steamcraft:legBraces").setTextureName("steamcraft:armour/pneumaticbraces");
		
		pickaxeEtherium = new ItemSCPickaxe(config.getItem("EtheriumPick",25027).getInt(), TOOLETHERIUM).setUnlocalizedName("steamcraft:pickaxeEtherium").setTextureName("steamcraft:tools/etheriumpick");
		shovelEtherium = new ItemSpade(config.getItem("EtheriumSpade",25028).getInt(), TOOLETHERIUM).setUnlocalizedName("steamcraft:shovelEtherium").setTextureName("steamcraft:tools/etheriumspade");
		axeEtherium = new ItemSCAxe(config.getItem("EtheriumAxe",25029).getInt(),TOOLETHERIUM).setUnlocalizedName("steamcraft:hatchetEtherium").setTextureName("steamcraft:tools/etheriumaxe");
		hoeEtherium = new ItemSCHoe(config.getItem("EtheriumHoe",25030).getInt(),TOOLETHERIUM).setUnlocalizedName("steamcraft:hoeEtherium").setTextureName("steamcraft:tools/etheriumhoe");
		swordEtherium = new ItemSCSword(config.getItem("EtheriumSword",25031).getInt(),TOOLETHERIUM).setUnlocalizedName("steamcraft:swordEtherium").setTextureName("steamcraft:tools/etheriumsword");
		drillEtherium = new ItemSCDrill(config.getItem("EtheriumDrill",25032).getInt(),TOOLETHERIUM).setUnlocalizedName("steamcraft:drillEtherium").setTextureName("steamcraft:tools/etheriumdrill");
		
		helmetEtherium = new ItemSCArmor(config.getItem("EtheriumHelmet",25033).getInt(), ARMORETHERIUM, armorIndexes[0], 0).setUnlocalizedName("steamcraft:helmetEtherium").setTextureName("steamcraft:armour/etheriumhelmet");
		plateEtherium = new ItemSCArmor(config.getItem("EtheriumPlate",25034).getInt(), ARMORETHERIUM, armorIndexes[0], 1).setUnlocalizedName("steamcraft:chestplateEtherium").setTextureName("steamcraft:armour/etheriumplate");
		legsEtherium = new ItemSCArmor(config.getItem("EtheriumLegs",25036).getInt(), ARMORETHERIUM, armorIndexes[0], 2).setUnlocalizedName("steamcraft:leggingsEtherium").setTextureName("steamcraft:armour/etheriumlegs");
		bootsEtherium = new ItemSCArmor(config.getItem("EtheriumBoots",25037).getInt(), ARMORETHERIUM, armorIndexes[0], 3).setUnlocalizedName("steamcraft:bootsEtherium").setTextureName("steamcraft:armour/etheriumboots");
		
		pickaxeSteam = new ItemSCPickaxe(config.getItem("SteamPick",25038).getInt(),TOOLSTEAM).setUnlocalizedName("steamcraft:pickaxeSteam").setTextureName("steamcraft:tools/steampick");
		shovelSteam = new ItemSpade(config.getItem("SteamSpade",25039).getInt(),TOOLSTEAM).setUnlocalizedName("steamcraft:shovelSteam").setTextureName("steamcraft:tools/steamspade");
		axeSteam = new ItemSCAxe(config.getItem("SteamAxe",25040).getInt(),TOOLSTEAM).setUnlocalizedName("steamcraft:hatchetSteam").setTextureName("steamcraft:tools/steamaxe");
		hoeSteam = new ItemSCHoe(config.getItem("SteamHoe",25041).getInt(),TOOLSTEAM).setUnlocalizedName("steamcraft:hoeSteam").setTextureName("steamcraft:tools/steamhoe");
		swordSteam =new ItemSCSword(config.getItem("SteamSword",25042).getInt(),TOOLSTEAM).setUnlocalizedName("steamcraft:swordSteam").setTextureName("steamcraft:tools/steamsword");
		drillSteam = new ItemSCDrill(config.getItem("SteamDrill",25043).getInt(),TOOLSTEAM).setUnlocalizedName("steamcraft:drillSteam").setTextureName("steamcraft:tools/steamdrill");
		
		
		drillSteel = new ItemSCDrill(config.getItem("IronDrill",25044).getInt(), EnumToolMaterial.IRON).setUnlocalizedName("steamcraft:drillIron").setTextureName("steamcraft:tools/irondrill");
		drillWood = new ItemSCDrill(config.getItem("WoodenDrill",25045).getInt(), EnumToolMaterial.WOOD).setUnlocalizedName("steamcraft:drillWood").setTextureName("steamcraft:tools/wooddrill");
		drillStone = new ItemSCDrill(config.getItem("StoneDrill",25046).getInt(), EnumToolMaterial.STONE).setUnlocalizedName("steamcraft:drillStone").setTextureName("steamcraft:tools/stonedrill");
		drillDiamond = new ItemSCDrill(config.getItem("DiamondDrill",25047).getInt(), EnumToolMaterial.EMERALD).setUnlocalizedName("steamcraft:drillDiamond").setTextureName("steamcraft:tools/diamonddrill");
		drillGold = new ItemSCDrill(config.getItem("GoldenDrill",25048).getInt(), EnumToolMaterial.GOLD).setUnlocalizedName("steamcraft:drillGold").setTextureName("steamcraft:tools/golddrill");
		
		chisel = new Item(config.getItem("Chisel",25049).getInt()).setFull3D().setMaxDamage(64).setMaxStackSize(1).setUnlocalizedName("steamcraft:chisel").setTextureName("steamcraft:tools/chisel");
		spanner = new Item(config.getItem("Spanner",25050).getInt()).setFull3D().setMaxDamage(3).setMaxStackSize(1).setUnlocalizedName("steamcraft:spanner").setTextureName("steamcraft:tools/spanner");				
		
		part = new MultiItem(config.getItem("FirearmParts",25056).getInt(),firearmParts).setUnlocalizedName("steamcraft:").setTextureName("steamcraft:");
		firearm = new ItemFirearm(config.getItem("Firearm",25057).getInt()).setUnlocalizedName("steamcraft:").setTextureName("steamcraft:tools/");
		flintlockMusket = new ItemStack(firearm,1,0);
		ItemFirearm.setFirePower(flintlockMusket,8);
		ItemFirearm.setMaxDamage(flintlockMusket, 100);
		matchlockMusket = new ItemStack(firearm,1,0);
		ItemFirearm.setFirePower(matchlockMusket,6);
		ItemFirearm.setMaxDamage(matchlockMusket, 200);
		percussionCapMusket = new ItemStack(firearm,1,0);
		ItemFirearm.setFirePower(percussionCapMusket,10);
		ItemFirearm.setMaxDamage(percussionCapMusket, 50);
		ItemFirearm.setPercussion(percussionCapMusket);
		flintlockRifle = new ItemStack(firearm,1,0);
		ItemFirearm.setFirePower(flintlockRifle,10);
		ItemFirearm.setMaxDamage(flintlockRifle, 120);
		ItemFirearm.setRifled(flintlockRifle);
		matchlockRifle = new ItemStack(firearm,1,0);
		ItemFirearm.setFirePower(matchlockRifle,8);
		ItemFirearm.setMaxDamage(matchlockRifle, 240);
		ItemFirearm.setRifled(matchlockRifle);
		percussionCapRifle = new ItemStack(firearm,1,0);
		ItemFirearm.setFirePower(percussionCapRifle,12);
		ItemFirearm.setMaxDamage(percussionCapRifle, 60);
		ItemFirearm.setRifled(percussionCapRifle);
		ItemFirearm.setPercussion(percussionCapRifle);
		EntityHighwayman.heldItems[0]=flintlockMusket;
		EntityHighwayman.heldItems[1]=flintlockRifle;
		EntityHighwayman.heldItems[2]=matchlockMusket;
		EntityHighwayman.heldItems[3]=matchlockRifle;
		EntityHighwayman.heldItems[4]=percussionCapMusket;
		EntityHighwayman.heldItems[5]=percussionCapRifle;
		
		electricLamp = new ItemElectricLamp(config.getItem("ElectricLamp",25063).getInt(), torchElectricIdle).setUnlocalizedName("steamcraft:electricLamp").setTextureName("steamcraft:electriclamp");
		wirelessLamp = new ItemElectricLamp(config.getItem("WirelessLamp",25064).getInt(), wirelessLampIdle).setUnlocalizedName("steamcraft:wirelessLamp").setTextureName("steamcraft:wirelesslamp");
		
		teaSeed = new ItemSeeds(config.getItem("TeaSeed",25065).getInt(), teaPlant.blockID, Block.tilledField.blockID).setUnlocalizedName("steamcraft:teaSeeds").setTextureName("steamcraft:teaseed");
		teaLeaves = new Item(config.getItem("TeaLeaf",25066).getInt()).setUnlocalizedName("steamcraft:teaLeaf").setTextureName("steamcraft:tealeaves");
		coldKettle = new ItemKettle(config.getItem("KettleCold",25067).getInt()).setUnlocalizedName("steamcraft:kettle").setTextureName("steamcraft:kettle");
		hotKettle = new ItemKettle(config.getItem("KettleHot",25068).getInt()).setUnlocalizedName("steamcraft:kettleHot").setTextureName("steamcraft:kettle");
		emptyTeacup = new ItemTeacup(config.getItem("TeacupEmpty",25069).getInt(), 0).setUnlocalizedName("steamcraft:teacupEmpty").setTextureName("steamcraft:teacupempty");
		fullTeacup = new ItemTeacup(config.getItem("TeacupFull",25070).getInt(), 4).setUnlocalizedName("steamcraft:teacup").setTextureName("steamcraft:teacupfull");
		emptyKettle = new ItemKettle(config.getItem("KettleEmpty",25071).getInt()).setUnlocalizedName("steamcraft:kettleempty").setTextureName("steamcraft:kettle");
		
		if(config.hasChanged())
			config.save();
		data.put(torchElectricIdle, new Object[]{"Electric Torch"});
		data.put(torchElectricActive, new Object[]{"Electric Torch Active"});
		data.put(electricLamp, new Object[]{"Electric Lamp"});
		data.put(torchTeslaIdle, new Object[]{"Tesla Coil"});
		data.put(torchTeslaActive, new Object[]{"Tesla Coil Active"});
		data.put(teslaReceiver, new Object[]{"Tesla Receiver"});
		data.put(teslaReceiverActive, new Object[]{"Tesla Receiver Active"});
		data.put(steamOvenIdle, new Object[]{"Steam Furnace"});
		data.put(steamOvenActive, new Object[]{"Steam Furnace Active"});
		data.put(battery, new Object[]{"Battery"});
		data.put(redstoneWire, new Object[]{"Copper Wire"});
		data.put(torchRedstoneIdle, new Object[]{"Inverter"});
		data.put(torchRedstoneActive, new Object[]{"Inverter Active"});
		data.put(redstoneRepeaterIdle, new Object[]{"Diode"});
		data.put(redstoneRepeaterActive, new Object[]{"Diode Active"});
		data.put(railPowered, new Object[]{"Rail"});
		data.put(brimstone, new Object[]{"Brimstone"});
		data.put(borniteOre, new Object[]{"Bornite","oreBornite"});
		data.put(orePhosphate, new Object[]{"Phosphate Ore","orePhosphate"});
		data.put(oreUranite, new Object[]{"Uranite","oreUranite"});
		data.put(oreVolucite, new Object[]{"Volucite","oreVolucite"});
		data.put(new ItemStack(material,1,0), new Object[]{"Etherium Crystal","oreEtherium"});
		data.put(new ItemStack(material,1,1), new Object[]{"Sulphur","oreSulphur"});
		data.put(new ItemStack(material,1,2), new Object[]{"Purified Copper","oreCopper"});
		data.put(new ItemStack(material,1,3), new Object[]{"Obsidian Slate","itemObsidianSlate"});
		data.put(new ItemStack(material,1,4), new Object[]{"Brass Ingot","ingotBrass"});
		data.put(new ItemStack(material,1,5), new Object[]{"Cast Iron Ingot","ingotCastIron"});
		data.put(new ItemStack(material,1,6), new Object[]{"Light Bulb","itemLightBulb"});
		data.put(new ItemStack(material,1,7), new Object[]{"Phosphorus","ingotPosphate"});
		data.put(torchPhosphorus, new Object[]{"Phosphorus Torch"});
		data.put(new ItemStack(material,1,8), new Object[]{"Uranium","oreUranium"});
		data.put(new ItemStack(material,1,9), new Object[]{"Uranium Pellet","ingotUranium"});
		data.put(chemOvenIdle, new Object[]{"Chemical Furnace"});
		data.put(chemOvenActive, new Object[]{"Chemical Furnace Active"});
		data.put(nukeOvenIdle, new Object[]{"Nuclear Reactor"});
		data.put(nukeOvenActive, new Object[]{"Nuclear Reactor Active"});
		data.put(new ItemStack(material,1,10), new Object[]{"Reactor Core","itemReactorCore"});
		data.put(coreDrill, new Object[]{"Core Drill"});
		data.put(new ItemStack(material,1,11), new Object[]{"Drill Base","itemDrillBase"});
		data.put(chisel, new Object[]{"Chisel"});
		data.put(spanner, new Object[]{"Spanner"});
		data.put(roofTile, new Object[]{"Slate Tiles"});
		data.put(railingCastIron, new Object[]{"Cast Iron Railing"});
		data.put(gateCastIron, new Object[]{"Cast Iron Gate"});
		data.put(teaPlant, new Object[]{"Tea Plant"});
		data.put(teaSeed, new Object[]{"Tea Seeds"});
		data.put(teaLeaves, new Object[]{"Tea Leaves"});
		data.put(coldKettle, new Object[]{"Kettle of Cold Tea"});
		data.put(hotKettle, new Object[]{"Kettle of Tea"});
		data.put(emptyTeacup, new Object[]{"Teacup"});
		data.put(fullTeacup, new Object[]{"Cup of Tea"});
		data.put(emptyKettle, new Object[]{"Empty Kettle"});
		data.put(lamp, new Object[]{"Lamp Block Active"});
		data.put(lampoff, new Object[]{"Lamp Block"});
		data.put(woodBrass, new Object[]{"Brass Log"});
		data.put(leavesLamp, new Object[]{"Luminous Brass Leaves"});
		data.put(wirelessLampIdle, new Object[]{"Wireless Lamp Idle"});
		data.put(wirelessLampActive, new Object[]{"Wireless Lamp Active"});
		data.put(wirelessLamp, new Object[]{"Wireless Lamp"});
		data.put(stairRoof, new Object[]{"Angled Slate Tiles"});
		data.put(pickaxeObsidian, new Object[]{"Obsidian Pickaxe"});
		data.put(shovelObsidian, new Object[]{"Obsidian Shovel"});
		data.put(axeObsidian, new Object[]{"Obsidian Axe"});
		data.put(hoeObsidian, new Object[]{"Obsidian Hoe"});
		data.put(swordObsidian, new Object[]{"Obsidian Sword"});
		data.put(drillObsidian, new Object[]{"Obsidian Drill"});
		data.put(pickaxeEtherium, new Object[]{"Etherium Pickaxe"});
		data.put(shovelEtherium, new Object[]{"Etherium Shovel"});
		data.put(axeEtherium, new Object[]{"Etherium Axe"});
		data.put(hoeEtherium, new Object[]{"Etherium Hoe"});
		data.put(swordEtherium, new Object[]{"Etherium Sword"});
		data.put(drillEtherium, new Object[]{"Etherium Drill"});
		data.put(pickaxeSteam, new Object[]{"Steam Pickaxe"});
		data.put(shovelSteam, new Object[]{"Steam Shove"});
		data.put(axeSteam, new Object[]{"Steam Chainsaw"});
		data.put(hoeSteam, new Object[]{"Steam Hoe"});
		data.put(swordSteam, new Object[]{"Steam Sword"});
		data.put(drillSteam, new Object[]{"Steam Drill"});
		data.put(helmetObsidian, new Object[]{"Obsidian Helmet"});
		data.put(plateObsidian, new Object[]{"Obsidian Chest Plate"});
		data.put(legsObsidian, new Object[]{"Obsidian Leggings"});
		data.put(bootsObsidian, new Object[]{"Obsidian Boots"});
		data.put(brassGoggles, new Object[]{"Brass Goggles"});
		data.put(aqualung, new Object[]{"Aqualung"});
		data.put(rollerSkates, new Object[]{"Roller Skates"});
		data.put(legBraces, new Object[]{"Pneumatic Braces"});
		data.put(helmetEtherium, new Object[]{"Etherium Helmet"});
		data.put(plateEtherium, new Object[]{"Etherium Chest Plate"});
		data.put(legsEtherium, new Object[]{"Etherium Leggings"});
		data.put(bootsEtherium, new Object[]{"Etherium Boots"});
		data.put(drillSteel, new Object[]{"Iron Drill"});
		data.put(drillWood, new Object[]{"Wooden Drill"});
		data.put(drillStone, new Object[]{"Stone Drill"});
		data.put(drillDiamond, new Object[]{"Diamond Drill"});
		data.put(drillGold, new Object[]{"Golden Drill"});
		data.put(new ItemStack(part,1,0), new Object[]{"Musket Cartridge","itemMusketCartridge"});
		data.put(new ItemStack(part,1,1), new Object[]{"Percussion Cap","itemPercussionCap"});
		data.put(new ItemStack(part,1,2), new Object[]{"Percussion Lock","itemPercussionLock"});
		data.put(new ItemStack(part,1,3), new Object[]{"Smoothbore Barrel","itemBarrel"});
		data.put(new ItemStack(part,1,4), new Object[]{"Rifled Barrel","itemBarrelRifled"});
		data.put(new ItemStack(part,1,5), new Object[]{"Wooden Stock","itemWoodenStock"});
		data.put(flintlockMusket, new Object[]{"Flintlock Musket","itemFlintlockMusket"});
		data.put(matchlockMusket, new Object[]{"Matchlock Musket","itemMatchlockMusket"});
		data.put(percussionCapMusket, new Object[]{"Percussion Musket","itemPercussionMusket"});
		data.put(flintlockRifle, new Object[]{"Flintlock Rifle","itemFlintlockRifle"});
		data.put(matchlockRifle, new Object[]{"Matchlock Rifle","itemMatchLockRifle"});
		data.put(percussionCapRifle, new Object[]{"Percussion Rifle","itemPercussionRifle"});
		
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
		
		for(Object obj:data.keySet())
		{
			LanguageRegistry.addName(obj,(String) data.get(obj)[0]);
			if(obj instanceof Block)
			{
				if(!((String)data.get(obj)[0]).toLowerCase().contains("active")||obj instanceof BlockInverter)
					((Block) obj).setCreativeTab(steamTab);
				GameRegistry.registerBlock((Block) obj,(String) data.get(obj)[0]);
			}
			else if(obj instanceof Item)
			{
				((Item) obj).setCreativeTab(steamTab);
				GameRegistry.registerItem((Item)obj, (String) data.get(obj)[0]);
			}
			else if(obj instanceof ItemStack)
			{
				OreDictionary.registerOre((String) data.get(obj)[1], (ItemStack)obj);
			}
		}
		MinecraftForge.setToolClass(drillWood, "drill", 0);
		MinecraftForge.setToolClass(drillStone, "drill", 1);
		MinecraftForge.setToolClass(drillSteel, "drill", 2);
		MinecraftForge.setToolClass(drillDiamond, "drill", 3);
		MinecraftForge.setToolClass(drillObsidian, "drill", 5);
		MinecraftForge.setToolClass(drillEtherium, "drill", 6);
		MinecraftForge.setToolClass(drillSteam, "drill", 7);
		MinecraftForge.setToolClass(drillGold, "drill", 0);
		MinecraftForge.setToolClass(pickaxeObsidian, "pickaxe", 5);
		MinecraftForge.setToolClass(pickaxeEtherium, "pickaxe", 6);
		MinecraftForge.setToolClass(pickaxeSteam, "pickaxe", 7);
		MinecraftForge.setToolClass(axeObsidian, "axe", 5);
		MinecraftForge.setToolClass(axeEtherium, "axe", 6);
		MinecraftForge.setToolClass(axeSteam, "axe", 7);
		MinecraftForge.setToolClass(shovelObsidian, "shovel", 5);
		MinecraftForge.setToolClass(shovelEtherium, "shovel", 6);
		MinecraftForge.setToolClass(shovelSteam, "shovel", 7);
		String[] toolsets={"pickaxe","drill"};
		GameRegistry.registerBlock(decorBlock,ItemBlockDecor.class,"steamcraft:decor");
		for (int ix = 0; ix < BlockDecor.names.length; ix++) {
			ItemStack stack = new ItemStack(decorBlock, 1, ix);
			LanguageRegistry.addName(stack, decorBlockNames [stack.getItemDamage()]);
			for(String tool:toolsets)
				MinecraftForge.setBlockHarvestLevel(decorBlock, ix, tool, 2);
		}
		for(String tool:toolsets){
			MinecraftForge.setBlockHarvestLevel(decorBlock, 0, tool, 1);
			MinecraftForge.setBlockHarvestLevel(decorBlock, 4, tool, 1);
			MinecraftForge.setBlockHarvestLevel(decorBlock, 7, tool, 1);
			MinecraftForge.setBlockHarvestLevel(decorBlock, 10, tool, 1);
			MinecraftForge.setBlockHarvestLevel(decorBlock, 1, tool, 5);
			MinecraftForge.setBlockHarvestLevel(decorBlock, 8, tool, 5);
		}
		MinecraftForge.addGrassSeed(new ItemStack(teaSeed), 5);
		
		EntityRegistry.registerModEntity(EntityMusketBall.class, "MusketBall", 1, this, 120, 1, true);
		EntityRegistry.registerModEntity(EntityHighwayman.class, "Highwayman", 2, this, 120, 1, true);
		
		EntityRegistry.addSpawn(EntityHighwayman.class, 5, 1, 5, EnumCreatureType.monster, WorldType.base12Biomes);

		GameRegistry.registerTileEntity(TileEntitySteamFurnace.class, "Steam Furnace");
		GameRegistry.registerTileEntity(TileEntityChemFurnace.class, "Chemical Furnace");
		GameRegistry.registerTileEntity(TileEntityNukeFurnace.class, "Nuclear Furnace");
		GameRegistry.registerTileEntity(TileEntityLamp.class, "Electric Lamp");
		
		GameRegistry.addSmelting(borniteOre.blockID, new ItemStack(material,1,2),1.0F);
		GameRegistry.addSmelting(brimstone.blockID, new ItemStack(material,1,1),1.0F);
		GameRegistry.addSmelting(oreVolucite.blockID, new ItemStack(material,1,0),1.0F);
		GameRegistry.addSmelting(Item.ingotIron.itemID, new ItemStack(material,1,5),1.0F);
		GameRegistry.addSmelting(orePhosphate.blockID, new ItemStack(material,1,7),1.0F);
		GameRegistry.addSmelting(oreUranite.blockID, new ItemStack(material,1,8),1.0F);
		GameRegistry.addSmelting(Block.gravel.blockID, new ItemStack(Item.flint),1.0F);
		GameRegistry.addSmelting(coldKettle.itemID, new ItemStack(hotKettle, 1, 1),1.0F);
		GameRegistry.addSmelting(Block.stoneBrick.blockID, new ItemStack(Block.stone),1.0F);
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(
			new ItemStack(redstoneWire, 4, 0), "oreCopper"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(material, 4, 3),
			"#", "#", Character.valueOf('#'), Block.obsidian));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.obsidian), 
			"##", "##", Character.valueOf('#'), "itemObsidianSlate"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(steamOvenIdle, 1),
			"# #", "#X#", "#I#", Character.valueOf('#'), "ingotBrass", Character.valueOf('X'), Item.bucketEmpty, Character.valueOf('I'), Block.furnaceIdle
        ));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.redstone, 8),
			"###", Character.valueOf('#'), "oreCopper"));
		GameRegistry.addRecipe(new ItemStack(material, 4, 6), 
			"I", "#", "X", Character.valueOf('#'), Item.redstone, Character.valueOf('X'), Item.ingotIron, Character.valueOf('I'), Block.glass
        );
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(electricLamp),
			"I", "#", "X", Character.valueOf('#'), "oreCopper", Character.valueOf('X'), "ingotCastIron", Character.valueOf('I'), new ItemStack(material, 1, 6)
        ));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(torchPhosphorus, 4),
			"X", "#", Character.valueOf('#'), Item.stick, Character.valueOf('X'), "ingotPosphate"
        ));
		GameRegistry.addRecipe(new ItemStack(battery),
			"###", "IXI", Character.valueOf('#'), Item.ingotIron, Character.valueOf('X'), Item.netherQuartz, Character.valueOf('I'), Item.redstone
        );
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chemOvenIdle),
			"###", "#X#", "#I#", Character.valueOf('#'), "ingotCastIron", Character.valueOf('X'), Item.diamond, Character.valueOf('I'), steamOvenIdle
        ));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(nukeOvenIdle),
			"#I#", "#X#", "#I#", Character.valueOf('#'), Item.ingotIron, Character.valueOf('X'), "itemReactorCore", Character.valueOf('I'), "oreEtherium"
        ));
		GameRegistry.addRecipe(new ItemStack(material, 1, 10),
			"###", "#X#", "###", Character.valueOf('#'), Block.obsidian, Character.valueOf('X'), chemOvenIdle
        );
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(material, 1, 11),
			"#X#", "# #", " # ", Character.valueOf('#'), Item.ingotIron, Character.valueOf('X'), "oreEtherium"
        ));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(coreDrill),
			"X", "#", "I", Character.valueOf('#'), "ingotPosphate", Character.valueOf('X'), new ItemStack(drillGold, 1, OreDictionary.WILDCARD_VALUE), Character.valueOf('I'), "itemDrillBase"
        ));
		GameRegistry.addRecipe(new ItemStack(roofTile, 4),
			"###", "###", "###", Character.valueOf('#'), Item.flint);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(lamp),
            "#X#", "XIX", "#X#", Character.valueOf('#'), "ingotCastIron", Character.valueOf('X'), Block.glass, Character.valueOf('I'), Item.glowstone
        ));
	/*	GameRegistry.addRecipe(new ItemStack(woodBrass, 4), new Object[] {
            "###", "#I#", "###", Character.valueOf('#'), ingotBrass, Character.valueOf('I'), Block.wood
        });
		GameRegistry.addRecipe(new ItemStack(leavesLamp, 4), new Object[] {
            "#X#", "XIX", "#X#", Character.valueOf('#'), ingotBrass, Character.valueOf('X'), Block.glass, Character.valueOf('I'), Item.lightStoneDust
        });*/
		GameRegistry.addRecipe(new ItemStack(wirelessLamp),
            "#", "X", Character.valueOf('#'), electricLamp, Character.valueOf('X'), teslaReceiver
        );
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(railingCastIron, 2),
			"###", "###", Character.valueOf('#'), "ingotCastIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(gateCastIron),
            "#X#", "#X#", Character.valueOf('#'), "ingotCastIron", Character.valueOf('X'), railingCastIron
        ));
	
		GameRegistry.addRecipe(new ShapelessOreRecipe(
			new ItemStack(material,1,4), Item.ingotIron, "oreCopper"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(
			new ItemStack(material, 2, 9), "oreUranium"));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(pickaxeEtherium),
			"XXX", " # ", " # ", Character.valueOf('#'), Item.stick, Character.valueOf('X'), "oreEtherium"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(shovelEtherium),
			"X", "#", "#", Character.valueOf('#'), Item.stick, Character.valueOf('X'), "oreEtherium"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(axeEtherium),
			"XX", "X#", " #", Character.valueOf('#'), Item.stick, Character.valueOf('X'), "oreEtherium"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(hoeEtherium),
			"XX", " #", " #", Character.valueOf('#'), Item.stick, Character.valueOf('X'), "oreEtherium"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(swordEtherium),
			"X", "X", "#", Character.valueOf('#'), Item.stick, Character.valueOf('X'), "oreEtherium"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(pickaxeObsidian),
			"XXX", " # ", " # ", Character.valueOf('#'), Item.stick, Character.valueOf('X'), "itemObsidianSlate"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(shovelObsidian),
             "X", "#", "#", Character.valueOf('#'), Item.stick, Character.valueOf('X'), "itemObsidianSlate"
        ));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(axeObsidian),
			"XX", "X#", " #", Character.valueOf('#'), Item.stick, Character.valueOf('X'), "itemObsidianSlate"
        ));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(hoeObsidian),
			"XX", " #", " #", Character.valueOf('#'), Item.stick, Character.valueOf('X'), "itemObsidianSlate"
        ));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(swordObsidian),
			"X", "X", "#", Character.valueOf('#'), Item.stick, Character.valueOf('X'), "itemObsidianSlate"
        ));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(pickaxeSteam),
			"XIX", " # ", " # ", Character.valueOf('#'), Item.stick, Character.valueOf('X'), "ingotBrass", Character.valueOf('I'), steamOvenIdle
        ));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(shovelSteam),
			"X", "#", "I", Character.valueOf('#'), Item.stick, Character.valueOf('X'), "ingotBrass", Character.valueOf('I'), steamOvenIdle
        ));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(axeSteam),
			"X ", "XI", "# ", Character.valueOf('#'), Item.stick, Character.valueOf('X'), "ingotBrass", Character.valueOf('I'), steamOvenIdle
        ));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(hoeSteam),
			"XI", " #", " #", Character.valueOf('#'), Item.stick, Character.valueOf('X'), "ingotBrass", Character.valueOf('I'), steamOvenIdle
        ));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(swordSteam),
			"X", "I", "#", Character.valueOf('#'), Item.stick, Character.valueOf('X'), "ingotBrass", Character.valueOf('I'), steamOvenIdle
        ));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(drillSteam),
			"XXX", "XIX", "XX#", Character.valueOf('#'), Item.stick, Character.valueOf('X'), "ingotBrass", Character.valueOf('I'), steamOvenIdle
        ));
		
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(helmetObsidian),
			"XXX", "X X", Character.valueOf('X'), "itemObsidianSlate"
        ));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(plateObsidian),
			"X X", "XXX", "XXX", Character.valueOf('X'), "itemObsidianSlate"
        ));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(legsObsidian),
			"XXX", "X X", "X X", Character.valueOf('X'), "itemObsidianSlate"
        ));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bootsObsidian),
			"X X", "X X", Character.valueOf('X'), "itemObsidianSlate"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(helmetEtherium),
			"XXX", "X X", Character.valueOf('X'), "oreEtherium"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(plateEtherium),
			"X X", "XXX", "XXX", Character.valueOf('X'), "oreEtherium"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(legsEtherium),
			"XXX", "X X", "X X", Character.valueOf('X'), "oreEtherium"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bootsEtherium),
			"X X", "X X", Character.valueOf('X'), "oreEtherium"));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(brassGoggles),
			"X#X", "# #", Character.valueOf('X'), Block.glass, Character.valueOf('#'), "ingotBrass"
        ));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aqualung),
			"XTX", "X X", "X#X", Character.valueOf('X'), "ingotBrass", Character.valueOf('#'), Block.pistonBase, Character.valueOf('T'), Block.glass
        ));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rollerSkates),
			"X X", "X X", "# #", Character.valueOf('X'), "ingotBrass", Character.valueOf('#'), Item.ingotIron
        ));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(legBraces),
			"XXX", "X X", "# #", Character.valueOf('X'), "ingotBrass", Character.valueOf('#'), Block.pistonBase
        ));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(spanner),
			"X X", "###", " # ", Character.valueOf('#'), "ingotBrass", Character.valueOf('X'), Item.diamond
		));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chisel),
			"#", "#", "X", Character.valueOf('#'), Item.ingotIron, Character.valueOf('X'), "ingotBrass"
        ));
		
		GameRegistry.addRecipe(new ItemStack(Block.music),
            "###", "#X#", "###", Character.valueOf('#'), new ItemStack(Block.planks,1,OreDictionary.WILDCARD_VALUE), Character.valueOf('X'), Item.netherQuartz
        );
		GameRegistry.addRecipe(new ItemStack(Block.railPowered, 6),
            "XRX", "X#X", "XRX", Character.valueOf('X'), Item.ingotGold, Character.valueOf('R'), Item.redstone, Character.valueOf('#'), Item.stick
        );
		GameRegistry.addRecipe(new ItemStack(Block.railDetector, 6),
            "XRX", "X#X", "XRX", Character.valueOf('X'), Item.ingotIron, Character.valueOf('R'), Item.redstone, Character.valueOf('#'), Block.pressurePlateStone
        );
		GameRegistry.addRecipe(new ItemStack(Block.lever),
            "X", "#", "I", Character.valueOf('#'), Block.cobblestone, Character.valueOf('X'), Item.stick, Character.valueOf('I'), battery
        );
		GameRegistry.addRecipe(new ItemStack(Block.torchRedstoneActive, 4),
            "X", "#", "I", Character.valueOf('#'), Item.stick, Character.valueOf('X'), Item.redstone, Character.valueOf('I'), battery
        );
		GameRegistry.addRecipe(new ItemStack(Item.redstoneRepeater),
            "#X#", "IRI", Character.valueOf('#'), Block.torchRedstoneActive, Character.valueOf('X'), Item.redstone, Character.valueOf('I'), Block.stone, Character.valueOf('R'), Item.netherQuartz
        );
		GameRegistry.addRecipe(new ItemStack(Item.pocketSundial),
            " # ", "#X#", " # ", Character.valueOf('#'), Item.ingotGold, Character.valueOf('X'), Item.netherQuartz
        );
		GameRegistry.addRecipe(new ItemStack(Item.compass),
            " # ", "#X#", " # ", Character.valueOf('#'), Item.ingotIron, Character.valueOf('X'), Item.netherQuartz
        );
		GameRegistry.addRecipe(new ItemStack(Block.stoneButton),
            "#", "#", "X", Character.valueOf('#'), Block.stone, Character.valueOf('X'), battery
        );
		GameRegistry.addRecipe(new ItemStack(Block.pressurePlateStone),
            "##", "X ", Character.valueOf('#'), Block.stone, Character.valueOf('X'), battery
        );
		GameRegistry.addRecipe(new ItemStack(Block.pressurePlatePlanks),
            "##", "X ", Character.valueOf('#'), new ItemStack(Block.planks,1,OreDictionary.WILDCARD_VALUE), Character.valueOf('X'), battery
        );
		GameRegistry.addRecipe(new ItemStack(Block.dispenser),
            "###", "#X#", "#R#", Character.valueOf('#'), Block.cobblestone, Character.valueOf('X'), Item.bow, Character.valueOf('R'), Item.netherQuartz
        );
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.gunpowder),
            "#X#", Character.valueOf('#'), "oreSulphur", Character.valueOf('X'), Item.coal
        ));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(torchTeslaIdle),
            " X ", "I#I", "ITI", Character.valueOf('#'), Item.ingotGold, Character.valueOf('X'), "itemLightBulb", Character.valueOf('I'), Item.redstone, Character.valueOf('T'), Item.netherQuartz
        ));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.glowstone, 4), 
           "X#X", "#I#", "X#X", Character.valueOf('#'), "ingotPosphate", Character.valueOf('X'), "oreSulphur", Character.valueOf('I'), "oreUranium"
        ));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(teslaReceiver),
           "#X#", "ITI", Character.valueOf('#'), "ingotCastIron", Character.valueOf('X'), Item.ingotGold, Character.valueOf('I'), Item.redstone, Character.valueOf('T'), Item.netherQuartz
        ));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.pistonBase),
            "TTT", "#X#", "#R#", Character.valueOf('#'), Block.cobblestone, Character.valueOf('X'), Item.ingotIron, Character.valueOf('R'), "oreCopper", Character.valueOf('T'), 
            new ItemStack(Block.planks,1,OreDictionary.WILDCARD_VALUE)
        ));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(part, 1, 2),
            "X ", "##", Character.valueOf('#'), Item.ingotIron, Character.valueOf('X'), "ingotBrass"
        ));
		GameRegistry.addRecipe(new ItemStack(part, 1, 5),
            "#  ", " # ", "  #", Character.valueOf('#'), new ItemStack(Block.planks,1,OreDictionary.WILDCARD_VALUE)
        );
		GameRegistry.addRecipe(new ItemStack(part, 1, 3),
            "#  ", " # ", "  #", Character.valueOf('#'), Item.ingotIron);
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(part, 1, 4),
			"itemBarrel", new ItemStack(chisel, 1, OreDictionary.WILDCARD_VALUE)));
		flintlockMusket.setItemDamage(flintlockMusket.getMaxDamage()-1);
		GameRegistry.addRecipe(new ShapedOreRecipe(flintlockMusket,
            "X", "#", "T", Character.valueOf('#'), "itemBarrel", Character.valueOf('X'), new ItemStack(Item.flintAndSteel, 1, OreDictionary.WILDCARD_VALUE), Character.valueOf('T'), "itemWoodenStock" 
        ));
		matchlockMusket.setItemDamage(matchlockMusket.getMaxDamage()-1);
		GameRegistry.addRecipe(new ShapedOreRecipe(matchlockMusket,
            "X", "#", "T", Character.valueOf('#'), "itemBarrel", Character.valueOf('X'), Item.silk, Character.valueOf('T'), "itemWoodenStock" 
        ));
        percussionCapMusket.setItemDamage(percussionCapMusket.getMaxDamage()-1);
		GameRegistry.addRecipe(new ShapedOreRecipe(percussionCapMusket,
            "X", "#", "T", Character.valueOf('#'), "itemBarrel", Character.valueOf('X'), "itemPercussionLock", Character.valueOf('T'), "itemWoodenStock" 
        ));
        flintlockRifle.setItemDamage(flintlockRifle.getMaxDamage()-1);
		GameRegistry.addRecipe(new ShapedOreRecipe(flintlockRifle, 
            "X", "#", "T", Character.valueOf('#'), "itemBarrelRifled", Character.valueOf('X'), new ItemStack(Item.flintAndSteel, 1, OreDictionary.WILDCARD_VALUE), Character.valueOf('T'), "itemWoodenStock" 
        ));
        matchlockRifle.setItemDamage(matchlockRifle.getMaxDamage()-1);
		GameRegistry.addRecipe(new ShapedOreRecipe(matchlockRifle,
            "X", "#", "T", Character.valueOf('#'), "itemBarrelRifled", Character.valueOf('X'), Item.silk, Character.valueOf('T'), "itemWoodenStock"
        ));
        percussionCapRifle.setItemDamage(percussionCapRifle.getMaxDamage()-1);
		GameRegistry.addRecipe(new ShapedOreRecipe(percussionCapRifle,
            "X", "#", "T", Character.valueOf('#'), "itemBarrelRifled", Character.valueOf('X'), "itemPercussionLock", Character.valueOf('T'), "itemWoodenStock"
        ));
		
		GameRegistry.addRecipe(new ItemStack(part, 8),
            "X", "#", "T", Character.valueOf('#'), Item.gunpowder, Character.valueOf('X'), Item.ingotIron, Character.valueOf('T'), Item.paper 
        );
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(part, 8, 1),
            "T", "#", "X", Character.valueOf('#'), Item.gunpowder, Character.valueOf('X'), "ingotBrass", Character.valueOf('T'), Item.paper
        ));
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Item.slimeBall),
			Item.bucketWater, "oreSulphur"));
		
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(emptyKettle, 1, emptyKettle.getMaxDamage()-1),
              "#  ", "###", " ##", Character.valueOf('#'), "ingotCastIron"));
		GameRegistry.addRecipe(new ItemStack(emptyTeacup),
              "# #", " # ", Character.valueOf('#'), Item.clay);
		GameRegistry.addShapelessRecipe(new ItemStack(coldKettle, 1, 1),
             new ItemStack(emptyKettle, 1, OreDictionary.WILDCARD_VALUE), Item.bucketWater, teaLeaves
        );
		
		DrillRecipeItems = (new Object[][] {
			new Object[] {
                new ItemStack(Block.planks,1,OreDictionary.WILDCARD_VALUE), Block.cobblestone, Item.ingotIron, Item.diamond, Item.ingotGold, new ItemStack(material,1,3), material
			}, new Object[] {
                drillWood, drillStone, drillSteel, drillDiamond, drillGold, drillObsidian, drillEtherium
            }
			});
			
		for(int i = 0; i < DrillRecipeItems[0].length; i ++){
			GameRegistry.addRecipe(new ItemStack((Item) DrillRecipeItems[1][i], 1),
                 "XXX", "XXX", "XX#", Character.valueOf('#'), Item.stick, Character.valueOf('X'), DrillRecipeItems[0][i]
        );
		}
		
		StoreBlockRecipeItems = (new Object[][] {
            new Object[] {
                new ItemStack(decorBlock,1,0), new ItemStack(material,9,5)
            }, new Object[] {
        		new ItemStack(decorBlock,1,1), new ItemStack(material,9,0)
            }, new Object[] {
        		new ItemStack(decorBlock,1,2), new ItemStack(material,9,4)
            }, new Object[] {
        		new ItemStack(decorBlock,1,3), new ItemStack(material,9,8),
            }
        });
		
		DecorBlockRecipeItems = (new Object[][] {
             new Object[] {
            		 new ItemStack(decorBlock,1,4), new ItemStack(Block.blockIron)
            },
            new Object[] {
            		 new ItemStack(decorBlock,1,5), new ItemStack(Block.blockGold)
             }, new Object[] {
            		 new ItemStack(decorBlock,1,6), new ItemStack(Block.blockDiamond)
            }, new Object[] {
            		 new ItemStack(decorBlock,1,7), new ItemStack(decorBlock,1,0)
            }, new Object[] {
            		 new ItemStack(decorBlock,1,8), new ItemStack(decorBlock,1,1)
            }, new Object[] {
            		 new ItemStack(decorBlock,1,9), new ItemStack(decorBlock,1,2)
            }, new Object[] {
            		 new ItemStack(decorBlock,1,10), new ItemStack(Block.blockLapis)
            }, new Object[] {
            		 new ItemStack(decorBlock,1,11), new ItemStack(Block.stone)
            }, new Object[] {
            		 new ItemStack(decorBlock,1,12), new ItemStack(decorBlock,1,3)
            }
        });
		
		GameRegistry.addRecipe(new ItemStack(stairRoof,3),
            "#  ", "## ", "###", Character.valueOf('#'), Item.flint);
		addSpannerRecipes();
		addStorageBlockRecipes();
		addDecorBlockRecipes();
		
		if(event.getSide().isClient())
			addAchievements();
	}
  
	private void addDecorBlockRecipes(){
		for(int i = 0; i < DecorBlockRecipeItems.length; i++){
			ItemStack block = (ItemStack)DecorBlockRecipeItems[i][0];
            ItemStack itemstack = (ItemStack)DecorBlockRecipeItems[i][1];
            GameRegistry.addShapelessRecipe(block,
            itemstack, new ItemStack(chisel, 1, OreDictionary.WILDCARD_VALUE));
            GameRegistry.addSmelting(block.itemID, itemstack,1.0F);
		}
	}
  
	private void addSpannerRecipes(){
		for(Item item: Item.itemsList){
			if(item != null && item.isRepairable()){
				ItemStack itemstack = new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE);
				GameRegistry.addShapelessRecipe(new ItemStack(item),
	                    itemstack, new ItemStack(spanner, 1, OreDictionary.WILDCARD_VALUE));
			}
        }
	}

	private void addStorageBlockRecipes(){
        for(int i = 0; i < StoreBlockRecipeItems.length; i++){
        	ItemStack block = (ItemStack)StoreBlockRecipeItems[i][0];
            ItemStack itemstack = (ItemStack)StoreBlockRecipeItems[i][1];
            GameRegistry.addRecipe(block,
                "###", "###", "###", Character.valueOf('#'), itemstack);
            GameRegistry.addRecipe(itemstack,
                "#", Character.valueOf('#'), block);
        }
    }
	private static void addAchievement(String ach, String desc){
        LanguageRegistry.instance().addStringLocalization("achievement." + ach, ach);
        LanguageRegistry.instance().addStringLocalization("achievement." + ach + ".desc", desc);
	}
	
	public static void addAchievements(){
		achs[0] = (new Achievement(AchievementList.achievementList.size(),"Black Magic",3,2, pickaxeObsidian, AchievementList.buildBetterPickaxe)).registerAchievement();
		addAchievement("Black Magic", "Construct an obsidian pickaxe");
		achs[1] = (new Achievement(AchievementList.achievementList.size(),"Carrying You",4,2, material, achs[0])).registerAchievement();
		addAchievement("Carrying You", "Mine some volucite");
		achs[2] = (new Achievement(AchievementList.achievementList.size(),"Spiral Nemesis",2,-1, drillSteel, AchievementList.buildWorkBench)).registerAchievement();
		addAchievement("Spiral Nemesis", "Construct a drill");
		achs[3] = (new Achievement(AchievementList.achievementList.size(),"Heaven Piercing",3,-1, coreDrill, achs[2])).setSpecial().registerAchievement();
		addAchievement("Heaven Piercing", "WHO THE HELL DO YOU THINK I AM?");
		achs[4] = (new Achievement(AchievementList.achievementList.size(),"Fallout",0,1, nukeOvenIdle, AchievementList.acquireIron)).registerAchievement();
		addAchievement("Fallout", "Smelt something with a nuclear reactor");
		achs[5] = (new Achievement(AchievementList.achievementList.size(),"Ruined Everything",0,0, new ItemStack(material,1,8), achs[4])).registerAchievement();
		addAchievement("Ruined Everything", "Melt down a nuclear reactor");
		achs[6] = (new Achievement(AchievementList.achievementList.size(),"It's Alive!",1,2, torchTeslaActive, AchievementList.acquireIron)).registerAchievement();
		addAchievement("It's Alive!", "Construct a tesla coil");
		achs[7] = (new Achievement(AchievementList.achievementList.size(),"Master Craftsman",1,3, decorBlock, AchievementList.acquireIron)).registerAchievement();
		addAchievement("Master Craftsman", "Engrave a block");
		achs[8] = (new Achievement(AchievementList.achievementList.size(),"Jethro Tull",0,3, aqualung, AchievementList.acquireIron)).registerAchievement();
		addAchievement("Jethro Tull", "Construct an aqualung");
		achs[9] = (new Achievement(AchievementList.achievementList.size(),"Lock, Stock and Barrel",-1,3, firearm, AchievementList.acquireIron)).registerAchievement();
		addAchievement("Lock, Stock and Barrel", "Construct a musket or rifle");
		achs[10] = (new Achievement(AchievementList.achievementList.size(),"Time For A Cuppa!",-1,2, fullTeacup, AchievementList.acquireIron)).registerAchievement();
		addAchievement("Time For A Cuppa!", "Pour yourself a cup of tea");
		AchievementPage.registerAchievementPage(new AchievementPage("Steamcraft",achs));
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if(world.provider.isHellWorld)
			generateNether(world,random,chunkX*16,chunkZ*16);
		else
			generateSurface(world,random,chunkX*16,chunkZ*16);
	}
	
	public void generateSurface(World world, Random rand, int k, int l) {
		int x,y,z;
		for(int i = 0; i < 12; i++)
        {
            x = k + rand.nextInt(16);
            y = rand.nextInt(64);
            z = l + rand.nextInt(16);
            (new WorldGenMinable(brimstone.blockID, 8)).generate(world, rand, x, y, z);
        }
		/*for(int i = 0; i < 6; i++)
        {
            x = k + rand.nextInt(16);
            y = rand.nextInt(64);
            z = l + rand.nextInt(16);
            (new WorldGenMinable(Block.oreIron.blockID, 16)).generate(world, rand, x, y, z);
        }*/
		for(int i = 0; i < 20; i++)
        {
            x = k + rand.nextInt(16);
            y = rand.nextInt(48);
            z = l + rand.nextInt(16);
            (new WorldGenMinable(borniteOre.blockID, 8)).generate(world, rand, x, y, z);
        }
		
		 for(int i = 0; i < 3; i++)
        {
            x = k + rand.nextInt(16);
            y = rand.nextInt(36);
            z = l + rand.nextInt(16);
            (new WorldGenMinable(orePhosphate.blockID, 6)).generate(world, rand, x, y, z);
        }
		for(int i = 0; i < 2; i++)
        {
            x = k + rand.nextInt(16);
            y = rand.nextInt(24);
            z = l + rand.nextInt(16);
            (new WorldGenMinable(oreUranite.blockID, 4)).generate(world, rand, x, y, z);
        }
		/*for(int i = 0; i < 4; i++)
        {
            x = k + rand.nextInt(16);
            y = rand.nextInt(48);
            z = l + rand.nextInt(16);
            (new WorldGenMinable(Block.oreRedstone.blockID, 4)).generate(world, rand, x, y, z);
        }*/
		for(int i = 0; i < 1; i++)
        {
            x = k + rand.nextInt(16);
            y = rand.nextInt(16)+12;
            z = l + rand.nextInt(16);
            (new WorldGenMinable(oreVolucite.blockID, 3)).generate(world, rand, x, y, z);
        }
		for(int i = 0; i < 8; i++)
        {
            x = k + rand.nextInt(16) + 8;
            y = rand.nextInt(128);
            z = l + rand.nextInt(16) + 8;
            (new WorldGenHighwaymanHideout()).generate(world, rand, x, y, z);
        }
	}
	
	public void generateNether(World world, Random rand, int k, int l) {
		for(int i = 0; i < 20; i++)
        {
            int x = k + rand.nextInt(16);
            int y = rand.nextInt(128);
            int z = l + rand.nextInt(16);
            (new WorldGenSCNetherTrees()).generate(world, rand, x, y, z);
        }
	}

	@Override
	public void notifyPickup(EntityItem item, EntityPlayer player) {
		if(item.getEntityItem().isItemEqual(new ItemStack(material))){
            player.triggerAchievement(achs[1]);
         }
	}

	@Override
	public void onCrafting(EntityPlayer player, ItemStack item, IInventory craftMatrix) {
		if(item.itemID == pickaxeObsidian.itemID){
			player.triggerAchievement(achs[0]);
			return;
		}
		if(item.itemID == drillWood.itemID || item.itemID == drillStone.itemID || item.itemID == drillSteel.itemID || item.itemID == drillDiamond.itemID || item.itemID == drillGold.itemID || item.itemID == drillObsidian.itemID || item.itemID == drillEtherium.itemID){
	        player.triggerAchievement(achs[2]);
	        return;
		}
	 	if(item.itemID == coreDrill.itemID){
	        player.triggerAchievement(achs[3]);
	        return;
	 	}
	 	if(item.itemID == torchTeslaIdle.blockID){
	        player.triggerAchievement(achs[6]);
	        return;
	 	}
	 	if(item.itemID == decorBlock.blockID){
			player.triggerAchievement(achs[7]);
			return;
	 	}
	 	if(item.itemID == aqualung.itemID){
	        player.triggerAchievement(achs[8]);
	        return;
     	}
	 	if(item.itemID == firearm.itemID){
	        player.triggerAchievement(achs[9]);
	        return;
	 	}
	 	int repairDmg = -1;
		ItemStack spaner = null;
        for(int i = 0; i < craftMatrix.getSizeInventory(); i++)
        {
        	ItemStack itemstack1 = craftMatrix.getStackInSlot(i);
            if(itemstack1 != null)
            {
            	if(itemstack1.getItem().itemID != spanner.itemID && itemstack1.isItemStackDamageable()){
        			repairDmg = itemstack1.getItemDamage();
    			}
            	if(itemstack1.getItem().itemID == chisel.itemID && itemstack1.getItemDamage()+1 < itemstack1.getMaxDamage()){
            		if(item.isItemEqual(new ItemStack(part,1,4))){
            			player.inventory.addItemStackToInventory(new ItemStack(itemstack1.getItem(), 1, itemstack1.getItemDamage() +16));
            		}else{
            			player.inventory.addItemStackToInventory(new ItemStack(itemstack1.getItem(), 1, itemstack1.getItemDamage() +1));
            		}
            	}
            	if(itemstack1.getItem().itemID == spanner.itemID){
            		if(spaner!=null){
            			return;
            		}
            		spaner = itemstack1.copy();
            	}
            }
        }
		if(spaner!=null && repairDmg >= 0 && item.getItem().isRepairable()){
			ItemStack stack;
			if(repairDmg == 0){
				stack = spaner;
				if(!player.inventory.addItemStackToInventory(stack))
					player.dropPlayerItem(stack);
			}
			else if(spaner.getItemDamage()+1 < spaner.getMaxDamage()){
				stack = new ItemStack(spanner, 1, spaner.getItemDamage()+1);
				if(!player.inventory.addItemStackToInventory(stack))
					player.dropPlayerItem(stack);
			}
		}
	}

	@Override
	public void onSmelting(EntityPlayer player, ItemStack item) {
	}
	@Override
	public int getBurnTime(ItemStack fuel) {
		if(TileEntityChemFurnace.fuels.containsKey(fuel.itemID)){
			return TileEntityChemFurnace.fuels.get(fuel.itemID);
		}
		else if(fuel.itemID==material.itemID){
			switch(fuel.getItemDamage()){
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
}
