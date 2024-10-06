package com.example.issuems.repo;
import com.example.issuems.domain.IssueRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRepo extends JpaRepository<IssueRecord, Long> {
}
