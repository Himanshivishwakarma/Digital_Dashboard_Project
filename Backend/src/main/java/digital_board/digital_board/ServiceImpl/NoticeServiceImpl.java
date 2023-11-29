package digital_board.digital_board.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import digital_board.digital_board.Dto.NoticeFilterDto;
import digital_board.digital_board.Entity.ExceptionResponse;
import digital_board.digital_board.Entity.Notice;
import digital_board.digital_board.Entity.UserNotification;
import digital_board.digital_board.Exception.ResourceNotFoundException;
import digital_board.digital_board.Repository.NoticeRepository;
import digital_board.digital_board.Servies.NoticeService;
import digital_board.digital_board.constants.ResponseMessagesConstants;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    NoticeRepository noticeRepository;

    @Autowired
    private EmailServiceImpl emailServices;

    @Autowired
    UserNotificationServiceImpl notificationServiceImpl;

    @Override
    public Notice createNoticeByUser(Notice notice) {

        Notice saveNotice = this.noticeRepository.save(notice);
        try {

            if (saveNotice != null && saveNotice.getStatus() == "enable") {
                List<UserNotification> userNotification = this.notificationServiceImpl.getAllUserNotification();
                for (UserNotification user : userNotification) {

                    System.out.println("user Email____________________");
                    System.out.println(user.getUserEmail());
                    if (user.getDepartmentName().equals(saveNotice.getDepartmentName())
                            || "All".equals(user.getDepartmentName())) {
                        System.out.println("mail component");
                        emailServices.sendSimpleMessage(user.getUserEmail(), "New Notice", user.getUserName());
                    }
                }

            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return saveNotice;
    }

    @Override
    public Notice getNoticeByNoticeId(String NoticeId) {
        System.out.println(NoticeId);
        Notice notice = this.noticeRepository.findById(NoticeId).orElseThrow();
        return notice;
    }

    @Override
    public List<Notice> getNoticeByUserId(String UserId) {
        List<Notice> notice = this.noticeRepository.getAllNoticeByUserId(UserId);
        return notice;
        // return null;
    }

    @Override
    public List<Notice> getAllNotice() {
        List<Notice> notice = this.noticeRepository.findAll();
        return notice;
    }

    @Override
    public List<Notice> getAllNoticesSorted(Pageable pageable) {
        // return null;

        // return
        Page<Notice> noticesPage = noticeRepository.findAll(pageable);

        // Page<Notice> noticesPage =
        // noticeRepository.findByCategoryAndDepartmentName(category, departmentName,
        // pageable);
        // List<Notice> notices =
        return noticesPage.getContent();
    }

    @Override
    public List<Notice> getNoticesByCategory(List<String> category, Pageable pageable) {
        return noticeRepository.findByCategoryIn(category, pageable);
    }

    @Override
    public List<Notice> getNoticesByDepartment(List<String> departmentName, Pageable pageable) {
        // if ("All".equalsIgnoreCase(departmentName)) {
        // return getAllNoticesSorted(pageable);
        // } else {
        // return noticeRepository.findByDepartmentName(departmentName, pageable);
        // }
        if (departmentName != null && departmentName.contains("All")) {
            return getAllNoticesSorted(pageable);
        } else {
            return noticeRepository.findByDepartmentNameIn(departmentName, pageable);
        }
    }

    @Override
    public List<Notice> filterNotices(NoticeFilterDto noticeFilterDto, Pageable pageable) {

        List<String> category = noticeFilterDto.getCategory();
        List<String> departmentName = noticeFilterDto.getDepartmentName();
        if (category != null && departmentName != null) {
            if (departmentName.contains("All")) {
                return getNoticesByCategory(category, pageable);
            } else {
                return noticeRepository.findByCategoryInAndDepartmentNameIn(category, departmentName, pageable);
            }
            // return noticeRepository.findByCategoryInAndDepartmentNameIn(category,
            // departmentName, pageable);

        } else if (departmentName == null && category != null) {

            return getNoticesByCategory(category, pageable);

        } else if (category == null && departmentName != null) {

            if (departmentName.contains("All")) {
                return getAllNoticesSorted(pageable);
            } else {
                return noticeRepository.findByDepartmentNameIn(departmentName, pageable);
            }
        } else {
            return getAllNoticesSorted(pageable);
        }

    }

    @Override
    public List<Notice> getAllImportantNotice(int limit) {
        List<Notice> findNoticesWithLimit = noticeRepository.findNoticesWithLimit(limit, "important");

        if (findNoticesWithLimit.isEmpty()) {
            throw new ResourceNotFoundException(ResponseMessagesConstants.messagelist.stream()
                    .filter(exceptionResponse -> "LIST_IS_EMPTY".equals(exceptionResponse.getExceptonName()))
                    .map(ExceptionResponse::getMassage)
                    .findFirst()
                    .orElse("Default message if not found"));
        } else {
            return findNoticesWithLimit;

        }

    }

    @Override
    public Notice updateNotice(Notice notice) {

        this.noticeRepository.findById(notice.getNoticeId())
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessagesConstants.messagelist.stream()
                        .filter(exceptionResponse -> "NOTICE_NOT_FOUND".equals(exceptionResponse.getExceptonName()))
                        .map(ExceptionResponse::getMassage)
                        .findFirst()
                        .orElse("Default message if not found")));

        return noticeRepository.save(notice);

    }

    // searching filter

    public List<Notice> searchNotices(List<String> department, List<String> categories, List<String> createdByList,
            String status)

    {

        if (department == null && categories == null) {
            List<Notice> findAllNotDisabled = noticeRepository.findAllNotDisabled();

            if (status == null && createdByList == null) {
                return findAllNotDisabled;
            } 
            else 
            {
                if (status != null && createdByList != null)
                {

                    List<Notice> filteredNotices = findAllNotDisabled.stream()
                            .filter(notice -> (status != null && status.equals(notice.getStatus()))
                                    && (createdByList != null && createdByList.contains(notice.getCreatedBy())))
                            .collect(Collectors.toList());
    
                    return filteredNotices;
                }
                else
                {
                      List<Notice> filteredNotices = findAllNotDisabled.stream()
                            .filter(notice -> (status != null && status.equals(notice.getStatus()))
                                    || (createdByList != null && createdByList.contains(notice.getCreatedBy())))
                            .collect(Collectors.toList());
    
                    return filteredNotices;

                }

            }

        }
        else 
        {
            System.out.println(categories + "categories");
            List<Notice> findByCreatedByInAndStatusNotDisable = noticeRepository
                    .findBycategoriesInAndStatusNotDisable(categories);
            List<Notice> findByDepartmentAndStatusNotDisabled = noticeRepository
                    .findByDepartmentAndStatusNotDisabled(department);

            List<Notice> finalListofData = new ArrayList<>();

            finalListofData.addAll(findByCreatedByInAndStatusNotDisable);

            finalListofData.addAll(findByDepartmentAndStatusNotDisabled);

            if (status == null && createdByList == null) {
                return finalListofData;
            } 
            else 
            {
               if(status != null && createdByList != null)
               {

                   List<Notice> filteredNotices = finalListofData.stream()
                           .filter(notice -> (status != null && status.equals(notice.getStatus()))
                                  && (createdByList != null && createdByList.contains(notice.getCreatedBy())))
                           .collect(Collectors.toList());

                           return filteredNotices;
               }
               else
               {
                   List<Notice> filteredNotices = finalListofData.stream()
                           .filter(notice -> (status != null && status.equals(notice.getStatus()))
                                  || (createdByList != null && createdByList.contains(notice.getCreatedBy())))
                           .collect(Collectors.toList());

                           return filteredNotices;
               }

               
            }

        }

    }

}