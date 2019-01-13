package com.xer.igou.util;

import java.util.ArrayList;
import java.util.List;

public class PageList<T> {
    private Long total;
    private List<T> records = new ArrayList<>();
    public PageList(Long total, List<T> records) {
        this.total = total;
        this.records = records;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}

