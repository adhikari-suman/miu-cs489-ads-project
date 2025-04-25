package edu.miu.cs489.adswebapp.repository;

import edu.miu.cs489.adswebapp.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {
}
