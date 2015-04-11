# Locee

Locee (pronounced "Low-key") is a neat GUI application for managing local Lucee web applications.

It is pre-alpha, so nothing to see here yet, and no prebuilt binaries are available to play with. You can run or build from source however, should you be truly curious or interested in contributing.

## Building from source

### Prerequisites

You'll need [Node.js](https://nodejs.org/), [NPM](https://www.npmjs.com/) and [Maven](https://maven.apache.org/) installed and available in your path. You will also need a JDK installed, at least 1.6. Finally, you will need to have [grunt-cli](https://github.com/gruntjs/grunt-cli) and [nw-cyg](https://github.com/nwjs/nw-gyp) globally installed with NPM:

    npm install -g grunt-cli
    npm install -g nw-gyp

### Dependencies

To install the node managed dependencies for the project, run the following from the root of the project:

    npm install
    cd ./app
    npm install

### Running the build

Running the build is done via [Grunt](http://gruntjs.com/) and can be as simple as running the following command from the root of the project:

    grunt

Built executables for each target system (e.g. OSX, Linux, Windows) can then be found (and executed) in `/builds/locee/`.

#### Refined builds

The grunt file also provides specific tasks for building executables for the three target systems, compiling native node-webkit modules and for packaging up the java source:

    grunt nwgyp // compiles native node modules (requires nw-gyp)
    grunt jars  // compiles and installs java source
	grunt linux // builds linux executables of the application
	grunt win   // builds windows executables of the application
	grunt osx   // builds osx executables of the application
    grunt       // executes all of the tasks above

### Troubleshooting

With the various dependencies, there are a few things that can go wrong with the build; it is also only tested so far in a Linux environment.

The most likely culprit will be problems to do with **nw-gyp** due to it having dependencies that are not automatically installed. Please visit the [nw-gyp website](https://github.com/nwjs/nw-gyp) for troubleshooting **nw-gyp**.

If you run into any other problems, please raise an issue here and we'll try to keep this section of the README up to date.


## Running locally with node-webkit

To be able to run the application without needing to build it every time, you will need [node-webkit](https://github.com/nwjs/nw.js) installed:

    npm install -g nodewebkit@0.11.6

> **IMPORTANT**: **node-webkit** has become **NW.js** but we need to install a version prior to this change because of incompatibilities with the [node-java](https://github.com/joeferner/node-java) module we are using for the core interactions between js and java.

With node-webkit installed, run the following command once to ensure that the jars are built and installed and that the native node modules are compiled (if you have not already done this):

    grunt jars && grunt nwgyp

Finally, to run the application without needing to build it, do:

    nodewebkit ./app


