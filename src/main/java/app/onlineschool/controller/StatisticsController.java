package app.onlineschool.controller;

import app.onlineschool.dto.StatisticsPage;
import app.onlineschool.repository.CourseRepository;
import app.onlineschool.repository.LessonRepository;
import app.onlineschool.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/statistics")
@Controller
public class StatisticsController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    LessonRepository lessonRepository;

    @GetMapping
    String index(Model model) {
        StatisticsPage sp = new StatisticsPage();

        // Define monthly labels (e.g. for the first 7 months)
        List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December");
        sp.setUserActivityLabels(months);

        // Collect monthly user activity data.
        List<Integer> monthlyActivityData = new ArrayList<>();
        for (int month = 1; month <= months.size(); month++) {
            monthlyActivityData.add(userRepository.countUsersByMonth(month));
        }
        sp.setUserActivityData(monthlyActivityData);

        // Use course creation data instead of enrollment data.
        sp.setCourseEnrollmentLabels(months);
        List<Integer> courseCreationData = new ArrayList<>();
        for (int month = 1; month <= months.size(); month++) {
            courseCreationData.add(courseRepository.countCoursesByMonth(month));
        }
        sp.setCourseEnrollmentData(courseCreationData);
        sp.setCoursesTotal(courseRepository.countAllCourses());
        sp.setLessonsTotal(lessonRepository.countAllLessons());
        sp.setUsersTotal(userRepository.countAllUsers());

        System.out.println(sp);
        model.addAttribute("page", sp);
        return "contents/statistics-page";
    }

}
