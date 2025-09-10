package com.shak.refdata.controller;

import com.shak.refdata.dto.BulkUploadJobResponse;
import com.shak.refdata.dto.BulkUploadResult;
import com.shak.refdata.dto.InstrumentRequestDto;
import com.shak.refdata.dto.InstrumentResponseDto;
import com.shak.refdata.entity.BulkUploadJob;
import com.shak.refdata.entity.Instrument;
import com.shak.refdata.exception.ResourceNotFoundException;
import com.shak.refdata.mapper.InstrumentMapper;
import com.shak.refdata.service.InstrumentService;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/instruments")
public class InstrumentController {

    private final InstrumentService instrumentService;

    public InstrumentController(InstrumentService instrumentService) {
        this.instrumentService = instrumentService;
    }

    // Create Instrument
    @PostMapping
    public ResponseEntity<InstrumentResponseDto> createInstrument(
            @RequestBody @Valid InstrumentRequestDto requestDto) {

        Instrument instrument = InstrumentMapper.toEntity(requestDto);
        Instrument saved = instrumentService.saveInstrument(instrument);
        return ResponseEntity.ok(InstrumentMapper.toDto(saved));
    }

    // Get All Instruments
    @GetMapping
    public ResponseEntity<List<InstrumentResponseDto>> getAllInstruments() {
        return ResponseEntity.ok(
                instrumentService.getAllInstruments()
                        .stream()
                        .map(InstrumentMapper::toDto)
                        .toList()
        );
    }

    // Get Instrument by ID
    @GetMapping("/{id}")
    public ResponseEntity<InstrumentResponseDto> getInstrumentById(@PathVariable("id") Long id) {
        Instrument instrument = instrumentService.getInstrumentById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instrument not found with id: " + id));
        return ResponseEntity.ok(InstrumentMapper.toDto(instrument));
    }

    // Get Instrument by ISIN
    @GetMapping("/isin/{isin}")
    public ResponseEntity<InstrumentResponseDto> getInstrumentByIsin(@PathVariable("isin") String isin) {
        Instrument instrument = instrumentService.getInstrumentByIsin(isin)
                .orElseThrow(() -> new ResourceNotFoundException("Instrument not found with ISIN: " + isin));
        return ResponseEntity.ok(InstrumentMapper.toDto(instrument));
    }

    // Delete Instrument
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstrument(@PathVariable("id") Long id) {
        instrumentService.getInstrumentById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instrument not found with id: " + id));
        instrumentService.deleteInstrument(id);
        return ResponseEntity.noContent().build();
    }
    
    //Updating whole instrument 
    @PutMapping("/{id}")
    public ResponseEntity<InstrumentResponseDto> updateInstrument(
            @PathVariable Long id,
            @RequestBody InstrumentRequestDto dto) {
        return ResponseEntity.ok(instrumentService.updateInstrument(id, dto));
    }
 
    
    //search
    @GetMapping("/search")
    public ResponseEntity<Page<InstrumentResponseDto>> searchInstruments(
            @RequestParam(required = false) String isin,
            @RequestParam(required = false) String ticker,
            @RequestParam(required = false) String exchange,
            @RequestParam(required = false) String currency,
            Pageable pageable) {

        Page<InstrumentResponseDto> result = instrumentService
                .searchInstruments(isin, ticker, exchange, currency, pageable)
                .map(InstrumentMapper::toDto);

        return ResponseEntity.ok(result);
    }
    
    //Synchronous bulk upload Api 
    @PostMapping("/upload")
    public ResponseEntity<BulkUploadResult> uploadInstruments(@RequestParam("file") MultipartFile file) {
        BulkUploadResult result = instrumentService.bulkUpload(file);
        return ResponseEntity.ok(result);
    }
    
    //Asynchronous Bulk Upload Api
    
    @PostMapping("/upload/async")
    public ResponseEntity<BulkUploadJobResponse> uploadInstrumentsAsync(@RequestParam("file") MultipartFile file) {
        BulkUploadJob job = instrumentService.createJob(file.getOriginalFilename());
        instrumentService.processJob(job.getId(), file); // runs in background
        return ResponseEntity.ok(
                new BulkUploadJobResponse(
                    job.getId(),
                    job.getFilename(),
                    job.getStatus(),
                    job.getSuccessCount(),
                    job.getFailureCount(),
                    job.getErrors(),
                    job.getCreatedAt(),
                    job.getUpdatedAt()
                )
        );
    }

    @GetMapping("/upload/status/{jobId}")
    public ResponseEntity<BulkUploadJobResponse> getJobStatus(@PathVariable Long jobId) {
        return ResponseEntity.ok(instrumentService.getJobStatus(jobId));
    }

    
    
}