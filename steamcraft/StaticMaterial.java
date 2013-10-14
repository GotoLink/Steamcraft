package steamcraft;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLogic;

public class StaticMaterial extends MaterialLogic {
	public StaticMaterial(MapColor par1MapColor) {
		super(par1MapColor);
		setImmovableMobility();
	}
}
