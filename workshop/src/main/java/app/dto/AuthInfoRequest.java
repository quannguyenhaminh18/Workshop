package app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthInfoRequest{
        @NotBlank(message = "{username.blank}")
        @Size(min = 4, max = 50, message = "{username.size}")
        @Pattern(regexp = "^[A-Za-z0-9]+$", message = "{username.invalid}")
        private String username;

        @NotBlank(message = "{password.blank}")
        @Size(min = 4, max = 72, message = "{password.size}")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z0-9@$!%*?&]+$",
                message = "{password.invalid}"
        )
        private String password;
}