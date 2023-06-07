package com.example.demo.model;

public interface Entity<ID> {
    void setId(ID id);
    ID getId();
}
