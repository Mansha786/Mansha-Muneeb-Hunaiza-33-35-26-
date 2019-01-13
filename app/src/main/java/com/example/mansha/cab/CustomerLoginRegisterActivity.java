package com.example.mansha.cab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CustomerLoginRegisterActivity extends AppCompatActivity {
    private Button customerLoginButton;
    private Button customerRegisterButton;
    private TextView customerRegisterLink;
    private TextView customerStatus;
    private EditText emailCustomer;
    private EditText passwordCustomer;
    private ProgressDialog loadingbar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login_register);
        mAuth= FirebaseAuth.getInstance();

        customerLoginButton=(Button) findViewById(R.id.customer_login_button);
        customerRegisterButton=(Button) findViewById(R.id.customer_register_button);
        customerRegisterLink=(TextView) findViewById(R.id.register_customer_link);
        customerStatus=(TextView) findViewById(R.id.customer_status);
        emailCustomer=(EditText) findViewById(R.id.email_customer);
        passwordCustomer=(EditText) findViewById(R.id.password_customer);

        loadingbar =new ProgressDialog(this);
        customerRegisterButton.setVisibility(View.INVISIBLE);
        customerRegisterButton.setEnabled(false);


        customerRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customerLoginButton.setVisibility(View.INVISIBLE);
                customerRegisterLink.setVisibility(View.INVISIBLE);
                customerStatus.setText("Register Customer");
                customerRegisterButton.setVisibility(View.VISIBLE);
                customerRegisterButton.setEnabled(true);
            }
        });
        customerRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=emailCustomer.getText().toString();
                String password=passwordCustomer.getText().toString();
                RegisterCustomer(email,password);
            }
        });

        customerLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String email=emailCustomer.getText().toString();
                String password=passwordCustomer.getText().toString();
                SiginCustomer(email,password);

            }
        });


    }

    private void SiginCustomer(String email, String password)
    {
        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(CustomerLoginRegisterActivity.this, "Please Write Email...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(CustomerLoginRegisterActivity.this, "Please Write Passward...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingbar.setTitle("Customer Login");
            loadingbar.setMessage("Please wait,while we are checking your data");
            loadingbar.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(CustomerLoginRegisterActivity.this, "Customer logged in Sucessfully...", Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                        Intent customerIntend=new Intent(CustomerLoginRegisterActivity.this,CustomersMapActivity.class);
                        startActivity(customerIntend);
                    }

                    else
                    {
                        Toast.makeText(CustomerLoginRegisterActivity.this, "Login Unsucessfull,Please Try Again...", Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                    }

                }
            });
        }

    }

    private void RegisterCustomer(String email, String password) {
        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(CustomerLoginRegisterActivity.this, "Please Write Email...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(CustomerLoginRegisterActivity.this, "Please Write Passward...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingbar.setTitle("Customer Registration");
            loadingbar.setMessage("Please wait,while we are register your data");
            loadingbar.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(CustomerLoginRegisterActivity.this, "Customer Register Sucessfull...", Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                        Intent customerIntend=new Intent(CustomerLoginRegisterActivity.this,CustomersMapActivity.class);
                        startActivity(customerIntend);

                    }
                    else
                    {
                        Toast.makeText(CustomerLoginRegisterActivity.this, "Email and passward are incorrect plz try again...", Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                    }

                }
            });
        }
    }
}
