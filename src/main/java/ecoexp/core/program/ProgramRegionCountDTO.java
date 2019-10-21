package ecoexp.core.program;

import javax.persistence.*;
import java.math.BigInteger;

public class ProgramRegionCountDTO {
	private String region;
	private Long count;

	public ProgramRegionCountDTO(Object[] data) {
		this.region = (String)data[0];
		this.count = ((BigInteger)data[1]).longValue();
	}

	public final String getRegion() {
		return region;
	}

	public final void setRegion(final String region) {
		this.region = region;
	}

	public final Long getCount() {
		return count;
	}

	public final void setCount(final Long count) {
		this.count = count;
	}

}
