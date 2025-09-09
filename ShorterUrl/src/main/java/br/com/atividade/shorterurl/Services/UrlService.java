package br.com.atividade.shorterurl.Services;

import br.com.atividade.shorterurl.Entitys.Url;
import br.com.atividade.shorterurl.Exception.UrlNotFoundException;
import br.com.atividade.shorterurl.Repositorys.UrlRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final SecureRandom secureRandom;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
        this.secureRandom = new SecureRandom();
    }

    public String shorterUrl(String originalUrl){
      try {
          //Valida a URL desde o início
          new java.net.URL(originalUrl).toURI();

          //Agrega-se a variável findedUrl a procura no banco de dados pelo parâmetro
          Optional<Url> findedUrl = urlRepository.findByOriginalUrl(originalUrl);

          //Se ela existir, retorna a Url encurtada da mesma !
          if (findedUrl.isPresent()) {
              return findedUrl.get().getShortUrl();
          }

          // Caso não encontre cria-se uma nova

          String shortUrl;

          do {
              // cria-se um array com 6 bytes que armazenará 8 caracteres
              byte[] radomBytes = new byte[6];


              //Gera numeros aleatóriamente de forma segura
              secureRandom.nextBytes(radomBytes);


              //transforma os bytes aleatórios em uma String legível
              shortUrl = Base64.getUrlEncoder().withoutPadding().encodeToString(radomBytes);

          } while (urlRepository.findByShortUrl(shortUrl).isPresent());

          Url newUrl = new Url(originalUrl, shortUrl);
          urlRepository.save(newUrl);
          return shortUrl;

      }catch (Exception e){
          throw new IllegalArgumentException("Invalid URl: "+ originalUrl);
      }
    }

    public String getOriginalUrl(String shortUrl){
      Url url = urlRepository.findByShortUrl(shortUrl).orElseThrow(()-> new UrlNotFoundException("URL with short code "+ shortUrl));
      return url.getOriginalUrl();
    }

}
