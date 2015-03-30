window.settings = ( function(){
	var ApplicationSettings = function (){};

	ApplicationSettings.prototype.getSetting = function( settingName ){
		return JSON.parse( localStorage.getItem( settingName ) || '""' );
	};

	ApplicationSettings.prototype.saveSetting = function( settingName, settingValue ) {
		localStorage.setItem( settingName, JSON.stringify( settingValue ) )
	};

	ApplicationSettings.prototype.getApplicationsDirectory = function(){
		return this.getSetting( "applicationsDirectory" );
	};

	ApplicationSettings.prototype.setApplicationsDirectory = function( applicationsDirectory ){
		return this.saveSetting( "applicationsDirectory", applicationsDirectory );
	};

	return new ApplicationSettings();
} )();