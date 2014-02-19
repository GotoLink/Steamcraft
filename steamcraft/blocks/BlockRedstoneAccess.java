package steamcraft.blocks;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.world.World;

public class BlockRedstoneAccess extends BlockRedstoneTorch{

    Field toggleTime;
	protected BlockRedstoneAccess(boolean par2) {
		super(par2);
	}

	protected boolean hasIndirectPower(World world, int i, int j, int k){
        return func_150110_m(world, i, j, k);
	}
	
	protected boolean checkBurnout(World world, int i, int j, int k, boolean is){
        return func_150111_a(world, i, j, k, is);
	}

	public static Map<World, List> getRedstoneUpdateList() {
		return field_150112_b;
	}

    public void updateToggleTimes(World world){
        List<?> list = (List<?>) getRedstoneUpdateList().get(world);
        if (list != null && !list.isEmpty()) {
            try {
                if(toggleTime==null){
                    toggleTime = list.get(0).getClass().getDeclaredFields()[3];
                }
                toggleTime.setAccessible(true);
                while (!list.isEmpty() && world.getTotalWorldTime() - Long.class.cast(toggleTime.get(list.get(0))) > 60L) {
                    list.remove(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
