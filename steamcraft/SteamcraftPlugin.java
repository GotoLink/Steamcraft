package steamcraft;

import cpw.mods.fml.common.asm.transformers.AccessTransformer;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.io.IOException;
import java.util.Map;

@IFMLLoadingPlugin.TransformerExclusions(value={"steamcraft"})
public class SteamcraftPlugin extends AccessTransformer implements IFMLLoadingPlugin {
    public SteamcraftPlugin() throws IOException {
        super("steamcraft_at.cfg");
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return "steamcraft.SteamcraftPlugin";
    }
}
