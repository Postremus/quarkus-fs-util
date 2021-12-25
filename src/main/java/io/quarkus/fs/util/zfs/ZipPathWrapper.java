package io.quarkus.fs.util.zfs;

import io.quarkus.fs.util.base.DelegatingPath;
import java.nio.file.FileSystem;
import java.nio.file.Path;

public class ZipPathWrapper extends DelegatingPath {
    private final FileSystem fileSystem;

    /**
     * @param delegate the Path to delegate to. May not be null.
     * @param fileSystem
     */
    protected ZipPathWrapper(Path delegate, FileSystem fileSystem) {
        super(delegate);
        this.fileSystem = fileSystem;
    }

    @Override
    public Path relativize(Path other) {
        return super.relativize(unwrap(other));
    }

    @Override
    public FileSystem getFileSystem() {
        return fileSystem;
    }
}
