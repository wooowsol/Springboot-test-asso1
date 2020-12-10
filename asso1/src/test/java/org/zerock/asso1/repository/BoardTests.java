package org.zerock.asso1.repository;

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
import org.zerock.asso1.domain.Reply;

@SpringBootTest
public class BoardTests {
    
    @Autowired
    private ReBoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertDummyReply(){

        IntStream.rangeClosed(1,1000).forEach(i -> {

            Long bno = (long)(Math.random()* 500) +1;
            ReBoard board = ReBoard.builder().bno(bno).build();

            String mid = "u" + (int)(Math.random()* 100);
            Member member = Member.builder().mid(mid).build();

            Reply reply = Reply.builder().replyText("Reply...."+i)
                .replyer(member)
                .board(board)
                .build();

            System.out.println(replyRepository.save(reply));    


        });
    }

    @Test
    public void testGetListWithReply(){

        Pageable pageble = PageRequest.of(0,10,Sort.Direction.DESC,"bno");

        Page<Object[]> result = boardRepository.getListWithReply(pageble);

        result.stream().forEach(arr -> {

            ReBoard board = (ReBoard)arr[0];
            long count = (long)arr[1];

            System.out.println(board);
            System.out.println(count);
            System.out.println("-------------------------");

        });
    }

    @Test
    public void testGetReplyWithReBoard(){

        Pageable pageable 
        =PageRequest.of(0,10,Sort.Direction.ASC,"rno");
        Long bno = 500L;

        Page<Object[]> result = replyRepository.getReplyWithReBoard(bno, pageable);

        result.stream().forEach(arr -> {

            Reply reply = (Reply)arr[0];

            System.out.println(reply);
            System.out.println(reply.getReplyer());
            System.out.println("------------------");
        });
    }


    @Test
    public void testGetReplyWithReBoard2(){

        Pageable pageable 
        =PageRequest.of(0,10,Sort.Direction.ASC,"rno");
        Long bno = 500L;

        Page<Reply> result = replyRepository.getReplyWithReBoard2(bno, pageable);

        result.stream().forEach(reply -> {

            System.out.println(reply);
            System.out.println(reply.getBoard());
            System.out.println(reply.getReplyer());
            System.out.println("------------------");
        });
    }

}