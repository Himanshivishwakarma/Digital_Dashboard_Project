package digital_board.digital_board.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import digital_board.digital_board.Entity.Notice;
import digital_board.digital_board.Entity.UserNotification;
import digital_board.digital_board.Repository.NoticeRepository;
import digital_board.digital_board.Servies.NoticeService;

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
        if (saveNotice != null && saveNotice.getStatus() == true) {
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
    public List<Notice> getAllNoticesSorted(Sort sort) {
        return noticeRepository.findAll(sort);
    }

    @Override
    public List<Notice> getNoticesByCategory(String category, Sort sort) {
        return noticeRepository.findByCategory(category, sort);
    }

    @Override
    public List<Notice> getNoticesByDepartment(String departmentName, Sort sort) {
        if ("All".equalsIgnoreCase(departmentName)) {
            return getAllNoticesSorted(sort);
        } else {
            return noticeRepository.findByDepartmentName(departmentName, sort);
        }
    }

}
