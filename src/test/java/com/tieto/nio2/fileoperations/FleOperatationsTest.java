/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.tieto.nio2.fileoperations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;


/**
 * @author tureltom
 *
 */
public class FleOperatationsTest {
    
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
    
    @Test (expected = DirectoryNotEmptyException.class)
    public void testDeleteNotEmptyDirectory() throws URISyntaxException, IOException{
        final Path p1 = Paths.get(getClass().getClassLoader().getResource("watchingFolder").toURI());
        Files.delete(p1);
    }
    
    @Test
    public void testResolvePath() throws URISyntaxException{
        final Path p1 = Paths.get(getClass().getClassLoader().getResource("watchingFolder").toURI());
        final Path p2 = Paths.get("src");
        
        System.out.println(p1.resolve(p2));
    }
    
    @Test
    public void testRelativizePath() throws URISyntaxException{
        final Path p1 = Paths.get("c:/");
        final Path p2 = Paths.get(getClass().getClassLoader().getResource("watchingFolder").toURI());
        
        System.out.println(p1.relativize(p2));
        System.out.println(p2.relativize(p1));
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
    
    @Test
    @Ignore
    public void testIsSameFile() throws URISyntaxException, IOException{
        final Path target = Paths.get(getClass().getClassLoader().getResource("watchingFolder").toURI());
        final Path p1 = Paths.get("c:/test1");
        final Path p2 = Paths.get("c:/test2");
        
        Files.createLink(p1, target);
        Files.createLink(p2, target);
        
        assertTrue(Files.isSameFile(p1, p2));
    }
}
