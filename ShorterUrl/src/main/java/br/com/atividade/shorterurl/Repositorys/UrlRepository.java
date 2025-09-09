package br.com.atividade.shorterurl.Repositorys;

import br.com.atividade.shorterurl.Entitys.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Integer> {
    Optional<Url> findByOriginalUrl(String originalUrl);

    Optional<Url> findByShortUrl(String shortUrl);
}
