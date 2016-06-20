package org.blankapp.validation.validators;

import android.support.annotation.StringDef;
import android.text.TextUtils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TypeValidator extends AbstractValidator<String> {

    @StringDef({
            DATE,
            INTEGER,
            STRING,
    })
    public @interface ValueType {}

    public static final String DATE          = "date";
    public static final String INTEGER       = "integer";
    public static final String STRING        = "string";

    private String mValueType;
    private String mValueFormat;

    public TypeValidator(@ValueType String valueType, String valueFormat) {
        this.mValueType = valueType;
        this.mValueFormat = valueFormat;
    }

    @Override
    public boolean isValid(String value) {
        switch (mValueType) {
            case DATE:
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat(mValueFormat);
                    sdf.parse(value);
                } catch (ParseException e) {
                    return false;
                }
                return true;
            case INTEGER:
                try {
                    int number = Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    return false;
                }
                return true;
            case STRING:
                return true;
        }
        return false;
    }

}
