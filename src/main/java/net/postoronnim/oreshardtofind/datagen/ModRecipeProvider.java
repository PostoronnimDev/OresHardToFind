package net.postoronnim.oreshardtofind.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.postoronnim.oreshardtofind.block.ModBlocks;
import net.postoronnim.oreshardtofind.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        offerBasicHelmetRecipe(ModItems.COPPER_HELMET, Items.COPPER_INGOT, exporter);
        offerBasicChestplateRecipe(ModItems.COPPER_CHESTPLATE, Items.COPPER_INGOT, exporter);
        offerBasicLeggingsRecipe(ModItems.COPPER_LEGGINGS, Items.COPPER_INGOT, exporter);
        offerBasicBootsRecipe(ModItems.COPPER_BOOTS, Items.COPPER_INGOT, exporter);

        offerBasicSwordRecipe(ModItems.COPPER_SWORD, Items.COPPER_INGOT, Items.STICK, exporter);
        offerBasicAxeRecipe(ModItems.COPPER_AXE, Items.COPPER_INGOT, Items.STICK, exporter);
        offerBasicPickaxeRecipe(ModItems.COPPER_PICKAXE, Items.COPPER_INGOT, Items.STICK, exporter);
        offerBasicShovelRecipe(ModItems.COPPER_SHOVEL, Items.COPPER_INGOT, Items.STICK, exporter);
        offerBasicHoeRecipe(ModItems.COPPER_HOE, Items.COPPER_INGOT, Items.STICK, exporter);

        offerReversibleCompactingRecipes(exporter, RecipeCategory.DECORATIONS, ModItems.MAGNETITE_SHARD, RecipeCategory.BUILDING_BLOCKS, ModBlocks.MAGNETITE_BLOCK);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.COPPER_MAGNETOMETER)
                .pattern("GCG")
                .pattern("GMG")
                .pattern("GCG")
                .input('G', Items.GOLD_INGOT)
                .input('C', Blocks.COPPER_BLOCK)
                .input('M', ModBlocks.MAGNETITE_BLOCK)
                .criterion(hasItem(ModItems.MAGNETITE_SHARD), conditionsFromItem(ModItems.MAGNETITE_SHARD))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.IRON_MAGNETOMETER)
                .pattern("GCG")
                .pattern("GMG")
                .pattern("GCG")
                .input('G', Items.GOLD_INGOT)
                .input('C', Blocks.IRON_BLOCK)
                .input('M', ModBlocks.CHARGED_MAGNET)
                .criterion(hasItem(ModBlocks.CHARGED_MAGNET.asItem()), conditionsFromItem(ModBlocks.CHARGED_MAGNET.asItem()))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.NETHERITE_MAGNETOMETER)
                .pattern("GCG")
                .pattern("GMG")
                .pattern("GCG")
                .input('G', Items.GOLD_INGOT)
                .input('C', Items.NETHERITE_INGOT)
                .input('M', Blocks.LODESTONE)
                .criterion(hasItem(Blocks.LODESTONE.asItem()), conditionsFromItem(Blocks.LODESTONE.asItem()))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.LAPIS_COMPASS)
                .pattern("GLG")
                .pattern("LCL")
                .pattern("GLG")
                .input('G', Items.GOLD_INGOT)
                .input('L', Blocks.LAPIS_BLOCK)
                .input('C', Items.COMPASS)
                .criterion(hasItem(Items.LAPIS_LAZULI), conditionsFromItem(Items.LAPIS_LAZULI))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.UNCHARGED_MAGNET)
                .pattern("MMM")
                .pattern("MIM")
                .pattern("MMM")
                .input('M', ModItems.MAGNETITE_SHARD)
                .input('I', Blocks.IRON_BLOCK)
                .criterion(hasItem(Blocks.IRON_BLOCK.asItem()), conditionsFromItem(Blocks.IRON_BLOCK.asItem()))
                .offerTo(exporter);
    }

    private void offerBasicHelmetRecipe(Item result, Item material, RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, result)
                .pattern("###")
                .pattern("# #")
                .pattern("   ")
                .input('#', material)
                .criterion(hasItem(material), conditionsFromItem(material))
                .offerTo(exporter);
    }
    private void offerBasicChestplateRecipe(Item result, Item material, RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, result)
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .input('#', material)
                .criterion(hasItem(material), conditionsFromItem(material))
                .offerTo(exporter);
    }
    private void offerBasicLeggingsRecipe(Item result, Item material, RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, result)
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .input('#', material)
                .criterion(hasItem(material), conditionsFromItem(material))
                .offerTo(exporter);
    }
    private void offerBasicBootsRecipe(Item result, Item material, RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, result)
                .pattern("   ")
                .pattern("# #")
                .pattern("# #")
                .input('#', material)
                .criterion(hasItem(material), conditionsFromItem(material))
                .offerTo(exporter);
    }

    private void offerBasicSwordRecipe(Item result, Item material, Item stick, RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, result)
                .pattern(" # ")
                .pattern(" # ")
                .pattern(" / ")
                .input('#', material)
                .input('/', stick)
                .criterion(hasItem(material), conditionsFromItem(material))
                .offerTo(exporter);
    }

    private void offerBasicAxeRecipe(Item result, Item material, Item stick, RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, result)
                .pattern(" ##")
                .pattern(" /#")
                .pattern(" / ")
                .input('#', material)
                .input('/', stick)
                .criterion(hasItem(material), conditionsFromItem(material))
                .offerTo(exporter);
    }
    private void offerBasicPickaxeRecipe(Item result, Item material, Item stick, RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, result)
                .pattern("###")
                .pattern(" / ")
                .pattern(" / ")
                .input('#', material)
                .input('/', stick)
                .criterion(hasItem(material), conditionsFromItem(material))
                .offerTo(exporter);
    }
    private void offerBasicShovelRecipe(Item result, Item material, Item stick, RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, result)
                .pattern(" # ")
                .pattern(" / ")
                .pattern(" / ")
                .input('#', material)
                .input('/', stick)
                .criterion(hasItem(material), conditionsFromItem(material))
                .offerTo(exporter);
    }
    private void offerBasicHoeRecipe(Item result, Item material, Item stick, RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, result)
                .pattern(" ##")
                .pattern(" / ")
                .pattern(" / ")
                .input('#', material)
                .input('/', stick)
                .criterion(hasItem(material), conditionsFromItem(material))
                .offerTo(exporter);
    }
}
