package org.locee;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class LoceeUndertowServerFactoryTest extends TestCase {

    public LoceeUndertowServerFactoryTest( String testName ) {
        super( testName );
    }

    public static Test suite() {
        return new TestSuite( LoceeUndertowServerFactoryTest.class );
    }

    public void testUndertowServerFactory() {
        assertTrue( true );
    }

}
