package app.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthInfoRequest {
    @Size(min = 4, max = 50, message = "{username.size}")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "{username.invalid}")
    private final String username;

    @Size(min = 4, max = 72, message = "{password.size}")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z0-9@$!%*?&]+$",
            message = "{password.invalid}"
    )
    private final String password;
}