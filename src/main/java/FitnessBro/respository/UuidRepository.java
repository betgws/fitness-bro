package FitnessBro.respository;

import FitnessBro.domain.common.Uuid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UuidRepository extends JpaRepository<Uuid, Long> {
    Uuid findByUuid(String savedUuid);

    void deleteByUuid(String savedUuid);
}

