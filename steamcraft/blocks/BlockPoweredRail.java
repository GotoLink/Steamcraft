package steamcraft.blocks;

import net.minecraft.block.BlockRailPowered;
import net.minecraft.util.IIcon;

public class BlockPoweredRail extends BlockRailPowered {
	private final boolean isPowered;

	public BlockPoweredRail(boolean flag) {
		super();
		isPowered = flag;
	}

	@Override
	public IIcon getIcon(int i, int j) {
		if (isPowered) {
			return super.getIcon(i, 8);
		} else
			return super.getIcon(i, 0);
	}
}
