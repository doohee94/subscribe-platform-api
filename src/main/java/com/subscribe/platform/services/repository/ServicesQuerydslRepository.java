package com.subscribe.platform.services.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.subscribe.platform.services.dto.ResStoreServiceDto;
import com.subscribe.platform.services.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.subscribe.platform.services.entity.QCategory.category;
import static com.subscribe.platform.services.entity.QServiceCategory.serviceCategory;
import static com.subscribe.platform.services.entity.QServiceImage.serviceImage;
import static com.subscribe.platform.services.entity.QServiceOption.*;
import static com.subscribe.platform.services.entity.QServices.*;

@RequiredArgsConstructor
@Repository
public class ServicesQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public List<Category> findCategory() {
        return queryFactory.selectFrom(category)
                .fetch();
    }

}
