package com.shak.refdata.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shak.refdata.entity.Instrument;

public interface InstrumentRepository extends JpaRepository<Instrument, Long>{
	Optional<Instrument> findByIsin(String isin);
	
	Optional<Instrument> findByCusip(String cusip);
	
	Optional<Instrument> findByTicker(String ticker);
}
