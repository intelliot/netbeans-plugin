/*
 *  The MIT License
 *
 *  Copyright (c) 2010 Radek Ježdík <redhead@email.cz>, Ondřej Brejla <ondrej@brejla.cz>
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package org.netbeans.modules.php.nette.utils;

import org.netbeans.junit.NbTestCase;

import static junit.framework.Assert.*;

/**
 * Unit test for Cache.
 */
public class CacheTest extends NbTestCase {

    public CacheTest(String name) {
        super(name);
    }


    public void testLruCache() {
        Cache<Integer, Integer> cache = new Cache<Integer, Integer>(2);
        putAndAssert(cache, 1, 2, 3);
        assertNull("Oldest value should not be found already", cache.get(1));
        assertEquals("Second entry should be found", 2, cache.get(2).intValue());
        assertEquals("Third entry should be found", 3, cache.get(3).intValue());
    }

    private void putAndAssert(Cache<Integer, Integer> cache, int... values) {
        for (int value : values) {
            cache.save(value, value);
            assertEquals(Integer.valueOf(value).intValue(), cache.get(value).intValue());
        }
    }

}
