package com.infernalsuite.ultimatecore.alchemy.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        return line.replace(this.key, this.value);
    }

    public List<String> process(String line, List<String> multiLines) {
        if (line == null)
            return Collections.singletonList("");

        List<String> list = new ArrayList<>();
        for(String newLine : multiLines)
            newLine.replace(this.key, this.value);
        return list;
    }
}
