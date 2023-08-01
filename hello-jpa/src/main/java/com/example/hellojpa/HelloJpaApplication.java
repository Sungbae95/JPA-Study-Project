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
			/*
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
			 */

			/*
			// 비영속
			Member member = new Member();
			member.setId(101L);
			member.setName("HelloJPA");

			// 영속 , entitymanager 안에 있는 영속성 컨텍스트에서 관리가 된다. before after 사이세 db에 쿼리가 날라가지 않는다.
			System.out.println("before");
			// 1차 캐시에 저장
			em.persist(member);
//			em.detach(member); // 회원 엔티티를 컨텍스트에서 분리, 준영속 상태
			System.out.println("after");

			// select 문 쿼리가 발생되지 않는다 왜냐 ? em.persist가 실행되서 영속성 컨텍스트에서 관리가 되기 때문에 DB를 조회하지 않고 1차 캐시에서 값이 존재하기 때문에
			// 1차 캐시에서 값을 가져온다.
			Member findMember = em.find(Member.class, 101L);
			System.out.println("findMember.id : " + findMember.getId());
			System.out.println("findMember.name : " + findMember.getName());
*/
			/*
			// findMember1에서 먼저 DB에서 조회를 해서 영속성 컨텍스트에 올려놓는다.
			// 그래서 findMember2에서 DB를 조회하지 않고 영속성 컨텍스트에 있는 1차 캐시에서 조회한다.
			Member findMember1 = em.find(Member.class, 101L);
			Member findMember2 = em.find(Member.class, 101L);
			// true 값을 가짐 , 영속 엔티티의 동일성 보장
			System.out.println("result = " + (findMember1 == findMember2));
			*/

			/*
			Member member1 = new Member(150L, "A");
			Member member2 = new Member(160L, "B");
			// persist 까진 영속성 컨텍스트에 올라갈 뿐이지 커밋이 될 때 까지는 DB에 저장되지 않는다.
			// 이때, 버퍼링 이라는 기능을 사용할 수 있음
			// 바로 쿼리를 날리면 최적화 할 수 있는 방법이 없음.
			em.persist(member1);
			em.persist(member2);
			*/
			/* 엔티티 수정 변경 감지 !
			// JPA는 컬렉션과 비슷하게 다루기 때문에 값을 꺼낸 뒤에 값을 변경 했을 때 자동으로 바꾸고 DB에 변경 된 것을 넣어 준다.
			// 영속 컨텍스트에서 최초로 값을 읽어놓은 시점에서 스냅샷이라는 걸 만들어서 최초의 값들을 저장해놓는다.
			// 값이 변경 되었을 때 JPA가 Entity와 스냅샷을 비교를 한다.
			// 여기서 값이 변경 되었을 때는 쓰기 지연 SQL 저장소에 반영하게 된다.
			// 그 다음 DB에 커밋을 하게 된다.
			// 엔티티 삭제도 이와 동일하게 이루어진다.
			// JPA는 값이 변경되면 트랜젝션이 커밋되는 시점에 변경이 반영된다. 라고 생각하고 코드를 짜는게 맞다.
			Member member = em.find(Member.class, 150L);
			member.setName("ZZZZZ");
			*/
			/*
			플러시
			영속성 컨텍스트의 변경내용을 데이터베이스에 반영
			데이터베이스 트랜잭션에 커밋되면 자동으로 발생
			영속성 컨텍스트를 비우지 않음
			영속성 컨텍스트의 변경내용을 데이터베이스에 동기화
			트랜잭션이라는 작업 단위가 중요 -> 커밋 직전에만 동기화 !

			JPQL 쿼리 실행시 플러시가 자동으로 호출되는 이유
			em.persist(memberA);
			em.persist(memberB);
			em.persist(memberC);

			query = em.createQuery("select m from Member m", Member.class);
			했을 때 위에 persist한 값들은 DB에 저장되지 않아서 쿼리문을 조회 했을 때 A B C 에 대한 값은 없다.
			하지만 JPA 에서는 JPQL 쿼리 실행시 이런 것을 방지하기 위해 자동으로 flush를 실행시켜 DB에 값을 넣어주어
			A B C의 값을 같이 꺼내올 수 있다.

			 */
			Member member = new Member(200L, "member200");
			em.persist(member);
			// commit 되기전에 즉시 실행된다.
			// flush를 해도 1차 캐시는 유지된다.
			em.flush();
			System.out.println("================");
			tx.commit(); // 해당 시점에서 db에 쿼리가 날라간다.
		} catch (Exception e){
			tx.rollback();
		} finally {
			// 사용을 다하고 나면 EntityManager를 꼭 닫아줘야 한다.
			em.close();
		}

		emf.close();

	}

}
