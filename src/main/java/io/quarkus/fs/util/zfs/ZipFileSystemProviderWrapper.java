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
import java.util.zip.ZipEntry;

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
        String pathStr = path.toString();
        if (pathStr.length() > 1) {
            pathStr = pathStr.substring(1);
            JarFile jarFile = ((ZipFileSystemWrapper) path.getFileSystem()).jarFile;

            ZipEntry entry = jarFile.getEntry(pathStr);
            if (entry != null) {
                return (A) new ZipEntryAttributes(entry);
            }
        }
        return super.readAttributes(path, type, options);
    }

    @Override
    public DirectoryStream<Path> newDirectoryStream(Path dir, DirectoryStream.Filter<? super Path> filter) throws IOException {
        return new ZipDirectoryStreamWrapper(super.newDirectoryStream(dir, filter), dir.getFileSystem());
    }

    private static class ZipEntryAttributes implements BasicFileAttributes {

        private final ZipEntry entry;

        public ZipEntryAttributes(ZipEntry entry) {
            this.entry = entry;
        }

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
            return entry.getCreationTime();
        }

        @Override
        public boolean isRegularFile() {
            return !entry.isDirectory();
        }

        @Override
        public boolean isDirectory() {
            return entry.isDirectory();
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
            return entry.getSize();
        }

        @Override
        public Object fileKey() {
            return entry.hashCode();
        }
    }
}
