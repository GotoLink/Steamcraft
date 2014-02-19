package steamcraft.blocks;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.world.World;

public class BlockRedstoneAccess extends BlockRedstoneTorch{

    Method isPower, burnout;
    Field toggleTime;
    public static Field redstoneMap;
	protected BlockRedstoneAccess(boolean par2) {
		super(par2);
	}

	protected boolean hasIndirectPower(World world, int i, int j, int k){
        if(isPower==null){
            for(Method m:BlockRedstoneTorch.class.getDeclaredMethods()){
                if(m.getParameterTypes().length==4 && Modifier.isPrivate(m.getModifiers())){//func_150110_m
                    isPower = m;
                    break;
                }
            }
        }
		isPower.setAccessible(true);
		try {
			return Boolean.class.cast(isPower.invoke(this, world, i, j, k));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	protected boolean checkBurnout(World world, int i, int j, int k, boolean is){
		if(burnout == null){
            for(Method m:BlockRedstoneTorch.class.getDeclaredMethods()){
                if(m.getParameterTypes().length==5 && Modifier.isPrivate(m.getModifiers())){//func_150111_a
                    burnout = m;
                    break;
                }
            }
        }
		burnout.setAccessible(true);
		try {
			return Boolean.class.cast(burnout.invoke(this, world, i, j, k, is));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static Map<World, List> getRedstoneUpdateList() {
		Object obj = null;
		try {
            if(redstoneMap==null){
                redstoneMap = BlockRedstoneTorch.class.getDeclaredFields()[1];//redstoneUpdateInfoCache
            }
            redstoneMap.setAccessible(true);
			obj = redstoneMap.get(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Map.class.cast(obj);
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
