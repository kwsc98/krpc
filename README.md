# krpc

实现一个基于netty单路复用网络模型的rpc框架，支持spring-boot启动，支持zookeeper，nacos注册中心。<br/>

## 以SpringBoot的方式启动
### 接口示例
 ```java
public interface ExampeService {
    ResponseDTO doRun(RequestDTO requestDTO);
}
 ``` 
### Customer
配置application.properties <br/>
krpc.registeredPath = nacos://114.116.3.221:8848
 ```java
@KrpcResource(version = "1.0.1",timeout = 1000)
private ExampeService exampeService;
 ``` 

### Provider
配置application.properties <br/>
krpc.registeredPath = nacos://114.116.3.221:8848 <br/>
krpc.port = 8082
 ```java
@KrpcService(version = "1.0.1")
public class ExampeServiceImpl implements ExampeService {
    
    @Override
    public ResponseDTO doRun(RequestDTO requestDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setDate(new Date(requestDTO.getDate().getTime() + (long) requestDTO.getNum() * 60 * 60 * 1000));
        return responseDTO;
    }
}
 ``` 
