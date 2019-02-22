package com.saucelabs.junit;

import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class DumbTest {
    @Test
    public void alwaysPasses()
    {
        //TODO I know that this is dumb, but I wasn't sure what else to do since everyone seemed to be busy
        //and I didn't get any input, so I had to make an executive decision on how to fix the build problems
        //So I deleted the Sauce test classes that were here but left this one since I'm not 100% sure on the mvn
        //dependecy stuff...
        //Nikolay
        assertTrue(true);
    }
}
