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
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;
import pers.krpc.core.protocol.KrpcMsg;

import java.util.Objects;

/**
 * 客户端处理器
 */
@Sharable
@Slf4j
public class NettyClientHandler extends SimpleChannelInboundHandler<KrpcMsg> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, KrpcMsg krpcMsg) throws Exception {
        log.info("接收到消息" + krpcMsg);
        Promise<Object> promise = NettyApplicationContext.getMSG_CACHE().getIfPresent(krpcMsg.getUniqueIdentifier());
        if (Objects.nonNull(promise)) {
            promise.setSuccess(krpcMsg.getObject());
        }
        log.info("client accepted channel: {}", ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 异常处理
        log.error("出现异常", cause);
        ctx.close();
    }
}
