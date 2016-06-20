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

public class RegexValidator extends AbstractValidator<CharSequence> {

    private Pattern pattern;

    public RegexValidator(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    public RegexValidator(Pattern pattern) {
        this.pattern = pattern;
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
        public static final Pattern ALPHA = Pattern.compile("^[A-Za-z]+$");
        public static final Pattern ALPHA_DASH = Pattern.compile("^\\w+$");
        public static final Pattern ALPHA_NUM = Pattern.compile("^[A-Za-z0-9]+$");

    }

}
