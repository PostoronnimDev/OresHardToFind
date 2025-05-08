package net.postoronnim.oreshardtofind.util;

import net.minecraft.client.item.CompassAnglePredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LodestoneTrackerComponent;
import net.minecraft.item.CompassItem;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.GlobalPos;
import net.postoronnim.oreshardtofind.OresHardToFind;
import net.postoronnim.oreshardtofind.item.ModItems;

public class ModModelPredicates {

    public static void registerModelPredicates() {

        ModelPredicateProviderRegistry.register(ModItems.LAPIS_COMPASS, Identifier.ofVanilla( "angle"), new CompassAnglePredicateProvider((world, stack, entity) -> {
            LodestoneTrackerComponent lodestoneTrackerComponent = stack.get(DataComponentTypes.LODESTONE_TRACKER);
            return lodestoneTrackerComponent != null ? (GlobalPos)lodestoneTrackerComponent.target().orElse(null) : CompassItem.createSpawnPos(world);
        }));

    }


}
