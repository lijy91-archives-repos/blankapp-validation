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

public class TextValidator extends AbstractValidator<String> {

    @IntDef({
            PATTERN_MIN_LENGTH,
            PATTERN_MAX_LENGTH,
            PATTERN_BETWEEN_LENGTH,
    })
    public @interface Patterns {}

    public static final int PATTERN_MIN_LENGTH     = 0x01;
    public static final int PATTERN_MAX_LENGTH     = 0x02;
    public static final int PATTERN_BETWEEN_LENGTH = 0x03;

    private long mMaxLength = 0;
    private long mMinLength = 0;
    private int mPattern   = 0;

    public TextValidator(long minLength, long maxLength, @Patterns int pattern) {
        this.mMinLength = minLength;
        this.mMaxLength = maxLength;
        this.mPattern = pattern;
    }

    @Override
    public boolean isValid(String value) {
        if (value == null) {
            return false;
        }
        switch (this.mPattern) {
            case PATTERN_MIN_LENGTH:
                return value.length() >= mMinLength;
            case PATTERN_MAX_LENGTH:
                return value.length() <= mMaxLength;
            case PATTERN_BETWEEN_LENGTH:
                return value.length() >= mMinLength && value.length() <= mMaxLength;
        }
        return false;
    }

}
