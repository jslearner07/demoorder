package com.ak.demo.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.ak.demo.exception.ResourceNotFoundException;
import com.ak.demo.model.Order;
import com.ak.demo.model.OrderLine;
import com.ak.demo.repository.OrderLineRepository;
import com.ak.demo.repository.OrderRepository;
import com.ak.demo.so.BookSO;


@Service
public class OrderService {

	@Autowired
	private RestTemplate restTemplate;

	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	@Value("${books.rest.url}")
	private String booksRestUrl;
	
	Logger logger = LoggerFactory.getLogger(OrderService.class);

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderLineRepository orderLineRepository;

	public Order save(Order order) {
		order.setCreationDate(new Date());
		for (OrderLine orderLine : order.getOrderLines()) {
		    String fetchUrl = booksRestUrl+"/{id}";
		    orderLine.setOrder(order);
		    orderLine.setCreationDate(new Date());
			try {
				ResponseEntity<BookSO> responseEntity = restTemplate.getForEntity(fetchUrl,BookSO.class,
						orderLine.getBookId());
				orderLine.setBookId(null);
				BookSO bookSO = responseEntity.getBody();
				logger.info("Response received from book service "+responseEntity.getStatusCode());
				if(bookSO!=null && bookSO.getId()!=null) {
					orderLine.setBookId(bookSO.getId());
				}
			} catch (HttpStatusCodeException e) {
				logger.error("No Response received from book service on fetch",e);
				orderLine.setBookId(null);
			}
			
			if(orderLine.getBookId()==null) {
				BookSO bookSO = new BookSO();
				bookSO.setAuthor(orderLine.getBookAuthor());
				bookSO.setDescription(orderLine.getBookDescription());
				bookSO.setIsbn(orderLine.getBookIsbn());
				bookSO.setName(orderLine.getBookName());
				bookSO.setPrice(orderLine.getBookPrice());
				
				HttpEntity<BookSO> request = new HttpEntity<>(bookSO);
				BookSO bookSOResponse = restTemplate.postForObject(booksRestUrl, request, BookSO.class);
				if(bookSOResponse!=null && bookSOResponse.getId()!=null) {
					orderLine.setBookId(bookSOResponse.getId());
					logger.info("Response received from book service. Book created"+bookSO.getName());
				}
			}
		    		
			//orderLineRepository.save(orderLine);
		}
		orderRepository.save(order);
		return order;
	}
	
	public Order findOrder(Long orderId) throws ResourceNotFoundException {
		String fetchUrl = booksRestUrl + "/{id}";
		Order orderResponse = orderRepository.findById(orderId).orElseThrow(ResourceNotFoundException::new);
		if (orderResponse != null && !CollectionUtils.isEmpty(orderResponse.getOrderLines())) {
			for (OrderLine orderLine : orderResponse.getOrderLines()) {
				ResponseEntity<BookSO> responseEntity = restTemplate.getForEntity(fetchUrl, BookSO.class,
						orderLine.getBookId());
				BookSO bookSO = responseEntity.getBody();
				logger.info("Response received from book service " + responseEntity.getStatusCode());
				if (bookSO != null && bookSO.getId() != null) {
					orderLine.setBookAuthor(bookSO.getAuthor());
					orderLine.setBookDescription(bookSO.getDescription());
					orderLine.setBookIsbn(bookSO.getIsbn());
					orderLine.setBookName(bookSO.getName());
					orderLine.setBookPrice(bookSO.getPrice());
				}
			}
		}
		return orderResponse;
	}

}
