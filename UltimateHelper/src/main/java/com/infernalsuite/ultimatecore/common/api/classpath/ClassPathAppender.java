package com.infernalsuite.ultimatecore.common.api.classpath;

import java.nio.file.*;

public interface ClassPathAppender extends AutoCloseable {

    void addJarToClassPath(Path path);

    @Override
    default void close() {}

}
