package pres.krpc.exampe.dto;


import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

/**
 * krpc
 * 2022/8/12 16:42
 *
 * @author wangsicheng
 * @since
 **/
@Data
public class RequestDTO implements Serializable {

    private int num;

    private Date date;

}
