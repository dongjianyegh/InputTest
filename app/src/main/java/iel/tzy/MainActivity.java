package iel.tzy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import iel.tzy.watcher.TEditText;
import iel.tzy.watcher.TInputConnection;
import iel.tzy.watcher.Toggles;

public class MainActivity extends AppCompatActivity {

    private TEditText editText;
    TInputConnection.BackspaceListener backspaceListener = new TInputConnection.BackspaceListener() {
        @Override
        public boolean onBackspace() {
            Editable editable = editText.getText();

            if(editable.length() == 0){
                return false;
            }

            int index = Math.max(0,editText.getSelectionStart() - 1);

            if(editable.charAt(index) == '@'){
                Toast toast = Toast.makeText(MainActivity.this,"无法删除@字符~",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
                return true;
            }
            return false;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.edit_text);
        editText.setBackSpaceLisetener(backspaceListener);
        Toggles.reset();
        for (int i = 0; i < Toggles.checkIds.length; ++i) {
            final int idx = i;
            ((CheckBox) findViewById(Toggles.checkIds[i])).setChecked(true);
            ((CheckBox) findViewById(Toggles.checkIds[i])).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Toggles.setEnable(idx, isChecked);
                }
            });
        }
    }
}
