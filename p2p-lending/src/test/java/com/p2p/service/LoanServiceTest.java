/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.p2p.service;

import com.p2p.domain.Borrower;
import com.p2p.domain.Loan;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class LoanServiceTest {

    // ================================================
    // TC-01: shouldRejectLoanWhenBorrowerNotVerified
    // ================================================
    @Test
    void shouldRejectLoanWhenBorrowerNotVerified() {
        // =========================
        // SCENARIO:
        // Borrower tidak terverifikasi (KYC = false)
        // Ketika borrower mengajukan pinjaman
        // Maka sistem harus menolak dengan melempar exception
        // =========================

        // Arrange
        Borrower borrower = new Borrower(false, 700);  // KYC = false
        LoanService loanService = new LoanService();
        BigDecimal amount = BigDecimal.valueOf(1000);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            loanService.createLoan(borrower, amount);
        });
    }

    // ================================================
    // TC-02: shouldRejectLoanWhenAmountIsZeroOrNegative
    // ================================================
    @Test
    void shouldRejectLoanWhenAmountIsZeroOrNegative() {
        // =========================
        // SCENARIO:
        // Borrower valid (KYC = true)
        // Amount yang diajukan = 0 atau negatif
        // Maka sistem harus menolak dengan melempar exception
        // =========================

        // Arrange
        Borrower borrower = new Borrower(true, 700);   // KYC valid
        LoanService loanService = new LoanService();
        BigDecimal zeroAmount = BigDecimal.ZERO;        // amount = 0

        // Act & Assert — test dengan amount 0
        assertThrows(IllegalArgumentException.class, () -> {
            loanService.createLoan(borrower, zeroAmount);
        });

        // Act & Assert — test dengan amount negatif
        BigDecimal negativeAmount = BigDecimal.valueOf(-500);
        assertThrows(IllegalArgumentException.class, () -> {
            loanService.createLoan(borrower, negativeAmount);
        });
    }

    // ================================================
    // TC-03: shouldApproveLoanWhenCreditScoreHigh
    // ================================================
    @Test
    void shouldApproveLoanWhenCreditScoreHigh() {
        // =========================
        // SCENARIO:
        // Borrower verified (KYC = true)
        // Credit score >= 600 (threshold)
        // Maka loan harus berstatus APPROVED
        // =========================

        // Arrange
        Borrower borrower = new Borrower(true, 700);   // credit score tinggi
        LoanService loanService = new LoanService();
        BigDecimal amount = BigDecimal.valueOf(5000);

        // Act
        Loan loan = loanService.createLoan(borrower, amount);

        // Assert
        assertEquals(Loan.Status.APPROVED, loan.getStatus());
    }

    // ================================================
    // TC-04: shouldRejectLoanWhenCreditScoreLow
    // ================================================
    @Test
    void shouldRejectLoanWhenCreditScoreLow() {
        // =========================
        // SCENARIO:
        // Borrower verified (KYC = true)
        // Credit score < 600 (di bawah threshold)
        // Maka loan harus berstatus REJECTED
        // =========================

        // Arrange
        Borrower borrower = new Borrower(true, 400);   // credit score rendah
        LoanService loanService = new LoanService();
        BigDecimal amount = BigDecimal.valueOf(5000);

        // Act
        Loan loan = loanService.createLoan(borrower, amount);

        // Assert
        assertEquals(Loan.Status.REJECTED, loan.getStatus());
    }
}