package steamcraft.blocks;

import java.util.Random;

import net.minecraft.world.World;
import steamcraft.Steamcraft;

public class BlockWirelessLamp extends BlockElectricLamp {
	public BlockWirelessLamp(int i, Class class1, boolean flag) {
		super(i, class1, flag);
	}

	@Override
	public int tickRate(World world) {
		return 2;
	}

	@Override
	protected int getIdleBlock() {
		return Steamcraft.wirelessLampIdle.blockID;
	}

	@Override
	public int idDropped(int i, Random random, int j) {
		return Steamcraft.wirelessLamp.itemID;
	}

	@Override
	protected int getActiveBlock() {
		return Steamcraft.wirelessLampActive.blockID;
	}

	@Override
	protected boolean isIndirectlyPowered(World par1World, int i, int j, int k) {
		for (int l = 0; l < 9; l++) {
			if (super.isIndirectlyPowered(par1World, i + l, j, k))
				return true;
			if (super.isIndirectlyPowered(par1World, i - l, j, k))
				return true;
			if (super.isIndirectlyPowered(par1World, i, j - l, k))
				return true;
			if (super.isIndirectlyPowered(par1World, i, j, k + l))
				return true;
			if (super.isIndirectlyPowered(par1World, i, j, k - l))
				return true;
		}
		return false;
	}
}
