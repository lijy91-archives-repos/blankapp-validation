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
import android.support.annotation.StringDef;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateValidator extends AbstractValidator<String> {

    @StringDef({
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

    @IntDef({
            PATTERN_AFTER,
            PATTERN_BEFORE,
            PATTERN_EQUAL,
    })
    public @interface Patterns {}

    public static final int PATTERN_AFTER  = 0x01;
    public static final int PATTERN_BEFORE = 0x02;
    public static final int PATTERN_EQUAL  = 0x03;

    private String mDateFlag;
    private SimpleDateFormat mSimpleDateFormat;
    private Date mDate;
    private String mDateStr;
    private String mDateFormat;
    private int mPattern;

    private DateValidator() {
    }

    public DateValidator(String dateStr, String dateFormat, @Patterns int pattern) {
        this.mSimpleDateFormat = new SimpleDateFormat(dateFormat);
        try {
            this.mDate = mSimpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            this.mDate = flagToDate(dateStr);
            this.mDateFlag = dateStr;
        }
        this.mDateStr = dateStr;
        this.mDateFormat = dateFormat;
        this.mPattern = pattern;
    }

    public DateValidator(Date date, String dateFormat, int pattern) {
        this.mSimpleDateFormat = new SimpleDateFormat(dateFormat);
        this.mDate = date;
        this.mDateStr = mSimpleDateFormat.format(mDate);
        this.mDateFormat = dateFormat;
        this.mPattern = pattern;
    }

    public String dateFlag() {
        return mDateFlag;
    }

    public Date date() {
        return mDate;
    }

    public int pattern() {
        return mPattern;
    }

    private Date flagToDate(@DateFlags String dateFlag) {
        Calendar c = Calendar.getInstance();
        switch (dateFlag) {
            case TODAY:
                break;
            case TOMORROW:
                c.add(Calendar.DATE, 1);
                break;
            case YESTERDAY:
                c.add(Calendar.DATE, -1);
                break;
            default:
                if (dateFlag.startsWith("this ") ||
                    dateFlag.startsWith("last ") ||
                    dateFlag.startsWith("next ")) {

                    String prefix = dateFlag.substring(0, dateFlag.indexOf(' '));
                    String dayOfWeek = dateFlag.substring(dateFlag.indexOf(' ') + 1);

                    switch (prefix) {
                        case "this":
                            break;
                        case "last":
                            c.add(Calendar.DATE, -7);
                            break;
                        case "next":
                            c.add(Calendar.DATE, 7);
                            break;
                    }

                    switch (dayOfWeek) {
                        case "sunday":
                            c.set(Calendar.DAY_OF_WEEK, 1);
                            break;
                        case "monday":
                            c.set(Calendar.DAY_OF_WEEK, 2);
                            break;
                        case "tuesday":
                            c.set(Calendar.DAY_OF_WEEK, 3);
                            break;
                        case "wednesday":
                            c.set(Calendar.DAY_OF_WEEK, 4);
                            break;
                        case "thursday":
                            c.set(Calendar.DAY_OF_WEEK, 5);
                            break;
                        case "friday":
                            c.set(Calendar.DAY_OF_WEEK, 6);
                            break;
                        case "saturday":
                            c.set(Calendar.DAY_OF_WEEK, 7);
                            break;
                    }
                }
                break;
        }

        // 将时、分、秒、毫秒置为0，仅对比日期。
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    @Override
    public boolean isValid(String value) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(mDateFormat);
        try {
            date = sdf.parse(value);
        } catch (ParseException e) {
            return false;
        }

        switch (mPattern) {
            case PATTERN_AFTER:
                return date.after(mDate);
            case PATTERN_BEFORE:
                return date.before(mDate);
            case PATTERN_EQUAL:
                return mDate.getTime() == date.getTime();
        }
        return false;
    }

}
