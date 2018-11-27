package testjpa.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "links")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
public class LinkDT {
    @Id
    @GeneratedValue(generator = "system-uuid")
    private String id;

    @Column
    private String link;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
