package com.sakshi.journal.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserResponse {
    private String userName;
    private List<String> roles;
}
