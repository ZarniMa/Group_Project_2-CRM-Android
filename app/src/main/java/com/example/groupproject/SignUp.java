package com.example.groupproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import Model.AllUsers;
import Model.Owner;
import Model.RetObject;

public class SignUp extends AppCompatActivity {

    Button confirm;
    EditText nameEdit;
    EditText idEdit;
    EditText pw;
    AllUsers allUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // Initialize the sign up
        final Intent intent = getIntent();
        allUsers = (AllUsers)intent.getSerializableExtra("AllUsers");

        // Get data from user input
        final RadioGroup radGroup = findViewById(R.id.select_user);
        final RadioButton radOwner = findViewById(R.id.radOwner);
        final RadioButton radCust = findViewById(R.id.radCustomer);

        final EditText editName = findViewById(R.id.edit_name);
        final EditText editID = findViewById(R.id.edit_id);
        final EditText editPW = findViewById(R.id.edit_pw);
        final EditText editAssociate = findViewById(R.id.edit_associate);

        //confirm button
        Button butConfirm = findViewById(R.id.confirm);
        butConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getEditableText().toString();
                String id = editID.getEditableText().toString();
                String pw = editPW.getEditableText().toString();
                String as = editAssociate.getEditableText().toString();
                int selected = radGroup.getCheckedRadioButtonId();
                RetObject retValue = null;
                if (selected == radOwner.getId()) {
                    retValue = signUpAsOwner(name, id, pw);
                } else if (selected == radCust.getId()) {
                    retValue = signUpAsCustomer(name, id, pw,as);
                }

                if (retValue == null) {
                    retValue = new RetObject();
                    retValue.setMsg("Failed to sign up! Please select one of the sign up options" +
                            " at the top.");
                }
                //After processing the sign up data
                Toast.makeText(SignUp.this, retValue.getMsg(), Toast.LENGTH_SHORT).show();
                if (retValue.getBool() == true) {
                    intent.putExtra("AllUsers", allUsers);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        //cancel button
        Button butCancel = findViewById(R.id.cancel);
        butCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected RetObject signUpAsOwner(String _name, String _id, String _pw) {
        //Check if business already existed
        RetObject ret = new RetObject();
        if (!allUsers.businessIsExisted(_id, _name).getBool()) {
            allUsers.addOwner(new Owner(_id,_pw,_name, 0));
            ret.setBool(true);
            ret.setMsg("Sign up successfully! Owner size: " + allUsers.getOwnerSize());
        } else {
            ret.setBool(false);
            ret.setMsg(allUsers.businessIsExisted(_id, _name).getMsg());
        }
        return ret;
    }

    protected RetObject signUpAsCustomer(String _name, String _id, String _pw, String _aName) {
        RetObject ret = new RetObject();
        return ret;
    }
}