package com.example.dto;

import java.math.BigDecimal;

import com.example.model.CustomerCardApplication.ApplicationStatus;

public class CustomerApplicationDTO {
    private Long applicationId;
    private Long customerId;
    private String customerName;
    private String email;
    private ApplicationStatus status; 
    private BigDecimal annualIncome;
    private String panId;
    
	private String mobileNumber;
    private String companyName;
    private String incomeProofFilePath;
    private String name;
    private String creditCard;
    
    public CustomerApplicationDTO(Long applicationId, Long customerId, String customerName, String email,
			ApplicationStatus status, BigDecimal annualIncome,  String panId, String mobileNumber,
			String companyName, String incomeProofFilePath, String name, String creditCard) {
		super();
		this.applicationId = applicationId;
		this.customerId = customerId;
		this.customerName = customerName;
		this.email = email;
		this.status = status;
		this.annualIncome = annualIncome;
		this.panId = panId;
		this.mobileNumber = mobileNumber;
		this.companyName = companyName;
		this.incomeProofFilePath = incomeProofFilePath;
		this.name = name;
		this.creditCard = creditCard;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(ApplicationStatus status) {
		this.status = status;
	}

	public BigDecimal getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(BigDecimal annualIncome) {
		this.annualIncome = annualIncome;
	}


	public String getPanId() {
		return panId;
	}

	public void setPanId(String panId) {
		this.panId = panId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getIncomeProofFilePath() {
		return incomeProofFilePath;
	}

	public void setIncomeProofFilePath(String incomeProofFilePath) {
		this.incomeProofFilePath = incomeProofFilePath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}
    
    

    
}
