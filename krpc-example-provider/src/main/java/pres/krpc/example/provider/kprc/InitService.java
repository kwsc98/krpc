package pres.krpc.example.provider.kprc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.krpc.core.InterfaceInfo;
import pers.krpc.core.KrpcApplicationContext;
import pres.krpc.exampe.ExampeService;
import pres.krpc.example.provider.service.ExampeServiceImpl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * krpc
 * 2022/8/16 15:29
 *
 * @author wangsicheng
 * @since
 **/
@Component
public class InitService {

    @Autowired
    private KrpcApplicationContext krpcApplicationContext;

    @Resource
    private ExampeServiceImpl exampeServiceImpl;

    @PostConstruct
    public void init(){
        krpcApplicationContext.setService(InterfaceInfo.build().setInterfaceClass(ExampeService.class).setVersion("1.0.0").setTimeout(1000),exampeServiceImpl);
    }

}
