module.exports = function (grunt) {
	'use strict';

	grunt.loadNpmTasks('grunt-node-webkit-builder');
	grunt.loadNpmTasks('grunt-exec');

	grunt.registerTask( 'default', [ 'exec:install_jar', 'exec:nwgyp', 'nodewebkit' ] );
	grunt.registerTask( 'jars', [ 'exec:install_jar' ] );
	grunt.registerTask( 'linux', [ 'nodewebkit:linux' ] );
	grunt.registerTask( 'win', [ 'nodewebkit:win' ] );
	grunt.registerTask( 'osx', [ 'nodewebkit:osx' ] );
	grunt.registerTask( 'nwgyp', [ 'exec:nwgyp' ] );

	grunt.initConfig( {
		nodewebkit: {
			linux: { src: ['./app/**/*'], options : { platforms : [ 'linux' ], buildDir: './builds', version: '0.11.6' } },
			win  : { src: ['./app/**/*'], options : { platforms : [ 'win'   ], buildDir: './builds', version: '0.11.6' } },
			osx  : { src: ['./app/**/*'], options : { platforms : [ 'osx'   ], buildDir: './builds', version: '0.11.6' } }
		},

		exec : {
			install_jar : {
				command : 'mvn package',
				cwd     : './undertowServerFactory/',
				stdout  : true,
				stderr  : true
			},
			nwgyp : {
				command : 'nw-gyp configure --target=0.11.6 && nw-gyp build',
				cwd     : './app/node_modules/java',
				stdout  : true,
				stderr  : true
			}
		}
	} );
};