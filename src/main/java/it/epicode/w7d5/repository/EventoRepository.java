package it.epicode.w7d5.repository;

import it.epicode.w7d5.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer>, PagingAndSortingRepository<Evento, Integer> {
}
