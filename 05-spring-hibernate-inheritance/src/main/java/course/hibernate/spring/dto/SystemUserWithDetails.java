package course.hibernate.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemUserWithDetails {
        private String subsystemId;
        private String username;
        private String name;
        private String qualifications;
    }
