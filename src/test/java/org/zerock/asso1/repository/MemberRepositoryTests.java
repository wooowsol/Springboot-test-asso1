package org.zerock.asso1.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.asso1.domain.Member;
import org.zerock.asso1.domain.ReBoard;

@SpringBootTest
public class MemberRepositoryTests {
    
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReBoardRepository boardRepository;

    @Test
    public void insertDummiesBoard(){

        IntStream.rangeClosed(1, 500).forEach(i -> {

            Member writer = Member.builder().mid("u" + (i % 100)).build();

            ReBoard board = ReBoard.builder()
            .title("Title.........." + i)
            .content("Content..............." + i)
            .writer(writer)
            .build();

            System.out.println(boardRepository.save(board));

        });

    }

    @Test
    public void insertDummies(){

        IntStream.rangeClosed(0,100).forEach(i -> {

            Member member = Member.builder()
            .mid("u"+i)
            .mpw("u"+i)
            .mname("USER-"+i)
            .build();

            System.out.println(memberRepository.save(member));

        });

    }


    @Test
    public void testRead(){

        Long bno = 500L;

        Optional<ReBoard> result = boardRepository.findById(bno);

        if(result.isPresent()){
            ReBoard board = result.get();
            System.out.println(board.getBno());
            System.out.println(board.getTitle());
            System.out.println(board.getWriter().getMname());
        }

    }

    @Transactional
    @Test
    public void testPage(){

        Pageable pageable
         = PageRequest.of(0,10,Sort.Direction.DESC,"bno");

        Page<ReBoard> result 
         = boardRepository.findAll(pageable);
        
        result.get().forEach(b -> {
            System.out.println(b.getBno());
            System.out.println(b.getTitle());
            System.out.println(b.getWriter().getMid());
            System.out.println(b.getWriter().getMname());


            System.out.println("----------------");
        }); 

    }

    @Test
    public void testSimpleList(){

        List<ReBoard> list = boardRepository.getList();
        
        for(int i = 0; i <10; i++){

            ReBoard board = list.get(i);

            System.out.println(board.getBno());
            System.out.println(board.getTitle());
            System.out.println(board.getWriter().getMid());
            System.out.println(board.getWriter().getMname());
            System.out.println("-------------------------");
            
        }
    }

    @Test
    public void testPageList(){

        Sort sort = Sort.by("bno").descending();
        Pageable pageable = PageRequest.of(0,10,sort);

        Page<Object[]> result = boardRepository.getListPage(pageable);

        result.get().forEach(arr -> {

            ReBoard board = (ReBoard)arr[0];
        

            System.out.println(board.getBno());
            System.out.println(board.getTitle());
            System.out.println(board.getWriter().getMid());
            System.out.println(board.getWriter().getMname());
            System.out.println("-------------------------");
        });

    }

}