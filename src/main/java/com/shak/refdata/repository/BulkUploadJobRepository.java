package com.shak.refdata.repository;

import com.shak.refdata.entity.BulkUploadJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BulkUploadJobRepository extends JpaRepository<BulkUploadJob, Long> {
}
