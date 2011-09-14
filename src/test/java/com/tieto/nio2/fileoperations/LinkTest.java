package com.tieto.nio2.fileoperations;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * The Class LinkTest.
 * 
 * @author monckdav
 */
public class LinkTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        Files.deleteIfExists(Paths.get("src/main/resources/sl1"));
        Files.deleteIfExists(Paths.get("src/main/resources/sl2"));
        Files.deleteIfExists(Paths.get("src/main/resources/hl1"));
        Files.deleteIfExists(Paths.get("src/main/resources/target.log"));
        Files.deleteIfExists(Paths.get("test1"));
        Files.deleteIfExists(Paths.get("test2"));
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCreateSymbolicLink() throws IOException {
        Path link = Paths.get("src/main/resources/sl1");
        Path target = Paths.get("src/main/resources/1.log");
        Files.createSymbolicLink(link, target);

        assertTrue(Files.isSymbolicLink(link));
        assertFalse(Files.isSymbolicLink(target));

        assertEquals(target, Files.readSymbolicLink(link));
    }

    @Test
    public void testCreateHardLink() throws IOException {
        Path link = Paths.get("src/main/resources/hl1");
        Path target = Paths.get("src/main/resources/target.log");
        Files.createFile(target);
        Files.createLink(link, target);

        assertTrue(Files.isSameFile(link, target));
    }

    @Test
    public void testDeleteHardLink() throws IOException {
        Path link = Paths.get("src/main/resources/hl1");
        Path target = Paths.get("src/main/resources/target.log");
        Files.delete(target);

        assertTrue(Files.exists(link));
        assertFalse(Files.exists(target));
    }

    @Test
    public void testIsSameFile() throws IOException {
        final Path target = Paths.get("src/main/resources/1.log");
        final Path p1 = Paths.get("test1");
        final Path p2 = Paths.get("test2");

        Files.createSymbolicLink(p1, target);
        Files.createSymbolicLink(p2, target);

        assertTrue(Files.isSameFile(p1, p2));
    }
}
