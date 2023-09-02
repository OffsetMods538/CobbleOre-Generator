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
import top.offsetmonkey538.monkeyconfig538.ConfigManager;

public class CobbleOreGenerator implements ModInitializer {
    public static final String MOD_ID = "cog";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static final Random random = new Random();

    @Override
    public void onInitialize() {
        ConfigManager.init(new ModConfig(), MOD_ID);
    }

    public static Block getRandomBlockFromConfig() {
        if (config().ores.isEmpty()) return Blocks.COBBLESTONE;
        int probabilitySum = config().ores.values().stream().mapToInt(Integer::intValue).sum();
        int randomNum = random.nextInt(probabilitySum);

        int partialSum = 0;
        for (Map.Entry<ModConfig.BlockEntry, Integer> entry : config().ores.entrySet()) {
            partialSum += entry.getValue();
            if (partialSum < randomNum) continue;

            List<Block> blocks = entry.getKey().getBlocks();

            if (blocks.isEmpty()) return Blocks.COBBLESTONE;

            // Does this need to be 'blocks.size() - 1'?
            return blocks.get(random.nextInt(blocks.size()));
        }

        return Blocks.COBBLESTONE;
    }

    public static ModConfig config() {
        return (ModConfig) ConfigManager.get(MOD_ID);
    }
}