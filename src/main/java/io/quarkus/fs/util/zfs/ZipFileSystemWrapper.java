package io.quarkus.fs.util.zfs;

import io.quarkus.fs.util.base.DelegatingFileSystem;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.spi.FileSystemProvider;
import java.util.jar.JarFile;

public class ZipFileSystemWrapper extends DelegatingFileSystem {
    JarFile jarFile;

    final FileSystemProvider fileSystemProvider;

    /**
     * @param delegate the FileSystem to delegate to. May not be null.
     * @param fileSystemProvider
     */
    public ZipFileSystemWrapper(FileSystem delegate, Path path, FileSystemProvider fileSystemProvider) {
        super(delegate);
        this.fileSystemProvider = fileSystemProvider;
        try {
            jarFile = new JarFile(path.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Path getPath(String first, String... more) {
        return new ZipPathWrapper(super.getPath(first, more), this);
    }

    @Override
    public FileSystemProvider provider() {
        return fileSystemProvider;
    }

    @Override
    public void close() throws IOException {
        jarFile.close();
        super.close();
    }

}
