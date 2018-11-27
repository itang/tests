package testjpa.domain;


public class LinkDTO {
    private String id;
    private String link;

    public LinkDTO() {
    }

    public LinkDTO(String id, String link) {
        this.id = id;
        this.link = link;
    }

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
