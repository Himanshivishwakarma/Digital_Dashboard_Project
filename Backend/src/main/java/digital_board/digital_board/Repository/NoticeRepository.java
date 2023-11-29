package digital_board.digital_board.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import digital_board.digital_board.Entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, String> {

      // @Query("SELECT n FROM Notice n WHERE n.noticeId=noticeId")
      // Notice getNoticeById(@Param("NoticeId") String NoticeId);

      @Query("SELECT n FROM Notice n WHERE n.createdBy=:userId")
      List<Notice> getAllNoticeByUserId(@Param("userId") String userId);

      List<Notice> findByCategoryIn(List<String> category, Pageable pageable);

      List<Notice> findByDepartmentNameIn(List<String> departmentName, Pageable pageable);

      Page<Notice> findAll(Pageable pageable);

      List<Notice> findByCategoryInAndDepartmentNameIn(List<String> categories, List<String> departmentNames,
                  Pageable pageable);

      // @Query("SELECT n FROM Notice n ORDER BY n.noticeCreatedDate asc limit 1")
      // List<Notice> findNoticesWithLimit();

      // @Query(value = "SELECT * FROM digital_board.notice ORDER BY
      // notice_created_date desc LIMIT :limit", nativeQuery = true)
      @Query(value = "SELECT * FROM digital_board.notice where digital_board.notice.status=:status order by digital_board.notice.notice_created_date desc  LIMIT :limit ;", nativeQuery = true)
      List<Notice> findNoticesWithLimit(@Param("limit") int limit, @Param("status") String status);

      // ***********searching***********

      @Query("SELECT n FROM Notice n WHERE n.category IN :categories AND n.status !='disable'")
      List<Notice> findBycategoriesInAndStatusNotDisable( @Param("categories") List<String>categories);



      @Query("SELECT n FROM Notice n WHERE n.departmentName IN :department AND n.status != 'disable'")
      List<Notice> findByDepartmentAndStatusNotDisabled(@Param("department") List<String >department);
      
      @Query("SELECT n FROM Notice n WHERE n.status !='disable'")
      List<Notice> findAllNotDisabled();

    List<Notice> findByNoticeTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description);
 

}
