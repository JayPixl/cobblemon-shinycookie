package io.github.jaypixl.shinycookie.config;

import io.github.jaypixl.shinycookie.item.crafting.CookieSeasoningEntry;

import java.util.HashMap;
import java.util.Map;

public class ShinyCookieConfig {
    public double defaultCookieShinyChance = 0.0001;

    public boolean doShinyCatchStardust = true;

    public int shinyCatchStardustMin = 1;

    public int shinyCatchStardustMax = 3;

    public boolean rewardStardustOnBurntCookie = true;

    public int burntCookieStardustMin = 1;

    public int burntCookieStardustMax = 1;

    public int starCookieHealAmount = 20;

    public Map<String, CookieSeasoningEntry> seasonings = new HashMap<>();
}
