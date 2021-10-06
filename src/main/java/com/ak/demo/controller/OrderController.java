package com.ak.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ak.demo.exception.ResourceNotFoundException;
import com.ak.demo.model.Order;
import com.ak.demo.repository.OrderRepository;
import com.ak.demo.service.OrderService;

@RestController
public class OrderController {

    private OrderService orderService;

    private OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderService orderService, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @RequestMapping(value = "/rest/orders", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    Iterable<Order> findAll() {
        return orderRepository.findAll();
    }

    @RequestMapping(value = "/rest/orders", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') or hasAuthority('ROLE_USER')")
    @ResponseStatus(value = HttpStatus.CREATED)
    void createOperation(@RequestBody Order order) {
        orderService.save(order);
    }

    @RequestMapping(value = "/rest/orders/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    void delete(@PathVariable Long id) {

        Order order = findOrderOrThrowException(id);
        orderRepository.delete(order);
    }

    private Order findOrderOrThrowException(@PathVariable Long id) {
        return orderRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }


    @RequestMapping(value = "/rest/orders/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR') or hasAuthority('ROLE_USER')")
    Order get(@PathVariable Long id) {
        return orderService.findOrder(id);
    }

	/*
	 * @RequestMapping(value = "/rest/orders/{id}", method = RequestMethod.PUT)
	 * 
	 * @ResponseStatus(value = HttpStatus.NO_CONTENT) void editOrder(@RequestBody
	 * Order order, @PathVariable Long id) {
	 * 
	 * findOrderOrThrowException(id); order.setId(id); orderRepository.save(order);
	 * 
	 * }
	 */


}