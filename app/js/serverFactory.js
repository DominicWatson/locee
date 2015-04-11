window.serverFactory = ( function( java ){
    "use strict";

    var ServerFactory = function (){
    	java.classpath.push( "./lib/locee-undertow-server-factory-1.0-SNAPSHOT-jar-with-dependencies.jar" );
    };

    ServerFactory.prototype.newServer = function( port, libDirList, webroot, webXmlPath, webInfDir ){
        return java.callStaticMethodSync(
              "org.locee.LoceeUndertowServerFactory"
            , "newServer"
            , port
            , libDirList
            , webroot
            , webXmlPath
            , webInfDir
        );
    };

    return new ServerFactory;

} )( require( 'java' ) );