//package com.shak.refdata.controller;
//
//import java.util.List;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import com.shak.refdata.entity.Instrument;
//import com.shak.refdata.service.InstrumentService;
//
//@RestController
//@RequestMapping("/api/instruments")
//public class InstrumentController_beforeDtoException {
//	
//	private final InstrumentService instrumentService;
//	
//	public InstrumentController_beforeDtoException(InstrumentService instrumentService) {
//		this.instrumentService = instrumentService;
//	}
//	
//	// Save Instrument
//	@PostMapping
//	public ResponseEntity<Instrument> createInstrument(@RequestBody Instrument intrument){
//		return ResponseEntity.ok(instrumentService.saveInstrument(intrument));
//	}
//	
//	// Get All Instruments
//	@GetMapping
//	public ResponseEntity<List<Instrument>> getAllInstruments(){
//		return ResponseEntity.ok(instrumentService.getAllInstruments());
//	}
//	
//	// Get Instrument by ID
//	@GetMapping("/{id}")
//	public ResponseEntity<Instrument> getInstrumentById(@PathVariable("id") Long id){
//		return instrumentService.getInstrumentById(id)
//				.map(ResponseEntity::ok)
//				.orElse(ResponseEntity.notFound().build());
//	}
//	
//	// Get Instrument by ISIN
//	@GetMapping("/isin/{isin}")
//	public ResponseEntity<Instrument> getInstrunmentByIsin(@PathVariable("isin") String isin){
//		return instrumentService.getInstrumentByIsin(isin)
//				.map(ResponseEntity::ok)
//				.orElse(ResponseEntity.notFound().build());
//	}
//	
//	// Delete Instrument
//	@DeleteMapping("/{id}")
//	public ResponseEntity<Void> deleteInstrument(@PathVariable Long id) {
//		instrumentService.deleteInstrument(id);
//		return ResponseEntity.noContent().build();
//	}
//	
//}
