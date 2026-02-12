package io.github.jaypixl.shinycookie.event;

import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.api.events.pokemon.PokemonCapturedEvent;
import com.cobblemon.mod.common.pokemon.Pokemon;
import io.github.jaypixl.shinycookie.config.ShinyCookieConfigLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

public final class ModEvents {
    public static void init() {
        CobblemonEvents.POKEMON_CAPTURED.subscribe(ModEvents::onPokemonCaptured);
    }

    private static void onPokemonCaptured(PokemonCapturedEvent event) {
        //ShinyCookieMod.LOGGER.info(String.valueOf(event.getPokemon().getShiny()));

        //event.getPlayer().sendSystemMessage(Component.literal("CAPTURED " + event.getPokemon().getSpecies().getName()).withStyle(ChatFormatting.AQUA));

        Pokemon pokemon = event.getPokemon();
        ServerPlayer player = event.getPlayer();

        if (!pokemon.getShiny() || !ShinyCookieConfigLoader.CONFIG.doShinyCatchStardust) { return; }

        RandomSource random = player.getRandom();
        int amount = ShinyCookieConfigLoader.CONFIG.shinyCatchStardustMin + random.nextInt(ShinyCookieConfigLoader.CONFIG.shinyCatchStardustMax);

        ItemStack stardust = new ItemStack(
                BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("shinycookie", "stardust")),
                amount
        );

        if (!player.getInventory().add(stardust)) {
            player.drop(stardust, false);
        }
    }
}
