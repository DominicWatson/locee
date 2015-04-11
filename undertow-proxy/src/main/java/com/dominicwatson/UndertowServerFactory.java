package com.dominicwatson;

import javax.servlet.ServletException;
import java.io.IOException;

import java.io.File;
import java.net.URLClassLoader;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;

import io.undertow.Undertow;
import io.undertow.Undertow.Builder;
import io.undertow.Handlers;
import io.undertow.servlet.handlers.DefaultServlet;
import io.undertow.server.*;
import io.undertow.util.Headers;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ServletInfo;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.resource.FileResourceManager;
import static io.undertow.servlet.Servlets.defaultContainer;
import static io.undertow.servlet.Servlets.deployment;

public class UndertowServerFactory {

    public static Undertow newServer( int port, String libDirs, String webroot, String webXmlPath, String webInfPath ) throws ServletException, IOException {
        Builder        builder     = getServerBuilder();
        DeploymentInfo servletInfo = buildServletInfo( libDirs, webroot, webXmlPath, webInfPath );
        PathHandler    pathHandler = createPathHandlerFromServletInfo( servletInfo );

        builder.addHttpListener( port, "localhost" );
        builder.setHandler( pathHandler );

        return builder.build();
    }

    private static DeploymentInfo buildServletInfo( String libDirs, String webroot, String webXmlPath, String webInfPath ) throws IOException {
        DeploymentInfo servletInfo = deployment();
        URLClassLoader classLoader = buildClassLoader( libDirs );

        servletInfo.setContextPath( "" );
        servletInfo.setClassLoader( classLoader );
        servletInfo.setDeploymentName( webroot );
        servletInfo.setResourceManager( new FileResourceManagerWithWebInfMapping( new File( webroot ), 100, new File( webInfPath ) ) );
        servletInfo.addServlet( getDefaultUndertowServlet() );

        WebXmlToUndertowDeploymentReader.readWebXml( new File( webXmlPath ), servletInfo );

        return servletInfo;
    }

    private static PathHandler createPathHandlerFromServletInfo( DeploymentInfo servletInfo ) throws ServletException {
        DeploymentManager manager = defaultContainer().addDeployment( servletInfo );

        manager.deploy();

        return Handlers.path( Handlers.redirect( "/" ) ).addPrefixPath( "/", manager.start() );
    }

    private static Builder getServerBuilder() {
        return Undertow.builder();
    }

    private static URLClassLoader buildClassLoader( String libDirs ) throws IOException {
        List<URL> jarList  = getJarListFromLibDirectories( libDirs );
        int       jarCount = jarList.size();

        System.err.println("Jars found: " + jarCount );
        System.err.println("Lib dirs: " + libDirs );

        return new URLClassLoader( jarList.toArray( new URL[ jarCount ] ) );
    }

    private static List<URL> getJarListFromLibDirectories( String libDirs ) throws IOException {
        List<URL> jarList             = new ArrayList<URL>();
        String[]  libDirArray         = libDirs.split( "," );

        for ( String dirPath : libDirArray ) {
            if ( ".".equals( dirPath ) || "..".equals( dirPath ) )
                continue;

            File dir = new File( dirPath );
            for( File file : dir.listFiles() ) {
                if ( !file.isDirectory() ) {
                    String fileName = file.getAbsolutePath().toLowerCase();
                    if ( fileName.endsWith( ".jar" ) || fileName.endsWith( ".zip" ) ) {
                    System.err.println( fileName );
                        jarList.add( file.toURI().toURL() );
                    }
                }
            }
        }

        return jarList;
    }

    private static ServletInfo getDefaultUndertowServlet() {
        return new ServletInfo(
              io.undertow.servlet.handlers.ServletPathMatches.DEFAULT_SERVLET_NAME
            , DefaultServlet.class
        ).addInitParam( "directory-listing", "true" );
    }
}