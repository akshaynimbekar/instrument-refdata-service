package com.shak.refdata.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shak.refdata.entity.Instrument;
import com.shak.refdata.repository.InstrumentRepository;

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
}
