package com.example.groupproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import Model.AllUsers;
import Model.Customer;

public class CustomerMainMenuActivity extends AppCompatActivity {

    AllUsers allUsers;
    Customer customer;
    private int SECOND_ACTIVITY_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main_menu);

        final Intent intent = getIntent();
        allUsers = (AllUsers)intent.getSerializableExtra("AllUsers");
        customer = (Customer) intent.getSerializableExtra("customer");
        final Context context = this;

        TextView textView = (TextView) findViewById(R.id.customer_credits);
        textView.setText(Double.toString(customer.getCredit()));
        //manage credits
        Button butCredits = findViewById(R.id.manage_credits);
        butCredits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerMainMenuActivity.this, CreditActivity.class);
                //send string id via intent so credit activity can get either customer or owner
                intent.putExtra("id",customer.getID());
                intent.putExtra("AllUsers",allUsers);
                startActivity(intent);
            }
        });



        //cancel button
        Button butCancel = findViewById(R.id.sign_out_button);
        butCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send data back to MainActivity
                intent.putExtra("AllUsers", allUsers);
                setResult(RESULT_OK, intent);
                finish();
            }
        });


        // view appointment
        /*Button viewApts = findViewById(R.id.viewapts);
        viewApts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerMainMenuActivity.this, DisplayCustomerAppointmentListActivity.class);
                //send string id via intent so credit activity can get either customer or owner
                intent.putExtra("customer",customer);
                intent.putExtra("AllUsers",allUsers);
                startActivity(intent);
            }
        });*/

        Button manAccount = findViewById(R.id.manage_customer_account);
        manAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerMainMenuActivity.this, ManageCustomerAccountActivity.class);
                //send string id via intent so credit activity can get either customer or owner
                //intent.putExtra("id",owner.getID());
                intent.putExtra("AllUsers",allUsers);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                allUsers = (AllUsers)data.getSerializableExtra("AllUsers");
                customer = (Customer)data.getSerializableExtra("customer");
            }
        }

    }
}
