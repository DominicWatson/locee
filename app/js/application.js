window.locee = ( function( sf, $ ) {
    'use strict';

    var server            = sf.newServer()
      , $serverControlBtn = $( '#server-control' );

	$serverControlBtn.data( "running", false );

	$serverControlBtn.on( "click", function(){
		if ( $serverControlBtn.data( "running" ) ) {
			server.stop();
			$serverControlBtn.removeClass( "btn-danger" );
			$serverControlBtn.addClass( "btn-success" );
			$serverControlBtn.html( "Start server" );
			$serverControlBtn.data( "running", false )
		} else {
			server.start();
			$serverControlBtn.removeClass( "btn-success" );
			$serverControlBtn.addClass( "btn-danger" );
			$serverControlBtn.html( "Stop server" );
			$serverControlBtn.data( "running", true )
		}
	} );

})( window.serverFactory, jQuery );