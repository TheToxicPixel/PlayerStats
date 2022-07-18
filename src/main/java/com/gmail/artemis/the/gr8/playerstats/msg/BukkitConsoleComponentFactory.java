package com.gmail.artemis.the.gr8.playerstats.msg;

import com.gmail.artemis.the.gr8.playerstats.config.ConfigHandler;
import com.gmail.artemis.the.gr8.playerstats.enums.DebugLevel;
import com.gmail.artemis.the.gr8.playerstats.enums.PluginColor;
import com.gmail.artemis.the.gr8.playerstats.utils.MyLogger;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

public class BukkitConsoleComponentFactory extends ComponentFactory {

    public BukkitConsoleComponentFactory(ConfigHandler config) {
        super(config);
        MyLogger.logMsg("BukkitConsoleFactory created!", DebugLevel.MEDIUM);
    }

    @Override
    protected void prepareColors() {
        PREFIX = PluginColor.GOLD.getConsoleColor();
        BRACKETS = PluginColor.GRAY.getConsoleColor();
        UNDERSCORE = PluginColor.DARK_PURPLE.getConsoleColor();
        MSG_MAIN = PluginColor.MEDIUM_BLUE.getConsoleColor();
        MSG_MAIN_2 = PluginColor.GOLD.getConsoleColor();
        MSG_ACCENT = PluginColor.MEDIUM_GOLD.getConsoleColor();
        MSG_ACCENT_2 = PluginColor.LIGHT_YELLOW.getConsoleColor();
        CLICKED_MSG = PluginColor.LIGHT_PURPLE.getConsoleColor();
        HOVER_MSG = PluginColor.LIGHT_BLUE.getConsoleColor();
        HOVER_ACCENT = PluginColor.LIGHT_GOLD.getConsoleColor();
    }

    @Override
    public TextColor getSharerNameColor() {
        return PluginColor.NAME_5.getConsoleColor();
    }

    @Override
    protected TextColor getHexColor(String hexColor) {
        TextColor hex = TextColor.fromHexString(hexColor);
        return hex != null ? NamedTextColor.nearestTo(hex) : NamedTextColor.WHITE;
    }
}