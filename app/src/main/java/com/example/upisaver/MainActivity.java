package com.example.upisaver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

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
    ImageView imgIcon;
    TextView textIt;
    EditText changeEdit;
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
    }
//    public void saveIt(View c){
//        String str = changeEdit.getText().toString();
//        int num = Integer.parseInt(str);
//        Toast.makeText(MainActivity.this,"Saved: "+num,Toast.LENGTH_SHORT).show();
//    }

}