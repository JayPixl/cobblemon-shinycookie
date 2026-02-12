package io.github.jaypixl.shinycookie.item.crafting;

public class CookieSeasoningEntry {
    public double shinyBoost = 0.01;
    public Double rareShinyChance = null; // optional future field

    public CookieSeasoningEntry setShinyBoost(double val) {
        shinyBoost = val;
        return this;
    }
}
