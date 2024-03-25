package dev.mshakeel.posts.repository;

import dev.mshakeel.posts.entity.Post;
import org.springframework.data.repository.CrudRepository;


public interface PostRepository extends CrudRepository<Post, Integer> {

}
