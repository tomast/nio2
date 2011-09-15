/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tieto.nio2.watchserviceapi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import junit.framework.TestCase;

import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author tureltom
 */
public class EmailTest extends TestCase {
    
    /**
     * Test of processEvents method, of class Email.
     */
    @Test
    @Ignore
    public void testProcessEvents() throws IOException, URISyntaxException {
//        final URL resourceToWatch = getClass().getClassLoader().getResource("c:/watchingFolder");
//        assertNotNull(resourceToWatch);
        
//        Path dir = FileSystems.getDefault().getPath("C:/watchingDirectory");
        
        //register directory and process its events
        final Path dir = Paths.get("C:/watchingDirectory");
        new Email(dir).processEvents();
    }
}
