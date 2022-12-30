package com.example.upisaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    ImageButton add;
    boolean b = true;
    BottomSheetDialog bottomSheetDialog;
    LinearLayout ll;
    SwitchMaterial inExp;
    boolean saveOption = false;
    private final int SMS_Code = 1;
    ImageView imgIcon;
    TextView textIt;
    EditText changeEdit;
    TextView transHeading;
    ImageButton img1;
    Button viewTransaction;
    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(getApplicationContext());

        realm=Realm.getDefaultInstance();
        bottomSheetDialog= new BottomSheetDialog(MainActivity.this,R.style.BottomSheet);
        View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.bottom_sheet_dialog,(LinearLayout)findViewById(R.id.bottomSheet));
        bottomSheetDialog.setContentView(v);
        add=findViewById(R.id.addTrans);
        ll=bottomSheetDialog.findViewById(R.id.saveIt);
        inExp= bottomSheetDialog.findViewById(R.id.switch1);
        changeEdit=bottomSheetDialog.findViewById(R.id.editTextNumber);
        imgIcon=bottomSheetDialog.findViewById(R.id.moneyIcon);
        textIt=bottomSheetDialog.findViewById(R.id.IncomeExpense);
        img1= findViewById(R.id.showSMS);
        transHeading=findViewById(R.id.transanctionH);
        viewTransaction=findViewById(R.id.viewTransactionButton);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity();
                if(b) {
                   // add.setImageResource(R.drawable.img_1);
                    add.setBackgroundResource(R.drawable.img_1);
                    b=false;
                }else{
                    add.setBackgroundResource(R.drawable.img);
                    b=true;
                }

                bottomSheetDialog.show();
            }
        });

        viewTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,msgView.class));
            }
        });

        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = changeEdit.getText().toString();
                final int num = Integer.parseInt(str);
                final String date1 = "";
                final String usage = " ";
                final boolean Switch1=inExp.isChecked();
                Number id = realm.where(transaction.class).max("id");

                // on below line we are
                // creating a variable for our id.
                int nextId;

                // validating if id is null or not.
                if (id == null) {
                    // if id is null
                    // we are passing it as 1.
                    nextId = 1;
                } else {
                    // if id is not null then
                    // we are incrementing it by 1
                    nextId = id.intValue() + 1;
                }

                try{
                    realm.beginTransaction();
                    transaction trans = realm.createObject(transaction.class,nextId);
                    trans.setAmount(num);
                    trans.setDate(date1);
                    trans.setType(Switch1);
                    trans.setUsage(usage);
                    realm.commitTransaction();
                }catch (Exception e){
                    System.out.println(e.getMessage());
                    realm.cancelTransaction();
                }

                Toast.makeText(MainActivity.this,"Saved: "+num,Toast.LENGTH_SHORT).show();

                realm.executeTransactionAsync(new Realm.Transaction(){

                    @Override
                    public void execute(@NonNull Realm realm) {
                        RealmQuery<transaction> std = realm.where(transaction.class);
                        RealmResults<transaction> rst = std.findAll();
                        for (int i = 0; i < rst.size(); i++) {
                            transaction tr = rst.get(i);
                            assert tr != null;
                            System.out.println(tr.getId()+" "+tr.getAmount()+" "+tr.getDate()+".");
                        }
                    }
                });
            }
        });

        inExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inExp.isChecked()){
                    imgIcon.setImageResource(R.drawable.img_4);
                    textIt.setText("Expense");
                    saveOption=true;
                }else{
                    imgIcon.setImageResource(R.drawable.img_3);
                    textIt.setText("Income");
                    saveOption=false;
                }
            }
        });


        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder msg = new StringBuilder();
                HashMap<Integer,String> msgDataTemp = new HashMap<>();

                if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_SMS)==PackageManager.PERMISSION_GRANTED){
                    Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);

                    if (cursor.moveToFirst()) { // must check the result to prevent exception
                        do {
                            String msgData = "";
                            for(int idx=0;idx<cursor.getColumnCount();idx++){
                                msgData += " " + cursor.getColumnName(idx) + ":" + cursor.getString(idx);
                                String s = cursor.getString(idx);
                                if(s.contains("UPI")){
                                    msgDataTemp.put(idx,s);
                                }
                                msgData+="\n\n\n";
                            }
                            // use msgData
                            msg.append(msgData).append("\n\n\n");
                        } while (cursor.moveToNext());
                        cursor.close();
                    } else {
                        Toast.makeText(MainActivity.this,"No msg",Toast.LENGTH_SHORT).show();
                        // empty box, no SMS
                    }
                }else{
                    requestSmsPermission();
                }

                transHeading.setText(msgDataTemp+" "+msg);
                //startActivity(new Intent(MainActivity.this,msgView.class));
            }
        });
    }


    //permission for the message
    private void requestSmsPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_SMS)){
            new AlertDialog.Builder(this).setTitle("Permission Needed").setMessage("Required for the app to work properly").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.READ_SMS},SMS_Code);
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).create().show();
        }else{
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_SMS},SMS_Code);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_Code) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Permission granted.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Permission Denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}