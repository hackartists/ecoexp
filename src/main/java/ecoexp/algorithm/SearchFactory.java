package ecoexp.algorithm;

import ecoexp.common.exception.EcoException;
import ecoexp.common.response.ErrorCode;



public class SearchFactory {
	public SearchAlgorithm createSearchAlgorithm(SearchStrategy strategy) throws EcoException {
		SearchAlgorithm res=null;

		switch(strategy) {
		case WeightedRegionStrategy:
			res = new WeightedRegionSearch();
			break;
		default:
			throw new EcoException(ErrorCode.UnknownSearchStrategyCode, "unknown search strategy type");
		}

		return res;
	}
}
