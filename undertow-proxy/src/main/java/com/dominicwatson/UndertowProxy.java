package com.dominicwatson;

import io.undertow.Undertow;
import io.undertow.Undertow.Builder;
import io.undertow.server.*;
import io.undertow.util.Headers;

public class UndertowProxy {
    public static void main( final String[] args ) {
        Undertow server = buildServer();

        server.start();
    }

    private static HttpHandler getHttpHandler() {
    	return new HttpHandler() {
            public void handleRequest( final HttpServerExchange exchange ) throws Exception {
                exchange.getResponseHeaders().put( Headers.CONTENT_TYPE, "text/plain" );
                exchange.getResponseSender().send( "Hello World" );
            }
        };
    }

    private static Undertow buildServer() {
        Builder serverBuilder = Undertow.builder()
            .addHttpListener( 3000, "localhost" )
            .setHandler( getHttpHandler() );

        return serverBuilder.build();
    }
}