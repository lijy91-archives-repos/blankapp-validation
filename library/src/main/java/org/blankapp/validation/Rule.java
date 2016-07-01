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

package org.blankapp.validation;

import android.content.res.Resources;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;

import org.blankapp.validation.validators.AcceptedValidator;
import org.blankapp.validation.validators.AbstractValidator;
import org.blankapp.validation.validators.ConfirmValidator;
import org.blankapp.validation.validators.DateValidator;
import org.blankapp.validation.validators.JSONValidator;
import org.blankapp.validation.validators.NumericValidator;
import org.blankapp.validation.validators.RegexValidator;
import org.blankapp.validation.validators.RequiredValidator;
import org.blankapp.validation.validators.TextValidator;
import org.blankapp.validation.validators.TypeValidator;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Rule {

    public static final String ACCEPTED         = "accepted";
    public static final String AFTER            = "after";
    public static final String ALPHA            = "alpha";
    public static final String ALPHA_DASH       = "alphaDash";
    public static final String ALPHA_NUM        = "alphaNum";
    public static final String BEFORE           = "before";
    public static final String BETWEEN          = "between";
    public static final String CONFIRMED        = "confirmed";
    public static final String DATE             = "date";
    public static final String DIGITS           = "digits";
    public static final String DIGITS_BETWEEN   = "digitsBetween";
    public static final String EMAIL            = "email";
    public static final String INTEGER          = "integer";
    public static final String IP               = "ip";
    public static final String JSON             = "json";
    public static final String MAX              = "max";
    public static final String MAX_LENGTH       = "maxLength";
    public static final String MIN              = "min";
    public static final String MIN_LENGTH       = "minLength";
    public static final String NUMERIC          = "numeric";
    public static final String REGEX            = "regex";
    public static final String REQUIRED         = "required";
    public static final String URL              = "url";

    public static Rule with(View view) {
        return new Rule(view, null);
    }

    public static Rule with(View view, @StringRes int nameResId) {
        String name = view.getResources().getString(nameResId);
        return new Rule(view, name);
    }

    public static Rule with(View view, String name) {
        return new Rule(view, name);
    }

    private String mName;
    private View mView;
    private String mPackageName;
    private Resources mResources;
    private Map<String, AbstractValidator> mValidators;
    private Map<String, String> mErrorMessages;

    private String mType = TypeValidator.STRING;

    private Rule() {
    }

    public Rule(View view, String name) {
        if (!(view instanceof EditText) &&
            !(view instanceof CompoundButton)) {
            throw new ValidationException("只支持 EditText 及 CompoundButton 及其派生控件。");
        }

        this.mName = name;
        this.mView = view;
        this.mPackageName = view.getContext().getPackageName();
        this.mResources = view.getResources();
        this.mValidators = new LinkedHashMap<>();
        this.mErrorMessages = new LinkedHashMap<>();

        if (mName == null) {
            String viewIdName = mResources.getResourceName(view.getId());
            String fieldName = viewIdName.substring(viewIdName.indexOf('_') + 1);
            Log.e("Rule", "validation_field_" + fieldName);
            int resId = mResources.getIdentifier("validation_field_" + fieldName, "string", mPackageName);
            this.mName = mResources.getString(resId == 0 ? R.string.validation_field : resId);
        }
    }

    /**
     * 判断是否允许跳过该验证规则
     *
     * @return
     */
    public boolean canSkip() {
        // 如果不存在必需和接受规则，则值为空，则允许跳过
        if (!mValidators.containsKey(REQUIRED) && !mValidators.containsKey(ACCEPTED)) {
            Object value = value();
            if (value == null) {
                return true;
            } else if (value instanceof String && ((String) value).length() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取当前规则的字段名称
     *
     * @return 字段名
     */
    public String name() {
        return mName;
    }

    /**
     * 获取当前规则的字段控件
     *
     * @return 控件
     */
    public View view() {
        return mView;
    }

    /**
     * 获取当前规则的字段的数据类型
     *
     * @return 类型
     */
    public String type() {
        return mType;
    }

    public Object value() {
        switch (mType) {
            case TypeValidator.BOOLEAN:
                return booleanValue();
            case TypeValidator.DOUBLE:
                return doubleValue();
            case TypeValidator.FLOAT:
                return floatValue();
            case TypeValidator.INTEGER:
                return intValue();
            case TypeValidator.LONG:
                return longValue();
            case TypeValidator.SHORT:
                return shortValue();
            case TypeValidator.STRING:
            default:
                return stringValue();
        }
    }

    public Boolean booleanValue() {
        if (mView instanceof CompoundButton) {
            return ((CompoundButton) mView).isChecked();
        }
        return null;
    }

    public Double doubleValue() {

        try {
            return Double.parseDouble(stringValue());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public Float floatValue() {
        try {
            return Float.parseFloat(stringValue());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public Integer intValue() {
        try {
            return Integer.parseInt(stringValue());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public Long longValue() {
        try {
            return Long.parseLong(stringValue());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public Short shortValue() {
        try {
            return Short.parseShort(stringValue());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public String stringValue() {
        if (mView instanceof EditText) {
            return String.valueOf(((EditText) mView).getText());
        }
        return null;
    }

    public Map<String, AbstractValidator> validators() {
        return mValidators;
    }

    public Map<String, String> errorMessages() {
        return mErrorMessages;
    }

    private void addValidator(String ruleName, AbstractValidator validator, @StringRes int messageResId) {
        String errorMessage = mResources.getString(messageResId);
        this.addValidator(ruleName, validator, errorMessage);
    }

    private void addValidator(String ruleName, AbstractValidator validator, @StringRes int messageResId, Object... formatArgs) {
        String errorMessage = mResources.getString(messageResId, formatArgs);
        this.addValidator(ruleName, validator, errorMessage);
    }

    private void addValidator(String ruleName, AbstractValidator validator, String errorMessage) {
        mValidators.put(ruleName, validator);
        mErrorMessages.put(ruleName, errorMessage);
    }

    private String dateFormat() {
        TypeValidator typeValidator = (TypeValidator) mValidators.get(DATE);
        return typeValidator.format();
    }

    /**
     * 验证字段值是否为 true。这在确认「服务条款」是否同意时相当有用。
     *
     * @return 规则
     */
    public Rule accepted() {
        if (!(mView instanceof CompoundButton)) {
            throw new ValidationException("该规则仅支持 CompoundButton 仅其派生控件");
        }
        mType = TypeValidator.BOOLEAN;
        addValidator(ACCEPTED, new AcceptedValidator(), R.string.validation_error_message_accepted, name());
        return this;
    }

    /**
     * 验证字段是否是在指定日期之后。
     *
     * @param dateStr
     * @return 规则
     */
    public Rule after(String dateStr) {
        DateValidator dateValidator = new DateValidator(dateStr, null, DateValidator.PATTERN_AFTER);
        String displayDate = dateStr;
        if (!TextUtils.isEmpty(dateValidator.dateFlag())) {
            int resId = mResources.getIdentifier("validation_date_" + dateValidator.dateFlag(), "string", mPackageName);
            displayDate = mResources.getString(resId);
        }
        addValidator(AFTER, dateValidator, R.string.validation_error_message_after, name(), displayDate);
        return this;
    }

    /**
     * 验证字段是否是在指定日期之后。
     *
     * @param dateStr
     * @param format
     * @return 规则
     */
    public Rule after(String dateStr, String format) {
        addValidator(AFTER, new DateValidator(dateStr, format, DateValidator.PATTERN_AFTER), R.string.validation_error_message_after, name(), dateStr);
        return this;
    }

    /**
     * 验证字段是否是在指定日期之后。
     *
     * @param date
     * @return 规则
     */
    public Rule after(Date date) {
        addValidator(AFTER, new DateValidator(date, dateFormat(), DateValidator.PATTERN_AFTER), R.string.validation_error_message_after, name(), null);
        return this;
    }

    /**
     * 验证字段值是否仅包含字母字符。
     *
     * @return 规则
     */
    public Rule alpha() {
        addValidator(ALPHA, new RegexValidator(RegexValidator.Patterns.ALPHA), R.string.validation_error_message_alpha, name());
        return this;
    }

    /**
     * 验证字段值是否仅包含字母、数字、破折号（ - ）以及下划线（ _ ）。
     *
     * @return 规则
     */
    public Rule alphaDash() {
        addValidator(ALPHA_DASH, new RegexValidator(RegexValidator.Patterns.ALPHA_DASH), R.string.validation_error_message_alpha_dash, name());
        return this;
    }

    /**
     * 验证字段值是否仅包含字母、数字。
     *
     * @return 规则
     */
    public Rule alphaNum() {
        addValidator(ALPHA_NUM, new RegexValidator(RegexValidator.Patterns.ALPHA_NUM), R.string.validation_error_message_alpha_dash, name());
        return this;
    }

    /**
     * 验证字段是否是在指定日期之前。
     *
     * @param dateStr 日期字符串
     * @return 规则
     */
    public Rule before(String dateStr) {
        DateValidator dateValidator = new DateValidator(dateStr, dateFormat(), DateValidator.PATTERN_BEFORE);
        String displayDate = dateStr;
        if (!TextUtils.isEmpty(dateValidator.dateFlag())) {
            int resId = mResources.getIdentifier("validation_date_" + dateValidator.dateFlag(), "string", mPackageName);
            displayDate = mResources.getString(resId);
        }
        addValidator(BEFORE, dateValidator, R.string.validation_error_message_before, name(), displayDate);
        return this;
    }

    /**
     * 验证字段是否是在指定日期之前。
     *
     * @param dateStr 日期字符串
     * @param format 日期格式
     * @return 规则
     */
    public Rule before(String dateStr, String format) {
        addValidator(BEFORE, new DateValidator(dateStr, format, DateValidator.PATTERN_BEFORE), R.string.validation_error_message_before, name(), dateStr);
        return this;
    }

    /**
     * 验证字段是否是在指定日期之前。
     *
     * @param date 日期对象
     * @return 规则
     */
    public Rule before(Date date) {
        addValidator(BEFORE, new DateValidator(date, dateFormat(), DateValidator.PATTERN_BEFORE), R.string.validation_error_message_before, name());
        return this;
    }

    /**
     * 验证字段值的大小是否介于指定的 min 和 max 之间。
     *
     * @param min 最小值
     * @param max 最大值
     * @return 规则
     */
    public Rule between(int min, int max) {
        mType = TypeValidator.INTEGER;
        addValidator(BETWEEN, new NumericValidator(min, max, NumericValidator.PATTERN_BETWEEN_VALUE), R.string.validation_error_message_between, name(), min, max);
        return this;
    }

    /**
     * 验证字段值必须和 field_confirmation 的字段值一致。
     * 例如，如果要验证的字段是 password，就必须和输入数据里的 password_confirmation 的值保持一致。
     *
     * @return 规则
     */
    private Rule confirmed() {
        addValidator(CONFIRMED, new ConfirmValidator(), R.string.validation_error_message_confirmed, name());
        return this;
    }

    /**
     * 验证字段值是否为有效日期。
     *
     * @return 规则
     */
    public Rule date(String format) {
        addValidator(DATE, new TypeValidator(TypeValidator.DATE, format), R.string.validation_error_message_date, name());
        return this;
    }

    /**
     * 验证字段值是否为 numeric 且长度为 value。
     *
     * @param value 长度
     * @return 规则
     */
    public Rule digits(long value) {
        mType = TypeValidator.LONG;
        addValidator(DIGITS, new NumericValidator(0, 0, value, value, NumericValidator.PATTERN_BETWEEN_LENGTH), R.string.validation_error_message_digits, name(), value);
        return this;
    }

    /**
     * 验证字段值的长度是否在 min 和 max 之间。
     *
     * @param min 最小长度
     * @param max 最大长度
     * @return
     */
    public Rule digitsBetween(long min, long max) {
        mType = TypeValidator.LONG;
        addValidator(DIGITS_BETWEEN, new NumericValidator(0, 0, min, max, NumericValidator.PATTERN_BETWEEN_LENGTH), R.string.validation_error_message_digits_between, name(), min, max);
        return this;
    }

    /**
     * 验证字段值是否符合 E-Mail 格式。
     *
     * @return 规则
     */
    public Rule email() {
        addValidator(EMAIL, new RegexValidator(RegexValidator.Patterns.EMAIL_ADDRESS), R.string.validation_error_message_email, name());
        return this;
    }

    public Rule in(String[] values) {
        return this;
    }

    public Rule in(String[] ... values) {
        return this;
    }

    /**
     * 验证字段值是否是整数。
     *
     * @return 规则
     */
    public Rule integer() {
        mType = TypeValidator.INTEGER;
        addValidator(INTEGER, new TypeValidator(TypeValidator.INTEGER), R.string.validation_error_message_integer, name());
        return this;
    }

    /**
     * 验证字段值是否符合 IP Address 的格式。
     * @return 规则
     */
    public Rule ip() {
        addValidator(IP, new RegexValidator(RegexValidator.Patterns.IP_ADDRESS), R.string.validation_error_message_ip, name());
        return this;
    }

    /**
     * 验证字段是否是一个有效的 JSON 字符串。
     *
     * @return 规则
     */
    public Rule json() {
        addValidator(JSON, new JSONValidator(), R.string.validation_error_message_json, name());
        return this;
    }

    public Rule max(long value) {
        addValidator(MAX, new NumericValidator(0, value, NumericValidator.PATTERN_MIN_VALUE), R.string.validation_error_message_max, name(), value);
        return this;
    }

    /**
     * 字段值必须小于或等于 value 。
     *
     * @param value
     * @return 规则
     */
    public Rule max(double value) {
        addValidator(MAX, new NumericValidator(0, value, NumericValidator.PATTERN_MIN_VALUE), R.string.validation_error_message_max, name(), value);
        return this;
    }

    /**
     * 字段值不能大于 value 个字符。
     *
     * @param value 最大字符串
     * @return 规则
     */
    public Rule maxLength(long value) {
        addValidator(MAX_LENGTH, new TextValidator(0, value, TextValidator.PATTERN_MAX_LENGTH), R.string.validation_error_message_max_length, name(), value);
        return this;
    }

    public Rule min(int value) {
        addValidator(MIN, new NumericValidator(value, 0, NumericValidator.PATTERN_MIN_VALUE), R.string.validation_error_message_min, name(), value);
        return this;
    }

    /**
     * 字段值必须大于或等于 value。
     *
     * @param value
     * @return 规则
     */
    public Rule min(double value) {
        addValidator(MIN, new NumericValidator(value, 0, NumericValidator.PATTERN_MIN_VALUE), R.string.validation_error_message_min, name(), value);
        return this;
    }

    /**
     * 字段值至少为 value 个字符。
     *
     * @param value 最小字符数
     * @return 规则
     */
    public Rule minLength(long value) {
        addValidator(MIN_LENGTH, new TextValidator(value, 0, TextValidator.PATTERN_MIN_LENGTH), R.string.validation_error_message_min_length, name(), value);
        return this;
    }

    private Rule notIn(String[] values) {
        return this;
    }

    private Rule notIn(String[] ... values) {
        return this;
    }

    /**
     * 验证字段值是否为数值。
     *
     * @return 规则
     */
    public Rule numeric() {
        mType = TypeValidator.DOUBLE;
        addValidator(NUMERIC, new TypeValidator(TypeValidator.DOUBLE), R.string.validation_error_message_numeric);
        return this;
    }

    /**
     * 验证字段值是否符合指定的正则表达式。
     *
     * @param pattern 正则表达式（字符串）
     * @return 规则
     */
    public Rule regex(String pattern) {
        addValidator(REGEX, new RegexValidator(pattern), R.string.validation_error_message_regex, name());
        return this;
    }

    /**
     * 验证字段值是否符合指定的正则表达式。
     *
     * @param pattern 正则表达式（Pattern）
     * @return 规则
     */
    public Rule regex(Pattern pattern) {
        addValidator(REGEX, new RegexValidator(pattern), R.string.validation_error_message_regex, name());
        return this;
    }

    /**
     * 验证字段必须存在输入数据，且不为空。字段符合下方任一条件时即为「空」
     *  1、该值为 null。
     *  2、该值为空字符串。
     *
     * @return 规则
     */
    public Rule required() {
        addValidator(REQUIRED, new RequiredValidator(), R.string.validation_error_message_required, name());
        return this;
    }

    /**
     * 验证字段值是否为一个有效的网址。
     *
     * @return 规则
     */
    public Rule url() {
        addValidator(URL, new RegexValidator(RegexValidator.Patterns.WEB_URL), R.string.validation_error_message_url, name());
        return this;
    }

    /**
     * 增加自定义验证规则。
     *
     * @param ruleName 规则名称
     * @param validator 验证器
     * @param errorMessage 验证失败消息
     * @return 规则
     */
    public Rule extend(String ruleName, AbstractValidator validator, String errorMessage) {
        addValidator(ruleName, validator, errorMessage);
        return this;
    }

}
