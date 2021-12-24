package io.quarkus.fs.util;

import io.quarkus.fs.util.sysfs.ConfigurableFileSystemProviderWrapper;
import io.quarkus.fs.util.sysfs.FileSystemWrapper;
import io.quarkus.fs.util.sysfs.PathWrapper;

import java.io.IOException;
import java.net.URI;
import java.nio.file.AccessMode;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.spi.FileSystemProvider;
import java.util.Map;
import java.util.Set;

public class FileSystemHelper {
    /**
     * Same as calling {@link FileSystems#newFileSystem(Path, ClassLoader)}. The env parameter is ignored when running on java
     * versions less than 13.
     *
     * @param path
     * @param env only used on java 13
     * @param classLoader
     * @return
     * @throws IOException
     */
    public static FileSystem openFS(Path path, Map<String, ?> env, ClassLoader classLoader) throws IOException {
        return FileSystems.newFileSystem(path, classLoader);
    }

    /**
     * Same as calling {@link FileSystems#newFileSystem(URI, Map, ClassLoader)}
     *
     * @param uri
     * @param env
     * @param classLoader
     * @return
     * @throws IOException
     */
    public static FileSystem openFS(URI uri, Map<String, ?> env, ClassLoader classLoader) throws IOException {
        return FileSystems.newFileSystem(uri, env, classLoader);
    }

    /**
     * Wraps the given path with a {@link io.quarkus.fs.util.sysfs.PathWrapper}, preventing file system writeability checks.
     * Only has an effect on jdk13 and up
     *
     * @param path
     * @return
     */
    public static Path ignoreFileWriteability(Path path) {
        FileSystemProvider provider = new ConfigurableFileSystemProviderWrapper(path.getFileSystem().provider(),
                Set.of(AccessMode.WRITE));
        FileSystem fileSystem = new FileSystemWrapper(path.getFileSystem(), provider);
        return new PathWrapper(path, fileSystem);
    }
}
