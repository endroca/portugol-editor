/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ResourceWaitQueue {

    private final ReentrantLock lock = new ReentrantLock(true); // use a FIFO lock.
    
    private final Condition goodtogo = lock.newCondition();
    private int releasecount = 0; // how many threads should be released.

    public final void await() throws InterruptedException {
        lock.lock();
        try {
            while (releasecount == 0) {
                goodtogo.await();
            }
            // releasecount > 0 and we were signalled.
            if (--releasecount > 0) {
                // reduce the releasdecount, but there's still another
                // thread that should be released.
                goodtogo.signal();
                // when that other thread releases, if there's still more
                // to be released, it can do that for us.
            };
        } finally {
            lock.unlock();
        }
    }

    public final void release() {
        lock.lock();
        try {
            // indicate there is work to do
            releasecount++;
            // signal the condition is true.... (only signal, not signalAll())
            goodtogo.signal();
        } finally {
            lock.unlock();
        }
    }
    
    
    public void interrupt(){
        try {
            lock.lockInterruptibly();
        } catch (InterruptedException ex) {
            Logger.getLogger(ResourceWaitQueue.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}