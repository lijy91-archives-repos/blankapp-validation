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

import java.util.Date;

public class DateValidator extends AbstractValidator<Date> {

    @StringDef({
        NOW,
        TODAY,
        TOMORROW,
        YESTERDAY,
        THIS_SUNDAY,
        THIS_MONDAY,
        THIS_TUESDAY,
        THIS_WEDNESDAY,
        THIS_THURSDAY,
        THIS_FRIDAY,
        THIS_SATURDAY,
        LAST_SUNDAY,
        LAST_MONDAY,
        LAST_TUESDAY,
        LAST_WEDNESDAY,
        LAST_THURSDAY,
        LAST_FRIDAY,
        LAST_SATURDAY,
        NEXT_SUNDAY,
        NEXT_MONDAY,
        NEXT_TUESDAY,
        NEXT_WEDNESDAY,
        NEXT_THURSDAY,
        NEXT_FRIDAY,
        NEXT_SATURDAY,
    })
    public @interface DateFlags {}

    public static final String NOW            = "now";
    public static final String TODAY          = "today";
    public static final String TOMORROW       = "tomorrow";
    public static final String YESTERDAY      = "yesterday";

    public static final String THIS_SUNDAY    = "this_sunday";
    public static final String THIS_MONDAY    = "this_monday";
    public static final String THIS_TUESDAY   = "this_tuesday";
    public static final String THIS_WEDNESDAY = "this_wednesday";
    public static final String THIS_THURSDAY  = "this_thursday";
    public static final String THIS_FRIDAY    = "this_friday";
    public static final String THIS_SATURDAY  = "this_saturday";

    public static final String LAST_SUNDAY    = "last_sunday";
    public static final String LAST_MONDAY    = "last_monday";
    public static final String LAST_TUESDAY   = "last_tuesday";
    public static final String LAST_WEDNESDAY = "last_wednesday";
    public static final String LAST_THURSDAY  = "last_thursday";
    public static final String LAST_FRIDAY    = "last_friday";
    public static final String LAST_SATURDAY  = "last_saturday";

    public static final String NEXT_SUNDAY    = "next_sunday";
    public static final String NEXT_MONDAY    = "next_monday";
    public static final String NEXT_TUESDAY   = "next_tuesday";
    public static final String NEXT_WEDNESDAY = "next_wednesday";
    public static final String NEXT_THURSDAY  = "next_thursday";
    public static final String NEXT_FRIDAY    = "next_friday";
    public static final String NEXT_SATURDAY  = "next_saturday";

    private String dateFlag;
    private Date date;

    public DateValidator(Date date) {
        this.date = date;
    }

    public DateValidator(@DateFlags String dateFlag) {
        this.dateFlag = dateFlag;

    }

    @Override
    public boolean isValid(Date date) {
        if (dateFlag == null) {

        }
        return false;
    }

}
