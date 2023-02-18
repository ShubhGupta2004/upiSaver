package com.example.upisaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;

public class setLimitForMonths extends AppCompatActivity {
    Button saveLimit;
    Realm realm;
    EditText days;
    EditText amnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_limit_for_months);
        Realm.init(getApplicationContext());
        saveLimit = findViewById(R.id.saveLimit);
        realm = Realm.getDefaultInstance();

        saveLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amnt = findViewById(R.id.getAmountLimit);
                days = findViewById(R.id.noOfDaysInput);

                int num = Integer.parseInt(amnt.getText().toString());
                String date1 = days.getText().toString();
                try {
                    realm.beginTransaction();
                    transaction trans = realm.createObject(transaction.class, 0);
                    trans.setAmount((int) num);
                    trans.setDate(date1);
                    trans.setType(false);
                    trans.setUsage("usage");
                    trans.setMsgId(1);
                    realm.commitTransaction();

                    Toast.makeText(setLimitForMonths.this, "Limit Saved.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    realm.cancelTransaction();
                    //workUpdate(num, date1);
                    Toast.makeText(setLimitForMonths.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                }
            }
        });

//    }
//    private void workUpdate(int num,String date){
//        final transaction modal = realm.where(transaction.class).equalTo("id", 0).findFirst();
//        updateCourse(modal, num, date);
//    }
//    private void updateCourse(transaction modal,int num,String date){
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(@NonNull Realm realm) {
//
//                // on below line we are setting data to our modal class
//                // which we get from our edit text fields.
//                modal.setAmount(num);
//                modal.setDate(date);
//                modal.setUsage("courseDuration");
//                modal.setType(true);
//                modal.setMsgId(1);
//                // inside on execute method we are calling a method to copy
//                // and update to real m database from our modal class.
//                realm.copyToRealmOrUpdate(modal);
//            }
//        });
//    }
    }
}