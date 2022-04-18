package mc.ultimatecore.helper.hooks.worldedit;

import lombok.*;

import java.io.*;
import java.nio.file.*;

@Getter
public class SchematicImpl implements Schematic {

    private final String name;
    private final String fileName;
    private final Path path;

    SchematicImpl(String name, String fileName, Path path) {
        this.name = name;
        this.fileName = fileName;
        this.path = path;
    }

    @Override
    public File getSchematicFile() {
        return path.resolve(fileName).toFile();
    }
}
