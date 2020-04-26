package com.example.crudrealmdatabase.adapter;

import android.content.Context;

import com.example.crudrealmdatabase.model.ModelMasterData;

import io.realm.RealmResults;

public class RealmDataAdapter extends RealmModelAdapter<ModelMasterData> {
    public RealmDataAdapter(Context context, RealmResults<ModelMasterData> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }
}
