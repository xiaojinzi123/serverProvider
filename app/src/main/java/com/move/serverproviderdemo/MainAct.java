package com.move.serverproviderdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.move.serverprovider.ServerConfigAct;
import com.move.serverprovider.ServerConfigEntity;

public class MainAct extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        startActivityForResult(new Intent(this, ServerConfigAct.class), 2);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            return;
        }

        ServerConfigEntity entity = (ServerConfigEntity) data.getSerializableExtra(ServerConfigAct.KEY_RESULT);
        if (entity == null) {
            return;
        }

        Toast.makeText(this, "entity:" + entity.baseUrl, Toast.LENGTH_SHORT).show();


    }
}
