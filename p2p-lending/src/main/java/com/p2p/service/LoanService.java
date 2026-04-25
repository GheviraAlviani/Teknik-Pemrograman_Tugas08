/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.p2p.service;

import com.p2p.domain.Borrower;
import com.p2p.domain.Loan;
import java.math.BigDecimal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoanService {

    // Logger Log4j
    private static final Logger logger = LogManager.getLogger(LoanService.class);

    // Threshold credit score minimum
    private static final int CREDIT_SCORE_THRESHOLD = 600;

    public Loan createLoan(Borrower borrower, BigDecimal amount) {
        logger.info("=== createLoan() dipanggil ===");
        logger.debug("Borrower verified: {}, creditScore: {}, amount: {}",
                borrower.isVerified(), borrower.getCreditScore(), amount);

        // Validasi borrower
        validateBorrower(borrower);

        // Validasi amount (TC-02)
        validateAmount(amount);

        // Buat loan
        Loan loan = new Loan();
        logger.info("Loan object berhasil dibuat, status awal: {}", loan.getStatus());

        // Penilaian credit score (TC-03 & TC-04)
        if (borrower.getCreditScore() >= CREDIT_SCORE_THRESHOLD) {
            loan.approve();
            logger.info("Loan APPROVED - credit score {} >= threshold {}",
                    borrower.getCreditScore(), CREDIT_SCORE_THRESHOLD);
        } else {
            loan.reject();
            logger.warn("Loan REJECTED - credit score {} < threshold {}",
                    borrower.getCreditScore(), CREDIT_SCORE_THRESHOLD);
        }

        logger.info("createLoan() selesai, status akhir: {}", loan.getStatus());
        return loan;
    }

    // =========================
    // PRIVATE VALIDATION METHOD
    // =========================
    private void validateBorrower(Borrower borrower) {
        if (!borrower.canApplyLoan()) {
            logger.error("GAGAL: Borrower belum terverifikasi (KYC = false)");
            throw new IllegalArgumentException("Borrower not verified");
        }
        logger.debug("Borrower lolos validasi KYC");
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            logger.error("GAGAL: Amount tidak valid -> {}", amount);
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        logger.debug("Amount lolos validasi: {}", amount);
    }
}