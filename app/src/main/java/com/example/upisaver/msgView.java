package com.example.upisaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);

        public void onLongItemClick(View view, int position);
    }

    GestureDetector mGestureDetector;

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && mListener != null) {
                    mListener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child));
                }
            }
        });
    }

    @Override public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            return true;
        }
        return false;
    }

    @Override public void onTouchEvent(@NonNull RecyclerView view, @NonNull MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent (boolean disallowIntercept){

    }
}
public class msgView extends AppCompatActivity {
    ArrayList<transactionViewData> transactionViewData;
    Realm realm;
    public int num =0;

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
                for (int i = rst.size()-2; i >=0; i--) {
                    transaction tr = rst.get(i);
                    assert tr != null;
                    transactionViewData.add(new transactionViewData(tr.getAmount(),tr.getDate(),tr.getUsage(),tr.isType(),tr.getId()));
                }
            }
        });

        transactionViewAdapter transactionViewAdapter = new transactionViewAdapter(transactionViewData);
        recyclerView.setAdapter(transactionViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView
                        ,new RecyclerItemClickListener.OnItemClickListener(){
            @Override public void onItemClick(View view, int position) {
                // do whatever
                //view.findViewById(R.id.);
                transactionViewData transactionViewData1 = transactionViewData.get(position);
                num=transactionViewData1.getAmount();
                toast(position);
                Intent it = new Intent(msgView.this,EditTransanctionAcitvity.class);
                it.putExtra("id",position);
                startActivity(it);
            }

            @Override public void onLongItemClick(View view, int position) {
                // do whatever
            }
        })
        );
    }
    private void toast(int num){
        Toast.makeText(msgView.this,num+" ",Toast.LENGTH_SHORT).show();
    }
}