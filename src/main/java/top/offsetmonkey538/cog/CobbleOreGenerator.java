package top.offsetmonkey538.cog;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.offsetmonkey538.cog.config.ModConfig;
import top.offsetmonkey538.monkeyconfig538.ConfigManager;

public class CobbleOreGenerator implements ModInitializer {
    public static final String MOD_ID = "cog";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ConfigManager.init(new ModConfig(), MOD_ID);
    }

    public static ModConfig config() {
        return (ModConfig) ConfigManager.get(MOD_ID);
    }
}