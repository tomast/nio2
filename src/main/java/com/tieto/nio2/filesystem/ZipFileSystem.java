package com.tieto.nio2.filesystem;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.spi.FileSystemProvider;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class ZipFileSystem.
 * 
 * @author monckdav
 */
public class ZipFileSystem {

    private static Map<String, Object> env = new HashMap<>();
    private static Path zipFile = Paths.get("src/main/resources/zipfs.jar");

    /**
     * Reads attributes.
     * 
     * @return attributes
     * @throws IOException
     */
    public static BasicFileAttributes readAttributes(String path) throws IOException {
        try (FileSystem fs = ZipFileSystem.getZipFileSystemProvider().newFileSystem(zipFile, env)) {
            // newFileSystem(URI.create("jar:file:/mydir/foo.jar"), env);
            Path fsPath = fs.getPath(path);
            return Files.readAttributes(fsPath, BasicFileAttributes.class);
        }
    }

    /**
     * Lists path.
     * 
     * @param path
     * @param verbose
     * @throws IOException
     */
    public static void list(Path path, boolean verbose) throws IOException {
        try (FileSystem fs = ZipFileSystem.getZipFileSystemProvider().newFileSystem(zipFile, env)) {
            Path zipPath = fs.getPath(path.toString());
            if (!"/".equals(path.toString())) {
                System.out.printf("  %s%n", path.toString());
                if (verbose)
                    System.out.println(Files.readAttributes(zipPath, BasicFileAttributes.class).toString());
            }
            if (Files.notExists(zipPath))
                return;
            if (Files.isDirectory(zipPath)) {
                try (DirectoryStream<Path> ds = Files.newDirectoryStream(zipPath)) {
                    for (Path child : ds)
                        list(child, verbose);
                }
            }
        }
    }

    /**
     * Adds, creates or updates zip file.
     * 
     * @param fs
     * @param path
     * @throws IOException
     */
    public static void update(String path) throws IOException {
        try (FileSystem fs = ZipFileSystem.getZipFileSystemProvider().newFileSystem(zipFile, env)) {
            Path src = FileSystems.getDefault().getPath(path);
            Path dst = fs.getPath(path);
            Path parent = dst.getParent();
            if (parent != null && Files.notExists(parent))
                mkdirs(parent);
            Files.copy(src, dst, REPLACE_EXISTING);
        }
    }

    private static void mkdirs(Path path) throws IOException {
        path = path.toAbsolutePath();
        Path parent = path.getParent();
        if (parent != null) {
            if (Files.notExists(parent))
                mkdirs(parent);
        }
        Files.createDirectory(path);
    }

    /**
     * Removes file. TODO remove also path which leads to file
     * 
     * @param path
     * @throws IOException
     */
    public static void rm(String path) throws IOException {
        try (FileSystem fs = ZipFileSystem.getZipFileSystemProvider().newFileSystem(zipFile, env)) {
            Files.delete(fs.getPath(path));
        }
    }

    /**
     * Checks whether file exist.
     * 
     * @param path
     * @return true if exist
     * @throws IOException
     */
    public static boolean exists(String path) throws IOException {
        try (FileSystem fs = ZipFileSystem.getZipFileSystemProvider().newFileSystem(zipFile, env)) {
            return Files.exists(fs.getPath(path));
        }
    }

    /**
     * Gets the zip file system provider.
     * 
     * @return the zip file system provider
     */
    static FileSystemProvider getZipFileSystemProvider() {
        for (FileSystemProvider provider : FileSystemProvider.installedProviders()) {
            if ("jar".equals(provider.getScheme()))
                return provider;
        }
        return null;
    }
}
