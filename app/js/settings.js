window.settings = ( function( $ ){
	var ApplicationSettings = function (){
		this.setupSettingsFormBehaviour();
	};

	ApplicationSettings.prototype.setupSettingsFormBehaviour = function(){
		var appSettings = this;

		appSettings.$applicationsDirectoryInput = $( '#applicationDirectory' );
		appSettings.$saveSettingsButton         = $( '#save-settings-button' );

		appSettings.$applicationsDirectoryInput.val( appSettings.getApplicationsDirectory() );
		appSettings.$saveSettingsButton.click( function( e ){
			e.preventDefault();
			appSettings.saveSettings()
		} );
	};

	ApplicationSettings.prototype.getSetting = function( settingName ){
		return JSON.parse( localStorage.getItem( settingName ) || '""' );
	};

	ApplicationSettings.prototype.saveSetting = function( settingName, settingValue ) {
		localStorage.setItem( settingName, JSON.stringify( settingValue ) )
	};

	ApplicationSettings.prototype.saveSettings = function() {
		this.setApplicationsDirectory( this.$applicationsDirectoryInput.val() );
	};

	ApplicationSettings.prototype.getApplicationsDirectory = function(){
		return this.getSetting( "applicationsDirectory" );
	};

	ApplicationSettings.prototype.setApplicationsDirectory = function( applicationsDirectory ){
		return this.saveSetting( "applicationsDirectory", applicationsDirectory );
	};

	return new ApplicationSettings();

} )( jQuery );