package com.example.singletonspring.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class WaitingList {
    private static WaitingList uniqueInstance;
    private List<Patient> patients = new ArrayList<>();

    private WaitingList() {}

    public static synchronized WaitingList getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new WaitingList();
        }

        return uniqueInstance;
    }
}
