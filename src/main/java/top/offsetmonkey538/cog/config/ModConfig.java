package top.offsetmonkey538.cog.config;

import blue.endless.jankson.Comment;
import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.api.Marshaller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import top.offsetmonkey538.monkeyconfig538.Config;
import top.offsetmonkey538.monkeyconfig538.serializer.ConfigSerializer;

public class ModConfig extends Config {

    @Comment("\nA block (or block tag) and its probability of being generated.\nThe higher the probability, the more likely that that block (tag) is going to be generated.\nYou can reset to default values by deleting the config file.")
    public Map<BlockEntry, Integer> generatableBlocks = new HashMap<>();

    public static final Map<BlockEntry, Integer> DEFAULT = Map.of(
            new BlockEntry(TagKey.of(Registry.BLOCK_KEY, new Identifier("c:ores"))), 25,
            new BlockEntry(Blocks.COBBLESTONE), 75
    );


    public static class BlockEntry {
        public TagKey<Block> tag = null;
        public Block block = null;

        public BlockEntry(TagKey<Block> tag) {
            this.tag = tag;
        }
        public BlockEntry(Block block) {
            this.block = block;
        }

        public List<Block> getBlocks() {
            if (tag != null) return Registry.BLOCK.getOrCreateEntryList(tag).stream()
                    .map(RegistryEntry::getKey)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(Registry.BLOCK::get)
                    .collect(Collectors.toList());
            if (block != null) return List.of(block);
            return List.of(Blocks.AIR);
        }

        /*
            The BlockEntry class is being used as the key for a map,
            and when Jankson serializes a map to json, it uses `key.toString()`
            so this is needed instead of `Serializer.toJson`.
         */
        @Override
        public String toString() {
            if (tag != null) return "#" + tag.id();
            if (block != null) return Registry.BLOCK.getId(block).toString();
            return "";
        }

        public static class Serializer implements ConfigSerializer<BlockEntry> {

            @Override
            public JsonElement toJson(BlockEntry value, Marshaller marshaller) {
                return marshaller.serialize(value.toString());
            }

            @Override
            public BlockEntry fromJson(JsonElement json, Marshaller marshaller) {
                final String value = marshaller.marshall(String.class, json);

                if (value.startsWith("#")) return new BlockEntry(TagKey.of(Registry.BLOCK_KEY, new Identifier(value.substring(1))));
                if (Registry.BLOCK.containsId(new Identifier(value))) return new BlockEntry(Registry.BLOCK.get(new Identifier(value)));
                return null;
            }
        }
    }

    @Override
    protected Jankson.Builder configureJankson(Jankson.Builder builder) {
        this.registerSerializer(builder, BlockEntry.class, new BlockEntry.Serializer());

        return super.configureJankson(builder);
    }
}
