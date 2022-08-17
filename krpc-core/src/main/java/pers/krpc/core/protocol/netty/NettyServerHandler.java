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

import com.fasterxml.jackson.databind.json.JsonMapper;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import pers.krpc.core.InterfaceContext;
import pers.krpc.core.InterfaceInfo;
import pers.krpc.core.KrpcApplicationContext;
import pers.krpc.core.protocol.KrpcMsg;
import pers.krpc.core.role.ServerInfo;

import java.lang.reflect.Method;

/**
 * native server端的处理器
 */
@Sharable
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<KrpcMsg> {

    private final KrpcApplicationContext krpcApplicationContext;

    public NettyServerHandler(KrpcApplicationContext krpcApplicationContext) {
        this.krpcApplicationContext = krpcApplicationContext;
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("accepted channel: {}", ctx.channel());
        log.info("accepted channel parent: {}", ctx.channel().parent());
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, KrpcMsg krpcresponse) throws Exception {
        log.info("接收到消息" + krpcresponse);
        InterfaceContext interfaceContext = krpcApplicationContext.getInterfaceContext(krpcresponse.getClassName());
        Object invokeObject = interfaceContext.getObject(krpcresponse.getVersion());
        Method method = Class.forName(krpcresponse.getClassName()).getMethod(krpcresponse.getMethodName(),krpcresponse.getParameterTypes());
        Object o = method.invoke(invokeObject,krpcresponse.getParams());
        krpcresponse.setObject(o);
        // 写入消息
        ctx.writeAndFlush(krpcresponse);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 异常处理
        log.error("出现异常", cause);
        ctx.close();
    }
}