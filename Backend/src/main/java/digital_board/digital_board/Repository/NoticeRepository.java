package digital_board.digital_board.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import digital_board.digital_board.Entity.Notice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface NoticeRepository extends JpaRepository<Notice, String>{

   //   @Query("SELECT n FROM Notice n WHERE n.noticeId=noticeId")
   //  Notice getNoticeById(@Param("NoticeId") String NoticeId);

   
     @Query("SELECT n FROM Notice n WHERE n.createdBy=:userId")
    List<Notice> getAllNoticeByUserId(@Param("userId") String userId);

    List<Notice> findByCategoryIn(List<String> category, Pageable pageable);
    List<Notice> findByDepartmentNameIn(List<String> departmentName,  Pageable pageable);

    Page<Notice> findAll(Pageable pageable);

    List<Notice> findByCategoryInAndDepartmentNameIn(List<String> categories, List<String> departmentNames, Pageable pageable);

    List<Notice> findByNoticeTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description);
 }

