package io.github.jaypixl.shinycookie.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.jaypixl.shinycookie.ShinyCookieMod;
import io.github.jaypixl.shinycookie.item.crafting.CookieSeasoningEntry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ShinyCookieConfigLoader {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH =
            Path.of("config", ShinyCookieMod.MOD_ID, "main.json");

    public static ShinyCookieConfig CONFIG;

    public static void load() {
        try {
            if (Files.notExists(CONFIG_PATH)) {
                Files.createDirectories(CONFIG_PATH.getParent());
                CONFIG = new ShinyCookieConfig();
                CONFIG.seasonings.put("shinycookie:stardust", new CookieSeasoningEntry().setShinyBoost(0.25));
                Files.writeString(CONFIG_PATH, GSON.toJson(CONFIG));
            } else {
                CONFIG = GSON.fromJson(
                        Files.readString(CONFIG_PATH),
                        ShinyCookieConfig.class
                );
            }
            //ShinyCookieMod.LOGGER.info(String.valueOf(CONFIG.defaultCookieShinyChance));
            //ShinyCookieMod.LOGGER.info(String.valueOf(CONFIG.seasonings));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Shiny Cookie config!", e);
        }
    }
}

