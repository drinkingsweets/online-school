package app.onlineschool.dto;

import com.nimbusds.jose.shaded.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Transfers params to statistics page for charts
 */
@Getter
@Setter
@ToString
public class StatisticsPage {
    private List<String> userActivityLabels;
    private List<Integer> userActivityData;
    private List<String> courseEnrollmentLabels;
    private List<Integer> courseEnrollmentData;
    private int coursesTotal;
    private int usersTotal;
    private int lessonsTotal;
}