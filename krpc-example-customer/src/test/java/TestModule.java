import pers.krpc.core.InterfaceContext;
import pers.krpc.core.InterfaceContextDetails;
import pers.krpc.core.InterfaceInfo;
import pers.krpc.core.KrpcApplicationContext;
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
        try {

            KrpcApplicationContext krpcApplicationContext = new KrpcApplicationContext();
            ExampeService exampeService = (ExampeService) krpcApplicationContext
                    .getService(InterfaceInfo.build()
                            .setInterfaceClass(ExampeService.class)
                            .setVersion("1.0.0")
                            .setTimeout(1000));
        } catch (Exception e) {

        }

    }

}
