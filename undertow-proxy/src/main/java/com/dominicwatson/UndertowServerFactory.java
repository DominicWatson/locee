package com.dominicwatson;

import io.undertow.Undertow;
import io.undertow.Undertow.Builder;
import io.undertow.server.*;
import io.undertow.util.Headers;

public class UndertowServerFactory {

    public static Undertow newServer() {
        return buildServer();
    }

    private static Undertow buildServer() {
        Builder builder = getServerBuilder();

        builder.addHttpListener( 3000, "localhost" );
        builder.setHandler( getHelloWorldHttpHandler() );

        return builder.build();
    }

    private static Builder getServerBuilder() {
        return Undertow.builder();
    }

    private static HttpHandler getHelloWorldHttpHandler() {
        return new HttpHandler() {
            public void handleRequest( final HttpServerExchange exchange ) throws Exception {
                exchange.getResponseHeaders().put( Headers.CONTENT_TYPE, "text/plain" );
                exchange.getResponseSender().send( "Hello World" );
            }
        };
    }
}