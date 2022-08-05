import pers.krpc.core.*;
import pers.krpc.core.registry.RegistryBuilderFactory;
import pers.krpc.core.registry.RegistryClientInfo;
import pers.krpc.core.registry.RegistryService;
import pres.krpc.exampe.ExampeService;

/**
 * krpc
 * 2022/7/27 17:28
 *
 * @author wangsicheng
 * @since
 **/
public class TestModule {

    public static void main(String[] args) {
        

            KrpcApplicationContext krpcApplicationContext = KrpcBuilderFactory.builder()
                    .setRegistryBuilderFactory(
                            RegistryBuilderFactory.builder(RegistryClientInfo.build().setIp("127.0.0.1:2181").setClient(RegistryClientInfo.Client.Zookeeper)))
                    .setPort("8081")
                    .build();
            ExampeService exampeService = (ExampeService) krpcApplicationContext
                    .getService(InterfaceInfo.build()
                            .setInterfaceClass(ExampeService.class)
                            .setVersion("1.0.0")
                            .setTimeout(1000));
            System.out.println();


    }

}
