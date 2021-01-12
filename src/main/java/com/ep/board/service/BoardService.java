package com.ep.board.service;

import com.ep.board.dto.BoardDTO;
import com.ep.board.dto.PageRequestDTO;
import com.ep.board.dto.PageResultDTO;
import com.ep.board.entity.Board;
import com.ep.board.entity.Member;

public interface BoardService {

    Long register(BoardDTO dto);

    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO); // 목록 처리

    BoardDTO get(Long bno);

    void removeWithReplies(Long bno); // 삭제 기능

    default Board dtoToEntity(BoardDTO dto) {

        Member member = Member.builder().email(dto.getWriterEmail()).build();

        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();
        return board;
    }

    //BoardService 인터페이스에 추가하는 entityToDTO()
    default BoardDTO entityToDTO(Board board, Member member, Long replyCount) {

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue()) // long으로 나오므로 int로 처리하도록
                .build();

        return boardDTO;
    }

    void modify(BoardDTO boardDTO);
}