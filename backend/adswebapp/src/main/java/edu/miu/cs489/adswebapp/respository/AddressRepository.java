package edu.miu.cs489.adswebapp.respository;

import edu.miu.cs489.adswebapp.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    @Query("SELECT COUNT(a) FROM Address a")
    long size();

}
