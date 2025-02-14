package ceu.proyecto.fct.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ceu.proyecto.fct.model.PracticeRecord;
@Repository
public interface PracticeRecordRepository extends JpaRepository<PracticeRecord, UUID>{

	
	public List<PracticeRecord> findByAssociatedStudentAndAssociatedDateBetweenAndState(UUID studentId, LocalDate startDate, LocalDate endDate, String state);
	
}
