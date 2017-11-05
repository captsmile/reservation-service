package com.example.reservationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collection;
import java.util.stream.Stream;

@SpringBootApplication
public class ReservationServiceApplication {

    @Bean
    CommandLineRunner commandLineRunner(ReservationRepository reservationRepository){
        return strings -> {
            Stream.of("Josh","Pieter","Tasha","Eric","Susie","Max")
                    .forEach( n -> reservationRepository.save(new Reservation(n)));
        };
    }

	public static void main(String[] args) {
	    SpringApplication.run(ReservationServiceApplication.class, args);
	}
}

@RepositoryRestResource
interface ReservationRepository extends JpaRepository<Reservation, Long>{
	@RestResource(path="by-name")
	Collection<Reservation> findByReservationName( @Param("name") String name);
}

@Entity
class Reservation{

	@Id
	@GeneratedValue
	private Long id;
	private String reservationName;

	@Override
	public String toString() {
		return "Reservation{" +
				"id=" + id +
				", reservationName='" + reservationName + '\'' +
				'}';
	}

	public Reservation() {
	}

	public Reservation(String reservationName ) {
		this.reservationName = reservationName;
	}

	public Long getId() {
		return id;
	}

	public String getReservationName() {
		return reservationName;
	}
}