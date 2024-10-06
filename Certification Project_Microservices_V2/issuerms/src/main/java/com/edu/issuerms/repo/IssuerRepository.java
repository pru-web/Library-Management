package com.edu.issuerms.repo;

import com.edu.issuerms.model.Issuer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IssuerRepository extends JpaRepository<Issuer, Integer> {
}
