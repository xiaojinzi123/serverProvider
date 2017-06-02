package com.move.serverprovider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ServerConfigAct extends AppCompatActivity {

    public static final int RESULT_CODE = 99;


    public static final String KEY_RESULT = "data";


    private ListView lv = null;

    private List<ServerConfigEntity> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_server_config);
        lv = (ListView) findViewById(R.id.lv);

        try {
            String json = SPUtil.get(this, "data", "[]");
            JSONArray ja = new JSONArray(json);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jb = ja.getJSONObject(i);
                this.data.add(new ServerConfigEntity(
                        jb.optBoolean("isPro"), jb.optString("baseUrl"), jb.optBoolean("isSelect")
                ));
            }
        } catch (Exception e) {
        }

        final CommonAdapter<ServerConfigEntity> adapter
                = new CommonAdapter<ServerConfigEntity>(this, ServerConfigAct.this.data, R.layout.act_server_config_item) {

            @Override
            public void convert(CommonViewHolder h, final ServerConfigEntity item, final int position) {

                final CheckBox cb = h.getView(R.id.cb);
                TextView tv = h.getView(R.id.tv);
                final Switch sw = h.getView(R.id.sw);
                Button bt_delete = h.getView(R.id.bt_delete);
                Button bt_edit = h.getView(R.id.bt_edit);

                bt_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data.remove(position);
                        notifyDataSetChanged();
                    }
                });

                bt_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, EditServerInfoAct.class);
                        intent.putExtra("data", item);
                        intent.putExtra("position", position);
                        startActivityForResult(intent, 1);
                    }
                });

                cb.setChecked(item.isSelect);
                tv.setText(item.baseUrl);
                sw.setChecked(item.isPro);

                cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (ServerConfigEntity serverConfigEntity : data) {
                            serverConfigEntity.isSelect = false;
                        }
                        item.isSelect = cb.isChecked();
                        notifyDataSetChanged();
                    }
                });

                sw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        item.isPro = sw.isChecked();
                    }
                });


            }

        };

        lv.setAdapter(adapter);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (intent == null) {
            return;
        }

        ServerConfigEntity entity = (ServerConfigEntity) intent.getSerializableExtra("data");

        if (entity == null) {
            return;
        }

        if (entity.isSelect) {
            for (ServerConfigEntity serverConfigEntity : data) {
                serverConfigEntity.isSelect = false;
            }
        }

        int position = intent.getIntExtra("position", -1);
        if (position > -1) {
            data.set(position, entity);
        } else {
            data.add(0, entity);
        }
        ((BaseAdapter) lv.getAdapter()).notifyDataSetChanged();

    }

    public void add(View view) {

        Intent intent = new Intent(this, EditServerInfoAct.class);
        startActivityForResult(intent, 1);

    }


    @Override
    public void finish() {

        try {
            JSONArray ja = new JSONArray();
            for (int i = 0; i < data.size(); i++) {
                ServerConfigEntity entity = data.get(i);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("isPro", entity.isPro);
                jsonObject.put("isSelect", entity.isSelect);
                jsonObject.put("baseUrl", entity.baseUrl);
                ja.put(i, jsonObject);
            }
            String json = ja.toString();
            SPUtil.put(this, "data", json);
        } catch (Exception e) {
        }

        for (ServerConfigEntity entity : data) {
            if (entity.isSelect) {
                Intent intent = new Intent();
                intent.putExtra(KEY_RESULT, entity);
                setResult(RESULT_CODE, intent);
                break;
            }
        }

        // 保存数据
        super.finish();

    }
}
