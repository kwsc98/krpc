/*
 * Copyright 2022 learn-netty4 Project
 *
 * The learn-netty4 Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package pers.krpc.core.protocol.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;


/**
 * Netty客户端
 * @author kwsc98
 */
@Slf4j
public final class NettyClient {

    private final Bootstrap bootstrap;

    NettyClient() {
        EventLoopGroup group = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();
        this.bootstrap.group(group).channel(NioSocketChannel.class).handler(new NettyInitializer<>(new NettyClientHandler()));
    }

    public Channel getChannel(String host, int port) {
        try {
            Channel channel = this.bootstrap.connect(host, port).sync().channel();
            log.info("client channel: {}", channel);
            return channel;
        } catch (Exception e) {
            log.error("Netty建立连接异常", e);
            throw new RuntimeException();
        }
    }
}
