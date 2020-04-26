package com.example.crudrealmdatabase;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudrealmdatabase.adapter.AdapterMain;
import com.example.crudrealmdatabase.adapter.RealmDataAdapter;
import com.example.crudrealmdatabase.model.ModelMasterData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton btnAdd;
    private Realm mRealm = null;
    private AdapterMain adapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RealmResults<ModelMasterData> dataModelDbs;
    private RealmDataAdapter realmDataAdapter;
    private String afdeling, blok, group_sample;
    private String dialogAfdeling, dialogBlok, dialogGroupSample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRealm = Realm.getDefaultInstance();

        setView();
        initView();
        setupRecyclerView();
    }

    private void setView() {
        btnAdd          = findViewById(R.id.btn_add_data);
        mRecyclerView   = findViewById(R.id.recycler_view);
    }

    private void initView() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tampilkanDialogTambah();
            }
        });
    }

    private void setupRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(MainActivity.this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        adapter = new AdapterMain(MainActivity.this);
        mRecyclerView.setAdapter(adapter);

        adapter.ubahData(new AdapterMain.UbahDataInterface() {
            @Override
            public void ubahData(View view, int position) {
                dapatkanData(view, dataModelDbs.get(position));
            }
        });

        adapter.hapusData(new AdapterMain.HapusDataInterface() {
            @Override
            public void hapusData(View view, int position) {
                menghapusData(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void tampilkanDialogTambah() {
        LayoutInflater inflater     = LayoutInflater.from(MainActivity.this);
        View itemView	            = inflater.inflate(R.layout.dialog_isi_data, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(itemView);

        //setView
        final EditText afdelingInput    = itemView.findViewById(R.id.et_afdeling);
        final EditText blokInput        = itemView.findViewById(R.id.et_blok);
        final EditText groupSampleInput = itemView.findViewById(R.id.et_group_sample);

        //set auto all caps
        afdelingInput.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        builder.setCancelable(false).setPositiveButton("Tambah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (afdelingInput.length() == 0 || blokInput.length() == 0 || groupSampleInput.length() == 0) {

                    Toast.makeText(MainActivity.this, "Data belum lengkap", Toast.LENGTH_SHORT).show();
                } else {

                    //getData
                    afdeling        = afdelingInput.getText().toString();
                    blok            = blokInput.getText().toString();
                    group_sample    = groupSampleInput.getText().toString();

                    tambahData(afdeling, blok, group_sample);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Data berhasil ditambah", Toast.LENGTH_SHORT).show();
                }
            }
        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void tambahData(String afdeling, String blok, String group_sample) {
        mRealm.beginTransaction();

        ModelMasterData dataModel = mRealm.createObject(ModelMasterData.class, dapatkanId());
        dataModel.setAfdeling(afdeling);
        dataModel.setBlok(blok);
        dataModel.setGroupSample(group_sample);

        mRealm.commitTransaction();
    }

    private String dapatkanId() {
        String dateTime = "";
        Calendar c = Calendar.getInstance();
        System.out.println("Current time =&gt; " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateTime = df.format(c.getTime());

        return dateTime;
    }

    private void dapatkanData(View view, ModelMasterData dataModelDb) {
        String id = dataModelDb.getId();
        dialogAfdeling = dataModelDb.getAfdeling();
        dialogBlok = dataModelDb.getBlok();
        dialogGroupSample = dataModelDb.getGroupSample();

        tampilkanDialogUbah(id, dialogAfdeling, dialogBlok, dialogGroupSample);
    }

    private void ubahData(String id, String afdeling, String blok, String group_sample) {
        mRealm.beginTransaction();

        ModelMasterData dataModel = mRealm.where(ModelMasterData.class).equalTo("id", id).findFirst();
        dataModel.setAfdeling(afdeling);
        dataModel.setBlok(blok);
        dataModel.setGroupSample(group_sample);

        mRealm.commitTransaction();
    }

    private void menghapusData(int position) {
        mRealm.beginTransaction();
        dataModelDbs.deleteFromRealm(position);
        mRealm.commitTransaction();
    }

    private void tampilkanDialogUbah(final String id, String afdeling, String blok, String group_sample) {
        LayoutInflater inflater     = LayoutInflater.from(MainActivity.this);
        View itemView	            = inflater.inflate(R.layout.dialog_isi_data, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(itemView);

        final EditText afdelingInput    = itemView.findViewById(R.id.et_afdeling);
        final EditText blokInput        = itemView.findViewById(R.id.et_blok);
        final EditText groupSampleInput = itemView.findViewById(R.id.et_group_sample);

        afdelingInput.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        afdelingInput.setText(afdeling);
        blokInput.setText(""+blok);
        groupSampleInput.setText(""+group_sample);

        builder.setCancelable(false).setPositiveButton("Ubah", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (afdelingInput.length() == 0 || blokInput.length() == 0 || groupSampleInput.length() == 0) {

                    Toast.makeText(MainActivity.this, "Data belum lengkap", Toast.LENGTH_SHORT).show();
                } else {

                    dialogAfdeling      = afdelingInput.getText().toString();
                    dialogBlok          = blokInput.getText().toString();
                    dialogGroupSample   = groupSampleInput.getText().toString();

                    ubahData(id, dialogAfdeling, dialogBlok, dialogGroupSample);

                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Data berhasil diubah", Toast.LENGTH_SHORT).show();
                }
            }
        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataModelDbs = mRealm.where(ModelMasterData.class).findAll();
        realmDataAdapter = new RealmDataAdapter(MainActivity.this, dataModelDbs, true);

        adapter.setRealmAdapter(realmDataAdapter);
        adapter.notifyDataSetChanged();
    }
}
