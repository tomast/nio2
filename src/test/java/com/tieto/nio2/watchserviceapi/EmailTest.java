/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tieto.nio2.watchserviceapi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import junit.framework.TestCase;

/**
 *
 * @author tur075
 */
public class EmailTest extends TestCase {
    
    /**
     * Test of processEvents method, of class Email.
     */
    
    public void testProcessEvents() throws IOException, URISyntaxException {
        final URL resourceToWatch = getClass().getClassLoader().getResource("watchingFolder");
        
        assertNotNull(resourceToWatch);
        
        //register directory and process its events
        Path dir = Paths.get(resourceToWatch.toURI());
        new Email(dir).processEvents();
    }
}
