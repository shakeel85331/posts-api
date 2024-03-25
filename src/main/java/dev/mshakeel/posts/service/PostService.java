package dev.mshakeel.posts.service;

import dev.mshakeel.posts.client.ExternalPostsApiClient;
import dev.mshakeel.posts.entity.Post;
import dev.mshakeel.posts.model.PostDto;
import dev.mshakeel.posts.repository.PostRepository;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
public class PostService {

  private final PostRepository postRepository;

  private final ModelMapper modelMapper;

  private final ExternalPostsApiClient externalPostsApiClient;

  public PostService(PostRepository postRepository, ModelMapper modelMapper,
      ExternalPostsApiClient externalPostsApiClient) {
    this.postRepository = postRepository;
    this.modelMapper = modelMapper;
    this.externalPostsApiClient = externalPostsApiClient;
  }

  public PostDto getPostById(int postId) {
    Post post = postRepository.findById(postId).orElse(null);
    return post != null ? modelMapper.map(post, PostDto.class) : null;
  }

  public List<PostDto> getAllPosts() {
    List<Post> posts = (List<Post>) postRepository.findAll();
    List<PostDto> list = posts.stream().map(post -> modelMapper.map(post, PostDto.class)).toList();
    return list;
  }

  public PostDto addPost(PostDto postDto) {
    Post post = modelMapper.map(postDto, Post.class);
    Post response = postRepository.save(post);

    return modelMapper.map(post, PostDto.class);
  }

  public PostDto updatePost(PostDto postDto, int postId) {
    PostDto response  = getPostById(postId);

    if (response == null) {
      return null;
    }

    Post post = modelMapper.map(postDto, Post.class);
    post.setId(postId);
    postRepository.save(post);
    return modelMapper.map(post, PostDto.class);
  }

  public PostDto deletePost(int postId) {
    PostDto postDto  = getPostById(postId);
    if (postDto == null) {
      return null;
    }

    Post post = modelMapper.map(postDto, Post.class);
    postRepository.delete(post);
    return postDto;
  }

  public List<PostDto> deleteAllPosts() {
    postRepository.deleteAll();
    return getAllPosts();
  }

  public List<PostDto> getAllExternalPosts() {
    return externalPostsApiClient.getAllPosts();
  }

  public List<PostDto> getAllExternalPostsByUserId(int userId) {
    List<PostDto> posts = getAllExternalPosts();
    return posts.stream().filter(postDto -> postDto.getUserId() == userId).toList();
  }
}
