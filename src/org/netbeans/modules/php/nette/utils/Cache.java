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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * LRU cache, thread-safe.
 * @author Radek Ježdík
 */
class Cache<T, S> {

    // @GuardedBy(dataLock)
    private final Map<T, S> data;
    private final ReadWriteLock dataLock = new ReentrantReadWriteLock();

    public Cache(final int capacity) {
        data = new LinkedHashMap<T, S>() {

            private static final long serialVersionUID = 687644375643131L;

            @Override
            protected boolean removeEldestEntry(Entry<T, S> eldest) {
                return size() > capacity;
            }

        };
    }

    public void save(T token, S sequence) {
        dataLock.writeLock().lock();
        try {
            data.put(token, sequence);
        } finally {
            dataLock.writeLock().unlock();
        }
    }

    public S get(T token) {
        dataLock.readLock().lock();
        try {
            return data.get(token);
        } finally {
            dataLock.readLock().unlock();
        }
    }

    public boolean has(T token) {
        dataLock.readLock().lock();
        try {
            return data.containsKey(token);
        } finally {
            dataLock.readLock().unlock();
        }
    }

}
