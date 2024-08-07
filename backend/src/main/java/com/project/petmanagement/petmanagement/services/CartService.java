package com.project.petmanagement.petmanagement.services;

import com.project.petmanagement.petmanagement.JWT.JWTUserDetail;
import com.project.petmanagement.petmanagement.advices.DataNotFoundException;
import com.project.petmanagement.petmanagement.models.entity.Cart;
import com.project.petmanagement.petmanagement.models.entity.CartItem;
import com.project.petmanagement.petmanagement.models.entity.Product;
import com.project.petmanagement.petmanagement.repositories.CartItemRepository;
import com.project.petmanagement.petmanagement.repositories.CartRepository;
import com.project.petmanagement.petmanagement.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    private Cart setTotalPriceForCart(Cart cart) {
        double totalPrice = 0;
        List<CartItem> cartItems = cart.getCartItems();
        if (cartItems != null) {
            for (CartItem cartItem : cartItems) {
                if (cartItem.getSelected()) {
                    totalPrice += cartItem.getQuantity() * cartItem.getProduct().getPrice();
                }
            }
        } else {
            cart.setCartItems(new ArrayList<>());
        }
        cart.setTotalPrice(totalPrice);
        return cart;
    }

    public Cart getCartByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JWTUserDetail jwtUserDetail = (JWTUserDetail) authentication.getPrincipal();
        Cart cart = cartRepository.findByUser(jwtUserDetail.getUser());
        return setTotalPriceForCart(cart);
    }

    @Transactional(rollbackFor = {Exception.class})
    public Cart addItemToCart(Long productId, Integer quantity) throws DataNotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new DataNotFoundException("Can not find product with ID: " + productId));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JWTUserDetail jwtUserDetail = (JWTUserDetail) authentication.getPrincipal();
        Cart cart = cartRepository.findByUser(jwtUserDetail.getUser());
        if (cart == null) {
            Cart newCart = Cart.builder()
                    .user(jwtUserDetail.getUser())
                    .build();
            cart = cartRepository.save(newCart);
        }
        List<CartItem> cartItems = cart.getCartItems();
        List<CartItem> newCartItems = new ArrayList<>();
        if (cartItems != null) {
            CartItem existingCartItem = null;
            boolean existed = false;
            for (CartItem cartItem : cartItems) {
                if (cartItem.getProduct().equals(product)) {
                    existed = true;
                    existingCartItem = cartItem;
                } else {
                    newCartItems.add(cartItem);
                }
            }
            if (existed) {
                int newQuantity = existingCartItem.getQuantity() + quantity;
                existingCartItem.setQuantity(newQuantity);
                newCartItems.add(cartItemRepository.save(existingCartItem));
            } else {
                CartItem cartItem = CartItem.builder()
                        .cart(cart)
                        .product(product)
                        .quantity(quantity)
                        .selected(false)
                        .build();
                newCartItems.add(cartItemRepository.save(cartItem));
            }
        } else {
            CartItem cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(quantity)
                    .selected(false)
                    .build();
            newCartItems.add(cartItemRepository.save(cartItem));
        }
        cart.setCartItems(newCartItems);
        return setTotalPriceForCart(cart);
    }

    @Transactional(rollbackFor = {InvalidParameterException.class})
    public Cart updateItemInCart(Long itemId, Integer quantity, Boolean selected) throws InvalidParameterException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JWTUserDetail jwtUserDetail = (JWTUserDetail) authentication.getPrincipal();
        Cart cart = cartRepository.findByUser(jwtUserDetail.getUser());
        List<CartItem> cartItems = cart.getCartItems();
        List<CartItem> newCartItems = new ArrayList<>();
        if (quantity > 0) {
            for (CartItem cartItem : cartItems) {
                if (cartItem.getId().equals(itemId)) {
                    cartItem.setQuantity(quantity);
                    cartItem.setSelected(selected);
                    newCartItems.add(cartItemRepository.save(cartItem));
                } else {
                    newCartItems.add(cartItem);
                }
            }
        } else {
            throw new InvalidParameterException("Quantity invalid");
        }
        cart.setCartItems(newCartItems);
        return setTotalPriceForCart(cart);
    }

    @Transactional(rollbackFor = {Exception.class})
    public Boolean deleteItemInCart(Long itemId) throws DataNotFoundException {
        CartItem cartItem = cartItemRepository.findById(itemId).orElseThrow(() -> new DataNotFoundException("Can not find item with ID: " + itemId));
        if (cartItem != null) {
            cartItemRepository.deleteById(itemId);
            return true;
        }
        return false;
    }
}