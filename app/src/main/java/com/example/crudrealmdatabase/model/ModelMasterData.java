package com.example.crudrealmdatabase.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ModelMasterData extends RealmObject {
    @PrimaryKey
    private String id;
    private String afdeling;
    private String blok;
    private String groupSample;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAfdeling() {
        return afdeling;
    }

    public void setAfdeling(String afdeling) {
        this.afdeling = afdeling;
    }

    public String getBlok() {
        return blok;
    }

    public void setBlok(String blok) {
        this.blok = blok;
    }

    public String getGroupSample() {
        return groupSample;
    }

    public void setGroupSample(String groupSample) {
        this.groupSample = groupSample;
    }
}
