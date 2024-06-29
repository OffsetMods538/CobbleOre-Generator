package top.offsetmonkey538.cog;

import java.util.List;
import java.util.Map;
import java.util.Random;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.offsetmonkey538.cog.config.ModConfig;
import top.offsetmonkey538.monkeylib538.config.ConfigManager;

public class CobbleOreGenerator implements ModInitializer {
    public static final String MOD_ID = "cog";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static final Random random = new Random();

    private static ModConfig config;

    @Override
    public void onInitialize() {

    }

    public static ModConfig getConfig() {
        if (config != null) return config;

        config = ConfigManager.init(new ModConfig(), LOGGER::error);
        if (config.generatableBlocks.isEmpty()) {
            config.generatableBlocks = ModConfig.DEFAULT;
        }
        ConfigManager.save(config, LOGGER::error);

        return config;
    }

    public static Block getRandomBlockFromConfig() {
        if (getConfig().generatableBlocks.isEmpty()) return Blocks.COBBLESTONE;
        int probabilitySum = getConfig().generatableBlocks.values().stream().mapToInt(Integer::intValue).sum();
        int randomNum = random.nextInt(probabilitySum);

        int partialSum = 0;
        for (Map.Entry<ModConfig.BlockEntry, Integer> entry : getConfig().generatableBlocks.entrySet()) {
            partialSum += entry.getValue();
            if (partialSum < randomNum) continue;

            List<Block> blocks = entry.getKey().getBlocks();

            if (blocks.isEmpty()) return Blocks.COBBLESTONE;

            // Does this need to be 'blocks.size() - 1'?
            return blocks.get(random.nextInt(blocks.size()));
        }

        return Blocks.COBBLESTONE;
    }
}