package com.tieto.nio2.fileoperations;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.nio.file.attribute.UserPrincipal;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * The Class PosixFilePermissionsTest.
 * 
 * @author monckdav
 */
public class PosixFilePermissionsTest {

    @Before
    public void setUp() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rw-------");
        Files.setPosixFilePermissions(Paths.get("src/main/resources/2.log"), perms);
    }

    @Test(expected = NoSuchFileException.class)
    public void testReadAttributesNonExistingFile() throws IOException {
        Path file = Paths.get("/tmp/foo");
        Files.readAttributes(file, PosixFileAttributes.class);
    }

    @Test
    public void testPathOperations() throws IOException {
        Path file = Paths.get(System.getProperty("user.home"), "workspaces", "jug", "nio2", "src/main/resources",
                "1.log");

        assertEquals("/", file.getFileSystem().getSeparator());
        assertEquals("/home/monckdav/workspaces/jug/nio2/src/main/resources/1.log", file.toString());
        assertEquals("/home/monckdav/workspaces/jug/nio2/src/main/resources", file.getParent().toString());
        assertEquals("1.log", file.getFileName().toString());
        assertEquals("home", file.getName(0).toString());
        assertEquals(9, file.getNameCount());
        assertEquals("workspaces/jug", file.subpath(2, 4).toString());
        assertEquals("/", file.getRoot().toString());

        // relative path
        file = Paths.get("src/main/resources");
        assertNull(file.getRoot());

        // redundacies
        file = Paths.get("src/main/abc/../resources/./1.log");
        assertEquals("src/main/resources/1.log", file.normalize().toString());

        // relativize
        Path p1 = Paths.get("home");
        Path p2 = Paths.get("home/sally/bar");
        assertEquals("sally/bar", p1.relativize(p2).toString());
        assertEquals("../..", p2.relativize(p1).toString());
    }

    @Test
    public void testReadAttributes() throws IOException {
        Path file = Paths.get("src/main/resources", "1.log");

        PosixFileAttributes attr = Files.readAttributes(file, PosixFileAttributes.class);
        assertEquals("rw-r--r--", PosixFilePermissions.toString(attr.permissions()));
        // System.out.format("%s %s %s %s%n", file.getFileName().toString(), attr.owner().getName(),
        // attr.group().getName(),
        // PosixFilePermissions.toString(attr.permissions()));
    }

    @Test
    public void testFromString() throws IOException {
        Path file = Paths.get("src/main/resources/2.log");

        Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwxrwxrw-");
        Files.setPosixFilePermissions(file, perms);
        assertEquals("rwxrwxrw-",
                PosixFilePermissions.toString(Files.readAttributes(file, PosixFileAttributes.class).permissions()));

        assertTrue(Files.isRegularFile(file));
        assertTrue(Files.isReadable(file));
        assertTrue(Files.isWritable(file));
        assertTrue(Files.isExecutable(file));
    }

    @Test
    @Ignore
    public void testChangeOwner() throws IOException {
        Path file = Paths.get("src/main/resources/3.log");
        UserPrincipal owner = file.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByName("monckdav");
        Files.setOwner(file, owner);
    }

    @Test
    public void testChangeGroup() throws IOException {
        String groupName = "adm";
        Path file = Paths.get("src/main/resources/3.log");
        GroupPrincipal group = file.getFileSystem().getUserPrincipalLookupService()
                .lookupPrincipalByGroupName(groupName);
        Files.getFileAttributeView(file, PosixFileAttributeView.class).setGroup(group);
    }
}
