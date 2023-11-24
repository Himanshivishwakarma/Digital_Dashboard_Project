package digital_board.digital_board.ServiceImpl;

import java.util.List;

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

            if (saveNotice != null && saveNotice.getStatus() == true) {
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
                                .filter(exceptionResponse -> "NOTICE_NOT_FOUND".equals(exceptionResponse.getExceptonName()))
                                .map(ExceptionResponse::getMassage)
                                .findFirst()
                                .orElse("Default message if not found")
                ));
    }

    @Override
    public List<Notice> getNoticeByUserId(String userId) {
        List<Notice> notices = this.noticeRepository.getAllNoticeByUserId(userId);
        if (notices.isEmpty()) {
            throw new ResourceNotFoundException(
                    ResponseMessagesConstants.messagelist.stream()
                            .filter(exceptionResponse -> "USER_NOT_FOUND".equals(exceptionResponse.getExceptonName()))
                            .map(ExceptionResponse::getMassage)
                            .findFirst()
                            .orElse("Default message if not found"));
        }
        return notices;
    }


    @Override
    public List<Notice> getAllNotice() {
        List<Notice> notice = this.noticeRepository.findAll();
        return notice;
    }


    @Override
    public List<Notice> getAllNoticesSorted(Pageable pageable) {
        Page<Notice> noticesPage = noticeRepository.findAll(pageable);
        return noticesPage.getContent();
    }

    @Override
    public List<Notice> getNoticesByCategory(List<String> category, Pageable pageable) {
        return noticeRepository.findByCategoryIn(category, pageable);
    }


    @Override
    public List<Notice> getNoticesByDepartment(List<String> departmentName, Pageable pageable) {
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
    public Long getTotalNoticeCount() {
        return noticeRepository.count();
    }


    @Override
    public List<Notice> searchNotices(String query) {
        return noticeRepository.findByNoticeTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query);
    }

}
