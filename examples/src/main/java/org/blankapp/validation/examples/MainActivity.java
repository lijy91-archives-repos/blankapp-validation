package org.blankapp.validation.examples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.blankapp.validation.Rule;
import org.blankapp.validation.ValidationError;
import org.blankapp.validation.ValidationListener;
import org.blankapp.validation.Validator;
import org.blankapp.validation.handlers.DefaultErrorHandler;

import static org.blankapp.validation.validators.DateValidator.TODAY;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText mEdtEmail;
    private EditText mEdtUsername;
    private EditText mEdtName;
    private EditText mEdtPassword;
    private EditText mEdtBirthday;
    private EditText mEdtAge;
    private EditText mEdtBio;
    private CheckBox mCbAccepted;
    private Button mBtnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mEdtEmail    = (EditText) findViewById(R.id.edt_email);
        this.mEdtUsername = (EditText) findViewById(R.id.edt_username);
        this.mEdtName     = (EditText) findViewById(R.id.edt_name);
        this.mEdtPassword = (EditText) findViewById(R.id.edt_password);
        this.mEdtBirthday = (EditText) findViewById(R.id.edt_birthday);
        this.mEdtAge      = (EditText) findViewById(R.id.edt_age);
        this.mEdtBio      = (EditText) findViewById(R.id.edt_bio);
        this.mCbAccepted  = (CheckBox) findViewById(R.id.cb_accepted);
        this.mBtnSubmit   = (Button) findViewById(R.id.btn_submit);

        // 实例化一个验证器
        final Validator validator = new Validator();

        // 构建你的规则链并添加到验证器
        validator.add(Rule.with(mEdtEmail).required().email());
        validator.add(Rule.with(mEdtUsername).required().alphaDash());
        validator.add(Rule.with(mEdtName).minLength(2).maxLength(32));
        validator.add(Rule.with(mEdtPassword).required().minLength(6).maxLength(32));
        validator.add(Rule.with(mEdtBirthday).required().date("yyyy-MM-dd").before(TODAY));
        validator.add(Rule.with(mEdtAge).required().between(18, 60));
        validator.add(Rule.with(mEdtBio).required().maxLength(5));
        validator.add(Rule.with(mCbAccepted).accepted());

        // 设置验证失败处理器
        validator.setErrorHandler(new DefaultErrorHandler());

        // 设置验证监听器
        validator.setValidatorListener(new ValidationListener() {
            @Override
            public void onValid() {
                Toast.makeText(MainActivity.this, "验证通过。", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInValid(List<ValidationError> errors) {
                StringBuilder sb = new StringBuilder();
                for (ValidationError error : errors) {
                    Log.w("MainActivity", "Name:" + getResources().getResourceName(error.view().getId()));
                    for (String key : error.errorMessages().keySet()) {
                        Log.e("MainActivity", error.errorMessages().get(key));
                        sb.append(error.errorMessages().get(key)).append("\n");
                    }
                }
                Toast.makeText(MainActivity.this, "验证失败。\n" + sb.toString(), Toast.LENGTH_LONG).show();
            }
        });

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });
    }
}
