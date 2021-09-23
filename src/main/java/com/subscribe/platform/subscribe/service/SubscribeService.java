package com.subscribe.platform.subscribe.service;

import com.subscribe.platform.common.model.ListResponse;
import com.subscribe.platform.services.entity.ImageType;
import com.subscribe.platform.services.repository.ServiceOptionRepository;
import com.subscribe.platform.subscribe.dto.ReqCancelSubscribeDto;
import com.subscribe.platform.subscribe.dto.ResShoppingDto;
import com.subscribe.platform.subscribe.dto.ResSubscribeListDto;
import com.subscribe.platform.subscribe.entity.Status;
import com.subscribe.platform.subscribe.entity.Subscribe;
import com.subscribe.platform.subscribe.repository.SubscribeRepository;
import com.subscribe.platform.user.entity.User;
import com.subscribe.platform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final ServiceOptionRepository serviceOptionRepository;
    private final UserRepository userRepository;

    /**
     * 나의 구독 리스트 조회
     */
    public ListResponse<ResSubscribeListDto> subscribesByCustomer() throws Exception {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        List<Subscribe> subscribes = subscribeRepository.findByCustomerIdAndStatus(user.getCustomer().getId(), Status.SUBSCRIBE);


        List<ResSubscribeListDto> result = subscribes.stream().map(
                subscribe -> ResSubscribeListDto.builder()
                        .serviceName(subscribe.getServices().getName())
                        .serviceImage(
                                subscribe.getServices()
                                        .getServiceImages().stream()
                                        .filter(image -> image.getImageType() == ImageType.THUMBNAIL && image.getImageSeq() == 1)
                                        .map(image -> image.getFakeName() + image.getExtensionName())
                                        .findFirst()
                                        .orElse(null)
                        )
                        .subscribe(subscribe)
                        .serviceOptions(
                                serviceOptionRepository.findByIdIn(
                                        subscribe.getPickedOptions().stream()
                                                .map(o -> o.getOptionId()).collect(Collectors.toList())
                                )
                        )
                        .paidCount(0)
                        .build()
        ).collect(Collectors.toList());


        return new ListResponse(result);

    }

    /**
     * 구독취소
     */
    @Transactional
    public void cancelSubscribe(ReqCancelSubscribeDto cancelDto) throws Exception {
        Optional<Subscribe> subscribe = subscribeRepository.findByIdAndStatus(cancelDto.getSubscribeId(), Status.SUBSCRIBE);
        // 취소
        subscribe.orElseThrow(EntityNotFoundException::new).cancelSubscribe(cancelDto.getCancelReason());
    }

    /**
     * 장바구니 리스트
     */
    public ListResponse<ResShoppingDto> shoppingList() throws Exception {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        List<Subscribe> subscribes = subscribeRepository.findByCustomerIdAndStatus(user.getCustomer().getId(), Status.SHOPPING);

        List<ResShoppingDto> result = subscribes.stream().map(
                subscribe -> ResShoppingDto.builder()
                        .serviceName(subscribe.getServices().getName())
                        .serviceImage(
                                subscribe.getServices().getServiceImages().stream()
                                        .filter(image -> image.getImageType() == ImageType.THUMBNAIL && image.getImageSeq() == 1)
                                        .map(image -> image.getFakeName() + image.getExtensionName())
                                        .findFirst()
                                        .orElse(null)
                        )
                        .serviceOptions(
                                serviceOptionRepository.findByIdIn(
                                        subscribe.getPickedOptions().stream()
                                                .map(o -> o.getOptionId()).collect(Collectors.toList())
                                )
                        )
                        .subscribe(subscribe)
                        .build()
        ).collect(Collectors.toList());

        return new ListResponse(result);
    }

    /**
     * 구독하기
     */
    @Transactional
    public void subscribe(List<Long> subscribeIds) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);

        // 장바구니정보 확인
        for (Long subscribeId : subscribeIds) {
            Subscribe subscribe = subscribeRepository.findByIdAndStatus(subscribeId, Status.SHOPPING).orElseThrow(EntityNotFoundException::new);
            // 장바구니에 담긴 물품이 있다면
            // 1. 구독으로 상태변경
            subscribe.startSubscribe();
        }

        // 2. 정상적으로 끝나면 모든 장바구니에 온 물건 결제 (option가격 다 더해서 결제정보 save하자)
    }

}
