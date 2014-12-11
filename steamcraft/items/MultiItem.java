package steamcraft.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import steamcraft.Steamcraft;

import java.util.List;

public class MultiItem extends Item {
	public final String[] names;
	private IIcon[] icons;

	public MultiItem(String[] names) {
		super();
		setHasSubtypes(true);
		this.names = names;
		setCreativeTab(Steamcraft.steamTab);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName() + names[stack.getItemDamage()];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1) {
		return icons[par1];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		icons = new IIcon[names.length];
		for (int index = 0; index < names.length; index++) {
			icons[index] = par1IconRegister.registerIcon(getIconString() + names[index]);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (int index = 0; index < names.length; index++) {
			par3List.add(new ItemStack(par1, 1, index));
		}
	}
}
