package com.move.serverprovider;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class EditServerInfoAct extends AppCompatActivity {

    EditText et;
    CheckBox cb_is_select;
    CheckBox cb_is_pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_edit_server_info);

        et = (EditText) findViewById(R.id.et);
        cb_is_select = (CheckBox) findViewById(R.id.cb_is_select);
        cb_is_pro = (CheckBox) findViewById(R.id.cb_is_pro);

        ServerConfigEntity entity = (ServerConfigEntity) getIntent().getSerializableExtra("data");
        if (entity != null) {
            et.setText(entity.baseUrl);
            cb_is_select.setChecked(entity.isSelect);
            cb_is_pro.setChecked(entity.isPro);
        }

        findViewById(R.id.bt)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ServerConfigEntity entity = new ServerConfigEntity(
                                cb_is_pro.isChecked(),
                                et.getEditableText().toString(),
                                cb_is_select.isChecked()
                        );

                        Intent intent = new Intent();
                        intent.putExtra("data", entity);
                        intent.putExtra("position", getIntent().getIntExtra("position", -1));

                        setResult(2, intent);
                        finish();

                    }
                });

    }
}
