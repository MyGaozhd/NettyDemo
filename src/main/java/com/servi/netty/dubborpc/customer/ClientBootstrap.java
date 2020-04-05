package com.servi.netty.dubborpc.customer;

import com.servi.netty.dubborpc.netty.NettyClient;
import com.servi.netty.dubborpc.publicinterface.HelloService;

public class ClientBootstrap {


    //这里定义协议头
    public static final String providerName = "HelloService#hello#";
    public static HelloService service = null;

    public static void main(String[] args) throws Exception {

        //创建一个消费者
        NettyClient customer = new NettyClient();

        //创建代理对象
        service = (HelloService) customer.getBean(HelloService.class, providerName);

        for (int i = 0; i < 10; i++) {
            new Thread(new TRunnable(i)).start();
        }

    }

    static class TRunnable implements Runnable {

        int i = 0;

        public TRunnable(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            for (; ; ) {
                try {
                    Thread.sleep(2 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //通过代理对象调用服务提供者的方法(服务)
                String res = service.hello("i=" + i);
                System.out.println("i=" + i + " res= " + res);
            }
        }
    }
}
