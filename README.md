# BLANKAPP-VALIDATION (正在开发)

[![Build Status](https://api.travis-ci.org/lijy91/blankapp-validation.svg?branch=master)](https://travis-ci.org/lijy91/blankapp-validation)

[阅读文档](https://github.com/lijy91/blankapp-validation/blob/master/docs/VALIDATION.md)

## 如何使用

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

// 添加指定的验证规则到验证器
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
