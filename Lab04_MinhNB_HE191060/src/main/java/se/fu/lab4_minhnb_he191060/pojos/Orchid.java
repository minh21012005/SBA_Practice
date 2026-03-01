package se.fu.lab4_minhnb_he191060.pojos;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "orchid")
public class Orchid implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orchidId;

    @Column(name = "orchid_name")
    private String orchidName;

    @Column(name = "is_natural", columnDefinition = "bit default 0")
    private boolean isNatural;

    @Column(name = "orchid_description")
    private String orchidDescription;

    @Column(name = "orchid_category")
    private String orchidCategory;

    @Column(name = "is_attractive", columnDefinition = "bit default 0")
    private boolean isAttractive;

    @Column(name = "orchid_url")
    private String orchidUrl;

    public String getOrchidName() {
        return orchidName;
    }

    public void setOrchidName(String orchidName) {
        this.orchidName = orchidName;
    }

    public String getOrchidDescription() {
        return orchidDescription;
    }

    public void setOrchidDescription(String orchidDescription) {
        this.orchidDescription = orchidDescription;
    }

    public int getOrchidId() {
        return orchidId;
    }

    public void setOrchidId(int orchidId) {
        this.orchidId = orchidId;
    }

    public boolean isNatural() {
        return isNatural;
    }

    public void setNatural(boolean natural) {
        isNatural = natural;
    }

    public String getOrchidCategory() {
        return orchidCategory;
    }

    public void setOrchidCategory(String orchidCategory) {
        this.orchidCategory = orchidCategory;
    }

    public boolean isAttractive() {
        return isAttractive;
    }

    public void setAttractive(boolean attractive) {
        isAttractive = attractive;
    }

    public String getOrchidUrl() {
        return orchidUrl;
    }

    public void setOrchidUrl(String orchidUrl) {
        this.orchidUrl = orchidUrl;
    }
}
