package edu.pnu.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Member;
import edu.pnu.persitense.MemberRepository;
//스프링에 구성요소 등록
@Service
public class MemberServiceImpl implements MemberService {
	//MemberRepository 해당 서비스를 스프링에 등록 의존성 주입
	@Autowired
	private MemberRepository memberRepo;
	
	@Override
	public Member getMember(Member member) {
		//Optional Repository에서 리턴 타입을 Optional로 바로 받을 수 있도록하는것
		Optional<Member> findMember = memberRepo.findById(member.getId());
		// isPresent == !=null
		if(findMember.isPresent())
			return findMember.get();
		else return null;
	}
	
}
