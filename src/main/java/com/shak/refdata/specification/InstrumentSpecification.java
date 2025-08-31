package com.shak.refdata.specification;

import com.shak.refdata.entity.Instrument;
import org.springframework.data.jpa.domain.Specification;

public class InstrumentSpecification {
	public static Specification<Instrument> hasIsin(String isin) {
        return (root, query, cb) ->
                isin == null ? null : cb.equal(root.get("isin"), isin);
    }

    public static Specification<Instrument> hasTicker(String ticker) {
        return (root, query, cb) ->
                ticker == null ? null : cb.equal(root.get("ticker"), ticker);
    }

    public static Specification<Instrument> hasExchange(String exchange) {
        return (root, query, cb) ->
                exchange == null ? null : cb.equal(root.get("exchange"), exchange);
    }

    public static Specification<Instrument> hasCurrency(String currency) {
        return (root, query, cb) ->
                currency == null ? null : cb.equal(root.get("currency"), currency);
    }
}
