package org.locee;

import java.io.File;

import io.undertow.server.handlers.resource.FileResource;
import io.undertow.server.handlers.resource.FileResourceManager;
import io.undertow.server.handlers.resource.Resource;

public class FileResourceManagerWithWebInfMapping extends FileResourceManager {
	private File WEBINF = null;

	public FileResourceManagerWithWebInfMapping( File base, long transferMinSize, File webInfDir) {
		super( base, transferMinSize );
		this.WEBINF = webInfDir;
	}

	public Resource getResource(String path) {
		if ( path.startsWith( "/WEB-INF" ) ) {
			File reqFile = new File( WEBINF, path.replace( "/WEB-INF", "" ) );
			return new FileResource( reqFile, this, path );
		}
		return super.getResource(path);
	}
}
