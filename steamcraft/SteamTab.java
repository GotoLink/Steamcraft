package steamcraft;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class SteamTab extends CreativeTabs {

	public SteamTab() {
		super("Steamcraft");
		LanguageRegistry.instance().addStringLocalization("itemGroup.Steamcraft", "Steamcraft");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public Item getTabIconItem()
    {
        return Steamcraft.coreDrill;
    }
}
