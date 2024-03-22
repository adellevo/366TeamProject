package csc366.jpademo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;


@Entity
public class Region {

    @Id
    @Column(name="region_id")
    private int region_id;

    @NotNull
    @Column(name="region_name")
    private String region_name;

    @OneToOne
    @JoinColumn(name="regional_manager_id", referencedColumnName = "manager_id", unique = true)
    private RegionalManager regional_manager;



    @Override
    public String toString() {
        return "Region{" +
                "region_id=" + region_id +
                ", region_name='" + region_name + '\'' +
                ", regional_manager=" + regional_manager +
                '}';
    }



    public int getRegion_id() {
        return region_id;
    }



    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }



    public Region(int region_id, @NotNull String region_name, RegionalManager regional_manager) {
        this.region_id = region_id;
        this.region_name = region_name;
        this.regional_manager = regional_manager;
    }



    public String getRegion_name() {
        return region_name;
    }



    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }



    public RegionalManager getRegional_manager() {
        return regional_manager;
    }



    public void setRegional_manager(RegionalManager regional_manager) {
        this.regional_manager = regional_manager;
    }

    
}
