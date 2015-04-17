package org.locee;

import io.undertow.Undertow;
import io.undertow.Undertow.Builder;
import io.undertow.Handlers;
import io.undertow.util.Headers;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.HttpHandler;
import static io.undertow.servlet.Servlets.defaultContainer;

import javax.servlet.ServletException;
import java.io.IOException;

public class LoceeUndertowServer {
	private Undertow          undertowServer;
	private DeploymentManager deploymentManager;
	private int               port;
	private String            host;
	private String            libDirs;
	private String            webroot;
	private String            webXmlPath;
	private String            webInfPath;

	public LoceeUndertowServer( int port, String host, String libDirs, String webroot, String webXmlPath, String webInfPath ) {
		this.port        = port;
		this.host        = host;
		this.libDirs     = libDirs;
		this.webroot     = webroot;
		this.webXmlPath  = webXmlPath;
		this.webInfPath  = webInfPath;
	}

	public void start() throws ServletException, IOException {
		DeploymentInfo servletInfo = LoceeServletBuilder.build( libDirs, webroot, webXmlPath, webInfPath );

		deploymentManager = defaultContainer().addDeployment( servletInfo );
		deploymentManager.deploy();

		HttpHandler httpHandler = deploymentManager.start();
		PathHandler pathHandler = Handlers.path( Handlers.redirect( "/" ) ).addPrefixPath( "/", httpHandler );
		Builder     builder     = Undertow.builder();

		builder.addHttpListener( port, host );
		builder.setHandler( pathHandler );

		undertowServer = builder.build();
		undertowServer.start();
	}

	public void shutdown() throws ServletException {
		deploymentManager.undeploy();
		undertowServer.stop();

		deploymentManager = null;
		undertowServer    = null;
		System.gc();
	}

	public void restart() throws ServletException, IOException {
		shutdown();
		start();
	}
}