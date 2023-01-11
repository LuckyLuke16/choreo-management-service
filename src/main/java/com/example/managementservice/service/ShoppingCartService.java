package com.example.managementservice.service;

import com.example.managementservice.exception.NoItemsFoundException;
import com.example.managementservice.exception.ShoppingCartContentNotFoundException;
import com.example.managementservice.model.ItemDetailDTO;
import com.example.managementservice.model.ShoppingCartItemDTO;
import com.example.managementservice.model.ItemQuantityDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ShoppingCartService {

    private final RestTemplate restTemplate;
    private final ProductService productService;

    private final RabbitTemplate rabbitTemplate;

    private final ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(ShoppingCartService.class);

    private final String SHOPPING_CART_SERVICE_URL = "http://localhost:8082";

    public ShoppingCartService(RestTemplate restTemplate, ProductService productService, RabbitTemplate rabbitTemplate, ModelMapper modelMapper) {
        this.restTemplate = restTemplate;
        this.productService = productService;
        this.rabbitTemplate = rabbitTemplate;
        this.modelMapper = modelMapper;
    }

    public void deleteShoppingCartItem(int itemId, String userId) {
        MessagePostProcessor messagePostProcessor = MessageProcessor.buildMessageProperties(userId);
        rabbitTemplate.convertAndSend("choreography-exchange", "shopping-cart.delete", itemId, messagePostProcessor);
    }

    public List<ShoppingCartItemDTO> fetchShoppingCartItems(String userId) throws ShoppingCartContentNotFoundException{
        List<ItemDetailDTO> listOfItems = new ArrayList<>();
        Set<Integer> idsOfShoppingCartItems;
        HashMap<Integer, Integer> cartItemsWithQuantity = new HashMap<>();

        ResponseEntity<ItemQuantityDTO> response = restTemplate.getForEntity(SHOPPING_CART_SERVICE_URL + "/shopping-cart?user=" + userId, ItemQuantityDTO.class);
        if(response.getStatusCode().is2xxSuccessful() && wereItemsFound(response)) {
            cartItemsWithQuantity = response.getBody().getItemsFromShoppingCart();
            idsOfShoppingCartItems = cartItemsWithQuantity.keySet();
            listOfItems = fetchShoppingCartItemDetails(idsOfShoppingCartItems, userId);
        }

        return convertToCartItemDTO(listOfItems, cartItemsWithQuantity);
    }

    private List<ShoppingCartItemDTO> convertToCartItemDTO(List<ItemDetailDTO> listOfItems, HashMap<Integer, Integer> cartItemsWithQuantity) throws ShoppingCartContentNotFoundException{
        List<ShoppingCartItemDTO> shoppingCartItemList = new ArrayList<>();

        if(listOfItems.isEmpty())
            throw new ShoppingCartContentNotFoundException();

        try {
            for (ItemDetailDTO item : listOfItems) {
                ShoppingCartItemDTO shoppingCartItemDTO = modelMapper.map(item, ShoppingCartItemDTO.class);
                shoppingCartItemDTO.setQuantityInCart(cartItemsWithQuantity.get(item.getId()));
                shoppingCartItemList.add(shoppingCartItemDTO);
            }
        } catch (Exception e) {
            logger.warn("Items could not be properly mapped to a ShoppingCartDTO");
        }

        return shoppingCartItemList;
    }

    private List<ItemDetailDTO> fetchShoppingCartItemDetails(Set<Integer> idsOfShoppingCartItems, String userId) {
        List<ItemDetailDTO> listOfItems = new ArrayList<>();
        for(Integer id : idsOfShoppingCartItems) {
            try {
                listOfItems.add(productService.fetchSingleItem(id));
            } catch (NoItemsFoundException e) {
                logger.warn("Item with id: {} not found.", id);
                this.deleteNonExistingShoppingCartItem(id, userId);
            } catch (Exception e) {
                logger.warn("Error when fetching details of shopping cart item from product service");
            }
        }
        return listOfItems;
    }

    private void deleteNonExistingShoppingCartItem(int i, String userId) {
        try {
            this.deleteShoppingCartItem(i, userId);
        } catch (Exception e) {
            logger.warn("Shopping cart item could not be deleted ", e);
        }
    }

    private boolean wereItemsFound(ResponseEntity<ItemQuantityDTO> response) {
        return response.getBody() != null && response.getBody().getItemsFromShoppingCart() != null && !response.getBody().getItemsFromShoppingCart().isEmpty();
    }

    public void addShoppingCartItem(int itemId, String userId) {
        MessagePostProcessor messagePostProcessor = MessageProcessor.buildMessageProperties(userId);
        rabbitTemplate.convertAndSend("choreography-exchange", "shopping-cart.add", itemId, messagePostProcessor);
    }
}
