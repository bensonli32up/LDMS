package ldms.technical.challenge.repository;

import ldms.technical.challenge.entity.ScheduleRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRecordRepository extends JpaRepository<ScheduleRecord, Integer> {
}
