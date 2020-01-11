package com.psawesome.learning.comments;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * package: com.psawesome.learning.comments
 * author: PS
 * DATE: 2020-01-11 토요일 12:23
 */
@Data
public class Comment {

    @Id
    private String id;
    private String imageId;
    private String comment;
}
