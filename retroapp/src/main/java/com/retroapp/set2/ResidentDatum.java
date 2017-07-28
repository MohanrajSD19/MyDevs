
package com.retroapp.set2;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResidentDatum {

    @SerializedName("residents")
    @Expose
    private List<Resident> residents = null;
    @SerializedName("status")
    @Expose
    private Integer status;

    public List<Resident> getResidents() {
        return residents;
    }

    public void setResidents(List<Resident> residents) {
        this.residents = residents;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
