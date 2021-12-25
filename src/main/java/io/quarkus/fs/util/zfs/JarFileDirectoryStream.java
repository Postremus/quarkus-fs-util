package io.quarkus.fs.util.zfs;


import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.jar.JarFile;

public class JarFileDirectoryStream implements DirectoryStream<Path> {
    private final JarFile jarFile;
    private final Path dir;
    private final DirectoryStream.Filter<? super Path> filter;
    private volatile boolean isClosed;
    private volatile Iterator<Path> itr;

    @Override
    public Iterator<Path> iterator() {
        jarFile.
        return null;
    }

    @Override
    public void close() throws IOException {
        isClosed = true;
    }
}
