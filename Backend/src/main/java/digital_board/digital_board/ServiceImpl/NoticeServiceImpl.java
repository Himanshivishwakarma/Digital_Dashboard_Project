package digital_board.digital_board.ServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import digital_board.digital_board.Controller.UserController;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(NoticeServiceImpl.class);

    @Autowired
    NoticeRepository noticeRepository;

    @Autowired
    private EmailServiceImpl emailServices;

    @Autowired
    UserNotificationServiceImpl notificationServiceImpl;

    @Override
    public Notice createNoticeByUser(Notice notice) {
        LOGGER.info("Start NoticeServiceImpl: createNoticeByUser method");
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
          LOGGER.info("End NoticeServiceImpl: createNoticeByUser method");
        return saveNotice;
    }

    @Override
    public Notice getNoticeByNoticeId(String noticeId) {
                LOGGER.info("Start NoticeServiceImpl: getNoticeByNoticeId method");
                  LOGGER.info("End NoticeServiceImpl: getNoticeByNoticeId method");
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
         LOGGER.info("Start NoticeServiceImpl: getNoticeByUserEmail method");
           LOGGER.info("End NoticeServiceImpl: getNoticeByUserEmail method");
        return this.noticeRepository.getAllNoticeByUserId(email, pageable);
    }

    @Override
    public List<Notice> getAllNotice() {
         LOGGER.info("Start NoticeServiceImpl: getAllNotice method");
        List<Notice> notice = this.noticeRepository.findAll();
          LOGGER.info("End NoticeServiceImpl: getAllNotice method");
        return notice;
    }

    @Override
    public Page<Notice> getAllNoticesSorted(Pageable pageable) {
         LOGGER.info("Start NoticeServiceImpl: getAllNoticesSorted method");
           LOGGER.info("End NoticeServiceImpl: getAllNoticesSorted method");
        return noticeRepository.findAll(pageable);
        
    }

    @Override
    public Page<Notice> getNoticesByCategory(List<String> category, Pageable pageable) {
         LOGGER.info("Start NoticeServiceImpl: getNoticesByCategory method");
           LOGGER.info("End NoticeServiceImpl: getNoticesByCategory method");
        return noticeRepository.findByCategoryIn(category, pageable);
    }

    @Override
    public Page<Notice> getNoticesByDepartment(List<String> departmentName, Pageable pageable) {
         LOGGER.info("Start NoticeServiceImpl: getNoticesByCategory method");
        if (departmentName != null && departmentName.contains("All")) {
            // return getAllNoticesSorted(pageable);
             LOGGER.info("End NoticeServiceImpl: getNoticesByCategory method");
            return null;
        } else {
             LOGGER.info("End NoticeServiceImpl: getNoticesByCategory method");
            return noticeRepository.findByDepartmentNameIn(departmentName, pageable);
        }
        
    }

    @Override
    public List<Notice> filterNotices(NoticeFilterDto noticeFilterDto, Pageable pageable) {

//         List<String> category = noticeFilterDto.getCategory();
//         List<String> departmentName = noticeFilterDto.getDepartmentName();
//         if (category != null && departmentName != null) {
//             if (departmentName.contains("All")) {
//                 // return getNoticesByCategory(category, pageable);
//                 return null;
//             } else {
//                 return noticeRepository.findByCategoryInAndDepartmentNameIn(category, departmentName, pageable);
//             }
//         } else if (departmentName == null && category != null) {

//             // return getNoticesByCategory(category, pageable);
//             return null;

//         // } else if (category == null && departmentName != null) {

//             if (departmentName.contains("All")) {
//                 // return getAllNoticesSorted(pageable);
// return null;
//             } else {
//                 // return noticeRepository.findByDepartmentNameIn(departmentName, pageable);
//                 return null;
//             }
//         } else {
//             // return getAllNoticesSorted(pageable);
            return null;
        // }

    }

    @Override
    public Long getTotalNoticeCount() {
         LOGGER.info("Start NoticeServiceImpl: getTotalNoticeCount method");
           LOGGER.info("End NoticeServiceImpl: getTotalNoticeCount method");
        return noticeRepository.count();
    }

    @Override
    public Page<Notice> searchNotices(String query, Pageable pageable) {
        List<String> status=new ArrayList<>();
        status.add("enable");
         status.add("important");
        return noticeRepository.findByNoticeTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query,pageable);
    }

    @Override
    public List<Notice> getAllImportantNotice(int limit) {
         LOGGER.info("Start NoticeServiceImpl: getAllImportantNotice method");
        List<Notice> findNoticesWithLimit = noticeRepository.findNoticesWithLimit(limit, "important");

        if (findNoticesWithLimit.isEmpty()) {
            throw new ResourceNotFoundException(ResponseMessagesConstants.messagelist.stream()
                    .filter(exceptionResponse -> "LIST_IS_EMPTY".equals(exceptionResponse.getExceptonName()))
                    .map(ExceptionResponse::getMassage)
                    .findFirst()
                    .orElse("Default message if not found"));
        } else {
              LOGGER.info("End NoticeServiceImpl: getAllImportantNotice method");
            return findNoticesWithLimit;

        }

    }

    @Override
    public Notice updateNotice(Notice notice) {
         LOGGER.info("Start NoticeServiceImpl: updateNotice method");
        this.noticeRepository.findById(notice.getNoticeId())
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessagesConstants.messagelist.stream()
                        .filter(exceptionResponse -> "NOTICE_NOT_FOUND".equals(exceptionResponse.getExceptonName()))
                        .map(ExceptionResponse::getMassage)
                        .findFirst()
                        .orElse("Default message if not found")));
          LOGGER.info("End NoticeServiceImpl: updateNotice method");
        return noticeRepository.save(notice);

    }

    // searching filter

    public Map<String, Object> filterNotices(List<String> department, List<String> categories, List<String> admins,
            String status, int page, int size)
    {
         LOGGER.info("Start NoticeServiceImpl: filterNotices method");
        if (department == null && categories == null) {
            List<Notice> findAllNotDisabled = noticeRepository.findAllNotDisabled();

            if (status == null && admins == null) {
                int startIndex = page * size;
                int endIndex = Math.min(startIndex + size, findAllNotDisabled.size());
                if (startIndex > endIndex) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("data", Collections.emptyList());
                    response.put("count", findAllNotDisabled.size());
                      LOGGER.info("End NoticeServiceImpl: filterNotices method");
                    return response;
                };
                Map<String, Object> response = new HashMap<>();
                response.put("data", findAllNotDisabled.subList(startIndex, endIndex));
                response.put("count", findAllNotDisabled.size());
                  LOGGER.info("End NoticeServiceImpl: filterNotices method");
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
                          LOGGER.info("End NoticeServiceImpl: filterNotices method");
                        return response;
                    }
                     Map<String, Object> response = new HashMap<>();
                     response.put("data",findAllNotDisabled2.subList(startIndex, endIndex));
                     response.put("count",findAllNotDisabled2.size());
                       LOGGER.info("End NoticeServiceImpl: filterNotices method");
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
                          LOGGER.info("End NoticeServiceImpl: filterNotices method");
                        return response;
                    }
                      Map<String, Object> response = new HashMap<>();
                        response.put("data",findAllNotDisabled3.subList(startIndex, endIndex));
                        response.put("count",findAllNotDisabled3.size());
                          LOGGER.info("End NoticeServiceImpl: filterNotices method");
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
                 LOGGER.info("End NoticeServiceImpl: filterNotices method");
                        return response;   
                }
                 Map<String, Object> response = new HashMap<>();
                        response.put("data", finalListofData.subList(startIndex, endIndex));
                        response.put("count",finalListofData.size());
                        LOGGER.info("End NoticeServiceImpl: filterNotices method");
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
                         LOGGER.info("End NoticeServiceImpl: filterNotices method");
                         return response;   
                    }
                       Map<String, Object> response = new HashMap<>();
                        response.put("data",findAllNotDisabled2.subList(startIndex, endIndex) );
                        response.put("count",findAllNotDisabled2.size());
                        LOGGER.info("End NoticeServiceImpl: filterNotices method");
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
                         LOGGER.info("End NoticeServiceImpl: filterNotices method");
                         return response;
                    }
                       Map<String, Object> response = new HashMap<>();
                         response.put("data",findAllNotDisabled3.subList(startIndex, endIndex));
                         response.put("count",findAllNotDisabled3.size());
                         LOGGER.info("End NoticeServiceImpl: filterNotices method");
                         return response;
                }
            }
        }
    }
    public Long countByCategory(String category) {
         LOGGER.info("Start NoticeServiceImpl: countByCategory method");
           LOGGER.info("End NoticeServiceImpl: countByCategory method");
        return noticeRepository.countByCategory(category);
    }

    public Long countByDepartmentName(String departmentName) {
         LOGGER.info("Start NoticeServiceImpl: countByDepartmentName method");
           LOGGER.info("End NoticeServiceImpl: countByDepartmentName method");
        return noticeRepository.countByDepartmentName(departmentName);
    }

}