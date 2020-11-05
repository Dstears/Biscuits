package com.mbiscuit.core.progress.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class DiaryDTO {

    private Long id;

    /**
     * 是否比昨天有提高，0-没有，1-有
     */
    private Integer hasProgress;

    /**
     * 哪里提升了
     */
    private String progressDetail;

    /**
     * 日期
     */
    private LocalDate createDate;

    /**
     * 是否是今天的数据 1-是，0-不是
     */
    private Integer isToday;
}
