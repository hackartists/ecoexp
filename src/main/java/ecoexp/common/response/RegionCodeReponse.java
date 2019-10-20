package ecoexp.common.response;

import ecoexp.core.region.RegionDTO;

import java.util.ArrayList;
import java.util.List;

class regionCode {
    public String regionCode;
    public String name;

    public regionCode(String regionCode, String name) {
        this.regionCode = regionCode;
        this.name = name;
    }
}

public class RegionCodeReponse {
    public List<regionCode> regions = new ArrayList<regionCode>();

    public void addRegion(String regionCode, String name) {
        this.regions.add(new regionCode(regionCode,name));
    }

    public void addRegion(RegionDTO dto) {
        this.regions.add(new regionCode(dto.getRegionCode(),dto.getName()));
    }
}
