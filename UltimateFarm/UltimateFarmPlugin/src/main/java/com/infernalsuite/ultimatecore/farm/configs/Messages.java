package com.infernalsuite.ultimatecore.farm.configs;

import java.util.Arrays;
import java.util.List;

public class Messages {
    public String invalidCommand = "%prefix% &7Invalid Command!";
    public String invalidArguments = "%prefix% &7Invalid Arguments!";
    public String regionAdded = "%prefix% &7You has added region &a%region% &7to &a%region_type% REGIONS";
    public String regionRemoved = "%prefix% &7You has removed region &a%region% &7from &a%region_type% REGIONS";
    public String regionAlreadyAdded = "%prefix% &7This region is already added!";
    public String regionNotExist = "%prefix% &7This region doesn't exist!";
    public String regionTypeNotExist = "%prefix% &7That Region doesn't exist!";
    public List<String> regionList = Arrays.asList("&6&lRegion Type >> &e%region_type%", "&8 > &7%region%");
    public List<String> typeList = Arrays.asList("&6&lRegion Types:", "&8 > &7%region_type%");
    public String mustTarget = "%prefix% &7You aren't targeting a block!";
    public String blockAlreadyAdded = "%prefix% &7This block is already added!";
    public String blockAdded = "%prefix% &7You succesfully added %region_type% to region types!";
    public String blockDontAdded = "%prefix% &7This block is not already added!";
    public String blockRemoved = "%prefix% &7This block has been removed!";

    public String mustBeInsideRegion = "%prefix% &7You must be inside the region!";
    public String addedGuardian = "%prefix% &7You added a new guardian succesfully!";
    public String alreadyGuardian = "%prefix% &7There is already a guardian in this region delete it first!";
    public String removedGuardian = "%prefix% &7You removed the guardian from this region!";
    public String notGuardian = "%prefix% &7There isn't a guardian in this region!";

    public String invalidPlayer = "%prefix% &7Invalid Player";
    public String offlinePlayer = "%prefix% That player is offline!\n%prefix% Tell him to log into server!";
    public String reloaded = "%prefix% &7Configuration reloaded.";
    public String noPermission = "%prefix% &7You don't have permission for that.";
    public String helpMessage = "&7%command% - &e%description%";
    public String helpHeader = "      &6&lIslandCore   ";
    public String helpfooter = "&e<< &6Page %page% of %maxpage% &e>>";
    public String previousPage = "<<";
    public String nextPage = ">>";
    public String helpPageHoverMessage = "Click to go to page %page%";
    public String mustBeAPlayer = "%prefix% &7You must be a player to execute this command.";
    public String unknownCommand = "%prefix% &7Unknown Command, Try /is help";

    public String noWorldGuard = "%prefix% &cWorldGuard and WorldEdit weren't detected, please install them!";

}
