package com.ss.training.lms.service.admin;

import com.ss.training.lms.jdbc.ConnectionUtil;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ss.training.lms.dao.BookLoanDAO;
import com.ss.training.lms.dao.BorrowerDAO;
import com.ss.training.lms.entity.BookLoan;
import com.ss.training.lms.entity.Borrower;

@Component
public class AdminBorrowerService {
	
	@Autowired
    ConnectionUtil connUtil;
	
	@Autowired
    BorrowerDAO borDAO;
	
	@Autowired
    BookLoanDAO loanDAO;
    
	/**
	 * 
	 * @param borrower
	 */
    public void saveBorrower(Borrower borrower) {
        borDAO.save(borrower);
    }

    /**
     * 
     * @param borrower
     */
    public void deleteBorrower(Borrower borrower) {
        List<BookLoan> loans = loanDAO.findByCardNo(borrower.getCardNo());
        for(BookLoan loan: loans) {
            loanDAO.delete(loan);
        }
        borDAO.delete(borrower);
    }

    /**
     * 
     * @param cardNo
     * @return
     */
    public Borrower readBorrower(Integer cardNo) {
        return borDAO.findByCardNo(cardNo);
    }

    /**
     * 
     * @return
     */
    public List<Borrower> readAllBorrowers() {
        return borDAO.findAll();
    }
}