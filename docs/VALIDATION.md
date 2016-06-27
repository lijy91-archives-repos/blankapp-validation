# Validation

### 验证规则顺序

> 必须规则 >> 类型规则 >> 逻辑规则 >> 扩展规则

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

##### 3、逻辑规则
```

```

##### 4、扩展规则
```

```

### 可用的验证规则

##### accepted
验证字段值是否为 true。这在确认「服务条款」是否同意时相当有用。

##### after
验证字段值是否是在指定日期之后。

##### alpha
验证字段值是否仅包含字母字符。

##### alphaDash
验证字段值是否仅包含字母、数字、破折号（ - ）以及下划线（ _ ）。

##### alphaNum
验证字段值是否仅包含字母、数字。

##### before
验证字段值是否是在指定日期之前。

##### between
验证字段值是否介于指定的 min 和 max 之间。

##### confirmed
验证字段值必须和 field_confirmation 的字段值一致。例如，如果要验证的字段是 password，就必须和输入数据里的 password_confirmation 的值保持一致。

##### date
验证字段值是否为有效日期。

##### digits
验证字段值是否为 numeric 且长度为 value。

##### digitsBetween
验证字段值的长度是否在 min 和 max 之间。

##### email
验证字段值是否符合 E-Mail 格式。

##### in
TODO

##### integer
验证字段值是否是整数。

##### ip
验证字段值是否符合 IP Address 的格式。

##### json
验证字段值是否是一个有效的 JSON 字符串。

##### max
验证字段值必须小于或等于 value 。

##### maxLength
验证字段值的长度不能大于 value 个字符。

##### min
验证字段值必须大于或等于 value。

##### minLength
验证字段值的长度至少为 value 个字符。

##### notIn
TODO

##### numeric
验证字段值是否为数值。

##### regex
验证字段值是否符合指定的正则表达式。

##### required
验证字段值必须存在输入数据，且不为空。字段符合下方任一条件时即为「空」

- 该值为 null。
- 该值为空字符串。

##### url
验证字段值是否为一个有效的网址。

