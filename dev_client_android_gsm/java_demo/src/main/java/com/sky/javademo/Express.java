package com.sky.javademo;

/**
 * @author yuandunbin782
 * @ClassName Express
 * @Description
 * @date 2020-03-04
 */
public class Express {
    public final static String CITY = "SHANGHAI";
    private int km;
    private String site;

    public Express(int km, String site) {
        this.km = km;
        this.site = site;
    }

    public synchronized void changeKm() {
        this.km = 101;
        notifyAll();
    }

    public synchronized void changeSite() {
        this.site = "BEIJING";
        notifyAll();
    }

    public synchronized void waitKm() throws InterruptedException {
        while (km < 100) {
            wait();
            System.out.println("check km thread [" + Thread.currentThread().getName() + "] is be notifyed");
            System.out.println("the km is" + this.km + ", i will be change db.");
        }
    }

    public synchronized void waitSite() throws InterruptedException {
        while (CITY.equals(site)) {
            wait();
            System.out.println("check site thread [" + Thread.currentThread().getName() + "] is be notifyed");
            System.out.println("the site is" + this.site + ", i will call user.");
        }
    }
}
