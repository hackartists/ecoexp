package ecoexp.core.eco;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EcoProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String themeId;

    public EcoProgram() {
    }

    public EcoProgram(String name) {
        this.name=name;
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

    public final String getThemeId() {
        return themeId;
    }

    public final void setThemeId(final String themeId) {
        this.themeId = themeId;
    }
}
