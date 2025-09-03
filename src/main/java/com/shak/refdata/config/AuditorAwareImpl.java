package com.shak.refdata.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorProvider")
public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		// In real-world: extract from SecurityContext (e.g. logged-in user)
		return Optional.of("system-user");
	}
}