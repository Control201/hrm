package com.hrm.bean;

public class Department {
    private Integer depid;

    private String depleader;

    private String depname;

    public Integer getDepid() {
        return depid;
    }

    public void setDepid(Integer depid) {
        this.depid = depid;
    }

    public String getDepleader() {
        return depleader;
    }

    public void setDepleader(String depleader) {
        this.depleader = depleader == null ? null : depleader.trim();
    }

    public String getDepname() {
        return depname;
    }

    public void setDepname(String depname) {
        this.depname = depname == null ? null : depname.trim();
    }
}