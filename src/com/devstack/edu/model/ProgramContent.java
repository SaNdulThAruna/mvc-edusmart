package com.devstack.edu.model;

public class ProgramContent {

    private long id;
    private String header;

    public ProgramContent() {

    }

    public ProgramContent(long id, String header) {
        this.id = id;
        this.header = header;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    @Override
    public String toString() {
        return "ProgramContent{" +
                "id=" + id +
                ", header='" + header + '\'' +
                '}';
    }
}
