# Validation

### 验证规则顺序

##### 1、必需规则
```
required
```
##### 2、类型规则
如果未设置类型规则，则默认为字符串
```
date
integer
numeric
```
##### 3、其他规则
```

```

### 可用的验证规则

##### accepted
验证字段值是否为 true。这在确认「服务条款」是否同意时相当有用。

##### after

##### alpha
验证字段值是否仅包含字母字符。

##### alphaDash
验证字段值是否仅包含字母、数字、破折号（ - ）以及下划线（ _ ）。

##### alphaNum
验证字段值是否仅包含字母、数字。

##### before

##### between

##### confirmed

##### date
验证字段值是否为有效日期。

##### different

##### digits

##### digitsBetween

##### email
验证字段值是否符合 E-Mail 格式。

##### in

##### integer
验证字段值是否是整数。

##### ip
验证字段值是否符合 IP Address 的格式。

##### json
验证字段是否是一个有效的 JSON 字符串。

##### max

##### maxLength

##### min

##### minLength

##### notIn

##### numeric

##### regex
验证字段值是否符合指定的正则表达式。

##### required
验证字段必须存在输入数据，且不为空。字段符合下方任一条件时即为「空」

- 该值为 null。
- 该值为空字符串。

##### url
验证字段值是否为一个有效的网址。

