package com.subscribe.platform.services.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.subscribe.platform.services.dto.QResServiceListDto;
import com.subscribe.platform.services.dto.ResServiceListDto;
import com.subscribe.platform.services.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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


    public Page<ResServiceListDto> findServicesByCategory(Long categoryId, Pageable pageable){
        QueryResults<ResServiceListDto> results = queryFactory.select(new QResServiceListDto(services.id, services.name, serviceImage.fakeName.concat(serviceImage.extensionName)))
                .from(services)
                .join(services.serviceCategories, serviceCategory)
                .join(services.serviceImages, serviceImage)
                .where(serviceCategory.category.id.eq(categoryId),
                        serviceImage.imageType.eq(ImageType.THUMBNAIL),
                        serviceImage.imageSeq.eq(1))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ResServiceListDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
}
