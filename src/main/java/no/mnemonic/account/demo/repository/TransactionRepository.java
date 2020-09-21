package no.mnemonic.account.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import no.mnemonic.account.demo.repository.entity.TransactionEntity;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long>, QueryByExampleExecutor<TransactionEntity> {

    Optional<TransactionEntity> findByExternalId(String externalId);

    @Query(value = "select te from TransactionEntity te "
            + " where te.executedTime is null "
            + " order by te.registeredTime asc ")
    List<TransactionEntity> findAllUnexecuted();
}
