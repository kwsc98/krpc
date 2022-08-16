package pres.krpc.example.customer.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.krpc.core.InterfaceInfo;
import pers.krpc.core.KrpcApplicationContext;
import pers.krpc.core.role.ServerInfo;
import pres.krpc.exampe.ExampeService;


/**
 * krpc
 * 2022/8/3 11:34
 *
 * @author wangsicheng
 * @since
 **/
@RestController
@Slf4j
public class TestController {

    @Autowired
    private KrpcApplicationContext krpcApplicationContext;

    @PostMapping("/test")
    public ServerInfo test() throws JsonProcessingException {
        ExampeService exampeService = krpcApplicationContext.getService(InterfaceInfo.build().setInterfaceClass(ExampeService.class).setVersion("1.0.0").setTimeout(1000));
        ServerInfo serverInfo = exampeService.doRun("dsd");
        log.info(String.valueOf(serverInfo));
        return serverInfo;
    }

}
