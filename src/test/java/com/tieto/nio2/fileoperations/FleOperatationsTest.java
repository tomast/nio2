/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.tieto.nio2.fileoperations;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author tur075
 */
public class FleOperatationsTest {
    
    @Before
    public void setUp() throws Exception {
    }
    
    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testDeleteNotExistingFileJava6() {
        final File testFile = new File("watchingFolder/test.txt");
        assertNotNull(testFile);
        boolean deleted = testFile.delete();
        // Who knows what went wrong ?!?
        assertFalse(deleted);
    }
    
    @Test(expected = NoSuchFileException.class)
    public void testDeleteNotExistingFileJava7() throws URISyntaxException, IOException {
        final Path testFile = Paths.get("watchingFolder/test.txt");
        assertNotNull(testFile);
        Files.delete(testFile);
    }
    
    @Test
    public void testReadingAllFileLines() throws URISyntaxException, IOException {
        final Path testFile = Paths.get(getClass().getClassLoader().getResource("watchingFolder/test.txt").toURI());
        assertNotNull(testFile);
        final List<String> lines = Files.readAllLines(testFile, Charset.forName("UTF-8"));
        System.out.print(lines);
    }
    
    @Test
    public void testDirectoryStreamWithGlob() throws URISyntaxException, IOException {
        final Path testFile = Paths.get(getClass().getClassLoader().getResource("watchingFolder").toURI());
        assertNotNull(testFile);
        final DirectoryStream<Path> dirStream = Files.newDirectoryStream(testFile, "*.txt");
        
        for (Path txtFile : dirStream) {
            System.out.format("File name> %s \n", txtFile);
        }
    }
}
