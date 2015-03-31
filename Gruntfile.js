module.exports = function (grunt) {
	'use strict';

	grunt.loadNpmTasks('grunt-node-webkit-builder');

	grunt.registerTask( 'default', [ 'nodewebkit' ] );

	grunt.initConfig( {
		nodewebkit: {
			options: {
				platforms: [ 'win', 'osx', 'linux' ],
				buildDir: './builds'
			},
			src: ['./app/**/*'] // Your node-webkit app
		}
	} );
};