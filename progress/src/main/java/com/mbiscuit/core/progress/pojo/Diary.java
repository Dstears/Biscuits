package com.mbiscuit.core.progress.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 是否比昨天有提高，0-没有，1-有
     */
    @NotNull
    @Max(1)
    @Min(0)
    private Integer hasProgress;

    /**
     * 哪里提升了
     */
    private String progressDetail;

    /**
     * 日期
     */
    private LocalDate createDate;
}
