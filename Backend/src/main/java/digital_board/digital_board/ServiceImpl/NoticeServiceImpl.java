package digital_board.digital_board.ServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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
                    if (user.getDepartmentName().equals(saveNotice.getDepartmentName())
                            || "All".equals(user.getDepartmentName())) {
                        emailServices.sendSimpleMessage(user.getUserEmail(), "New Notice", user.getUserName());
                    }
                }

            }
        } catch (Exception e) {

        }
        return saveNotice;
    }

    @Override
    public Notice getNoticeByNoticeId(String noticeId) {
        return this.noticeRepository.findById(noticeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ResponseMessagesConstants.messagelist.stream()
                                .filter(exceptionResponse -> "NOTICE_NOT_FOUND"
                                        .equals(exceptionResponse.getExceptonName()))
                                .map(ExceptionResponse::getMassage)
                                .findFirst()
                                .orElse("Default message if not found")));
    }

    @Override
    public Page<Notice> getNoticeByUserEmail(String email,Pageable pageable) {
        return this.noticeRepository.getAllNoticeByUserId(email, pageable);
    }

    @Override
    public List<Notice> getAllNotice() {
        List<Notice> notice = this.noticeRepository.findAll();
        return notice;
    }

    @Override
    public Page<Notice> getAllNoticesSorted(Pageable pageable) {
        return noticeRepository.findAll(pageable);
        
    }

    @Override
    public Page<Notice> getNoticesByCategory(List<String> category, Pageable pageable) {
        return noticeRepository.findByCategoryIn(category, pageable);
    }

    @Override
    public Page<Notice> getNoticesByDepartment(List<String> departmentName, Pageable pageable) {
        if (departmentName != null && departmentName.contains("All")) {
            return getAllNoticesSorted(pageable);
        } else {
            return noticeRepository.findByDepartmentNameIn(departmentName, pageable);
        }
    }

    @Override
    public List<Notice> filterNotices(NoticeFilterDto noticeFilterDto, Pageable pageable) {

        // List<String> category = noticeFilterDto.getCategory();
        // List<String> departmentName = noticeFilterDto.getDepartmentName();
        // if (category != null && departmentName != null) {
        //     if (departmentName.contains("All")) {
        //         return getNoticesByCategory(category, pageable);
        //     } else {
        //         return noticeRepository.findByCategoryInAndDepartmentNameIn(category, departmentName, pageable);
        //     }
        // } else if (departmentName == null && category != null) {

        //     return getNoticesByCategory(category, pageable);

        // } else if (category == null && departmentName != null) {

        //     if (departmentName.contains("All")) {
        //         return getAllNoticesSorted(pageable);
        //     } else {
        //         return noticeRepository.findByDepartmentNameIn(departmentName, pageable);
        //     }
        // } else {
        //     return getAllNoticesSorted(pageable);
        // }
return null;
    }

    @Override
    public Long getTotalNoticeCount() {
        return noticeRepository.count();
    }

    @Override
    public Page<Notice> searchNotices(String query, Pageable pageable) {
        return noticeRepository.findByNoticeTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query,
                pageable);
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

    public Map<String, Object> filterNotices(List<String> department, List<String> categories, List<String> admins,
            String status, int page, int size)
    {
        if (department == null && categories == null) {
            List<Notice> findAllNotDisabled = noticeRepository.findAllNotDisabled();

            if (status == null && admins == null) {
                int startIndex = page * size;
                int endIndex = Math.min(startIndex + size, findAllNotDisabled.size());
                if (startIndex > endIndex) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("data", Collections.emptyList());
                    response.put("count", findAllNotDisabled.size());
                    return response;
                };
                Map<String, Object> response = new HashMap<>();
                response.put("data", findAllNotDisabled.subList(startIndex, endIndex));
                response.put("count", findAllNotDisabled.size());
                return response;

            } else {
                if (status != null && admins != null) {

                    List<Notice> findAllNotDisabled2 = findAllNotDisabled.stream()
                            .filter(notice -> (status != null && status.equals(notice.getStatus()))
                                    && (admins != null && admins.contains(notice.getCreatedBy())))
                            .collect(Collectors.toList());
                    int startIndex = page * size;
                    int endIndex = Math.min(startIndex + size, findAllNotDisabled2.size());
                    if (startIndex > endIndex) 
                    {
                        Map<String, Object> response = new HashMap<>();
                        response.put("data", Collections.emptyList());
                        response.put("count",findAllNotDisabled2.size());
                        return response;
                    }
                     Map<String, Object> response = new HashMap<>();
                     response.put("data",findAllNotDisabled2.subList(startIndex, endIndex));
                     response.put("count",findAllNotDisabled2.size());
                     return response;

                } 
                else
                {
                    List<Notice> findAllNotDisabled3 = findAllNotDisabled.stream()
                            .filter(notice -> (status != null && status.equals(notice.getStatus()))
                                    || (admins != null && admins.contains(notice.getCreatedBy())))
                            .collect(Collectors.toList());

                    int startIndex = page * size;
                    int endIndex = Math.min(startIndex + size, findAllNotDisabled3.size());
                    if (startIndex > endIndex) {
                         Map<String, Object> response = new HashMap<>();
                        response.put("data", Collections.emptyList());
                        response.put("count",findAllNotDisabled3.size());
                        return response;
                    }
                      Map<String, Object> response = new HashMap<>();
                        response.put("data",findAllNotDisabled3.subList(startIndex, endIndex));
                        response.put("count",findAllNotDisabled3.size());
                        return response;
                }
            }
        } 
        else 
        {
            List<Notice> findByCreatedByInAndStatusNotDisable = noticeRepository
                    .findBycategoriesInAndStatusNotDisable(categories);

            List<Notice> findByDepartmentAndStatusNotDisabled = noticeRepository
                    .findByDepartmentAndStatusNotDisabled(department);
            List<Notice> finalListofData = new ArrayList<>();
            finalListofData.addAll(findByCreatedByInAndStatusNotDisable);
            finalListofData.addAll(findByDepartmentAndStatusNotDisabled);
            if (status == null && admins == null) {
                int startIndex = page * size;
                int endIndex = Math.min(startIndex + size, finalListofData.size());
                if (startIndex > endIndex) {
                      Map<String, Object> response = new HashMap<>();
                        response.put("data", Collections.emptyList());
                         response.put("count",finalListofData.size());
                        return response;   
                }
                 Map<String, Object> response = new HashMap<>();
                        response.put("data", finalListofData.subList(startIndex, endIndex));
                        response.put("count",finalListofData.size());
                        return response;
            }
             else
              {
                if (status != null && admins != null) {
                    List<Notice> findAllNotDisabled2 = finalListofData.stream()
                            .filter(notice -> (status != null && status.equals(notice.getStatus()))
                                    && (admins != null && admins.contains(notice.getCreatedBy())))
                            .collect(Collectors.toList());
                    int startIndex = page * size;
                    int endIndex = Math.min(startIndex + size, findAllNotDisabled2.size());
                    if (startIndex > endIndex) {
                           Map<String, Object> response = new HashMap<>();
                         response.put("data", Collections.emptyList());
                         response.put("count",findAllNotDisabled2.size());
                         return response;   
                    }
                       Map<String, Object> response = new HashMap<>();
                        response.put("data",findAllNotDisabled2.subList(startIndex, endIndex) );
                        response.put("count",findAllNotDisabled2.size());
                        return response;

                } 
                else
                 {
                    List<Notice> findAllNotDisabled3 = finalListofData.stream()
                            .filter(notice -> (status != null && status.equals(notice.getStatus()))
                                    || (admins != null && admins.contains(notice.getCreatedBy())))
                            .collect(Collectors.toList());
                    int startIndex = page * size;
                    int endIndex = Math.min(startIndex + size, findAllNotDisabled3.size());
                    if (startIndex > endIndex) {
                         Map<String, Object> response = new HashMap<>();
                         response.put("data", Collections.emptyList());
                         response.put("count",findAllNotDisabled3.size());
                         return response;
                    }
                       Map<String, Object> response = new HashMap<>();
                         response.put("data",findAllNotDisabled3.subList(startIndex, endIndex));
                         response.put("count",findAllNotDisabled3.size());
                         return response;
                }
            }
        }
    }
    public Long countByCategory(String category) {
        return noticeRepository.countByCategory(category);
    }

    public Long countByDepartmentName(String departmentName) {
        return noticeRepository.countByDepartmentName(departmentName);
    }

}