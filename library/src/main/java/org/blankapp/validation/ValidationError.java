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

import android.view.View;

import java.util.Map;

public class ValidationError {

    private String mName;
    private View mView;
    private Map<String, String> mErrorMessages;

    private ValidationError() {
    }

    public ValidationError(String name, View view, Map<String, String> message) {
        this.mName = name;
        this.mView = view;
        this.mErrorMessages = message;
    }

    public String name() {
        return mName;
    }

    public View view() {
        return mView;
    }

    public Map<String, String> errorMessages() {
        return mErrorMessages;
    }

}
