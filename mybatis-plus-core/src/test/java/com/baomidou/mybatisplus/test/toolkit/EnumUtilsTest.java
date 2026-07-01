package com.baomidou.mybatisplus.test.toolkit;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.baomidou.mybatisplus.core.toolkit.EnumMetadata;
import com.baomidou.mybatisplus.core.toolkit.EnumUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

/**
 * Tests for {@link EnumUtils}.
 *
 * @author baomidou
 * @since 3.5.15
 */
class EnumUtilsTest {

    @Test
    void shouldExposeMetadata() {
        EnumMetadata metadata = EnumUtils.metadata(GradeEnum.class);

        Assertions.assertEquals(Integer.class, metadata.getPropertyType());
        Assertions.assertEquals("code", metadata.getGetterName());
        Assertions.assertArrayEquals(GradeEnum.values(), metadata.getEnumConstants());
        Assertions.assertEquals(GradeEnum.PRIMARY, metadata.valueOf(1));
        Assertions.assertEquals(GradeEnum.PRIMARY, metadata.valueOf(1L));
        Assertions.assertEquals(1, metadata.getValue(GradeEnum.PRIMARY));
    }

    @Test
    void shouldProtectEnumConstantsSnapshot() {
        EnumMetadata metadata = EnumUtils.metadata(GradeEnum.class);

        Enum<?>[] enumConstants = metadata.getEnumConstants();
        enumConstants[0] = null;

        Assertions.assertEquals(GradeEnum.PRIMARY, metadata.getEnumConstants()[0]);
    }

    @Test
    void shouldResolveEnumValue() {
        Assertions.assertEquals(SexEnum.MAN, EnumUtils.valueOf(SexEnum.class, 1));
        Assertions.assertEquals(SexEnum.WO_MAN, EnumUtils.valueOf(SexEnum.class, "2"));
        Assertions.assertEquals(GradeEnum.HIGH, EnumUtils.valueOf(GradeEnum.class, BigInteger.valueOf(3L)));
        Assertions.assertEquals(BigDecimalEnum.TEN, EnumUtils.valueOf(BigDecimalEnum.class, BigInteger.TEN));
        Assertions.assertNull(EnumUtils.valueOf(GradeEnum.class, 9));
    }

    @Test
    void shouldResolvePersistentValue() {
        Assertions.assertEquals(1, EnumUtils.getValue(SexEnum.MAN));
        Assertions.assertEquals(3, EnumUtils.getValue(GradeEnum.HIGH));
        Assertions.assertNull(EnumUtils.getValue(null));
    }

    @Test
    void shouldDetectMpEnums() {
        Optional<String> fieldName = EnumUtils.findEnumValueFieldName(GradeEnum.class);

        Assertions.assertTrue(fieldName.isPresent());
        Assertions.assertEquals("code", fieldName.get());
        Assertions.assertFalse(EnumUtils.findEnumValueFieldName(SexEnum.class).isPresent());
        Assertions.assertFalse(EnumUtils.findEnumValueFieldName(String.class).isPresent());
        Assertions.assertTrue(EnumUtils.isMpEnums(SexEnum.class));
        Assertions.assertTrue(EnumUtils.isMpEnums(GradeEnum.class));
        Assertions.assertFalse(EnumUtils.isMpEnums(PlainEnum.class));
    }

    @Test
    void shouldNormalizeValue() {
        Assertions.assertEquals(1L, EnumUtils.normalizeValue(1));
        Assertions.assertEquals(1L, EnumUtils.normalizeValue(BigInteger.ONE));
        Assertions.assertEquals(10L, EnumUtils.normalizeValue(BigDecimal.TEN));
        Assertions.assertEquals(new BigDecimal("1.1"), EnumUtils.normalizeValue(new BigDecimal("1.10")));
    }

    @Test
    void shouldFailFastForIllegalType() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> EnumUtils.metadata(null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> EnumUtils.metadata(String.class));
        Assertions.assertThrows(IllegalArgumentException.class, () -> EnumUtils.metadata(PlainEnum.class));
    }

    @Getter
    @AllArgsConstructor
    enum SexEnum implements IEnum<Integer> {

        MAN(1),
        WO_MAN(2);

        private final Integer value;
    }

    @Getter
    @AllArgsConstructor
    enum GradeEnum {

        PRIMARY(1),
        SECONDARY(2),
        HIGH(3);

        @EnumValue
        private final int code;
    }

    @Getter
    @AllArgsConstructor
    enum BigDecimalEnum {

        TEN(BigDecimal.TEN);

        @EnumValue
        private final BigDecimal code;
    }

    enum PlainEnum {

        A
    }
}
