package com.shak.refdata.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
	
	public Page<Instrument> searchInstruments(String isin, String ticker, String exchange, String currency, Pageable pageable) {
	    Specification<Instrument> spec = Specification
	    		.where(InstrumentSpecification.hasIsin(isin))
	            .and(InstrumentSpecification.hasTicker(ticker))
	            .and(InstrumentSpecification.hasExchange(exchange))
	            .and(InstrumentSpecification.hasCurrency(currency));

	    return instrumentRepository.findAll(spec, pageable);
	}
	
}
