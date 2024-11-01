package com.green.sahwang.detail.service;

import com.green.sahwang.detail.dto.response.*;
import com.green.sahwang.dto.response.ImageResDto;
import com.green.sahwang.entity.*;
import com.green.sahwang.exception.BizException;
import com.green.sahwang.exception.ErrorCode;
import com.green.sahwang.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductDetailPagePageServiceImpl implements ProductDetailPageService {

    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;
    private final PurchaseProductRepository purchaseProductRepository;
    private final PurchasePaymentRepository purchasePaymentRepository;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final FavoriteRepository favoriteRepository;

    @Transactional
    public List<DetailChartResDto> getSaleProducts(Long productId, int size){
        Product product = productRepository.findById(productId).orElseThrow();
        Product product1 = productRepository.findByNameAndSize(product.getName(), size);
        List<PurchaseProduct> purchaseProductList = purchaseProductRepository.findAllByProduct(product1);
        List<PurchasePayment> purchasePaymentList = purchasePaymentRepository.findAllByPurchaseProductIn(purchaseProductList);

        if (purchasePaymentList.isEmpty()){
            throw new BizException(ErrorCode.NO_PURCHASE_PRODUCT);
//            return null;
        }


        List<DetailChartResDto> detailChartResDtoList = new ArrayList<>();

        for (PurchasePayment purchasePayment : purchasePaymentList){
            DetailChartResDto detailChartResDto = new DetailChartResDto();
            detailChartResDto.setTradePrice(product.getPrice());
            detailChartResDto.setTradeCompletedDate(purchasePayment.getCreatedDate());

            detailChartResDtoList.add(detailChartResDto);
        }

        return detailChartResDtoList;
    }

    @Transactional
    public DetailImagesResDto getProductImages(Long productId){
        Product product = productRepository.findById(productId)
                .orElse(null);
        List<ProductImage> productImages = productImageRepository.findAllByProduct(product);

        List<ImageResDto> imageResDtoList = new ArrayList<>();
        for (ProductImage productImage : productImages) {
            imageResDtoList.add(new ImageResDto(productImage.getFilename(), productImage.getPath(), productImage.getFileDesc()));
        }
        DetailImagesResDto detailImagesResDto = new DetailImagesResDto();
        detailImagesResDto.setImages(imageResDtoList);

        return detailImagesResDto;
    }

    @Transactional
    public List<DetailProductInfoResDto> getDetailProductInfo(Long productId){
        Product product = productRepository.findById(productId).orElseThrow();
        List<Product> productList = productRepository.findAllByName(product.getName());

        List<DetailProductInfoResDto> detailProductInfoResDtoList = new ArrayList<>();

        for (Product product1 : productList){
            DetailProductInfoResDto detailProductInfoResDto = new DetailProductInfoResDto();
            detailProductInfoResDto.setBrandName(product1.getBrand().getName());
            detailProductInfoResDto.setProductName(product1.getName());
            detailProductInfoResDto.setSize(product1.getSize());
            detailProductInfoResDto.setPrice(product1.getPrice());

            detailProductInfoResDtoList.add(detailProductInfoResDto);
        }

        return detailProductInfoResDtoList;
    }

    @Transactional
    public DetailReviewInfoResDto getDetailReviewInfo(Long productId){
        List<PurchaseProduct> purchaseProductList = purchaseProductRepository.findAllByProductId(productId);
        List<Review> reviewList = reviewRepository.findAllByPurchaseProductIn(purchaseProductList);
        List<Favorite> favoriteList = favoriteRepository.findAllByReviewIn(reviewList);

        int oneStarCount = 0;
        int twoStarCount = 0;
        int threeStarCount = 0;
        int fourStarCount = 0;
        int fiveStarCount = 0;
        double totalStars = 0.0;

        for (Review review : reviewList) {
            double star = review.getStar();
            totalStars += star; // 총 별점 합계

            if (star == 1.0) oneStarCount++;
            else if (star == 2.0) twoStarCount++;
            else if (star == 3.0) threeStarCount++;
            else if (star == 4.0) fourStarCount++;
            else if (star == 5.0) fiveStarCount++;
        }

        // 별점 평균 계산
        double averageStar = reviewList.isEmpty() ? 0.0 : totalStars / reviewList.size();

        DetailReviewInfoResDto detailReviewInfoResDto = new DetailReviewInfoResDto();
        detailReviewInfoResDto.setStarAverage(averageStar);
        detailReviewInfoResDto.setOneStarCount(oneStarCount);
        detailReviewInfoResDto.setTwoStarCount(twoStarCount);
        detailReviewInfoResDto.setThreeStarCount(threeStarCount);
        detailReviewInfoResDto.setFourStarCount(fourStarCount);
        detailReviewInfoResDto.setFiveStarCount(fiveStarCount);
        detailReviewInfoResDto.setReviewCount(reviewList.size());
        detailReviewInfoResDto.setFavoriteCount(favoriteList.size());

        return detailReviewInfoResDto;
    }

    @Transactional
    public DetailImageResDto getDetailPageImage(Long productId){


        return null;
    }

    @Transactional
    public List<ReviewResDto> getReviewPages(Long productId, int pageNum, int size){
        List<PurchaseProduct> purchaseProductList = purchaseProductRepository.findAllByProductId(productId);

        Pageable pageable = PageRequest.of(pageNum, size);

        Page<Review> reviewList = reviewRepository.findAllByPurchaseProductIn(purchaseProductList, pageable);

        List<ReviewResDto> reviewResDtoList = new ArrayList<>();

        for(Review review : reviewList.getContent()){
            Member member = review.getMember();
            MemberDetailReviewResDto memberDetailReviewResDto = new MemberDetailReviewResDto(
                    member.getNickName(), member.getProfileImage()
            );

            ReviewResDto reviewResDto = new ReviewResDto();
            reviewResDto.setReviewId(review.getId());
            reviewResDto.setStar(review.getStar());
            reviewResDto.setContent(review.getContent());
            reviewResDto.setReviewCreationDate(review.getReviewCreationDate());
            reviewResDto.setReviewModifiedDate(review.getReviewModifiedDate());
            reviewResDto.setMemberDetailReviewResDto(memberDetailReviewResDto);

            reviewResDtoList.add(reviewResDto);
        }

        return reviewResDtoList;
    }

    @Transactional
    public List<FavoriteCheckedResDto> getChecked(Long productId, UserDetails userDetails){
        List<PurchaseProduct> purchaseProductList = purchaseProductRepository.findAllByProductId(productId);
        List<Review> reviewList = reviewRepository.findAllByPurchaseProductIn(purchaseProductList);
        List<Favorite> favoriteList = favoriteRepository.findAllByReviewIn(reviewList);
        List<Member> memberList = memberRepository.findAllByEmail(userDetails.getUsername());

        Set<Long> favoriteMemberIds = favoriteList.stream()
                .map(favorite -> favorite.getMember().getId())
                .collect(Collectors.toSet());

        List<FavoriteCheckedResDto> favoriteCheckedResDtoList = memberList.stream()
                .map(member -> {
                    Boolean isChecked = favoriteMemberIds.contains(member.getId());
                    FavoriteCheckedResDto favoriteCheckedResDto = new FavoriteCheckedResDto();
                    favoriteCheckedResDto.setChecked(isChecked);
                    return favoriteCheckedResDto;
                })
                .toList();

        return favoriteCheckedResDtoList;
    }

    @Transactional
    public String clickFavorite(Long reviewId, UserDetails userDetails){
        Member member = memberRepository.findByEmail(userDetails.getUsername());
        Review review = reviewRepository.findById(reviewId).orElseThrow(()->new NoSuchElementException("잘못된 리뷰Id 입니다"));

        Favorite favorite = Favorite.builder()
                .review(review)
                .member(member)
                .build();

        favoriteRepository.save(favorite);
        return "Success";
    }

    @Transactional
    public String cancelFavorite(Long reviewId, UserDetails userDetails){
        Member member = memberRepository.findByEmail(userDetails.getUsername());
        Review review = reviewRepository.findById(reviewId).orElseThrow(()->new NoSuchElementException("잘못된 리뷰Id 입니다"));
        Favorite favorite = favoriteRepository.findByMemberAndReview(member, review);

        favoriteRepository.deleteById(favorite.getId());
        return "Cancel";
    }

}