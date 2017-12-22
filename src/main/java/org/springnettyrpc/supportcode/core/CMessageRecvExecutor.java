package org.springnettyrpc.supportcode.core;

import com.google.common.util.concurrent.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springnettyrpc.supportcode.model.CMessageKeyVal;
import org.springnettyrpc.supportcode.model.CMessageRequest;
import org.springnettyrpc.supportcode.model.CMessageResponse;
import org.springnettyrpc.supportcode.support.CRpcSerializeProtocol;

import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;

/**
 * Rpc服务器执行模块
 * @author ZHS
 * @create 2017-12-15 10:56
 */
public class CMessageRecvExecutor  implements ApplicationContextAware, InitializingBean {

    private String serverAddress;
    //默认JKD本地序列化协议
    private CRpcSerializeProtocol serializeProtocol = CRpcSerializeProtocol.JDKSERIALIZE;

    private final static String DELIMITER = ":";

    private Map<String, Object> handlerMap = new ConcurrentHashMap<String, Object>();

    private static ListeningExecutorService threadPoolExecutor;

    public CMessageRecvExecutor(String serverAddress, String serializeProtocol) {
        this.serverAddress = serverAddress;
        this.serializeProtocol = Enum.valueOf(CRpcSerializeProtocol.class, serializeProtocol);
    }

    public static void submit(Callable<Boolean> task, ChannelHandlerContext ctx, CMessageRequest request, CMessageResponse response) {
        if (threadPoolExecutor == null) {
            synchronized (CMessageRecvExecutor.class) {
                if (threadPoolExecutor == null) {
                    threadPoolExecutor = MoreExecutors.listeningDecorator((ThreadPoolExecutor) CRpcThreadPool.getExecutor(16, -1));
                }
            }
        }

        ListenableFuture<Boolean> listenableFuture = threadPoolExecutor.submit(task);
        //Netty服务端把计算结果异步返回
        Futures.addCallback(listenableFuture, new FutureCallback<Boolean>() {
            public void onSuccess(Boolean result) {
                ctx.writeAndFlush(response).addListener(new ChannelFutureListener() {
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        System.out.println("RPC Server Send message-id respone:" + request.getMessageId());
                    }
                });
            }

            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        }, threadPoolExecutor);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        try {
            CMessageKeyVal keyVal = (CMessageKeyVal) applicationContext.getBean(Class.forName("org.springnettyrpc.supportcode.model.CMessageKeyVal"));
            Map<String, Object> rpcServiceObject = keyVal.getMessageKeyVal();

            Set s = rpcServiceObject.entrySet();
            Iterator<Map.Entry<String, Object>> it = s.iterator();
            Map.Entry<String, Object> entry;

            while (it.hasNext()) {
                entry = it.next();
                handlerMap.put(entry.getKey(), entry.getValue());
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CMessageRecvExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        //netty的线程池模型设置成主从线程池模式，这样可以应对高并发请求
        //当然netty还支持单线程、多线程网络IO模型，可以根据业务需求灵活配置
        ThreadFactory threadRpcFactory = new CNamedThreadFactory("NettyRPC ThreadFactory");

        //方法返回到Java虚拟机的可用的处理器数量
        int parallel = Runtime.getRuntime().availableProcessors() * 2;

        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup(parallel, threadRpcFactory, SelectorProvider.provider());

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
                    .childHandler(new CMessageRecvChannelInitializer(handlerMap).buildRpcSerializeProtocol(serializeProtocol))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            String[] ipAddr = serverAddress.split(CMessageRecvExecutor.DELIMITER);

            if (ipAddr.length == 2) {
                String host = ipAddr[0];
                int port = Integer.parseInt(ipAddr[1]);
                ChannelFuture future = bootstrap.bind(host, port).sync();
                System.out.printf("[author tangjie] Netty RPC Server start success!\nip:%s\nport:%d\nprotocol:%s\n\n", host, port, serializeProtocol);
                future.channel().closeFuture().sync();
            } else {
                System.out.printf("[author tangjie] Netty RPC Server start fail!\n");
            }
        } finally {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }

}
