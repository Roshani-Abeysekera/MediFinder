package com.adl.interns.medifinder.entity;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
@Table(name="searchCount")

public class SearchCount{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="search_id")
    private Long id;

    @Column(name="search_name")
    private String name;

    @Column(name="search_count")
    private int count;

    @Autowired
    public SearchCount(Long id, String name, int count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }

    public SearchCount() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "SearchCount{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
