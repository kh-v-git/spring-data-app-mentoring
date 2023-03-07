package com.taskone.demo.service.userAccount;

import com.taskone.demo.utils.exception.BookingServiceException;

public interface UserAccountService {
    void depositMoneyToUserAccount(int userId, String amount) throws BookingServiceException;

    void debitMoneyFromUserAccount(int userId, String amount) throws BookingServiceException;

    String getUserAccountBalance(int userId) throws BookingServiceException;
}
