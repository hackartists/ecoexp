package ecoexp.core.region;

import com.google.common.hash.Hashing;
import ecoexp.common.utils.Generalizer;
import ecoexp.core.program.ProgramDTO;
import ecoexp.core.raw.EcoData;
import ecoexp.core.theme.ThemeDTO;
import org.hibernate.annotations.NaturalId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "regions")
public class RegionDTO {
	@Transient
	private Logger logger = LoggerFactory.getLogger(RegionDTO.class);

	@Column(name="REGION_ID")
    private Long id;

	@NotNull
	@Column(name="NAME")
    private String name;

	@Id
	@NotNull
	@Column(name="REGION_CODE")
	private String regionCode;

	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {CascadeType.PERSIST, CascadeType.MERGE},
			mappedBy="linkedRegions")
    private Set<ProgramDTO> linkedPrograms = new HashSet<ProgramDTO>();

	public RegionDTO() {
    }

    public RegionDTO(String name, ProgramDTO programDTO) {
		logger.debug("In: RegionDTO");
		String md = Hashing.sha256().hashString(name, StandardCharsets.UTF_8).toString().substring(0,4);
		this.id = Long.parseLong(md, 16);
		this.regionCode = String.format("reg%d",this.id);
		this.name= name;
		logger.debug("RegionDTO: {}:{}", this.id, this.name);
		this.linkedPrograms.add(programDTO);

		logger.debug("Out: RegionDTO");
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

	public Set<ProgramDTO> getLinkedPrograms() {
		return linkedPrograms;
	}

	public void setLinkedPrograms(Set<ProgramDTO> linkedPrograms) {
		this.linkedPrograms = linkedPrograms;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
}
