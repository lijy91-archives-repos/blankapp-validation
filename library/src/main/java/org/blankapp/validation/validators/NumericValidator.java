/**
 * Copyright (C) 2015 JianyingLi <lijy91@foxmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.blankapp.validation.validators;

import android.support.annotation.IntDef;

public class NumericValidator extends AbstractValidator<Number> {

    @IntDef({
            PATTERN_MIN_VALUE,
            PATTERN_MAX_VALUE,
            PATTERN_BETWEEN_VALUE,
            PATTERN_MIN_LENGTH,
            PATTERN_MAX_LENGTH,
            PATTERN_BETWEEN_LENGTH,
    })
    public @interface Patterns {}

    public static final int PATTERN_MIN_VALUE       = 0x01;
    public static final int PATTERN_MAX_VALUE       = 0x02;
    public static final int PATTERN_BETWEEN_VALUE   = 0x03;
    public static final int PATTERN_MIN_LENGTH      = 0x04;
    public static final int PATTERN_MAX_LENGTH      = 0x05;
    public static final int PATTERN_BETWEEN_LENGTH  = 0x06;

    private double mMinValue = 0;
    private double mMaxValue = 0;
    private long mMaxLength = -1;
    private long mMinLength = -1;
    private int mPattern = 0;

    public NumericValidator(double minValue, double maxValue, @Patterns int pattern) {
        this.mMinValue = minValue;
        this.mMaxValue = maxValue;
        this.mPattern = pattern;
    }

    public NumericValidator(double minValue, double maxValue, long minLength, long maxLength, @Patterns int pattern) {
        this.mMinValue = minValue;
        this.mMaxValue = maxValue;
        this.mMinLength = minLength;
        this.mMaxLength = maxLength;
        this.mPattern = pattern;
    }

    @Override
    public boolean isValid(Number value) {
        if (value == null) {
            return false;
        }
        double numeric = value.doubleValue();
        String numericStr = String.valueOf(value.doubleValue());

        // 整数位长度
        int integerLength = numericStr.substring(0, numericStr.lastIndexOf('.')).length();
        // 小数位长度
        int decimalLength = numericStr.substring(numericStr.lastIndexOf('.')).length() - 1;
        switch (mPattern) {
            case PATTERN_MIN_VALUE:
                return numeric >= mMinValue;
            case PATTERN_MAX_VALUE:
                return numeric <= mMaxValue;
            case PATTERN_BETWEEN_VALUE:
                return numeric >= mMinValue && numeric <= mMaxValue;
            case PATTERN_MIN_LENGTH:
                return integerLength >= mMinLength;
            case PATTERN_MAX_LENGTH:
                return integerLength <= mMaxLength;
            case PATTERN_BETWEEN_LENGTH:
                return integerLength >= mMinLength && integerLength <= mMaxLength;
        }
        return false;
    }

}
