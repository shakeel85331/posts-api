package dev.mshakeel.posts.client;

import dev.mshakeel.posts.model.PostDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "externalPostsClient", url = "${external.posts-api.url}")
public interface ExternalPostsApiClient {

  @GetMapping(path = "/posts", consumes = MediaType.APPLICATION_JSON_VALUE)
  List<PostDto> getAllPosts();

}
