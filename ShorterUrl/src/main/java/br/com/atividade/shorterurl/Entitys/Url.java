package br.com.atividade.shorterurl.Entitys;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name= "urls")
@Data
@NoArgsConstructor
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String originalUrl;

    @Column(nullable = false, unique = true)
    private String shortUrl;

    public Url( String originalUrl, String shortUrl) {
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
    }
}
