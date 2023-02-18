package com.example.upisaver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class EditTransanctionAcitvity extends AppCompatActivity {
    TextView amount;
    ArrayList<transactionViewData> transactionViewData = new ArrayList<>();

    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transanction_acitvity);
        Intent it = getIntent();
        Realm.init(getApplicationContext());
        realm=Realm.getDefaultInstance();
        transactionViewData tr ;
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmQuery<transaction> std = realm.where(transaction.class);
                RealmResults<transaction> rst = std.findAll();
                for (int i = rst.size()-2; i >=0; i--) {
                    transaction tr = rst.get(i);
                    assert tr != null;
                    transactionViewData.add(new transactionViewData(tr.getAmount(),tr.getDate(),tr.getUsage(),tr.isType(),tr.getId()));
                }

            }
        });
        int a1 = it.getIntExtra("id",0);
        transactionViewData transactionViewData1 = transactionViewData.get(a1);
        amount=findViewById(R.id.AmountEdit);
        amount.setText(String.valueOf(transactionViewData1.getAmount()));
        Toast.makeText(this,"Date: "+transactionViewData1.getData(),Toast.LENGTH_SHORT).show();

    }
}