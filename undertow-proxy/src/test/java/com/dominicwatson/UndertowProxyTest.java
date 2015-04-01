package com.dominicwatson;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class UndertowProxyTest extends TestCase {

    public UndertowProxyTest( String testName ) {
        super( testName );
    }

    public static Test suite() {
        return new TestSuite( UndertowProxyTest.class );
    }

    public void testUndertowProxy() {
        assertTrue( true );
    }

}
