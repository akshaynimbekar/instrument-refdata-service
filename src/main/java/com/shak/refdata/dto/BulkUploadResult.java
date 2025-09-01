package com.shak.refdata.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


public class BulkUploadResult {
    private int successCount;
    private int failureCount;
    private List<String> errors;
	public int getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}
	public int getFailureCount() {
		return failureCount;
	}
	public void setFailureCount(int failureCount) {
		this.failureCount = failureCount;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public BulkUploadResult(int successCount, int failureCount, List<String> errors) {
		super();
		this.successCount = successCount;
		this.failureCount = failureCount;
		this.errors = errors;
	}
	public BulkUploadResult() {
		super();
	}
	@Override
	public String toString() {
		return "BulkUploadResult [successCount=" + successCount + ", failureCount=" + failureCount + ", errors="
				+ errors + "]";
	}
    
    
}
