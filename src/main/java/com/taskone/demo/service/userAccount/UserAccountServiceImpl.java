package com.taskone.demo.service.userAccount;

import com.taskone.demo.domain.User;
import com.taskone.demo.domain.UserAccount;
import com.taskone.demo.repository.UserAccountRepository;
import com.taskone.demo.repository.UserRepository;
import com.taskone.demo.utils.exception.BookingServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserAccountServiceImpl implements UserAccountService {
    private final UserRepository userRepository;
    private final UserAccountRepository userAccountRepository;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void depositMoneyToUserAccount(int userId, String amount) throws BookingServiceException {
        BigDecimal depositAmount = parseAccountAmount(amount);

        User user = userRepository.findById((long) userId).orElseThrow(() -> new BookingServiceException(String.format("User not found with id: %s.", userId)));


        if (userAccountRepository.existsUserAccountByUserId((long) userId)) {
            UserAccount userAccount = userAccountRepository.findUserAccountByUserId((long) userId).orElseThrow();
            userAccount.setAmount(userAccount.getAmount().add(depositAmount));

            userAccountRepository.save(userAccount);
        } else {
            UserAccount account = UserAccount.builder()
                    .amount(depositAmount)
                    .user(user)
                    .build();

            userAccountRepository.save(account);
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void debitMoneyFromUserAccount(int userId, String amount) throws BookingServiceException {

        if (!userRepository.existsById((long) userId)) {
            throw new BookingServiceException(String.format("User not found with id: %s.", userId));
        }

        if (userAccountRepository.existsUserAccountByUserId((long) userId)) {
            UserAccount userAccount = userAccountRepository.findUserAccountByUserId((long) userId).orElseThrow();
            userAccount.setAmount(userAccount.getAmount().add(parseAccountAmount(amount)));

            userAccountRepository.save(userAccount);
        } else {
            throw new BookingServiceException(String.format("UserAccount not found for User with id: %s.", userId));
        }
    }

    @Override
    public String getUserAccountBalance(int userId) throws BookingServiceException {
        return userAccountRepository.findUserAccountByUserId((long) userId).orElseThrow(() -> new BookingServiceException(String.format("UserAccount not found for User with id: %s.", userId))).getAmount().toString();
    }

    private BigDecimal parseAccountAmount(String amount) throws BookingServiceException {
        try {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator('.');
            String pattern = "#0.00";
            DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
            decimalFormat.setParseBigDecimal(true);

            return (BigDecimal) decimalFormat.parse(amount);
        } catch (ParseException exc) {
            throw new BookingServiceException("Money amount parsing error", exc);
        }
    }
}
