package mc.ultimatecore.helper.hooks.worldedit;

import java.io.*;
import java.nio.file.*;

public interface Schematic {

    String getName();

    String getFileName();

    File getSchematicFile();

    static SchematicImpl of(String name, String fileName, Path path) {
        return new SchematicImpl(name, fileName, path);
    }

}
