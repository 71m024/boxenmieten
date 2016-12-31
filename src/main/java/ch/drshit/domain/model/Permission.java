package ch.drshit.domain.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author timo
 */
@Entity
@Table(name="`PERMISSION`")
public class Permission implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;
    
    @Column(name="NAME", length=100)
    private String name;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Permission other = (Permission) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public enum BmRole {
        
        EDIT_USERS("EDIT_USERS"),
        EDIT_GROUPS("EDIT_GROUPS"),
        EDIT_ROLES("EDIT_ROLES"),
        EDIT_LOCALES("EDIT_LOCALES");
                
        public final Permission permission;
        
        BmRole(String name) {
            this.permission = new Permission();
            this.permission.setName(name);
        }
        
    }
}
