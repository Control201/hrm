package com.hrm.dto.emp;

import java.io.Serializable;

/**
 * @Description:
 */
public class objectTotal implements Serializable {
    //当前条件下该的学生人数
    private Integer total;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }


    @Override
    public String toString() {
        return "ObjectTotal{" +
                "total=" + total +
                '}';
    }
}
