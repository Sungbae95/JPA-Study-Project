package com.example.hellojpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

@SpringBootApplication
public class HelloJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloJpaApplication.class, args);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("sungbae");
		// DB Connection 얻어서 쿼리를 날리고 종료되는 단위를 할때마다 EntityManager를 꼭 작성해줘야함 !
		EntityManager em = emf.createEntityManager();
		// JPA를 하기 위해선 트랜잭션을 받야야함
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try{
//			Member member = new Member();
//			member.setId(2L);
//			member.setName("HelloB");

			// 조회 - PK를 이용한 조회
			Member findMember = em.find(Member.class, 1L);

			// 맴버 객체를 다 가져오는 것, 대상이 객체가 됨
			List<Member> result = em.createQuery("select m from Member as m", Member.class)
							// 페이징 함수 setFirstResult : 몇번부터 시작할껀지, SetMaxResults : 몇개 가지고 올건지
							.setFirstResult(0)
							.setMaxResults(1)
							.getResultList();
			for (Member memeber : result){
				System.out.println(memeber.getName());
			}

			// 값만 바꿔도 데이터베이스에 바로 업데이트가 된다.
			// JPA를 통해도 가지고 오면 JPA가 관리함
			// 바뀌었는 지 안바뀌었는 지 관리를 하기 때문에 변하면 알아서 Update 쿼리가 발생한다.
			findMember.setName("HelloJPA");
			// 삭제
//			em.remove(findMember);

			// persist(~~) 저장이 됨
//			em.persist(member);
			tx.commit();
		} catch (Exception e){
			tx.rollback();
		} finally {
			// 사용을 다하고 나면 EntityManager를 꼭 닫아줘야 한다.
			em.close();
		}

		emf.close();

	}

}
