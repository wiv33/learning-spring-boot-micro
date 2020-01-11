package com.psawesome.learning.images;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * package: com.psawesome.learning.images
 * author: PS
 * DATE: 2020-01-11 토요일 12:41
 */
@Data
public class Image {

    @Id
    private String id;

    private String name;

    public Image(String id, String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

}

