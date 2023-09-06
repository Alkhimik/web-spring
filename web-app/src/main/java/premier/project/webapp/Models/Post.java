package premier.project.webapp.Models;

import jakarta.persistence.*;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 60)
    private String title;

    @Column(length = 100)
    private String anons;

    @Column(length = 1500)
    private String full_text;
    private int views;
    private String author;
    private Long authorId;

    public Post(){}
    public Post(String title, String anons, String full_text, String author, Long authorId) {
        this.title = title;
        this.anons = anons;
        this.full_text = full_text;
        this.author = author;
        this.authorId = authorId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnons() {
        return anons;
    }

    public void setAnons(String anons) {
        this.anons = anons;
    }

    public String getFull_text() {
        return full_text;
    }

    public void setFull_text(String full_text) {
        this.full_text = full_text;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    @Override
    public String toString(){
        return "[login = " + id + ", title = " + title +  ", anons = " + anons + ", \n full_text = " + full_text + "]";
    }
}
