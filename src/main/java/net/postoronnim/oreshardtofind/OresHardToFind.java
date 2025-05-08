package net.postoronnim.oreshardtofind;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.gen.feature.GeodeFeature;
import net.postoronnim.oreshardtofind.block.ModBlocks;
import net.postoronnim.oreshardtofind.block.entity.ModBlockEntities;
import net.postoronnim.oreshardtofind.effect.ModEffects;
import net.postoronnim.oreshardtofind.entity.ModEntities;
import net.postoronnim.oreshardtofind.entity.custom.IronOreGolemEntity;
import net.postoronnim.oreshardtofind.feature.ModFeatures;
import net.postoronnim.oreshardtofind.item.ModArmorMaterials;
import net.postoronnim.oreshardtofind.item.ModItemGroups;
import net.postoronnim.oreshardtofind.item.ModItems;
import net.postoronnim.oreshardtofind.loot_table.ModLootTables;
import net.postoronnim.oreshardtofind.util.ModModelPredicates;
import net.postoronnim.oreshardtofind.world.gen.ModWorldGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OresHardToFind implements ModInitializer {
	public static final String MOD_ID = "oreshardtofind";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ModModelPredicates.registerModelPredicates();

		ModBlockEntities.registerModBlockEntities();

		ModBlocks.registerModBlocks();

		ModLootTables.registerModLootTables();

		ModItemGroups.registerItemGroups();

		ModFeatures.register();

		ModEntities.registerModEntities();

		ModWorldGeneration.generateWorldGen();

		ModArmorMaterials.registerArmorMaterials();

		ModItems.registerModItems();

		ModEffects.registerEffects();

		FabricDefaultAttributeRegistry.register(ModEntities.IRON_ORE_GOLEM, IronOreGolemEntity.createAttributes());

		TradeOfferHelper.registerVillagerOffers(VillagerProfession.CLERIC, 1, factories -> {
			factories.add((entity, random) -> new TradeOffer(
					new TradedItem(Items.EMERALD, 14),
					new ItemStack(ModItems.STORMBRINGER_BOTTLE, 1), 1, 7, 0.04f
			));
		});

		LOGGER.info("Hello Fabric world!");
	}
}