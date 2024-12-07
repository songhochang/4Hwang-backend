package com.green.sahwang.service.impl.cart;

import com.green.sahwang.dto.request.cart.CartProductsRemoveReqDto;
import com.green.sahwang.dto.request.cart.CartProductsReqDto;
import com.green.sahwang.entity.*;
import com.green.sahwang.exception.CartDomainException;
import com.green.sahwang.exception.DomainException;
import com.green.sahwang.exception.ProductDomainException;
import com.green.sahwang.exception.PurchaseDomainException;
import com.green.sahwang.exception.payment.PaymentDomainException;
import com.green.sahwang.model.payment.avro.PurchasePaidEventAvroModel;
import com.green.sahwang.repository.*;
import com.green.sahwang.service.cart.CartService;
import com.green.sahwang.service.impl.cart.helper.CartServiceHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final CartProductRepository cartProductRepository;
    private final CartServiceHelper cartServiceHelper;
    private final PaymentRepository paymentRepository;
    private final PurchasePaymentRepository purchasePaymentRepository;

    @Override
    @Transactional
    public Cart getCartForMember(Long memberId) {
        Member member = getMemberById(memberId);
        return cartRepository.findByMember(member)
                .orElseGet(() -> createNewCartForMember(member));
    }

    @Override
    @Transactional
    public void addProductToCart(String email, Long productId, int quantity) {
        Member member = getMemberByEmail(email);

        Cart cart = cartRepository.findByMember(member)
                .orElseGet(() -> createNewCartForMember(member));

        Product product = getProduct(productId);


        CartProduct cartProduct = cartProductRepository.findByCartAndProduct(cart, product)
                .orElse(null);

        if (cartProduct == null) {
            cartProduct = CartProduct.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(quantity)
                    .build();
        } else {
            // 이미 있으면 더함
            cartProduct.setQuantity(cartProduct.getQuantity() + quantity);
        }
        cartProductRepository.save(cartProduct);
    }

    @Override
    @Transactional
    public void removeProductFromCart(String email, List<CartProductsRemoveReqDto> cartProductsRemoveReqDtos) {
        List<Product> products = cartProductsRemoveReqDtos.stream()
                .map(cartProductsRemoveReqDto -> productRepository.findById(cartProductsRemoveReqDto.getProductId())
                        .orElseThrow(() -> new ProductDomainException("productId " + cartProductsRemoveReqDto.getProductId() + " 해당 제품이 존재하지 않습니다"))).toList();

        Member member = getMemberByEmail(email);
        Cart cart = cartRepository.findByMember(member)
                .orElseThrow(() -> new CartDomainException("No cart"));

        List<CartProduct> cartProducts = cartProductRepository.findAllByProductIn(products)
                .stream()
                .filter(cartProduct -> cartProduct.getCart().equals(cart)) // 특정 카트에 해당하는 것만 필터링
                .collect(Collectors.toList());
        cartProductRepository.deleteAll(cartProducts);
    }

    @Override
    @Transactional
    public void mergeProductsInCartWithUserLogin(List<CartProductsReqDto> cartProductsReqDtos, String userEmail) {
        Member member = getMemberByEmail(userEmail);
        Cart cart = getCartForMember(member.getId());

        List<CartProduct> localProducts = cartServiceHelper.getLocalProducts(cart, cartProductsReqDtos);

        List<CartProduct> existingProducts = cartProductRepository.findByCart(cart);

        for (CartProduct localProduct : localProducts) {
            CartProduct existingCartProduct = existingProducts.stream()
                    .filter(product -> product.getProduct().getId().equals(localProduct.getProduct().getId()))
                    .findFirst()
                    .orElse(null);

            if (existingCartProduct != null) {
                existingCartProduct.setQuantity(existingCartProduct.getQuantity() + localProduct.getQuantity());
                cartProductRepository.save(existingCartProduct);
            } else {
                Product product = getProduct(localProduct.getProduct().getId());
                CartProduct cartProduct = CartProduct.builder()
                        .cart(cart)
                        .product(product)
                        .quantity(localProduct.getQuantity())
                        .build();
                cartProductRepository.save(cartProduct);
            }
        }
    }

    @Override
    @Transactional
    public void clearCart(List<String> keys, List<PurchasePaidEventAvroModel> messages) {
        log.info("after paid, clear cart");
        Payment payment = null;
        List<PurchasePaidEventAvroModel> purchasePaidEventAvroModels = messages.stream().toList();
        for (PurchasePaidEventAvroModel purchasePaidEventAvroModel : purchasePaidEventAvroModels) {
            payment = paymentRepository.findByImpUid(purchasePaidEventAvroModel.getTransactionId())
                    .orElseThrow(() -> new PaymentDomainException("No Payment TransactionId"));

            log.info("paymentId : {}, status : {}", payment.getId(), payment.getStatus());
            payment.validatePaidCompletedStatus();

            // 결제된 제품 ID만 제거해야함
            // paymentId -> purchasePayments -> purchaseProduct -> productIds
            List<PurchasePayment> purchasePayments = purchasePaymentRepository.findAllByPayment(payment);
            cartProductRepository.deleteAllByProductIdIn(purchasePayments.stream()
                    .map(purchasePayment -> purchasePayment.getPurchaseProduct().getProduct().getId())
                    .toList());
        }
    }

    private Cart createNewCartForMember(Member member) {
        Cart cart = new Cart();
        cart.setMember(member);
        return cartRepository.save(cart);
    }

    private Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CartDomainException("memberId " + memberId + " 해당 사용자가 존재하지 않습니다."));
    }

    private Member getMemberByEmail(String userEmail) {
        return memberRepository.findByEmail(userEmail);
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductDomainException("productId " + productId + " 해당 제품이 존재하지 않습니다"));
    }
}
