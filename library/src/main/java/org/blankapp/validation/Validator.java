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

import android.util.Log;
import android.view.View;

import org.blankapp.validation.validators.AbstractValidator;
import org.blankapp.validation.validators.RequiredValidator;
import org.blankapp.validation.validators.TypeValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Validator {

    private static final String TAG = "Validator";

    private List<Rule> mRules;
    private List<ValidationError> mErrors;
    private ErrorHandler mErrorHandler;
    private ValidationListener mValidationListener;

    public Validator() {
        this(null);
    }

    public Validator(ErrorHandler errorHandler) {
        this.mRules = new ArrayList<>();
        this.mErrors = new ArrayList<>();
        this.mErrorHandler = errorHandler;
    }

    public void add(Rule rule) {
        mRules.add(rule);
    }

    public void add(Rule ... rules) {
        mRules.addAll(Arrays.asList(rules));
    }

    public List<Rule> rules() {
        return mRules;
    }

    public List<ValidationError> errors() {
        return mErrors;
    }

    public ErrorHandler getErrorHandler() {
        return mErrorHandler;
    }

    public void setErrorHandler(ErrorHandler errorHandler) {
        this.mErrorHandler = errorHandler;
    }

    public ValidationListener getValidatorListener() {
        return mValidationListener;
    }

    public void setValidatorListener(ValidationListener validationListener) {
        this.mValidationListener = validationListener;
    }

    @SuppressWarnings("unchecked")
    public boolean validate() {
        mErrors.clear();
        for (Rule rule : mRules) {
            String fieldName = rule.name();
            View view = rule.view();
            Object value = null;

            Map<String, String> errorMessages = new LinkedHashMap<>();

            for (String ruleName : rule.validators().keySet()) {
                AbstractValidator validator = rule.validators().get(ruleName);
                // 必须及类型验证器校验的值均为字符串
                if (validator instanceof RequiredValidator ||
                    validator instanceof TypeValidator) {
                    value = rule.stringValue();
                } else {
                    value = rule.value();
                }

                if (rule.canSkip() || validator.isValid(value)) {
                    continue;
                }

                String errorMessage = rule.errorMessages().get(ruleName);
                errorMessages.put(ruleName, errorMessage);
            }
            if (!errorMessages.isEmpty()) {
                mErrors.add(new ValidationError(fieldName, view, errorMessages));
            }
        }
        if (!mErrors.isEmpty()) {
            if (mErrorHandler != null) mErrorHandler.onInValid(mRules, mErrors);
            if (mValidationListener != null) mValidationListener.onInValid(mErrors);
            return false;
        }
        if (mErrorHandler != null) mErrorHandler.onValid(mRules);
        if (mValidationListener != null) mValidationListener.onValid();
        return true;
    }

}
