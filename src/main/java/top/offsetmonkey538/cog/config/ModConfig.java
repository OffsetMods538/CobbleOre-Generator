package top.offsetmonkey538.cog.config;

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
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import top.offsetmonkey538.monkeyconfig538.Config;
import top.offsetmonkey538.monkeyconfig538.serializer.ConfigSerializer;

public class ModConfig extends Config {

    public Map<BlockEntry, Integer> generatableBlocks = new HashMap<>();


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
            if (tag != null) return Registries.BLOCK.getOrCreateEntryList(tag).stream()
                    .map(RegistryEntry::getKey)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(Registries.BLOCK::get)
                    .collect(Collectors.toList());
            if (block != null) return List.of(block);
            return List.of(Blocks.AIR);
        }

        @Override
        public String toString() {
            if (tag != null) return "#" + tag.id();
            if (block != null) return Registries.BLOCK.getId(block).toString();
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

                if (value.startsWith("#")) return new BlockEntry(TagKey.of(RegistryKeys.BLOCK, new Identifier(value.substring(1))));
                if (Registries.BLOCK.containsId(new Identifier(value))) return new BlockEntry(Registries.BLOCK.get(new Identifier(value)));
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
