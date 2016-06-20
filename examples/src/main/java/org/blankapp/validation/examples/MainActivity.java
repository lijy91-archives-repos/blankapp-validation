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
import org.blankapp.validation.handlers.DefaultHandler;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText mEtEmail;
    private EditText mEtName;
    private EditText mEtPassword;
    private EditText mEtBirthday;
    private EditText mEtBio;
    private CheckBox mCbAccepted;
    private Button mBtnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mEtEmail = (EditText) findViewById(R.id.et_email);
        this.mEtName = (EditText) findViewById(R.id.et_name);
        this.mEtPassword = (EditText) findViewById(R.id.et_password);
        this.mEtBirthday = (EditText) findViewById(R.id.et_birthday);
        this.mEtBio = (EditText) findViewById(R.id.et_bio);
        this.mCbAccepted = (CheckBox) findViewById(R.id.cb_accepted);
        this.mBtnSubmit = (Button) findViewById(R.id.btn_submit);

        final Validator validator = new Validator();

        validator.add(Rule.with(mEtEmail).required().email());
        validator.add(Rule.with(mEtName).required().alpha().minLength(2).maxLength(32));
        validator.add(Rule.with(mEtPassword).required().minLength(6).maxLength(32));
        validator.add(Rule.with(mEtBirthday).required().date("yyyy-MM-dd"));
        validator.add(Rule.with(mEtBio).required().maxLength(255));
        validator.add(Rule.with(mCbAccepted).accepted());

        validator.setErrorHandler(new DefaultHandler());

        validator.setValidatorListener(new ValidationListener() {
            @Override
            public void onValid() {
                Toast.makeText(MainActivity.this, "验证通过。", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInValid(List<ValidationError> errors) {
                for (ValidationError error : errors) {
                    Log.w("MainActivity", "Name:" + error.name());
                    for (String key : error.errorMessages().keySet()) {
                        Log.e("MainActivity", error.errorMessages().get(key));
                    }
                }
                Toast.makeText(MainActivity.this, "验证失败。", Toast.LENGTH_SHORT).show();
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
