package io.quarkus.fs.util.zfs;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.util.Iterator;

public class ZipDirectoryStreamWrapper implements DirectoryStream<Path> {
    private final DirectoryStream<Path> delegate;

    private final FileSystem fileSystem;

    public ZipDirectoryStreamWrapper(DirectoryStream<Path> delegate, FileSystem fileSystem) {
        this.delegate = delegate;
        this.fileSystem = fileSystem;
    }

    @Override
    public Iterator<Path> iterator() {
        return new IteratorWrapper(delegate.iterator());
    }

    @Override
    public void close() throws IOException {
        delegate.close();
    }

    private class IteratorWrapper implements Iterator<Path> {

        private final Iterator<Path> delegate;

        public IteratorWrapper(Iterator<Path> delegate) {
            this.delegate = delegate;
        }

        @Override
        public boolean hasNext() {
            return delegate.hasNext();
        }

        @Override
        public Path next() {
            return new ZipPathWrapper(delegate.next(), fileSystem);
        }

        @Override
        public void remove() {
            delegate.remove();
        }
    }
}
