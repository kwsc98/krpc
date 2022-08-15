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

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import pers.krpc.core.protocol.codec.KrpcDecoder;
import pers.krpc.core.protocol.codec.KrpcEncoder;

/**
 * @author kwsc98
 */
public class NettyInitializer<T> extends ChannelInitializer<SocketChannel> {

    private final SimpleChannelInboundHandler<T> channelInboundHandler;

    public NettyInitializer(SimpleChannelInboundHandler<T> channelInboundHandler) {
        this.channelInboundHandler = channelInboundHandler;
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 添加行分割器
        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        // 添加String Decoder和String Encoder,用来进行字符串的转换
        pipeline.addLast(new KrpcDecoder());
        pipeline.addLast(new KrpcEncoder());
        // 最后添加真正的处理器
        pipeline.addLast(this.channelInboundHandler);
    }
}
