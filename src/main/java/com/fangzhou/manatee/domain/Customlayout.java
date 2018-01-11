package com.fangzhou.manatee.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Customlayout.
 */
@Entity
@Table(name = "customlayout")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Customlayout implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "layout")
    private String layout;

    @Column(name = "jhi_timestamp")
    private LocalDate timestamp;

    @ManyToOne
    private User layout_user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLayout() {
        return layout;
    }

    public Customlayout layout(String layout) {
        this.layout = layout;
        return this;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getTitle() {
        return title;
    }

    public Customlayout title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public Customlayout timestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    public User getLayout_user() {
        return layout_user;
    }

    public Customlayout layout_user(User user) {
        this.layout_user = user;
        return this;
    }

    public void setLayout_user(User user) {
        this.layout_user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customlayout customlayout = (Customlayout) o;
        if (customlayout.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customlayout.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Customlayout{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            "}";
    }
}
