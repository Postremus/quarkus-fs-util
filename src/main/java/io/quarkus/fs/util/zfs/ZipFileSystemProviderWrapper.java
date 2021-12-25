package io.quarkus.fs.util.zfs;

import io.quarkus.fs.util.base.DelegatingFileSystemProvider;
import java.io.IOException;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileTime;
import java.nio.file.spi.FileSystemProvider;
import java.util.Set;
import java.util.jar.JarFile;

public class ZipFileSystemProviderWrapper extends DelegatingFileSystemProvider {
    /**
     * @param delegate the FileSystem to delegate to. May not be null.
     */
    public ZipFileSystemProviderWrapper(FileSystemProvider delegate) {
        super(delegate);
    }

    @Override
    public SeekableByteChannel newByteChannel(Path path, Set<? extends OpenOption> options, FileAttribute<?>... attrs)
            throws IOException {
        return super.newByteChannel(path, options, attrs);
    }

    @Override
    public <A extends BasicFileAttributes> A readAttributes(Path path, Class<A> type, LinkOption... options)
            throws IOException {
        JarFile jarFile = ((ZipFileSystemWrapper) path.getFileSystem()).jarFile;
        return super.readAttributes(path, type, options);
    }

    @Override
    public DirectoryStream<Path> newDirectoryStream(Path dir, DirectoryStream.Filter<? super Path> filter) throws IOException {
        return super.newDirectoryStream(dir, filter);
    }

    private static class ZipEntryAttributes implements BasicFileAttributes {

        @Override
        public FileTime lastModifiedTime() {
            return null;
        }

        @Override
        public FileTime lastAccessTime() {
            return null;
        }

        @Override
        public FileTime creationTime() {
            return null;
        }

        @Override
        public boolean isRegularFile() {
            return false;
        }

        @Override
        public boolean isDirectory() {
            return false;
        }

        @Override
        public boolean isSymbolicLink() {
            return false;
        }

        @Override
        public boolean isOther() {
            return false;
        }

        @Override
        public long size() {
            return 0;
        }

        @Override
        public Object fileKey() {
            return null;
        }
    }
}
