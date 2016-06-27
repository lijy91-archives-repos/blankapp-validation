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

import android.support.annotation.StringDef;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TypeValidator extends AbstractValidator<String> {

    @StringDef({
            DATE,
            DOUBLE,
            FLOAT,
            INTEGER,
            LONG,
            SHORT,
            STRING,
    })
    public @interface ValueType {}

    public static final String BOOLEAN  = "boolean";
    public static final String DATE     = "date";
    public static final String DOUBLE   = "double";
    public static final String FLOAT    = "float";
    public static final String INTEGER  = "integer";
    public static final String LONG     = "long";
    public static final String SHORT    = "short";
    public static final String STRING   = "string";

    private String mValueType;
    private String mValueFormat;
    private SimpleDateFormat mSimpleDateFormat;

    public TypeValidator(@ValueType String valueType) {
        this(valueType, null);
    }

    public TypeValidator(@ValueType String valueType, String valueFormat) {
        this.mValueType = valueType;
        this.mValueFormat = valueFormat;
        if (DATE.equals(valueType)) {
            mSimpleDateFormat = new SimpleDateFormat(mValueFormat);
        }
    }

    public String type() {
        return mValueType;
    }

    public String format() {
        return mValueFormat;
    }

    @Override
    public boolean isValid(String value) {
        try {
            switch (mValueType) {
                case DATE:
                    Date date = mSimpleDateFormat.parse(value);
                    if (!value.equals(mSimpleDateFormat.format(date))) {
                        return false;
                    }
                    break;
                case DOUBLE:
                    Double.parseDouble(value);
                    break;
                case FLOAT:
                    Float.parseFloat(value);
                    break;
                case INTEGER:
                    Integer.parseInt(value);
                    break;
                case LONG:
                    Long.parseLong(value);
                    break;
                case SHORT:
                    Short.parseShort(value);
                    break;
                case STRING:
                    return true;
                default:
                    return false;
            }
        } catch (ParseException | NumberFormatException e) {
            return false;
        }
        return true;
    }

}
