package com.infernalsuite.ultimatecore.common.classpath;

import com.infernalsuite.ultimatecore.common.api.classpath.*;

import java.net.*;
import java.nio.file.*;

public class ReflectionClassPathAppender implements ClassPathAppender {

    private final URLClassLoaderAccess classLoaderAccess;

    public ReflectionClassPathAppender(ClassLoader classLoader) throws IllegalStateException {
        if (classLoader instanceof URLClassLoader urlClassLoader) {
            this.classLoaderAccess = URLClassLoaderAccess.create(urlClassLoader);
        } else {
            throw new IllegalStateException("ClassLoader is not an instance of URLClassLoader");
        }
    }

    @Override
    public void addJarToClassPath(Path path) {
        try {
            this.classLoaderAccess.addUrl(path.toUri().toURL());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
