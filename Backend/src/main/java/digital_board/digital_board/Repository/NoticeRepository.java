package digital_board.digital_board.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import digital_board.digital_board.Entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, String>{

   //   @Query("SELECT n FROM Notice n WHERE n.noticeId=noticeId")
   //  Notice getNoticeById(@Param("NoticeId") String NoticeId);

   
     @Query("SELECT n FROM Notice n WHERE n.createdBy=:userId")
    List<Notice> getAllNoticeByUserId(@Param("userId") String userId);


 }

