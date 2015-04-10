window.serverFactory = ( function( java ){
    "use strict";

    java.classpath.push( "./lib/undertow-proxy-1.0-SNAPSHOT-jar-with-dependencies.jar" );

    var ServerFactory = function (){};

    ServerFactory.prototype.newServer = function(){
        return java.callStaticMethodSync( "com.dominicwatson.UndertowServerFactory", "newServer" );
    };

    return new ServerFactory;

} )( require( 'java' ) );