package dev.mshakeel.posts.controller;

import dev.mshakeel.posts.service.PostService;
import dev.mshakeel.posts.model.PostDto;
import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PostController {

  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping(path = "/posts/{postId}")
  public ResponseEntity<PostDto> getPostById(@PathVariable("postId") int postId) {
    PostDto postDto = postService.getPostById(postId);
    if (postDto == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(postService.getPostById(postId), HttpStatus.OK);
  }

  @GetMapping("/posts")
  public ResponseEntity<List<PostDto>> getAllPosts() {
    return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
  }

  @PostMapping("/posts")
  public ResponseEntity<PostDto> addPost(@RequestBody PostDto postDto) {
    return new ResponseEntity<>(postService.addPost(postDto), HttpStatus.CREATED);
  }

  @PutMapping("/posts/{postId}")
  public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable("postId") int postId)
      throws NotFoundException {
    PostDto response = postService.updatePost(postDto, postId);
    if(response == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/posts/{id}")
  public ResponseEntity<Void> deletePost(@PathVariable("id") int postId) {
    PostDto response = postService.deletePost(postId);
    if(response == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/posts")
  public ResponseEntity<List<PostDto>> deletePost() {
    List<PostDto> list = postService.deleteAllPosts();
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/external/posts")
  public ResponseEntity<List<PostDto>> getAllExternalPosts(@RequestParam(required = false) String userId) {
    System.out.println("UserId :: "+ userId);
    if(userId == null) {
      return new ResponseEntity<>(postService.getAllExternalPosts(), HttpStatus.OK);
    }

    return new ResponseEntity<>(postService.getAllExternalPostsByUserId(Integer.parseInt(userId)), HttpStatus.OK);
  }



}
