package com.bzvs.easydict.repository.api;

import com.bzvs.easydict.entity.ReviewEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ReviewEventRepository extends JpaRepository<ReviewEventEntity, Long> {

    @Query(value = "SELECT DATE(create_date) as d, " +
            "SUM(CASE WHEN remembered THEN 1 ELSE 0 END) as correct, " +
            "SUM(CASE WHEN NOT remembered THEN 1 ELSE 0 END) as incorrect " +
            "FROM review_event WHERE user_uuid = :userUuid AND deleted = false AND create_date >= :from " +
            "GROUP BY DATE(create_date) ORDER BY d", nativeQuery = true)
    List<Object[]> countByDayForUserFrom(@Param("userUuid") UUID userUuid, @Param("from") LocalDateTime from);
}
