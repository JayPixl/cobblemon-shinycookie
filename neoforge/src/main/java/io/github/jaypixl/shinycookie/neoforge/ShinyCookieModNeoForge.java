package io.github.jaypixl.shinycookie.neoforge;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.mod.common.client.tooltips.TooltipGenerator;
import com.cobblemon.mod.common.client.tooltips.TooltipManager;
import com.cobblemon.mod.common.item.crafting.SeasoningProcessor;
import com.cobblemon.mod.common.item.group.CobblemonItemGroups;
import io.github.jaypixl.shinycookie.ShinyCookieMod;
import io.github.jaypixl.shinycookie.client.ShinyCookieTooltipGenerator;
import io.github.jaypixl.shinycookie.config.ShinyCookieConfigLoader;
import io.github.jaypixl.shinycookie.event.ModEvents;
import io.github.jaypixl.shinycookie.item.ModItems;
import io.github.jaypixl.shinycookie.item.StarCookieItem;
import io.github.jaypixl.shinycookie.item.components.CookiePowerComponent;
import io.github.jaypixl.shinycookie.item.components.ModComponents;
import io.github.jaypixl.shinycookie.item.crafting.CookiePowerProcessor;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(ShinyCookieMod.MOD_ID)
public class ShinyCookieModNeoForge {

    public ShinyCookieModNeoForge(IEventBus eventBus) {
        eventBus.register(ShinyCookieModNeoForge.class);

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

        eventBus.addListener((RegisterEvent event) -> {
            event.register(
                    Registries.DATA_COMPONENT_TYPE,
                    helper -> helper.register(
                            ResourceLocation.fromNamespaceAndPath(ShinyCookieMod.MOD_ID, "cookie_power"),
                            ModComponents.COOKIE_POWER
                    )
            );
            event.register(
                    Registries.ITEM,
                    helper -> helper.register(
                            ResourceLocation.fromNamespaceAndPath(ShinyCookieMod.MOD_ID, "star_cookie"),
                            ModItems.STAR_COOKIE
                    )
            );
            event.register(
                    Registries.ITEM,
                    helper -> helper.register(
                            ResourceLocation.fromNamespaceAndPath(ShinyCookieMod.MOD_ID, "burnt_star_cookie"),
                            ModItems.BURNT_STAR_COOKIE
                    )
            );
            event.register(
                    Registries.ITEM,
                    helper -> helper.register(
                            ResourceLocation.fromNamespaceAndPath(ShinyCookieMod.MOD_ID, "stardust"),
                            ModItems.STARDUST
                    )
            );
        });

        ModEvents.init();
    }

    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.STARDUST);
        } else if (event.getTabKey() == CobblemonItemGroups.getCONSUMABLES_KEY()) {
            event.accept(ModItems.STAR_COOKIE);
            event.accept(ModItems.BURNT_STAR_COOKIE);
        }
    }


}
