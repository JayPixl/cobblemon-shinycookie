package io.github.jaypixl.shinycookie.fabric;

import com.cobblemon.mod.common.client.tooltips.TooltipManager;
import com.cobblemon.mod.common.item.crafting.SeasoningProcessor;
import com.cobblemon.mod.common.item.group.CobblemonItemGroups;
import io.github.jaypixl.shinycookie.ShinyCookieMod;
import io.github.jaypixl.shinycookie.client.ShinyCookieTooltipGenerator;
import io.github.jaypixl.shinycookie.config.ShinyCookieConfigLoader;
import io.github.jaypixl.shinycookie.event.ModEvents;
import io.github.jaypixl.shinycookie.item.ModItems;
import io.github.jaypixl.shinycookie.item.components.CookiePowerComponent;
import io.github.jaypixl.shinycookie.item.components.ModComponents;
import io.github.jaypixl.shinycookie.item.crafting.CookiePowerProcessor;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;

public class ShinyCookieModFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        // Load Config
        ShinyCookieConfigLoader.load();

        // Register Tooltip Generator to Cobblemon
        TooltipManager.INSTANCE.registerTooltipGenerator(ShinyCookieTooltipGenerator.INSTANCE);

        // Register Cookie Power Processor for cooking to Cobblemon
        SeasoningProcessor.Companion.getProcessors().put(CookiePowerProcessor.INSTANCE.getType(), CookiePowerProcessor.INSTANCE);


        ModComponents.COOKIE_POWER =
                DataComponentType.<CookiePowerComponent>builder()
                        .persistent(CookiePowerComponent.CODEC)
                        .networkSynchronized(CookiePowerComponent.PACKET_CODEC)
                        .build();

        Registry.register(
                BuiltInRegistries.DATA_COMPONENT_TYPE,
                ResourceLocation.fromNamespaceAndPath(ShinyCookieMod.MOD_ID, "cookie_power"),
                ModComponents.COOKIE_POWER
        );

        // Register data component + items

        Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(ShinyCookieMod.MOD_ID, "star_cookie"),
                ModItems.STAR_COOKIE
        );

        Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(ShinyCookieMod.MOD_ID, "stardust"),
                ModItems.STARDUST
        );

        Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(ShinyCookieMod.MOD_ID, "burnt_star_cookie"),
                ModItems.BURNT_STAR_COOKIE
        );

        ItemGroupEvents.modifyEntriesEvent(CobblemonItemGroups.getCONSUMABLES_KEY()).register(itemGroup -> {
            itemGroup.accept(ModItems.STAR_COOKIE);
            itemGroup.accept(ModItems.BURNT_STAR_COOKIE);
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(itemGroup -> {
            itemGroup.accept(ModItems.STARDUST);
        });

        ModEvents.init();
    }

}
