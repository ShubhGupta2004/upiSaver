package com.example.upisaver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class msgView extends AppCompatActivity {
    ArrayList<transactionViewData> transactionViewData;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_view);
        Realm.init(getApplicationContext());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        transactionViewData = new ArrayList<>();
        realm=Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmQuery<transaction> std = realm.where(transaction.class);
                RealmResults<transaction> rst = std.findAll();
                for (int i = 0; i < rst.size(); i++) {
                    transaction tr = rst.get(i);
                    assert tr != null;
                    transactionViewData.add(new transactionViewData(tr.getAmount(),tr.getDate(),tr.getUsage(),tr.isType(),tr.getId()));
                }
            }
        });

        transactionViewAdapter transactionViewAdapter = new transactionViewAdapter(transactionViewData);
        recyclerView.setAdapter(transactionViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}