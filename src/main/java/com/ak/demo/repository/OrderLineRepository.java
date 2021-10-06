package com.ak.demo.repository;

import com.ak.demo.model.OrderLine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineRepository extends CrudRepository<OrderLine, Long> {
}
