package com.baomidou.mybatisplus.generator.samples;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.query.DefaultQuery;
import org.apache.ibatis.type.JdbcType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TableFieldMetaInfoCustomizerTest extends BaseGeneratorTest {

    private static final String H2URL = "jdbc:h2:mem:test-h2-meta-customizer;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;CASE_INSENSITIVE_IDENTIFIERS=TRUE;MODE=MYSQL;DATABASE_TO_LOWER=TRUE";

    private static final DataSourceConfig DATA_SOURCE_CONFIG = new DataSourceConfig.Builder(H2URL, "sa", "")
        .databaseQueryClass(DefaultQuery.class).build();

    @BeforeAll
    static void before() throws SQLException {
        initDataSource(DATA_SOURCE_CONFIG);
    }

    @Test
    void shouldCustomizeFieldMetaInfoAfterTypeConversion() {
        StrategyConfig strategyConfig = new StrategyConfig.Builder()
            .addInclude("t_simple")
            .entityBuilder()
            .tableFieldMetaInfoCustomizer((tableInfo, tableField) -> {
                // 这里进行不同表的处理
                if ("name".equals(tableField.getColumnName())) {
                    tableField.getMetaInfo().setJdbcType(JdbcType.OTHER);
                    tableField.getMetaInfo().setTypeName("POINT");
                    tableField.setColumnType(DbColumnType.OBJECT);
                }
            })
            .build();
        GlobalConfig globalConfig = globalConfig().build();
        ConfigBuilder configBuilder = new ConfigBuilder(null, DATA_SOURCE_CONFIG, strategyConfig, null, globalConfig, null);

        List<TableInfo> tableInfoList = configBuilder.getTableInfoList();
        TableField field = tableInfoList.get(0).getFields().stream()
            .filter(tableField -> "name".equals(tableField.getColumnName()))
            .findFirst()
            .orElseThrow();

        assertThat(field.getMetaInfo().getJdbcType()).isEqualTo(JdbcType.OTHER);
        assertThat(field.getMetaInfo().getTypeName()).isEqualTo("POINT");
        assertThat(field.getColumnType()).isEqualTo(DbColumnType.OBJECT);
    }
}
