package se.fu.lab5_minhnb_he191060.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.fu.lab5_minhnb_he191060.entities.Orchid;

public interface IOrchidRepository extends JpaRepository<Orchid, Integer> {
}
