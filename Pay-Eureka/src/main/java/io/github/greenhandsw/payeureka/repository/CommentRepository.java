package io.github.greenhandsw.payeureka.repository;

import io.github.greenhandsw.common.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    public void deleteAllByUserId(Long userId);
}
