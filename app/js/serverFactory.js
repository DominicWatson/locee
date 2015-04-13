window.serverFactory = ( function( java ){
    "use strict";

    var ServerFactory = function (){
    	java.classpath.push( "./lib/locee-undertow-server-factory.jar" );
    };

    ServerFactory.prototype.newServer = function( port, libDirList, webroot, webXmlPath, webInfDir ){
        return java.newInstanceSync(
              "org.locee.LoceeUndertowServer"
            , port
            , "localhost"
            , libDirList
            , webroot
            , webXmlPath
            , webInfDir
        );
    };

    return new ServerFactory;

} )( require( 'java' ) );