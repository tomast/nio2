/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tieto.nio2.fileoperations;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFileAttributes;
import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author tur075
 */
public class FileAttributesTest {
    
    @Before
    public void setUp() throws Exception {
    }
    
    @After
    public void tearDown() throws Exception {
    }
    
    @Test (expected = UnsupportedOperationException.class)
    public void testReadingFileAttributesUnsupportedFileSystem() throws URISyntaxException, IOException{
        final Path testFile = Paths.get(getClass().getClassLoader().getResource("watchingFolder/test.txt").toURI());
        assertNotNull(testFile);
        Files.readAttributes(testFile, PosixFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
    }
    
    @Test
    public void testReadingFileAttributesInBulk() throws URISyntaxException, IOException{
        final Path testFile = Paths.get(getClass().getClassLoader().getResource("watchingFolder/test.txt").toURI());
        assertNotNull(testFile);
        final BasicFileAttributes atts = Files.readAttributes(testFile, BasicFileAttributes.class);
        assertNotNull(atts);
        System.out.format("Creation time> %s \n", atts.creationTime());
        System.out.format("Last Access Time> %s \n",atts.lastAccessTime());
        System.out.format("Last Modified Time> %s \n",atts.lastModifiedTime());
        System.out.format("Size> %s bytes \n",atts.size());
        assertFalse(atts.isDirectory());
        assertTrue(atts.isRegularFile());
        assertFalse(atts.isSymbolicLink());
    }
    
    @Test
    public void testReadingFileAttributesOneByOne() throws URISyntaxException, IOException{
        final Path testFile = Paths.get(getClass().getClassLoader().getResource("watchingFolder/test.txt").toURI());
        assertNotNull(testFile);
        System.out.format("Last Modified Time> %s \n",Files.getLastModifiedTime(testFile));
        System.out.format("Size> %s bytes \n",Files.size(testFile));
        assertFalse(Files.isDirectory(testFile));
        assertTrue(Files.isRegularFile(testFile));
        assertFalse(Files.isSymbolicLink(testFile));
    }
    
    @Test
    public void testReadingFileAttributesOldWay() throws URISyntaxException, IOException{
        final File file = new File(getClass().getClassLoader().getResource("watchingFolder/test.txt").toURI());
        assertNotNull(file);
        System.out.format("Last Modified Time> %ta \n", new Date(file.lastModified()));
        System.out.format("Size> %s bytes \n", file.length());
        assertFalse(file.isDirectory());
        assertTrue(file.isFile());
    }
    
    @Test
    public void testSetFileAttribute() throws URISyntaxException, IOException {
        final Path testFile = Paths.get(getClass().getClassLoader().getResource("watchingFolder/test.txt").toURI());
        assertNotNull(testFile);
        BasicFileAttributes attr = Files.readAttributes(testFile, BasicFileAttributes.class);
        long currentTime = System.currentTimeMillis();
        FileTime updatedTime = FileTime.fromMillis(currentTime);
        Files.setLastModifiedTime(testFile, updatedTime);
    }
    
    @Test
    public void testFileStore() throws URISyntaxException, IOException {
        final Path testFile = Paths.get(getClass().getClassLoader().getResource("watchingFolder/test.txt").toURI());
        assertNotNull(testFile);
        
        FileStore store = Files.getFileStore(testFile);

        System.out.format("Total space > %s MB \n", store.getTotalSpace() / 1024);
        System.out.format("Used > %s MB \n", (store.getTotalSpace() - store.getUnallocatedSpace()) / 1024);
        System.out.format("Available > %s MB \n", store.getUsableSpace() / 1024);
    }
}
