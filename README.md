# BLANKAPP-VALIDATION (正在开发)

专注于 Android 表单验证，用方法链构建你的验证规则。

[![Build Status](https://api.travis-ci.org/lijy91/blankapp-validation.svg?branch=master)](https://travis-ci.org/lijy91/blankapp-validation)

[阅读文档](https://github.com/lijy91/blankapp-validation/blob/master/docs/VALIDATION.md)

## 如何使用

### 支持验证的控件

支持以下控件或派生自以下的控件

- EditText
- CompoundButton

### 添加依赖
1、只需将 [mvn-repo](github.com/lijy91/mvn-repo/) 地址添加到您的项目根目录 build.gradle 文件：
```
repositories {
    maven { url 'https://raw.githubusercontent.com/lijy91/mvn-repo/master/' }
    jcenter()
}
```

2、在要集成的模块 build.gradle 文件中添加依赖，如下：
```
dependencies {
    compile 'org.blankapp:blankapp-validation:0.0.1-alpha@aar'
}
```

### 快速集成

```java
// 实例化一个验证器
final Validator validator = new Validator();

// 构建你的规则链并添加到验证器
validator.add(Rule.with(mEtEmail).required().email());
validator.add(Rule.with(mEtUsername).required().alphaDash());
validator.add(Rule.with(mEtName).required().minLength(2).maxLength(32));
validator.add(Rule.with(mEtPassword).required().minLength(6).maxLength(32));
validator.add(Rule.with(mEtBirthday).required().date("yyyy-MM-dd").before(DateValidator.TODAY));
validator.add(Rule.with(mEtAge).required().integer());
validator.add(Rule.with(mEtBio).required().maxLength(255));
validator.add(Rule.with(mCbAccepted).accepted());

// 设置验证失败处理器
validator.setErrorHandler(new DefaultHandler());

// 设置验证监听器
validator.setValidatorListener(new ValidationListener() {
    @Override
    public void onValid() {
        Toast.makeText(MainActivity.this, "验证通过", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInValid(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            Log.w("MainActivity", "Id:" + error.view().getId());
            for (String key : error.errorMessages().keySet()) {
                Log.e("MainActivity", error.errorMessages().get(key));
            }
        }
        Toast.makeText(MainActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
    }
});
```

### 添加自定义的字段名
在你构建规则时，如果不指定字段名，程序将会根据传入 View 的 Id 按规则从 strings 资源里获取字段名

资源名的命名规则为：前缀 `validation_field_` 与移除前缀后的 `viewId` 拼接组成。
如果 `viewId` 为 `edt_email`，则该字段名的资源名为 `validation_field_email`

P.S. 控件的命名规则必须为下划线命名法，并且必须包含前缀，如 `btn_`、`edt_` 等 

示例：
```xml
<string name="validation_field_email">电子邮箱</string>
<string name="validation_field_username">用户名</string>
<string name="validation_field_name">姓名</string>
<string name="validation_field_password">密码</string>
<string name="validation_field_birthday">生日</string>
<string name="validation_field_age">年龄</string>
<string name="validation_field_bio">简介</string>
```
### 自定义默认错误消息

将需要修改的错误消息添加到你的项目资源文件里，[查看全部可配置的错误消息](https://github.com/lijy91/blankapp-validation/blob/master/library/src/main/res/values-zh-rCN/strings.xml)。
 
示例：
```xml
...
<string name="validation_error_message_accepted">%1$s 必须接受。</string>
<string name="validation_error_message_after">%1$s 必须是一个在 %2$s 之后的日期。</string>
<string name="validation_error_message_alpha">%1$s 只能由字母组成。</string>
<string name="validation_error_message_alpha_dash">%1$s 只能由字母、数字和破折号组成。</string>
<string name="validation_error_message_alpha_num">%1$s 只能由字母和数字组成。</string>
...
```

## 编译

```
$ ./gradlew clean library:build library:uploadArchives
```

## License

    Copyright (C) 2015 JianyingLi <lijy91@foxmail.com>

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
