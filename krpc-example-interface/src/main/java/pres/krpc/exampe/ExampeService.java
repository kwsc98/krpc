package pres.krpc.exampe;


import pers.krpc.core.role.ServerInfo;
import pres.krpc.exampe.dto.RequestDTO;
import pres.krpc.exampe.dto.ResponseDTO;

/**
 * krpc
 * 2022/7/26 15:06
 *
 * @author wangsicheng
 **/
public interface ExampeService {
     ResponseDTO doRun(RequestDTO requestDTO);
}
