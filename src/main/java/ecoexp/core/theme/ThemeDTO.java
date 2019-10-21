package ecoexp.core.theme;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import ecoexp.core.program.ProgramDTO;
import javax.persistence.ManyToMany;
import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import org.hibernate.annotations.NaturalId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "themes")
public class ThemeDTO {
	@Transient
	private Logger logger = LoggerFactory.getLogger(ThemeDTO.class);

    @Id
	@Column(name="THEME_ID")
    private Long id;

	@NotNull
	// @NaturalId
	@Column(name="NAME")
    private String name;

	@ManyToMany(fetch = FetchType.LAZY,
			cascade = { CascadeType.PERSIST, CascadeType.MERGE },
			mappedBy="linkedThemes")
	private Set<ProgramDTO> linkedPrograms =  new HashSet<ProgramDTO>();


	public ThemeDTO() {
    }

	public ThemeDTO(String name, ProgramDTO programDTO) {
		String md = Hashing.sha256().hashString(name, StandardCharsets.UTF_8).toString().substring(0,4);
		this.id = Long.parseLong(md, 16);
		logger.debug("{} ID: {}", name, this.id);
		this.name=name;
		this.linkedPrograms.add(programDTO);
    }

    @Override
    public String toString() {
        return "";
    }

    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final Long getThemeId() {
        return id;
    }

    public final void setThemeId(final Long themeId) {
        this.id = themeId;
    }

	public final Set<ProgramDTO> getLinkedPrograms() {
		return linkedPrograms;
	}

	public final void setLinkedPrograms(final Set<ProgramDTO> programDTO) {
		this.linkedPrograms = programDTO;
	}

}
