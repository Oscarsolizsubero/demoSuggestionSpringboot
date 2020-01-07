package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Suggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "suggestion_generator")
    @SequenceGenerator(name = "suggestion_generator", initialValue = 100)
    private long id;
    @Column(nullable = false, length = 200)
    private String title;
    @Column(nullable = false)
    private String description;

    public int quantityVote;

    @Temporal(TemporalType.TIMESTAMP)
    public Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    public Date updatedDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User author;

    @ManyToMany(mappedBy = "Suggestionslikes")
    public Set<User> votesUserSuggestions;

    public boolean deleted;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    public Status status;

}
