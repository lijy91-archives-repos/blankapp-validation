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
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.blankapp.validation.validators.AcceptedValidator;
import org.blankapp.validation.validators.AbstractValidator;
import org.blankapp.validation.validators.DateValidator;
import org.blankapp.validation.validators.RegexValidator;
import org.blankapp.validation.validators.RequiredValidator;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Rule {

    public static final String ACCEPTED     = "accepted";
    public static final String AFTER        = "after";
    public static final String ALPHA        = "alpha";
    public static final String ALPHA_DASH   = "alphaDash";
    public static final String ALPHA_NUM    = "alphaNum";
    public static final String BEFORE       = "before";
    public static final String DATE         = "date";
    public static final String EMAIL        = "email";
    public static final String IP           = "ip";
    public static final String REGEX        = "regex";
    public static final String REQUIRED     = "required";
    public static final String URL          = "url";

    public static Rule with(View view) {
        return new Rule(view, null);
    }

    public static Rule with(View view, @StringRes int nameResId) {
        return new Rule(view, nameResId);
    }

    public static Rule with(View view, String name) {
        return new Rule(view, name);
    }

    private View mView;
    private Resources mResources;
    private Map<String, AbstractValidator> mValidators;
    private Map<String, String> mErrorMessages;

    private String mName;

    private Rule() {
    }

    public Rule(View view) {
        this(view, null);
        int viewId = view.getId();
    }

    public Rule(View view, @StringRes int nameResId) {
        this(view, null);
        this.mName = mResources.getString(nameResId);
    }

    public Rule(View view, String name) {
        this.mView = view;
        this.mResources = view.getResources();
        this.mValidators = new LinkedHashMap<>();
        this.mErrorMessages = new LinkedHashMap<>();
        this.mName = name;
    }

    public View view() {
        return mView;
    }

    public Object value() {
        if (mView instanceof EditText) {
            return ((EditText) mView).getText();
        } else if (mView instanceof CheckBox) {
            return ((CheckBox) mView).isChecked();
        }
        return null;
    }

    public Map<String, AbstractValidator> validators() {
        return mValidators;
    }

    public Map<String, String> errorMessages() {
        return mErrorMessages;
    }

    public String name() {
        return mName;
    }

    private void addValidator(String ruleName, AbstractValidator validator, @StringRes int resId) {
        String errorMessage = mResources.getString(resId);
        this.addValidator(ruleName, validator, errorMessage);
    }

    private void addValidator(String ruleName, AbstractValidator validator, @StringRes int resId, Object... formatArgs) {
        String errorMessage = mResources.getString(resId, formatArgs);
        this.addValidator(ruleName, validator, errorMessage);
    }

    private void addValidator(String ruleName, AbstractValidator validator, String errorMessage) {
        mValidators.put(ruleName, validator);
        mErrorMessages.put(ruleName, errorMessage);
    }

    /**
     *
     * @return
     */
    public boolean isRequired() {
        return mValidators.containsKey(REQUIRED);
    }

    /**
     * 验证字段值是否为 true。这在确认「服务条款」是否同意时相当有用。
     *
     * @return 规则
     */
    public Rule accepted() {
        addValidator(ACCEPTED, new AcceptedValidator(), R.string.accepted, name());
        return this;
    }

    public Rule after(String date, String format) {
        return after(new Date());
    }

    public Rule after(Date date) {
        addValidator(AFTER, new DateValidator(date), R.string.after, name());
        return this;
    }

    public Rule after(@DateValidator.DateFlags String dateFlag) {
        addValidator(AFTER, new DateValidator(dateFlag), R.string.after, name());
        return this;
    }

    /**
     * 验证字段值是否仅包含字母字符。
     *
     * @return 规则
     */
    public Rule alpha() {
        addValidator(ALPHA, new RegexValidator(RegexValidator.Patterns.ALPHA), R.string.alpha, name());
        return this;
    }

    /**
     * 验证字段值是否仅包含字母、数字、破折号（ - ）以及下划线（ _ ）。
     *
     * @return 规则
     */
    public Rule alphaDash() {
        addValidator(ALPHA_DASH, new RegexValidator(RegexValidator.Patterns.ALPHA_DASH), R.string.alpha_dash, name());
        return this;
    }

    /**
     * 验证字段值是否仅包含字母、数字。
     *
     * @return 规则
     */
    public Rule alphaNum() {
        addValidator(ALPHA_NUM, new RegexValidator(RegexValidator.Patterns.ALPHA_NUM), R.string.alpha_dash, name());
        return this;
    }

    public Rule before(String date) {
        addValidator(BEFORE, new DateValidator(new Date()), R.string.before, name());
        return this;
    }

    public Rule before(Date date) {
        addValidator(BEFORE, new DateValidator(new Date()), R.string.before, name());
        return this;
    }

    public Rule between(int min, int max) {
        return this;
    }

    public Rule confirmed() {
        return this;
    }

    /**
     * 验证字段值是否为有效日期。
     *
     * @return 规则
     */
    public Rule date(String format) {
        addValidator(DATE, new DateValidator(new Date()), R.string.before, name());
        return this;
    }

    public Rule digits(long value) {
        return this;
    }

    public Rule digitsBetween(int min, int max) {
        return this;
    }

    /**
     * 验证字段值是否符合 E-Mail 格式。
     *
     * @return 规则
     */
    public Rule email() {
        addValidator(EMAIL, new RegexValidator(RegexValidator.Patterns.EMAIL_ADDRESS), R.string.email, name());
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
        return this;
    }

    /**
     * 验证字段值是否符合 IP Address 的格式。
     * @return 规则
     */
    public Rule ip() {
        addValidator(IP, new RegexValidator(RegexValidator.Patterns.IP_ADDRESS), R.string.ip, name());
        return this;
    }

    /**
     * 验证字段是否是一个有效的 JSON 字符串。
     *
     * @return 规则
     */
    public Rule json() {
        return this;
    }

    public Rule max(long value) {
        return this;
    }

    public Rule maxLength(long value) {
        return this;
    }

    public Rule min(long value) {
        return this;
    }

    public Rule minLength(long value) {
        return this;
    }

    public Rule notIn(String[] values) {
        return this;
    }

    public Rule notIn(String[] ... values) {
        return this;
    }

    public Rule numeric() {
        return this;
    }


    /**
     * 验证字段值是否符合指定的正则表达式。
     *
     * @param pattern
     * @return 规则
     */
    public Rule regex(String pattern) {
        addValidator(REGEX,new RegexValidator(pattern), R.string.regex, name());
        return this;
    }

    /**
     * 验证字段值是否符合指定的正则表达式。
     *
     * @param pattern
     * @return 规则
     */
    public Rule regex(Pattern pattern) {
        addValidator(REGEX,new RegexValidator(pattern), R.string.regex, name());
        return this;
    }

    /**
     * 验证字段必须存在输入数据，且不为空。字段符合下方任一条件时即为「空」
     *  1、该值为 null。
     *  2、该值为空字符串。
     * @return
     */
    public Rule required() {
        addValidator(REQUIRED, new RequiredValidator(), R.string.required, name());
        return this;
    }

    public Rule size(int value) {
        return null;
    }

    /**
     * 验证字段值是否为一个有效的网址。
     *
     * @return 规则
     */
    public Rule url() {
        addValidator(URL, new RegexValidator(RegexValidator.Patterns.WEB_URL), R.string.url, name());
        return this;
    }

    /**
     * 增加自定义验证规则
     * @param ruleName 规则名称
     * @param validator 验证器
     * @param errorMessage 验证失败消息
     * @return
     */
    public Rule extend(String ruleName, AbstractValidator validator, String errorMessage) {
        addValidator(ruleName, validator, errorMessage);
        return this;
    }

}
