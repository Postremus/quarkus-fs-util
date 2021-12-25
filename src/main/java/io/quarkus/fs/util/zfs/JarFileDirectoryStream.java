package io.quarkus.fs.util.zfs;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.util.Iterator;

public class JarFileDirectoryStream implements DirectoryStream<Path> {
    @Override
    public Iterator<Path> iterator() {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
