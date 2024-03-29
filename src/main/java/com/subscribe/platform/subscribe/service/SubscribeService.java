package com.subscribe.platform.subscribe.service;

import com.subscribe.platform.common.model.ListResponse;
import com.subscribe.platform.services.entity.ImageType;
import com.subscribe.platform.services.entity.ServiceOption;
import com.subscribe.platform.services.entity.Services;
import com.subscribe.platform.services.repository.ServiceOptionRepository;
import com.subscribe.platform.services.repository.ServicesRepository;
import com.subscribe.platform.subscribe.dto.*;
import com.subscribe.platform.subscribe.entity.*;
import com.subscribe.platform.subscribe.event.DeliveryEventDto;
import com.subscribe.platform.subscribe.repository.PaymentResultRepository;
import com.subscribe.platform.subscribe.repository.SubscribeRepository;
import com.subscribe.platform.user.entity.Customer;
import com.subscribe.platform.user.entity.User;
import com.subscribe.platform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final ServiceOptionRepository serviceOptionRepository;
    private final ServicesRepository servicesRepository;
    private final UserRepository userRepository;
    private final PaymentResultRepository paymentResultRepository;

    private final ApplicationEventPublisher eventPublisher;


    /**
     * 나의 구독 리스트 조회
     */
    public ListResponse<ResSubscribeListDto> subscribesByCustomer(){

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
    public void cancelSubscribe(ReqCancelSubscribeDto cancelDto){

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);

        Subscribe subscribe = subscribeRepository.findByIdAndStatus(cancelDto.getSubscribeId(), Status.SUBSCRIBE).orElseThrow(EntityNotFoundException::new);
        // 취소
        subscribe.cancelSubscribe(cancelDto.getCancelReasonId(), cancelDto.getCancelReasonEtc());

    }

    /**
     * 장바구니 리스트
     */
    public ListResponse<ResShoppingDto> shoppingList(){

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
     * 장바구니 물건 삭제
     */
    @Transactional
    public void removeShopping(Long subscribeId){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = userRepository.findByEmail(email).getCustomer();

        subscribeRepository.delete(
                subscribeRepository.findByIdAndStatusAndCustomerId(subscribeId, Status.SHOPPING, customer.getId())
                        .orElseThrow(EntityNotFoundException::new)
        );

    }

    /**
     * 구독하기
     */
    @Transactional
    public void subscribe(ReqPayInfoDto payInfoDto){

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = userRepository.findByEmail(email).getCustomer();

        int totalPrice =0;
        String subscribes = "";
        // 장바구니정보 확인
        for (Long subscribeId : payInfoDto.getSubscribeIds()) {
            Subscribe subscribe = subscribeRepository.findByIdAndStatusAndCustomerId(subscribeId, Status.SHOPPING, customer.getId())
                .orElseThrow(EntityNotFoundException::new);
            // 장바구니에 담긴 물품이 있다면
            // 1. 구독으로 상태변경
            subscribe.startSubscribe();

            Set<PickedOption> pickedOptions = subscribe.getPickedOptions();
            // 2. 정상적으로 끝나면 총 결제금액 계산 (option가격 다 더해서 결제정보 save하자)
            if(pickedOptions == null){
                throw new EntityNotFoundException("결제할 서비스의 옵션이 존재하지 않음");
            }
            for (PickedOption pickedOption : pickedOptions) {
                ServiceOption option = serviceOptionRepository.findById(pickedOption.getOptionId()).orElseThrow(EntityNotFoundException::new);
                totalPrice += (option.getPrice() * pickedOption.getQuantity());
            }

            // 3. 결제정보 조회 : 기존 결제정보에 일치하는정보가 있는지 확인. 없으면 새로 저장
            Set<PayInfo> payInfos = customer.getPayInfos();
            boolean isSaved = false;
            for (PayInfo payInfo : payInfos) {
                if(payInfo.isSavedInfo(payInfoDto.getCardNo(), payInfoDto.getCreditCardCompany())){   // 기존 정보 존재하는 경우
                    payInfo.addSubscribe(subscribe);
                    isSaved = true;
                    break;
                }
            }
            if(!isSaved){    // 기존정보 존재 안하는 경우 새로 결제정보 만들어서 저장
                PayInfo newPayInfo = PayInfo.builder()
                        .creditCardCompany(payInfoDto.getCreditCardCompany())
                        .cardNo(payInfoDto.getCardNo())
                        .subscribe(subscribe)
                        .build();

                customer.addPayInfo(newPayInfo);
            }

            subscribes += subscribe.getId()+",";
        }

        // 4. 결제 결과 저장
        savePaymentResult(payInfoDto, customer, totalPrice, subscribes);

        //5. 배송정보 저장 이벤트
        eventPublisher.publishEvent(new DeliveryEventDto(subscribes));
    }

    /**
     * 결제 결과 저장 메서드
     */
    private void savePaymentResult(ReqPayInfoDto payInfoDto, Customer customer, int totalPrice, String subscribes) {
        PaymentResult paymentResult = PaymentResult.builder()
                .status(PayStatus.PAID)
                .ownerId(customer.getId())
                .creditCardCompany(payInfoDto.getCreditCardCompany())
                .paidCardNo(payInfoDto.getCardNo())
                .payPrice(totalPrice)
                .subscribes(subscribes.substring(0, subscribes.length()-1))
                .build();
        paymentResultRepository.save(paymentResult);
    }

    /**
     * 쇼핑리스트 등록
     */
    public void addShoppingList(CreateShoppingDto shoppingDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = userRepository.findByEmail(email).getCustomer();

        Services services = servicesRepository
                .findById(shoppingDto.getServiceId())
                .orElseThrow(EntityNotFoundException::new);

        Subscribe subscribe = shoppingDto.of(customer, services);

        subscribeRepository.save(subscribe);

    }

    public List<Subscribe> findByIds(List<Long> subscribeIds) {
        return subscribeRepository.findByIdIn(subscribeIds);
    }
}
