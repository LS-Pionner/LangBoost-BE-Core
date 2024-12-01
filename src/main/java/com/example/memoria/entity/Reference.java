package com.example.memoria.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "sentence_id", nullable = false)
    private Sentence sentence;

    @Column(nullable = false)
    private String url;

    public void updateReference(String newUrl) {
        this.url = newUrl;
    }

}

