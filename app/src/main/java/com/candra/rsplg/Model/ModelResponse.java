package com.candra.rsplg.Model;

import java.util.List;

public class ModelResponse {
    private String kode, pesan;
    private List<ModelRS> data;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public List<ModelRS> getData() {
        return data;
    }
}
