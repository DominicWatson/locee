var tray, trayMenu; // to prevent garbage collection issues

( function( gui, pkg ){
	var win         = gui.Window.get()
	  , isMinimized = false
	  , toggleVisibility;

	win.on( 'minimize', function(){ isMinimized = true;  } );
	win.on( 'restore' , function(){ isMinimized = false; } );

	toggleVisibility = function(){
		isMinimized ? win.restore() : win.minimize();
	};

    trayMenu = new gui.Menu();
    trayMenu.append(new gui.MenuItem({
        label: 'Show/Hide window',
        click: toggleVisibility
    }));
    trayMenu.append(new gui.MenuItem({
        type: 'separator'
    }));
    trayMenu.append(new gui.MenuItem({
        label: 'Exit',
        click: function () { process.exit(); }
    }));

	tray = new gui.Tray( {
          title : pkg.window.title
        , icon  : pkg.window.icon
        , click : toggleVisibility
        , menu  : trayMenu
    });

} )( require( 'nw.gui' ), require( "./package.json" ) );