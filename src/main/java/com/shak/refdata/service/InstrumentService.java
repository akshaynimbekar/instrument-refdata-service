package com.shak.refdata.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shak.refdata.dto.BulkUploadResult;
import com.shak.refdata.entity.Instrument;
import com.shak.refdata.repository.InstrumentRepository;
import com.shak.refdata.specification.InstrumentSpecification;

@Service
public class InstrumentService {
	private final InstrumentRepository instrumentRepository;
	
	public InstrumentService(InstrumentRepository instrumentRepository) {
		this.instrumentRepository = instrumentRepository;
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
	
	//bulk upload
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
	
}
