package com.example.util;

import java.security.SecureRandom;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;



@Component
public class KeyGenerator {



	// Function to generate a unique card number
	// ----------------------------------------

	public Long generateUniqueCardNumber() {
		return 1000000000L + (long) (new SecureRandom().nextDouble() * 9000000000L);

//		Long cardNumber = customerCardAccountRepository.findLastCreditCardNumber();
//
//		return (cardNumber + 1);
	}

	// Function to generate PIN
	// ----------------------------------------

	public int generatePin() {
		// Generate a 4-digit PIN
		return 1000 + new SecureRandom().nextInt(9000);
	}

	// Function to generate CVV
	// ----------------------------------------

	public String generateCvv() {
		// Generate a 3-digit CVV
		return String.format("%03d", new SecureRandom().nextInt(1000));
	}

	// Function to generate Password
	// ----------------------------------------

	public String generatePassword() {
		final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
		final String DIGITS = "0123456789";
		final String SPECIAL_CHARACTERS = "!@#$%^&*()-_+=<>?";
		final String ALL_CHARACTERS = UPPER_CASE + LOWER_CASE + DIGITS + SPECIAL_CHARACTERS;
		final int PASSWORD_LENGTH = 12;

		SecureRandom random = new SecureRandom();
		String password;

		String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+<>?]).{" + PASSWORD_LENGTH + ",}$";
		Pattern pattern = Pattern.compile(regex);

		do {
			StringBuilder sb = new StringBuilder(PASSWORD_LENGTH);
			for (int i = 0; i < PASSWORD_LENGTH; i++) {
				sb.append(ALL_CHARACTERS.charAt(random.nextInt(ALL_CHARACTERS.length())));
			}
			password = sb.toString();
		} while (!pattern.matcher(password).matches());

		return password;
	}
}
