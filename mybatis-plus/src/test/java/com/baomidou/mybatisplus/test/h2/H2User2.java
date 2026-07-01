package com.baomidou.mybatisplus.test.h2;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.test.h2.entity.SuperEntity;
import com.baomidou.mybatisplus.test.h2.enums.AgeEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@TableName(value = "h2user", autoResultMap = true)
public class H2User2 extends SuperEntity {

    @TableField(typeHandler = H2User2NameTypeHandler.class)
    private String name;

    private AgeEnum age;

    /*BigDecimal 测试*/
    private BigDecimal price;

    /* 测试下划线字段命名类型, 字段填充 */
    @TableField(fill = FieldFill.INSERT)
    private Integer testType;

    /**
     * 转义关键字测试
     */
    @TableField("`desc`")
    private String desc;

    /**
     * 该注解 select 默认不注入 select 查询
     */
    @TableField(select = false)
    private Date testDate;

    @Version
    private Integer version;

    @TableLogic
    private Integer deleted;

    @TableField("created_dt")
    private LocalDateTime createdDt;

}
