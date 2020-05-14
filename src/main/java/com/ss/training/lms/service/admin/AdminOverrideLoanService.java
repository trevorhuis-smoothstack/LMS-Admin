package com.ss.training.lms.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ss.training.lms.dao.BookLoanDAO;
import com.ss.training.lms.entity.BookLoan;
import com.ss.training.lms.jdbc.ConnectionUtil;

@Component
public class AdminOverrideLoanService {
    
	@Autowired
	ConnectionUtil connUtil;
	
	@Autowired
	BookLoanDAO loanDAO;

    public boolean addAWeekToALoan(BookLoan loan) {
        List<BookLoan> loans = loanDAO.findByCardNo(loan.getCardNo());
        for (BookLoan l: loans)
        {
            if (   l.getBookId() == loan.getBookId()
                && l.getCardNo() == loan.getCardNo()
                && l.getDateOut() == loan.getDateOut()
                && l.getDateIn() != null)
            {
                return false;
            }
        }
        
        LocalDateTime timeTochange = loan.getDueDate().toLocalDateTime();
        timeTochange = timeTochange.plusDays(7);
        Timestamp newTime = Timestamp.valueOf(timeTochange);
        loan.setDueDate(newTime);
        loanDAO.save(loan);
        return true;
    }

}