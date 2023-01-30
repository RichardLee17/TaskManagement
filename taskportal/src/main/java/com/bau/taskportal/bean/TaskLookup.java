package com.bau.taskportal.bean;

import javax.persistence.*;

@Entity
@Table(name = "taskLookup")
public class TaskLookup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String valueCd;

    private String valueTxt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValueCd() {
        return valueCd;
    }

    public void setValueCd(String valueCd) {
        this.valueCd = valueCd;
    }

    public String getValueTxt() {
        return valueTxt;
    }

    public void setValueTxt(String valueTxt) {
        this.valueTxt = valueTxt;
    }
}
