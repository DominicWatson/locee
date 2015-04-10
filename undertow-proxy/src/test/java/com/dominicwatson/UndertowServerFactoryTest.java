package com.dominicwatson;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class UndertowServerFactoryTest extends TestCase {

    public UndertowServerFactoryTest( String testName ) {
        super( testName );
    }

    public static Test suite() {
        return new TestSuite( UndertowServerFactoryTest.class );
    }

    public void testUndertowServerFactory() {
        assertTrue( true );
    }

}
