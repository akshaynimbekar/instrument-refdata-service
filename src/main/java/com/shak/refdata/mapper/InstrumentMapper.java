package com.shak.refdata.mapper;

import com.shak.refdata.dto.InstrumentRequestDto;
import com.shak.refdata.dto.InstrumentResponseDto;
import com.shak.refdata.entity.Instrument;

public class InstrumentMapper {

    public static Instrument toEntity(InstrumentRequestDto dto) {
        Instrument instrument = new Instrument();
        instrument.setIsin(dto.getIsin());
        instrument.setCusip(dto.getCusip());
        instrument.setTicker(dto.getTicker());
        instrument.setExchange(dto.getExchange());
        instrument.setCurrency(dto.getCurrency());
        return instrument;
    }

    public static InstrumentResponseDto toDto(Instrument instrument) {
        InstrumentResponseDto dto = new InstrumentResponseDto();
        dto.setId(instrument.getId());
        dto.setIsin(instrument.getIsin());
        dto.setCusip(instrument.getCusip());
        dto.setTicker(instrument.getTicker());
        dto.setExchange(instrument.getExchange());
        dto.setCurrency(instrument.getCurrency());
        dto.setCreatedAt(instrument.getCreatedAt());
        dto.setUpdatedAt(instrument.getUpdatedAt());
        return dto;
    }
}
