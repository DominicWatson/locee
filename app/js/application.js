
window.locee = ( function( $, serverFactory ) {
    'use strict';

    // temporary hack code
    var $btn = $( "#server-control-button" )
      , status = "stopped"
      , appServer = serverFactory.newServer( 4040, "/home/dom/apps/lucee-4.5/lib", "/home/dom/code/lucee-docs/server", "/home/dom/web.xml", "/home/dom/code/lucee-docs/server/WEB-INF" )
      , setStatus, startServer, stopServer, statuses;

    statuses = {
    	stopped  : { icon : "fa-play"      , btnclass : "btn-primary", message : "Start application", btnEnabled : true  },
    	started  : { icon : "fa-stop"      , btnclass : "btn-danger" , message : "Stop application" , btnEnabled : true  },
    	starting : { icon : "fa-ellipsis-h", btnclass : "btn-default", message : "Starting"         , btnEnabled : false },
    	stopping : { icon : "fa-ellipsis-h", btnclass : "btn-default", message : "Stopping"         , btnEnabled : false }
    };

    setStatus = function( s ){
    	$btn.removeClass( statuses[ status ].btnclass );
    	$btn.addClass( statuses[ s ].btnclass );
    	$btn.html( statuses[ s ].message )
    	$btn.prop( "disabled", !statuses[ s ].message );

    	status = s;
    };

    startServer = function(){
    	setStatus( "starting" );
    	appServer.start( function(){
    		setStatus( "started" );
    	})
    };

    stopServer = function(){
    	setStatus( "stopping" );
    	appServer.shutdown( function(){
    		setStatus( "stopped" );
    	} );
    }


    $btn.on( "click", function( e ){
    	e.preventDefault();

    	switch( status ){
    		case "stopped":
    			startServer();
    		break;
    		case "started":
    			stopServer();
    	}

    } );
    // end temporary hack code

})( jQuery, window.serverFactory );