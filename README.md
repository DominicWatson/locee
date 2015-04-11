# Locee

Locee (pronounced "Low-key") is a neat GUI application for managing local Lucee web applications.

It is pre-alpha, nothing to see here... yet.

## Building from source

You'll need **NodeJs**, **npm** and **Maven** installed and available in your path. Then, run the following commands from the root of the project:

    npm install
    grunt

Built executables for each target system (e.g. OSX, Linux, Windows) can be found in `/builds/locee/`.

### Refined builds

The grunt file also provides specific tasks for building executables for the three target systems and also a task just for packaging the java code using maven. The tasks are:

    grunt jars
	grunt linux
	grunt win
	grunt osx

## Running locally with nodewebkit

To be able to run the application without needing to build it every time, you will need nodewebkit installed:

    npm install -g nodewebkit@0.11.6

**Note**: **nodewebkit** has become **nw** but we do not want to install **nw** because of incompatibilities with the **node-java** module we are using for the core interactions between js and java.

With nodewebkit installed, run the following to ensure that the jars are built and installed:

    grunt jars

Finally, to run the application without needing to build it, do:

    nodewebkit ./app


