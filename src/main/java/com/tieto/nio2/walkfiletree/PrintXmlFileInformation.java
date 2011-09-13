/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.tieto.nio2.walkfiletree;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.attribute.BasicFileAttributes;


/**
 * @author tureltom
 */
public class PrintXmlFileInformation implements FileVisitor<Path> {
    
    private final static String XML_FILE_PATTERN = "glob:*.xml";
    private PathMatcher pathMatcher;
    
    public PrintXmlFileInformation() {
        pathMatcher = FileSystems.getDefault().getPathMatcher(XML_FILE_PATTERN);
    }
    
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        System.out.format("Started listing directory: %s%n", dir);
        return FileVisitResult.CONTINUE;
    }
    
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (pathMatcher.matches(file.getFileName())) {
            if (attrs.isSymbolicLink()) {
                System.out.format("Symbolic link: %s ", file);
            } else if (attrs.isRegularFile()) {
                System.out.format("Regular file: %s ", file);
            } else {
                System.out.format("Other: %s ", file);
            }
            System.out.println("(" + attrs.size() + "bytes)");
        }
        return FileVisitResult.CONTINUE;
    }
    
    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        System.err.println(exc);
        return FileVisitResult.CONTINUE;
    }
    
    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        System.out.format("Finished listing directory: %s%n", dir);
        return FileVisitResult.CONTINUE;
    }
}
