package digital_board.digital_board.Repository;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import digital_board.digital_board.Entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, String> {
        @Query("SELECT n FROM Notice n WHERE n.createdBy=:userId")
        Page<Notice> getAllNoticeByUserId(@Param("userId") String userId, Pageable pageable);

        @Query("SELECT n FROM Notice n WHERE n.category IN :category AND (:department IS NULL OR n.departmentName IN :department) AND n.status <> 'disable' AND n.status <> 'completed'")
        Page<Notice> findByCategoryInDepartmentNameInAndStatusNotDisable(List<String> category,
                        @Param("department") List<String> department, Pageable pageable);

        @Query("SELECT n FROM Notice n WHERE n.departmentName IN :departmentName AND (:categories IS NULL OR n.category IN :categories) AND n.status <> 'disable' AND n.status <> 'completed'")
        Page<Notice> findByDepartmentNameInANDcategoriesInAndStatusNotDisable(List<String> departmentName,
                        @Param("categories") List<String> categories, Pageable pageable);

        @Query("SELECT n FROM Notice n WHERE n.status <> 'disable' AND n.status <> 'completed'")
        // @Query("SELECT * FROM notice WHERE status <> 'disable' AND status <>
        // 'completed' AND COALESCE(images_url, '{}') <> '{}'")
        Page<Notice> findAll(Pageable pageable);

        @Query(value = "SELECT * FROM notice WHERE status <> 'disable' AND status <> 'completed'", nativeQuery = true)
        List<Object[]> findAllNotDisabledOrCompleted();

        List<Notice> findByCategoryInAndDepartmentNameIn(List<String> categories, List<String> departmentNames,
                        Pageable pageable);

        @Query(value = "SELECT * FROM digital_board.notice where digital_board.notice.status=:status order by digital_board.notice.notice_created_date desc  LIMIT :limit ;", nativeQuery = true)
        List<Notice> findNoticesWithLimit(@Param("limit") int limit, @Param("status") String status);

        @Query("SELECT n FROM Notice n WHERE n.category IN :categories AND n.status !='disable'")
        List<Notice> findBycategoriesInAndStatusNotDisable(@Param("categories") List<String> categories);

        @Query("SELECT n FROM Notice n WHERE n.departmentName IN :department AND n.status != 'disable'")
        List<Notice> findByDepartmentAndStatusNotDisabled(@Param("department") List<String> department);

        @Query("SELECT n FROM Notice n WHERE n.status !='disable'")
        List<Notice> findAllNotDisabled();

        @Query("SELECT n FROM Notice n WHERE " +
                        "(LOWER(n.noticeTitle) LIKE LOWER(CONCAT('%', :title, '%')) OR " +
                        "LOWER(n.description) LIKE LOWER(CONCAT('%', :description, '%'))) " +
                        "AND n.status <> 'disable' AND n.status <> 'completed'")
        Page<Notice> findByNoticeTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                        String title, String description, Pageable pageable);

        Long countByCategory(String category);

        Long countByDepartmentName(String departmentName);

        // get important notice by limit
        List<Notice> findByStatus(String status, Sort sort, PageRequest of);

        List<Notice> findByImportantTrueAndStatusIs(String status, Sort sort, PageRequest of);

        @Query("SELECT n FROM Notice n WHERE (:categories IS NULL OR n.category IN :categories) " +
                        "AND (:departmentNames IS NULL OR n.departmentName IN :departmentNames) " +
                        "AND (:createdBy IS NULL OR n.createdBy IN :createdBy) " +
                        "And n.status <> 'disable' AND n.status <> 'completed'")
        Page<Notice> findByCategoryInAndDepartmentNameInAndAndCreatedByIn(
                        @Param("categories") List<String> categories,
                        @Param("departmentNames") List<String> departmentNames,
                        @Param("createdBy") List<String> createdBy,
                        Pageable pageable);

        @Query("SELECT n FROM Notice n " +
                        "WHERE (:categories IS NULL OR n.category IN :categories) " +
                        "AND (:departmentNames IS NULL OR n.departmentName IN :departmentNames) " +
                        "AND (:createdBy IS NULL OR n.createdBy IN :createdBy) " +
                        "AND (n.important IS NULL OR n.important = true) " +
                        "And n.status <> 'disable' AND n.status <> 'completed'")
        Page<Notice> findByCategoryInAndDepartmentNameInAndStatusInAndCreatedByInAndImportant(
                        @Param("categories") List<String> categories,
                        @Param("departmentNames") List<String> departmentNames,

                        @Param("createdBy") List<String> createdBy,
                        Pageable pageable);

        // today created notice count
        @Query(value = "SELECT n FROM Notice n WHERE CAST(n.noticeCreatedDate AS date) = current_date")
        List<Notice> findByNoticeCreatedDateIsCurrentDate();
}
