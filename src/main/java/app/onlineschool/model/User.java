package app.onlineschool.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * User entity with all info
 */
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class User implements BaseEntity, UserDetails {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank
    private String fullName;

    @NotBlank
    private String passwordDigest;

    private int role; // 0 - student, 1 - admin

    @CreatedDate
    private LocalDate createdAt;

    private LocalDate lastLogin;

    @ManyToMany
    @JoinTable(
        name = "user_courses",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "user_course_progress", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "course_id")
    private Map<Long, CourseProgress> courseProgress = new HashMap<>();

    private String pfpLink;

    public String checkIfAdminAndRedirectTo(String redirectAdmin, String redirectUser) {
        if (this.isAdmin()) {
            return redirectAdmin;
        }
        return redirectUser;
    }

    public boolean isAdmin() {
        return this.getRole() == 1;
    }

    @Override
    public String getPassword() {
        return passwordDigest;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<GrantedAuthority>();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Embeddable
    @Getter
    @Setter
    public static class CourseProgress {
        private int completedLessons;
        private boolean isFinished;
    }
}
