package pres.krpc.example.provider.service;


import org.springframework.stereotype.Service;
import pers.krpc.core.role.ServerInfo;
import pres.krpc.exampe.ExampeService;

/**
 * krpc
 * 2022/8/16 15:23
 *
 * @author wangsicheng
 * @since
 **/
@Service
public class ExampeServiceImpl implements ExampeService {
    @Override
    public ServerInfo doRun(String str1) {
        return new ServerInfo().setIp("测试成功").setPort(str1);
    }
}
