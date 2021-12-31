package com.superdroid.test.activity.mymemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MyMemo extends AppCompatActivity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);

        editText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                int start = editText.getSelectionStart();
                int end = editText.getSelectionEnd();

                // 선택된 영역의 시작~끝을 가져온다
                String str = editText.getText().toString();
                str = str.substring(start,end);
                Intent intent = new Intent();
                switch(menuItem.getItemId()) {
                    case android.R.id.shareText: {
                        Character c = str.charAt(0);
                        if (Character.isDigit(c)) {
                            intent.setAction(Intent.ACTION_SEND);
                            intent.putExtra("number", str);
                            startActivity(intent);
                            break;
                        } else {
                            intent.setAction(Intent.ACTION_SEND);
                            intent.putExtra("link", str);
                            startActivity(intent);
                        }
                    }
                }
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
            }
        });
    }


}