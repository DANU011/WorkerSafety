package edu.pnu.persitense;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
//CrrudRepository는 crud만 하면 
//JpaRepository crud 플러스 페이징,sort,jpa 
}
