package com.macro.mall.portal.repository;

import com.macro.mall.portal.domain.ProductComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MemberProductCommentRepository extends MongoRepository<ProductComment, String> {
    Page<ProductComment> findByProductIdOrderByCreateTimeDesc(Long memberId, Pageable pageable);

    ProductComment findByProductIdAndMemberId(Long productId, Long id);
}
