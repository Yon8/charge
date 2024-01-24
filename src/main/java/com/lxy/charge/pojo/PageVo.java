package com.lxy.charge.pojo;

public class PageVo {
    protected long total;
    protected long size;
    protected long current;

    public PageVo() {
    }

    public PageVo(long total, long size, long current) {
        this.total = total;
        this.size = size;
        this.current = current;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        this.current = current;
    }

    @Override
    public String toString() {
        return "PageVo{" +
                "total=" + total +
                ", size=" + size +
                ", current=" + current +
                '}';
    }
}
