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

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import pers.krpc.core.InterfaceContext;
import pers.krpc.core.KrpcApplicationContext;
import pers.krpc.core.protocol.KrpcMsg;


import java.lang.reflect.Method;

/**
 * native server端的处理器
 *
 * @author kwsc98
 */
@Sharable
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<KrpcMsg> {

    private final KrpcApplicationContext krpcApplicationContext;

    public NettyServerHandler(KrpcApplicationContext krpcApplicationContext) {
        this.krpcApplicationContext = krpcApplicationContext;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("Channel连接建立: [{}] parent [{}]", ctx.channel(), ctx.channel().parent());
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, KrpcMsg krpcRequest) throws Exception {
        log.debug("服务端获取请求:[{}]",krpcRequest);
        InterfaceContext interfaceContext = krpcApplicationContext.getInterfaceContext(krpcRequest.getClassName());
        Object invokeObject = interfaceContext.getObject(krpcRequest.getVersion());
        Method method = Class.forName(krpcRequest.getClassName()).getMethod(krpcRequest.getMethodName(), krpcRequest.getParameterTypes());
        Object o = method.invoke(invokeObject, krpcRequest.getParams());
        krpcRequest.setObject(o);
        ctx.writeAndFlush(krpcRequest);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 异常处理
        log.error("出现异常", cause);
        ctx.close();
    }
}
