/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.app.ldn.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 *
 */
public class ContextTest {

    @Test
    public void testFromDataverseContext() {
        Context fromDataverse = new Context();

        fromDataverse.setIetfCiteAs("Test");
        assertEquals("Test", fromDataverse.getIetfCiteAs());
    }

    @Test
    public void testToDataverseContext() {
        Context toDataverse = new Context();

        toDataverse.setIetfCiteAs("Test");
        assertEquals("Test", toDataverse.getIetfCiteAs());
    }

}
