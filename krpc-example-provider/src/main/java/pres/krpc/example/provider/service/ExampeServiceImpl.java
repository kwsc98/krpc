package pres.krpc.example.provider.service;


import pres.krpc.exampe.ExampeService;
import pres.krpc.exampe.dto.RequestDTO;
import pres.krpc.exampe.dto.ResponseDTO;
import pres.krpc.spring.annotation.KrpcService;

/**
 * krpc
 * 2022/8/16 15:23
 *
 * @author wangsicheng
 * @since
 **/
@KrpcService
public class ExampeServiceImpl implements ExampeService {

    @Override
    public ResponseDTO doRun(RequestDTO requestDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(requestDTO.getData()+"doRun");
        responseDTO.setDate(requestDTO.getDate());
        return responseDTO;
    }
}
