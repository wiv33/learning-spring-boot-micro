package com.psawesome.learning.images;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * package: com.psawesome.learning.images
 * author: PS
 * DATE: 2020-01-11 토요일 12:40
 */
@Data
public class Comment {

    @Id
    private String id;
    private String imageId;
    private String comment;

}
