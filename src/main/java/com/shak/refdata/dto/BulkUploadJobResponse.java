package com.shak.refdata.dto;

import com.shak.refdata.entity.JobStatus;
import java.time.LocalDateTime;

public class BulkUploadJobResponse {
    private Long jobId;
    private String filename;
    private JobStatus status;
    private int successCount;
    private int failureCount;
    private String errors;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BulkUploadJobResponse(Long jobId, String filename, JobStatus status,
                                 int successCount, int failureCount,
                                 String errors, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.jobId = jobId;
        this.filename = filename;
        this.status = status;
        this.successCount = successCount;
        this.failureCount = failureCount;
        this.errors = errors;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public JobStatus getStatus() {
		return status;
	}

	public void setStatus(JobStatus status) {
		this.status = status;
	}

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

	public String getErrors() {
		return errors;
	}

	public void setErrors(String errors) {
		this.errors = errors;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

    
}
