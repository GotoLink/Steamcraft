package steamcraft.blocks;

import net.minecraft.block.BlockRailPowered;
import net.minecraft.util.Icon;

public class BlockPoweredRail extends BlockRailPowered {
	private final boolean isPowered;

	public BlockPoweredRail(int i, boolean flag) {
		super(i);
		isPowered = flag;
	}

	@Override
	public Icon getIcon(int i, int j) {
		if (isPowered) {
			return super.getIcon(i, 8);
		} else
			return super.getIcon(i, 0);
	}
}
