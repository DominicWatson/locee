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
import io.undertow.server.*;
import io.undertow.util.Headers;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.resource.FileResourceManager;
import static io.undertow.servlet.Servlets.defaultContainer;
import static io.undertow.servlet.Servlets.deployment;

public class UndertowServerFactory {

    public static Undertow newServer(
          int      port
        , String[] libDirs
        , String   webroot
        , String   webXmlPath
    ) throws ServletException, IOException {
        Builder        builder     = getServerBuilder();
        DeploymentInfo servletInfo = buildServletInfo( libDirs, webroot, webXmlPath );
        PathHandler    pathHandler = createPathHandlerFromServletInfo( servletInfo );

        builder.addHttpListener( port, "localhost" );
        builder.setHandler( pathHandler );

        return builder.build();
    }

    private static DeploymentInfo buildServletInfo(
          String[]       libDirs
        , String         webroot
        , String         webXmlPath
    ) throws IOException {
        DeploymentInfo servletInfo = deployment();
        URLClassLoader classLoader = buildClassLoader( libDirs );

        servletInfo.setContextPath( "" );
        servletInfo.setClassLoader( classLoader );
        servletInfo.setDeploymentName( webroot );
        servletInfo.setResourceManager( new FileResourceManager( new File( webroot ), 100 ) );

        WebXmlToUndertowDeploymentReader.readWebXml( new File( webXmlPath ), servletInfo );

        return servletInfo;
    }

    private static PathHandler createPathHandlerFromServletInfo( DeploymentInfo servletInfo ) throws ServletException {
        DeploymentManager manager = defaultContainer().addDeployment( servletInfo );

        manager.deploy();

        return Handlers.path( Handlers.redirect( "" ) ).addPrefixPath( "", manager.start() );
    }

    private static Builder getServerBuilder() {
        return Undertow.builder();
    }

    private static URLClassLoader buildClassLoader( String[] libDirs ) throws IOException {
        List<URL> jarList  = getJarListFromLibDirectories( libDirs );
        int       jarCount = jarList.size();

        return new URLClassLoader( jarList.toArray( new URL[ jarCount ] ) );
    }

    private static List<URL> getJarListFromLibDirectories( String[] libDirs ) throws IOException {
        List<URL> jarList             = new ArrayList<URL>();
        String    jarFileRegexPattern = "\\.(jar|zip)$";

        for ( String dirPath : libDirs ) {
            if ( ".".equals( dirPath ) || "..".equals( dirPath ) )
                continue;

            File dir = new File( dirPath );
            for( File file : dir.listFiles() ) {
                if ( !file.isDirectory() ) {
                    String fileName = file.getAbsolutePath().toLowerCase();
                    if ( fileName.matches( jarFileRegexPattern ) ) {
                        jarList.add( file.toURI().toURL() );
                    }
                }
            }
        }

        return jarList;
    }
}