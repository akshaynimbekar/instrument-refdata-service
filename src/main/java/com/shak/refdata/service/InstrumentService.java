package com.shak.refdata.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shak.refdata.dto.BulkUploadJobResponse;
import com.shak.refdata.dto.BulkUploadResult;
import com.shak.refdata.entity.BulkUploadJob;
import com.shak.refdata.entity.Instrument;
import com.shak.refdata.entity.JobStatus;
import com.shak.refdata.repository.BulkUploadJobRepository;
import com.shak.refdata.repository.InstrumentRepository;
import com.shak.refdata.specification.InstrumentSpecification;

@Service
@EnableAsync
public class InstrumentService {
	private final InstrumentRepository instrumentRepository;
    private final BulkUploadJobRepository jobRepository;

    public InstrumentService(InstrumentRepository instrumentRepository, BulkUploadJobRepository jobRepository) {
        this.instrumentRepository = instrumentRepository;
        this.jobRepository = jobRepository;
    }
	
	
	public Instrument saveInstrument(Instrument instrument) {
		return instrumentRepository.save(instrument);
	}
	
	public List<Instrument> getAllInstruments(){
		return instrumentRepository.findAll();
	}
	
	public Optional<Instrument> getInstrumentById(Long id) {
		return instrumentRepository.findById(id);
	}
	
	public Optional<Instrument> getInstrumentByIsin(String isin){
		return instrumentRepository.findByIsin(isin);
	}
	
	public void deleteInstrument(Long id){
		instrumentRepository.deleteById(id);
	}
	
	//search
	public Page<Instrument> searchInstruments(String isin, String ticker, String exchange, String currency, Pageable pageable) {
	    Specification<Instrument> spec = Specification
	    		.where(InstrumentSpecification.hasIsin(isin))
	            .and(InstrumentSpecification.hasTicker(ticker))
	            .and(InstrumentSpecification.hasExchange(exchange))
	            .and(InstrumentSpecification.hasCurrency(currency));

	    return instrumentRepository.findAll(spec, pageable);
	}
	
	//bulk upload synchronous
	public BulkUploadResult bulkUpload(MultipartFile file) {
        int successCount = 0;
        int failureCount = 0;
        List<String> errors = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .withFirstRecordAsHeader()
                     .withIgnoreHeaderCase()
                     .withTrim())) {

            List<Instrument> instruments = new ArrayList<>();

            for (CSVRecord record : csvParser) {
                try {
                    Instrument instrument = new Instrument();
                            instrument.setIsin(record.get("isin"));
                            instrument.setCusip(record.get("cusip"));
                            instrument.setTicker(record.get("ticker"));
                            instrument.setExchange(record.get("exchange"));
                            instrument.setCurrency(record.get("currency"));

                    instruments.add(instrument);
                    successCount++;
                } catch (Exception e) {
                    failureCount++;
                    errors.add("Row " + record.getRecordNumber() + ": " + e.getMessage());
                }
            }

            instrumentRepository.saveAll(instruments);

        } catch (Exception e) {
            throw new RuntimeException("Failed to process CSV: " + e.getMessage(), e);
        }

        return new BulkUploadResult(successCount, failureCount, errors);
    }
	
	//BULK UPLOAD ASYNCHRONOUS
	
	public BulkUploadJob createJob(String filename) {
        BulkUploadJob job = new BulkUploadJob(filename, JobStatus.PENDING);
        return jobRepository.save(job);
    }

    @Async
    public CompletableFuture<Void> processJob(Long jobId, MultipartFile file) {
        BulkUploadJob job = jobRepository.findById(jobId).orElseThrow();
        job.setStatus(JobStatus.IN_PROGRESS);
        jobRepository.save(job);

        int successCount = 0;
        int failureCount = 0;
        StringBuilder errorLog = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .withFirstRecordAsHeader()
                     .withIgnoreHeaderCase()
                     .withTrim())) {

            List<Instrument> instruments = new ArrayList<>();

            for (CSVRecord record : csvParser) {
                try {
                    Instrument instrument = new Instrument();
                    instrument.setIsin(record.get("isin"));
                    instrument.setCusip(record.get("cusip"));
                    instrument.setTicker(record.get("ticker"));
                    instrument.setExchange(record.get("exchange"));
                    instrument.setCurrency(record.get("currency"));

                    instruments.add(instrument);
                    successCount++;
                } catch (Exception e) {
                    failureCount++;
                    errorLog.append("Row ").append(record.getRecordNumber())
                            .append(": ").append(e.getMessage()).append("\n");
                }
            }

            instrumentRepository.saveAll(instruments);

            job.setStatus(JobStatus.COMPLETED);
        } catch (Exception e) {
            job.setStatus(JobStatus.FAILED);
            errorLog.append("Fatal: ").append(e.getMessage());
        }

        job.setSuccessCount(successCount);
        job.setFailureCount(failureCount);
        job.setErrors(errorLog.toString());
        job.setUpdatedAt(LocalDateTime.now());
        jobRepository.save(job);

        return CompletableFuture.completedFuture(null);
    }

    public BulkUploadJobResponse getJobStatus(Long jobId) {
        BulkUploadJob job = jobRepository.findById(jobId).orElseThrow();
        return new BulkUploadJobResponse(
                job.getId(),
                job.getFilename(),
                job.getStatus(),
                job.getSuccessCount(),
                job.getFailureCount(),
                job.getErrors(),
                job.getCreatedAt(),
                job.getUpdatedAt()
        );
    }
	
}
