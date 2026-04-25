/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.p2p.domain;

/**
 *
 * @author ghevira
 */
public class Loan {
    public enum Status {
        PENDING, APPROVED, REJECTED
    }

    private Status status;

    public Loan() {
        this.status = Status.PENDING;
    }

    public void approve() {
        this.status = Status.APPROVED;
    }

    public void reject() {
        this.status = Status.REJECTED;
    }

    public Status getStatus() {
        return status;
    }
}
