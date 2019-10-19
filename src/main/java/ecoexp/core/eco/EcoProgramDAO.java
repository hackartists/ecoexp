package ecoexp.core.eco;

import java.io.IOException;

public interface EcoProgramDAO {
	boolean save(EcoProgramDTO ecoProgram) throws IOException;
}
