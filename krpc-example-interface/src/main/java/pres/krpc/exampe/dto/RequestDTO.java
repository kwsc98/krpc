package pres.krpc.exampe.dto;


import lombok.Getter;

import java.util.Date;

/**
 * krpc
 * 2022/8/12 16:42
 *
 * @author wangsicheng
 * @since
 **/
@Getter
public class RequestDTO {

    private String paprm1 = "很大声的撒的谎";

    private Date date = new Date();
}
