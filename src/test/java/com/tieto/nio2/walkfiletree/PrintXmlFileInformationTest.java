/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.tieto.nio2.walkfiletree;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author tur075
 */
public class PrintXmlFileInformationTest {
    
    @BeforeClass
    public static void setUpClass() throws Exception {
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Test of preVisitDirectory method, of class PrintFileInformation.
     */
    @Test
    public void testWalkFileTree() throws Exception {
        final Path rootDir = Paths.get(getClass().getClassLoader().getResource("watchingFolder").toURI());
        assertNotNull(rootDir);
        
        Files.walkFileTree(rootDir, new PrintXmlFileInformation());
    }
    
}
