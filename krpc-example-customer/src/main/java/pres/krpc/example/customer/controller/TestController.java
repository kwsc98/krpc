package pres.krpc.example.customer.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pres.krpc.exampe.ExampeService;
import pres.krpc.exampe.dto.RequestDTO;
import pres.krpc.exampe.dto.ResponseDTO;
import pres.krpc.spring.annotation.KrpcResource;

import java.util.Date;


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

    @KrpcResource
    private ExampeService exampeService;

    @PostMapping("/test")
    public ResponseDTO test() {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setData("哈哈哈哈");
        requestDTO.setDate(new Date());
        return exampeService.doRun(requestDTO);
    }

}
