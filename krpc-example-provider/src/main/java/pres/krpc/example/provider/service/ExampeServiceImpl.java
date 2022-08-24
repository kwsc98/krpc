package pres.krpc.example.provider.service;


import pres.krpc.exampe.ExampeService;
import pres.krpc.exampe.dto.RequestDTO;
import pres.krpc.exampe.dto.ResponseDTO;
import pres.krpc.spring.annotation.KrpcService;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * krpc
 * 2022/8/16 15:23
 *
 * @author wangsicheng
 **/
@KrpcService(version = "1.0.1")
public class ExampeServiceImpl implements ExampeService {

    @Override
    public ResponseDTO doRun(RequestDTO requestDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setDate(new Date(requestDTO.getDate().getTime() + (long) requestDTO.getNum() * 60 * 60 * 1000));
        return responseDTO;
    }
}
