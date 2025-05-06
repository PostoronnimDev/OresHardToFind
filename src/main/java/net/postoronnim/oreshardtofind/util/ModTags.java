package net.postoronnim.oreshardtofind.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.postoronnim.oreshardtofind.OresHardToFind;

public class ModTags {
    public static class Blocks {

        public static final TagKey<Block> NEEDS_COPPER_TOOL = createTag("needs_copper_tool");
        public static final TagKey<Block> INCORRECT_FOR_COPPER_TOOL = createTag("incorrect_for_copper_tool");
        public static final TagKey<Block> IRON_MAGNETOMETER_FINDABLE = createTag("iron_magnetometer_findable");
        public static final TagKey<Block> MAGNETITE_ORES = createTag("magnetite_ores");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(OresHardToFind.MOD_ID, name));
        }
    }

    public static class Items {


        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(OresHardToFind.MOD_ID, name));
        }
    }
}
