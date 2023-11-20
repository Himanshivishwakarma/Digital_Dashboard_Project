package digital_board.digital_board.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import digital_board.digital_board.Dto.NoticeFilterDto;
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
        try {

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

    @Override
    public List<Notice> filterNotices(NoticeFilterDto noticeFilterDto, Sort sort) {

        String category = noticeFilterDto.getCategory();
        String departmentName = noticeFilterDto.getDepartmentName();
        if (category != null && departmentName != null) {

            return noticeRepository.findByCategoryAndDepartmentName(category, departmentName, sort);

        } else if (departmentName == null && category != null) {

            return getNoticesByCategory(category, sort);

        } else if (category == null && departmentName != null) {

            if ("All".equalsIgnoreCase(departmentName)) {

                return getAllNoticesSorted(sort);

            } else {

                return noticeRepository.findByDepartmentName(departmentName, sort);
            }
        }

        return getAllNoticesSorted(sort);
    }

}
