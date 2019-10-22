package ecoexp.algorithm;

import ecoexp.core.region.RegionDTO;
import ecoexp.common.response.RecommendResponse;

public interface SearchAlgorithm {
	RecommendResponse recommendPlace(RegionDTO region, String keyword);
}
