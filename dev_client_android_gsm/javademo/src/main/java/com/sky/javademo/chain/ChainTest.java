package com.sky.javademo.chain;

/**
 * @author yuandunbin
 * @date 2020/7/10
 * 责任链模式
 */
class ChainTest {
    /**
     * 传送者
     */
    public abstract class Transmit {
        protected Transmit nextTransmit;

        abstract boolean request(String msg);

        public void setNextTransmit(Transmit transmit) {
            nextTransmit = transmit;
        }
    }

    public class Kobe extends Transmit {
        @Override
        boolean request(String msg) {
            System.out.println("kobe说no");
            boolean request = nextTransmit.request(msg);
            return request;
        }
    }

    public class Onear extends Transmit {
        @Override
        boolean request(String msg) {
            System.out.println("Onear说no");
            boolean request = nextTransmit.request(msg);
            return request;
        }
    }

    public class qiaodan extends Transmit {
        @Override
        boolean request(String msg) {
            System.out.println("qiaodan说yes");

            return true;
        }
    }

    public Transmit getTransmit() {
        Kobe kobe = new Kobe();
        Onear onear = new Onear();
        qiaodan qiaodan = new qiaodan();
        kobe.setNextTransmit(onear);
        onear.setNextTransmit(qiaodan);
        return kobe;
    }

    public static void main(String[] args) {
        Transmit transmit = new ChainTest().getTransmit();
        transmit.request("可以说yes吗");
    }
}
