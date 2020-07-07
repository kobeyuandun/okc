package com.sky.javademo.hashcode;

/**
 * @author yuandunbin
 * @date 2020/6/29
 */
public class HashCodeClass {
    private String str0;
    private double dou0;
    private int int0;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof HashCodeClass) {
            HashCodeClass hcc = (HashCodeClass) obj;
            if (hcc.str0.equals(this.str0) &&
                    hcc.dou0 == this.dou0 &&
                    hcc.int0 == this.int0) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    public static void main(String[] args) {
        System.out.println(new HashCodeClass().hashCode());
        System.out.println(new HashCodeClass().hashCode());
        System.out.println(new HashCodeClass().hashCode());
        System.out.println(new HashCodeClass().hashCode());
        System.out.println(new HashCodeClass().hashCode());
        System.out.println(new HashCodeClass().hashCode());
    }
}
