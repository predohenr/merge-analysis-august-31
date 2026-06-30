package com.baomidou.mybatisplus.generator;

import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import org.jetbrains.annotations.NotNull;

/**
 * Customizes generator field metadata after type conversion and before templates render.
 *
 * @since 3.5.18
 */
@FunctionalInterface
public interface ITableFieldMetaInfoCustomizer {

    /**
     * Customize current field metadata.
     *
     * @param tableInfo current table info
     * @param tableField current field info, including {@link TableField.MetaInfo}
     */
    void customize(@NotNull TableInfo tableInfo, @NotNull TableField tableField);
}
