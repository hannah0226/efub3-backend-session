package efub.session.blog.post.repository;

import efub.session.blog.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {
    Optional<Post> findByPostIdAndOwner_MemberId(Long postId, Long memberId);
}
