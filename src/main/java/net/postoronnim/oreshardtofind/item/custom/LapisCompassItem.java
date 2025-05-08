package net.postoronnim.oreshardtofind.item.custom;

import net.minecraft.block.Blocks;
import net.minecraft.client.item.CompassAnglePredicateProvider;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LodestoneTrackerComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeKeys;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class LapisCompassItem extends Item {

    private static final int MIN_DEEPDARK_Y = -60; // Deep Dark typically generates below this
    private static final int MAX_DEEPDARK_Y = -40; // and above this
    private static final int INITIAL_SEARCH_STEP = 64; // Large steps since biomes are big
    private static final int MAX_SEARCH_RADIUS = 2048; // Max search distance
    public static final int COOLDOWN_TICKS = 60;


    public LapisCompassItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.contains(DataComponentTypes.LODESTONE_TRACKER) || super.hasGlint(stack);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (world.isClient) {
            return TypedActionResult.success(stack);
        }

        if(user.getItemCooldownManager().isCoolingDown(this)) {
            return TypedActionResult.fail(stack);
        } else {
            user.getItemCooldownManager().set(this, COOLDOWN_TICKS);
        }

        BlockPos nearestDeepDark = findNearestDeepDarkBiome((ServerWorld) world, user.getBlockPos());
        if (nearestDeepDark == null) {
          world.playSound(null, user.getBlockPos(), SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT,
                        SoundCategory.PLAYERS, 0.7f, 1f);
            return TypedActionResult.fail(stack);
        }

        world.playSound(null, user.getBlockPos(), SoundEvents.ITEM_LODESTONE_COMPASS_LOCK,
                SoundCategory.PLAYERS, 1.0F, 1.0F);

        // Modify the existing stack instead of creating a new one
        LodestoneTrackerComponent tracker = new LodestoneTrackerComponent(
                Optional.of(GlobalPos.create(world.getRegistryKey(), nearestDeepDark)),
                true);

        stack.set(DataComponentTypes.LODESTONE_TRACKER, tracker);

        return TypedActionResult.success(stack);
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return stack.contains(DataComponentTypes.LODESTONE_TRACKER) ? "item.minecraft.lodestone_compass" : super.getTranslationKey(stack);
    }

    @Nullable
    private BlockPos findNearestDeepDarkBiome(ServerWorld world, BlockPos center) {
        int searchY = (MIN_DEEPDARK_Y + MAX_DEEPDARK_Y) / 2;
        return spiralSearch(world, center, searchY, INITIAL_SEARCH_STEP, MAX_SEARCH_RADIUS);
    }

    @Nullable
    private BlockPos spiralSearch(ServerWorld world, BlockPos center, int y, int step, int maxRadius) {
        if (world.getBiome(center.withY(y)).matchesKey(BiomeKeys.DEEP_DARK)) {
            return center.withY(y);
        }

        final double  pidiv2 = Math.PI / 2d;
        final double  pimul2 = Math.PI * 2;

        for (int radius = step; radius <= maxRadius; radius += step) {
            double angleStep = pidiv2 / ((double) radius / step);
            for (double angle = 0; angle < 2*pimul2; angle += angleStep) {
                int x = center.getX() + (int)(radius * Math.cos(angle));
                int z = center.getZ() + (int)(radius * Math.sin(angle));

                BlockPos pos = new BlockPos(x, y, z);

                if (world.getBiome(pos).matchesKey(BiomeKeys.DEEP_DARK)) {
                    return pos;
                }
            }
        }
        return null;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        tooltip.add(Text.translatable("tooltip.oreshardtofind.lapis_compass.tooltip"));
    }
}

