package com.tieto.nio2.filesystem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.spi.FileSystemProvider;
import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * The Class ZipFileSystem.
 * 
 * @author monckdav
 */
public class ZipFileSystemTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("-----\n");
    }

    @Test
    public void testList() throws IOException {
        Path file = Paths.get("/META-INF");
        ZipFileSystem.list(file, false);
    }

    @Test
    public void testGetBasicFileAttributes() throws IOException {
        BasicFileAttributes readAttributes = ZipFileSystem.readAttributes("/META-INF/MANIFEST.MF");

        assertEquals(331, readAttributes.size());
        System.out.println("Size: " + readAttributes.size());
    }

    @Test
    public void testGetPosixFileAttributes() throws IOException {
        try (FileSystem fs = ZipFileSystem.getZipFileSystemProvider().newFileSystem(
                Paths.get("src/main/resources/test.zip"), new HashMap<String, Object>())) {
            Path path = fs.getPath("/1.log");
            PosixFileAttributes readAttributes = Files.readAttributes(path, PosixFileAttributes.class);
            // null
            assertNull(readAttributes);
        }
    }

    @Test
    public void testAddFile() throws IOException {
        String path = "src/main/resources/1.log";
        ZipFileSystem.update(path);

        assertTrue(ZipFileSystem.readAttributes("src/main/resources/1.log").isRegularFile());
    }

    @Test
    public void testRemoveFile() throws IOException {
        String path = "src/main/resources/1.log";
        ZipFileSystem.rm(path);

        assertFalse(ZipFileSystem.exists("src/main/resources/1.log"));
    }

    @Test
    public void testGetZipFileSystemProvider() {
        FileSystemProvider zipFileSystemProvider = ZipFileSystem.getZipFileSystemProvider();
        assertNotNull(zipFileSystemProvider);
    }
}
