package com.efub.blogsession.domain.account.service;

import com.efub.blogsession.domain.account.domain.Account;
import com.efub.blogsession.domain.account.dto.AccountUpdateRequestDto;
import com.efub.blogsession.domain.account.dto.SignUpRequestDto;
import com.efub.blogsession.domain.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@org.springframework.transaction.annotation.Transactional
@RequiredArgsConstructor
public class AccountService {
	private final AccountRepository accountRepository;

	public Long signUp(SignUpRequestDto requestDto) {
		if (existsByEmail(requestDto.getEmail())) {
			throw new IllegalArgumentException("이미 존재하는 email입니다." + requestDto.getEmail());
		}
		Account account = accountRepository.save(requestDto.toEntity());
		return account.getAccountId();
	}

	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public boolean existsByEmail(String email) {
		return accountRepository.existsByEmail(email);
	}

	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public Account findAccountById(Long id) {
		return accountRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(("해당 ID를 가진 Account를 찾을 수 없습니다. ID=" + id)));
	}

	public Long update(Long accountId, AccountUpdateRequestDto requestDto) {
		Account account = findAccountById(accountId);
		account.updateAccount(requestDto.getBio(), requestDto.getNickname());
		return account.getAccountId();
	}

	@Transactional
	public void withdraw(Long accountId) {
		Account account = findAccountById(accountId);
		account.withdrawAccount();
	}

	public void delete(Long accountId) {
		Account account = findAccountById(accountId);
		accountRepository.delete(account);
	}

	//email로 계정 찾기
	@Transactional(readOnly = true)
	public Account findAccountByEmail(String email){
		return accountRepository.findByEmail(email)
				.orElseThrow(()->new EntityNotFoundException("해당 email을 가진 계정을 찾을 수 없습니다.email="+email));
	}
}
