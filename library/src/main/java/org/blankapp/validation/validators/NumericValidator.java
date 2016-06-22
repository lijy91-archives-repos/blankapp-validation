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
            PATTERN_MIN,
            PATTERN_MAX,
            PATTERN_BETWEEN,
    })
    public @interface Patterns {}

    public static final int PATTERN_MIN = 0x01;
    public static final int PATTERN_MAX = 0x02;
    public static final int PATTERN_BETWEEN = 0x03;

    private Number mMin = 0;
    private Number mMax = 0;
    private int mPattern = 0;

    public NumericValidator(Number min, Number max, @Patterns int pattern) {
        this.mMin = min;
        this.mMax = max;
        this.mPattern = pattern;
    }

    @Override
    public boolean isValid(Number number) {
        if (number == null) {
            return false;
        }
        switch (mPattern) {
            case PATTERN_MIN:
                break;
            case PATTERN_MAX:
                break;
            case PATTERN_BETWEEN:
                break;
        }
        return false;
    }

}
