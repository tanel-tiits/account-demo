package no.mnemonic.account.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import no.mnemonic.account.demo.repository.entity.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Long>, QueryByExampleExecutor<AccountEntity> {

    Optional<AccountEntity> findByExternalId(String externalId);
}
