package com.lxy.charge.pojo.charge;

public class Station {
    private String id;
    private String name;
    private int count;
    private String location;
    private String status;
    private String warden;

    public Station() {
    }

    public Station(String id, String name, int count, String location, String status, String warden) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.location = location;
        this.status = status;
        this.warden = warden;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWarden() {
        return warden;
    }

    public void setWarden(String warden) {
        this.warden = warden;
    }

    @Override
    public String toString() {
        return "Station{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", location='" + location + '\'' +
                ", status='" + status + '\'' +
                ", warden='" + warden + '\'' +
                '}';
    }
}
