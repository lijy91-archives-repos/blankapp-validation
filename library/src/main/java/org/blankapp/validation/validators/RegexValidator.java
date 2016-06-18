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

import java.util.regex.Pattern;

import static org.blankapp.validation.validators.RegexValidator.Patterns.ALPHA;
import static org.blankapp.validation.validators.RegexValidator.Patterns.ALPHA_DASH;
import static org.blankapp.validation.validators.RegexValidator.Patterns.ALPHA_NUM;
import static org.blankapp.validation.validators.RegexValidator.Patterns.DOMAIN_NAME;
import static org.blankapp.validation.validators.RegexValidator.Patterns.EMAIL_ADDRESS;
import static org.blankapp.validation.validators.RegexValidator.Patterns.IP_ADDRESS;
import static org.blankapp.validation.validators.RegexValidator.Patterns.WEB_URL;

public class RegexValidator extends AbstractValidator<CharSequence> {

    private Pattern pattern;

    public RegexValidator(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    public RegexValidator(Pattern pattern) {
        this.pattern = pattern;
        if (EMAIL_ADDRESS.equals(pattern)) {
        } else if (IP_ADDRESS.equals(pattern)) {
        } else if (WEB_URL.equals(pattern)) {
        } else if (DOMAIN_NAME.equals(pattern)) {
        } else if (ALPHA.equals(pattern)) {
        } else if (ALPHA_DASH.equals(pattern)) {
        } else if (ALPHA_NUM.equals(pattern)) {
        }
    }

    @Override
    public boolean isValid(CharSequence value) {
        return pattern.matcher(value).matches();
    }

    public static class Patterns {
        public static final Pattern EMAIL_ADDRESS = android.util.Patterns.EMAIL_ADDRESS;
        public static final Pattern IP_ADDRESS = android.util.Patterns.IP_ADDRESS;
        public static final Pattern WEB_URL = android.util.Patterns.WEB_URL;
        public static final Pattern DOMAIN_NAME = android.util.Patterns.DOMAIN_NAME;
        public static final Pattern ALPHA = Pattern.compile("\\p{Alpha}");
        public static final Pattern ALPHA_DASH = Pattern.compile("\\p{Alpha}");
        public static final Pattern ALPHA_NUM = Pattern.compile("\\p{Alpha}");

    }

}
