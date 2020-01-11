package com.psawesome.learning;

import com.psawesome.learning.images.Comment;
import com.psawesome.learning.images.CommentHelper;
import com.psawesome.learning.images.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;

/**
 * package: com.psawesome.learning
 * author: PS
 * DATE: 2020-01-11 토요일 12:39
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ImageService imageService;

    // tag::injection[]
    private final CommentHelper commentHelper;

    // end::injection[]

    @GetMapping("/")
    public Mono<String> index(Model model) {
        model.addAttribute("images",
                imageService
                        .findAllImages()
                        .map(image -> new HashMap<String, Object>() {{
                            put("id", image.getId());
                            put("name", image.getName());
                            put("comments",
                            // tag::comments[]
                                    commentHelper.getComments(image));
                            // end::comments[]
                        }})
        );
        return Mono.just("index");
    }
}
