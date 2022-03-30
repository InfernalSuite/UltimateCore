package com.infernalsuite.ultimatecore.skills.utils;


public class Placeholder {
    private final String key;

    private final String value;

    public Placeholder(String key, String value) {
        this.key = "%" + key + "%";
        this.value = value;
    }

    public String process(String line) {
        if (line == null)
            return "";
        //if (HyperSkills.getInstance().getConfiguration().translatePAPIPlaceholders)
        return line.replace(this.key, this.value);
    }

}
