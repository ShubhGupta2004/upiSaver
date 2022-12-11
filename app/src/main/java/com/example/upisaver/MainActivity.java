package com.example.upisaver;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    ImageButton add;
    boolean b = true;
    BottomSheetDialog bottomSheetDialog;
    LinearLayout ll;
    Switch inExp;
    boolean saveOption = false;
    private final int SMS_Code = 1;
    ImageView imgIcon;
    TextView textIt;
    EditText changeEdit;
    TextView transHeading;
    ImageButton img1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = changeEdit.getText().toString();
                int num = Integer.parseInt(str);
                Toast.makeText(MainActivity.this,"Saved: "+num,Toast.LENGTH_SHORT).show();
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
                if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_SMS)==PackageManager.PERMISSION_GRANTED){
                    Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);

                    if (cursor.moveToFirst()) { // must check the result to prevent exception
                        do {
                            String msgData = "";
                            for(int idx=0;idx<cursor.getColumnCount();idx++)
                            {
                                msgData += " " + cursor.getColumnName(idx) + ":" + cursor.getString(idx);
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


                transHeading.setText(msg.toString());
                //startActivity(new Intent(MainActivity.this,msgView.class));
            }
        });
    }

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

    //    public void saveIt(View c){
//        String str = changeEdit.getText().toString();
//        int num = Integer.parseInt(str);
//        Toast.makeText(MainActivity.this,"Saved: "+num,Toast.LENGTH_SHORT).show();
//    }

}