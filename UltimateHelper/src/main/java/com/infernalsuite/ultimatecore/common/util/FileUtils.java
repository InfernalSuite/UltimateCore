package com.infernalsuite.ultimatecore.common.util;

import lombok.experimental.*;

import java.io.*;
import java.nio.file.*;

@UtilityClass
public class FileUtils {

    public Path createFileIfNotExists(Path path) throws IOException {
        if (!Files.exists(path)) Files.createFile(path);
        return path;
    }

    public Path createDirectoryIfNotExists(Path path) throws IOException {
        if (Files.exists(path) && (Files.isDirectory(path) || Files.isSymbolicLink(path))) {
            return path;
        }

        try {
            Files.createDirectory(path);
        } catch (FileAlreadyExistsException ignored) {}

        return path;
    }

    public Path createDirectoriesIfNotExists(Path path) throws IOException {
        if (Files.exists(path) && (Files.isDirectory(path) || Files.isSymbolicLink(path))) {
            return path;
        }

        try {
            Files.createDirectories(path);
        } catch (FileAlreadyExistsException ignored) {}

        return path;
    }

    public void deleteDirectory(Path path) throws IOException {
        if (!Files.exists(path) || !Files.isDirectory(path)) {
            return;
        }

        try (DirectoryStream<Path> contents = Files.newDirectoryStream(path)) {
            for (Path file : contents) {
                if (Files.isDirectory(file)) {
                    deleteDirectory(file);
                } else {
                    Files.delete(file);
                }
            }
        }

        Files.delete(path);
    }

}
