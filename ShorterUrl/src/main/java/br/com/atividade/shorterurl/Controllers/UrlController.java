package br.com.atividade.shorterurl.Controllers;


import br.com.atividade.shorterurl.Services.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;



@RestController
@RequestMapping("/")
public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
   public ResponseEntity<String> urlShorten(@RequestBody String originalUrl){
        String shorterUrl = urlService.shorterUrl(originalUrl);
        return new ResponseEntity<>(shorterUrl, HttpStatus.CREATED);
    }

    @GetMapping("/{shortUrl}")
    public RedirectView redirectToOriginal(@PathVariable String shortUrl){
        String url = urlService.getOriginalUrl(shortUrl);
        return new RedirectView(url);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlerIllegalArgumentException(IllegalArgumentException ex){
        return ex.getMessage();
    }
}
