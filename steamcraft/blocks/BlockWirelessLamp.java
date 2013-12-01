package steamcraft.blocks;

import java.util.Random;

import net.minecraft.world.World;
import steamcraft.BlockHandler;
import steamcraft.HandlerRegistry;

public class BlockWirelessLamp extends BlockElectricLamp {
	public BlockWirelessLamp(int i, Class<?> class1, boolean flag) {
		super(i, class1, flag);
	}

	@Override
	public int idDropped(int i, Random random, int j) {
		return HandlerRegistry.getItem("steamcraft:wirelessLamp").getID();
	}

	@Override
	public int tickRate(World world) {
		return 2;
	}

	@Override
	protected BlockHandler getActive() {
		return HandlerRegistry.getBlock("steamcraft:wirelessLampOn");
	}

	@Override
	protected BlockHandler getIdle() {
		return HandlerRegistry.getBlock("steamcraft:wirelessLamp");
	}

	@Override
	protected boolean hasIndirectPower(World par1World, int i, int j, int k) {
		for (int l = 0; l < 9; l++) {
			if (super.hasIndirectPower(par1World, i + l, j, k))
				return true;
			if (super.hasIndirectPower(par1World, i - l, j, k))
				return true;
			if (super.hasIndirectPower(par1World, i, j - l, k))
				return true;
			if (super.hasIndirectPower(par1World, i, j, k + l))
				return true;
			if (super.hasIndirectPower(par1World, i, j, k - l))
				return true;
		}
		return false;
	}
}
