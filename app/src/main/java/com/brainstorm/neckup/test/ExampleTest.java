package com.brainstorm.neckup.test;

import android.test.InstrumentationTestCase;

/**
 * Created by kerwin on 15-5-21.
 */
public class ExampleTest extends InstrumentationTestCase {
    public void test() throws Exception {
        final int expected = 1;
        final int reality = 1;
        assertEquals(expected, reality);
    }
}